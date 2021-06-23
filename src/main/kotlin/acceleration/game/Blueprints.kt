package acceleration.game

import acceleration.Acceleration
import acceleration.type.modularunit.Blueprint
import acceleration.type.modularunit.MUModule
import arc.files.Fi
import arc.struct.Seq
import arc.util.Log
import arc.util.io.Reads
import arc.util.io.Writes
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
        return read(Reads(DataInputStream(file.read())))
    }

    open fun read(read: Reads): Blueprint {
        val blueprint = Blueprint()

        val arrayEmpty = read.bool()
        val serialized = read.str()
        blueprint.name = read.str()

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
        write(Writes(DataOutputStream(file.write())), blueprint)
    }

    open fun write(write: Writes, blueprint: Blueprint) {
        fun MUModule.serialize(): String {
            return "$internalName,$level"
        }

        write.bool(blueprint.modules.isNotEmpty())
        write.str(blueprint.modules.joinToString(", ", transform = MUModule::serialize))
        write.str(blueprint.name)
    }
}