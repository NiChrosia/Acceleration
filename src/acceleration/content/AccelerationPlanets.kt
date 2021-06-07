package acceleration.content

import acceleration.maps.generators.CustomPlanetGenerator
import arc.func.Prov
import arc.graphics.Color
import arc.struct.Seq
import mindustry.content.Blocks
import mindustry.content.Planets
import mindustry.ctype.ContentList
import mindustry.type.Planet
import mindustry.graphics.g3d.HexMesh

class AccelerationPlanets : ContentList {
    override fun load() {
        cryogenia = object : Planet("cryogenia", Planets.sun, 3, 1f) {
            init {
                generator = CustomPlanetGenerator().apply {
                        arr = arrayOf(
                            arrayOf(
                                Blocks.water,
                                Blocks.darksandWater,
                                Blocks.darksand,
                                Blocks.darksand,
                                Blocks.darksand,
                                Blocks.darksand,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.deepwater,
                                Blocks.stone,
                                Blocks.stone
                            ),
                            arrayOf(
                                Blocks.water,
                                Blocks.darksandWater,
                                Blocks.darksand,
                                Blocks.darksand,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.deepwater,
                                Blocks.stone,
                                Blocks.stone,
                                Blocks.stone
                            ),
                            arrayOf(
                                Blocks.water,
                                Blocks.darksandWater,
                                Blocks.darksand,
                                Blocks.sand,
                                Blocks.salt,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.deepwater,
                                Blocks.stone,
                                Blocks.stone,
                                Blocks.stone
                            ),
                            arrayOf(
                                Blocks.water,
                                Blocks.sandWater,
                                Blocks.sand,
                                Blocks.salt,
                                Blocks.salt,
                                Blocks.salt,
                                Blocks.sand,
                                Blocks.stone,
                                Blocks.stone,
                                Blocks.stone,
                                Blocks.snow,
                                Blocks.iceSnow,
                                Blocks.ice
                            ),
                            arrayOf(
                                Blocks.deepwater,
                                Blocks.water,
                                Blocks.sandWater,
                                Blocks.sand,
                                Blocks.salt,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.basalt,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.ice
                            ),
                            arrayOf(
                                Blocks.deepwater,
                                Blocks.water,
                                Blocks.sandWater,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.ice,
                                Blocks.iceSnow,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.snow,
                                Blocks.ice
                            ),
                            arrayOf(
                                Blocks.deepwater,
                                Blocks.sandWater,
                                Blocks.sand,
                                Blocks.sand,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.snow,
                                Blocks.basalt,
                                Blocks.basalt,
                                Blocks.basalt,
                                Blocks.ice,
                                Blocks.snow,
                                Blocks.ice
                            ),
                            arrayOf(
                                Blocks.taintedWater,
                                Blocks.deepwater,
                                Blocks.darksand,
                                Blocks.darksand,
                                Blocks.basalt,
                                Blocks.ice,
                                Blocks.basalt,
                                Blocks.hotrock,
                                Blocks.basalt,
                                Blocks.ice,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.ice
                            ),
                            arrayOf(
                                Blocks.darksandWater,
                                Blocks.darksand,
                                Blocks.darksand,
                                Blocks.darksand,
                                Blocks.ice,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.basalt,
                                Blocks.basalt,
                                Blocks.ice,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.ice
                            ),
                            arrayOf(
                                Blocks.darksandWater,
                                Blocks.darksand,
                                Blocks.darksand,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.ice
                            ),
                            arrayOf(
                                Blocks.taintedWater,
                                Blocks.deepwater,
                                Blocks.darksand,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.ice
                            ),
                            arrayOf(
                                Blocks.deepwater,
                                Blocks.deepwater,
                                Blocks.darksand,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.snow,
                                Blocks.iceSnow,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.ice
                            ),
                            arrayOf(
                                Blocks.darksandWater,
                                Blocks.darksand,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.iceSnow,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.snow,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.ice,
                                Blocks.ice
                            )
                        )

                        alwaysNoEnemyBase = Seq.with(
                            117,
                            120
                        )
                }

                atmosphereColor = Color.valueOf("55b7e0")
                atmosphereRadIn = 0.003f
                atmosphereRadOut = 0.45f
                bloom = false
                meshLoader = Prov { HexMesh(this, 6) }
                startSector = 5
            }
        }
    }

    companion object {
        lateinit var cryogenia : Planet
    }
}