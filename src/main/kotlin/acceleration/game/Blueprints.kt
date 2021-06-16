package acceleration.game

import acceleration.Acceleration
import acceleration.type.modularunit.Blueprint
import arc.Core
import arc.files.Fi
import arc.struct.Seq
import arc.util.Log
import arc.util.serialization.JsonReader
import arc.util.serialization.SerializationException

open class Blueprints {
    open val directory: Fi = Fi(Core.files.localStoragePath).child("blueprints/")
    open val all = Seq<Blueprint>()

    init {
        this.load()
    }

    open fun addBlueprint(blueprint: Blueprint) {
        all.add(blueprint)
    }

    open fun load() {
        if (directory.isDirectory) {
            directory.list().forEach { file ->
                if (file.isDirectory || !file.name().endsWith("mblu")) return@forEach

                try {
                    val blueprintJson = JsonReader().parse(file.readString())
                    val blueprint = Blueprint()

                    blueprintJson.get("modules").JsonIterator().forEach {
                        val module = Acceleration.MUModules.get(it.name)
                        if (module != null) blueprint.add(module)
                    }

                    all.add(blueprint)
                } catch(e: SerializationException) {
                    Log.err("Error reading blueprint file ${file.absolutePath()}")
                }
            }
        }
    }

    open fun save() {
        directory.emptyDirectory()

        all.each { bp ->
            val moduleStrings = mutableListOf<String>()
            bp.modules.toList().forEach {
                moduleStrings += "\"${it.internalName}\""
            }

            directory.child("${bp.name}-${bp.hashCode()}.mblu").writeString("""{
                "modules": [
                    ${moduleStrings.joinToString(", \n")}
                ]
            }""".trimIndent())
        }
    }
}