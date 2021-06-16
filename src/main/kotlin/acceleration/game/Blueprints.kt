package acceleration.game

import acceleration.Acceleration
import acceleration.type.modularunit.Blueprint
import acceleration.type.modularunit.MUModule
import arc.files.Fi
import arc.struct.Seq
import arc.util.Log
import mindustry.Vars
import java.io.*
import kotlin.math.abs

open class Blueprints {
    open val directory: Fi = Vars.dataDirectory.child("blueprints/")
    open val all = Seq<Blueprint>()

    init {
        this.load()
    }

    open fun addBlueprint(blueprint: Blueprint) {
        all.add(blueprint)
    }

    open fun load() {
        directory.list().forEach { file ->
            try {
                val blueprint = read(file)
                all.add(blueprint)
            } catch(e: IOException) {
                Log.err("Failed to load blueprint file ${file.name()} with error ${e.stackTraceToString()}")
            }
        }
    }

    open fun save() {
        directory.emptyDirectory()

        all.each { bp ->
            val blueprintFile = directory.child("${bp.name}-${abs(bp.hashCode())}.mblu")
            write(blueprintFile, bp)
        }
    }

    open fun read(file: Fi): Blueprint {
        return read(file.read())
    }

    open fun read(read: InputStream): Blueprint {
        val blueprint = Blueprint()
        val r = DataInputStream(read)

        val arrayEmpty = r.readBoolean()
        val serialized = r.readUTF()
        blueprint.name = r.readUTF()

        if (arrayEmpty) {
            serialized.split(", ").forEach {
                val (name, level) = it.split(",")

                Acceleration.MUModules.get(name)?.let { existingModule ->
                    blueprint.modules.add(existingModule.copy(name = name, level = level.toInt()))
                }
            }
        }

        return blueprint
    }

    open fun write(file: Fi, blueprint: Blueprint) {
        write(file.write(), blueprint)
    }

    open fun write(write: OutputStream, blueprint: Blueprint) {
        fun MUModule.serialize(): String {
            return "$internalName,$level"
        }

        val w = DataOutputStream(write)

        w.writeBoolean(blueprint.modules.isNotEmpty())
        w.writeUTF(blueprint.modules.joinToString(", ", transform = MUModule::serialize))
        w.writeUTF(blueprint.name)
    }
}