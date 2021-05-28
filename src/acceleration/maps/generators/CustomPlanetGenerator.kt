package acceleration.maps.generators

import arc.graphics.Color
import arc.math.Angles
import arc.math.Mathf
import arc.math.Rand
import arc.math.geom.Geometry
import arc.math.geom.Point2
import arc.math.geom.Vec3
import arc.struct.FloatSeq
import arc.struct.ObjectMap
import arc.struct.ObjectSet
import arc.struct.Seq
import arc.util.Tmp
import arc.util.noise.Noise
import arc.util.noise.RidgedPerlin
import mindustry.Vars
import mindustry.ai.Astar
import mindustry.ai.BaseRegistry
import mindustry.content.Blocks
import mindustry.game.Schematics
import mindustry.game.Team
import mindustry.game.Waves
import mindustry.maps.generators.BaseGenerator
import mindustry.maps.generators.PlanetGenerator
import mindustry.type.Sector
import mindustry.world.Block
import mindustry.world.Tile
import mindustry.world.TileGen
import mindustry.world.Tiles
import kotlin.math.abs
import kotlin.math.max

open class CustomPlanetGenerator : PlanetGenerator() {
    var arr = arrayOf(
        arrayOf(Blocks.deepwater, Blocks.water, Blocks.water, Blocks.snow, Blocks.ice, Blocks.darksand, Blocks.darksand, Blocks.basalt, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.ice),
        arrayOf(Blocks.deepwater, Blocks.water, Blocks.water, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.snow, Blocks.ice),
        arrayOf(Blocks.deepwater, Blocks.water, Blocks.darksand, Blocks.darksand, Blocks.snow, Blocks.snow, Blocks.darksand, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.ice, Blocks.darksand, Blocks.ice),
        arrayOf(Blocks.deepwater, Blocks.water, Blocks.snow, Blocks.snow, Blocks.basalt, Blocks.snow, Blocks.basalt, Blocks.hotrock, Blocks.basalt, Blocks.ice, Blocks.snow, Blocks.ice, Blocks.ice),
        arrayOf(Blocks.water, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.tar, Blocks.snow, Blocks.basalt, Blocks.basalt, Blocks.ice, Blocks.snow, Blocks.ice, Blocks.ice),
        arrayOf(Blocks.deepwater, Blocks.water, Blocks.water, Blocks.snow, Blocks.ice, Blocks.darksand, Blocks.darksand, Blocks.basalt, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.ice),
        arrayOf(Blocks.deepwater, Blocks.water, Blocks.water, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.snow, Blocks.ice),
        arrayOf(Blocks.deepwater, Blocks.water, Blocks.darksand, Blocks.darksand, Blocks.snow, Blocks.snow, Blocks.darksand, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.ice, Blocks.darksand, Blocks.ice),
        arrayOf(Blocks.deepwater, Blocks.water, Blocks.snow, Blocks.snow, Blocks.basalt, Blocks.snow, Blocks.basalt, Blocks.hotrock, Blocks.basalt, Blocks.ice, Blocks.snow, Blocks.ice, Blocks.ice),
        arrayOf(Blocks.water, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.tar, Blocks.snow, Blocks.basalt, Blocks.basalt, Blocks.ice, Blocks.snow, Blocks.ice, Blocks.ice)
    )
    var rid = RidgedPerlin(1, 2)
    var basegen = BaseGenerator()
    var water: Float = 2f / arr[0].size
    var scl = 5f
    var waterOffset = 0.07f
    var tars: ObjectMap<Block, Block> = ObjectMap.of(
        Blocks.sporeMoss, Blocks.shale,
        Blocks.moss, Blocks.shale
    )
    var alwaysEnemyBase = Seq<Int>()
    var alwaysNoEnemyBase = Seq<Int>()

    private fun rawHeight(position: Vec3): Float {
        var pos = position
        pos = Tmp.v33.set(pos).scl(scl)
        return (Mathf.pow(
            noise.octaveNoise3D(
                7.0, 0.5, (1f / 3f).toDouble(), pos.x.toDouble(),
                pos.y.toDouble(), pos.z.toDouble()
            ).toFloat(), 2.3f
        ) + waterOffset) / (1f + waterOffset)
    }

    override fun generateSector(sector: Sector) {
        val tile = sector.tile
        var any = false
        val poles = abs(tile.v.y)
        val noise = Noise.snoise3(tile.v.x, tile.v.y, tile.v.z, 0.001f, 0.58f)
        if (noise + poles / 7.1 > 0.12 && poles > 0.23) {
            any = true
        }
        if (noise < 0.16) {
            for (other in tile.tiles) {
                val osec = sector.planet.getSector(other)

                //no sectors near start sector!
                if (osec.id == sector.planet.startSector || //near starting sector
                    osec.generateEnemyBase && poles < 0.85 ||  //near other base
                    sector.preset != null && noise < 0.11 //near preset
                ) {
                    return
                }
            }
        }

        sector.generateEnemyBase = any

        alwaysEnemyBase.each { e ->
            if (sector.id == e) sector.generateEnemyBase = true
        }

        alwaysNoEnemyBase.each { e ->
            if (sector.id == e) sector.generateEnemyBase = false
        }
    }

    override fun getHeight(position: Vec3): Float {
        val height = rawHeight(position)
        return max(height, water)
    }

    override fun getColor(position: Vec3): Color? {
        val block = getBlock(position)
        //replace salt with sand color
        return if (block === Blocks.salt) Blocks.sand.mapColor else Tmp.c1.set(block.mapColor)
            .a(1f - block.albedo)
    }

    override fun genTile(position: Vec3, tile: TileGen) {
        tile.floor = getBlock(position)
        tile.block = tile.floor.asFloor().wall
        if (rid.getValue(position.x.toDouble(), position.y.toDouble(), position.z.toDouble(), 22f) > 0.32) {
            tile.block = Blocks.air
        }
    }

    private fun getBlock(position: Vec3): Block {
        var pos = position
        var height = rawHeight(pos)
        Tmp.v31.set(pos)
        pos = Tmp.v33.set(pos).scl(scl)
        val rad = scl
        var temp = Mathf.clamp(abs(pos.y * 2f) / rad)
        val tnoise = noise.octaveNoise3D(
            7.0,
            0.56,
            (1f / 3f).toDouble(),
            pos.x.toDouble(),
            (pos.y + 999f).toDouble(),
            pos.z.toDouble()
        ).toFloat()
        temp = Mathf.lerp(temp, tnoise, 0.5f)
        height *= 1.2f
        height = Mathf.clamp(height)
        val tar = noise.octaveNoise3D(
            4.0,
            0.55,
            (1f / 2f).toDouble(),
            pos.x.toDouble(),
            (pos.y + 999f).toDouble(),
            pos.z.toDouble()
        ).toFloat() * 0.3f + Tmp.v31.dst(0f, 0f, 1f) * 0.2f
        val res = arr[Mathf.clamp(
            (temp * arr.size).toInt(),
            0,
            arr[0].size - 1
        )][Mathf.clamp((height * arr[0].size).toInt(), 0, arr[0].size - 1)]
        return if (tar > 0.5f) {
            tars.get(res, res)
        } else {
            res
        }
    }

    override fun noise(
        x: Float,
        y: Float,
        octaves: Double,
        falloff: Double,
        scl: Double,
        mag: Double
    ): Float {
        val v = sector.rect.project(x, y).scl(5f)
        return noise.octaveNoise3D(
            octaves,
            falloff,
            1f / scl,
            v.x.toDouble(),
            v.y.toDouble(),
            v.z.toDouble()
        ).toFloat() * mag.toFloat()
    }

    override fun generate() {
        class Room(var x: Int, var y: Int, var radius: Int) {
            var connected = ObjectSet<Room>()
            fun connect(to: Room) {
                if (connected.contains(to)) return
                connected.add(to)
                val nscl = Mathf.rand.random(20f, 60f)
                val stroke = Mathf.rand.random(4, 12)
                brush(pathfind(
                    x,
                    y, to.x, to.y, { tile ->
                        (if (tile.solid()) 5f else 0f) + noise(
                            tile.x.toFloat(),
                            tile.y.toFloat(), 1.0, 1.0, (1f / nscl).toDouble()
                        ) * 60
                    }, Astar.manhattan
                ), stroke
                )
            }

            init {
                connected.add(this)
            }
        }
        cells(4)
        distort(10f, 12f)
        val constraint = 1.3f
        val radius = width / 2f / Mathf.sqrt3
        val rooms = Mathf.rand.random(2, 5)
        val roomseq = Seq<Room>()
        for (i in 0 until rooms) {
            Tmp.v1.trns(Mathf.rand.random(360f), Mathf.rand.random(radius / constraint))
            val rx = width / 2f + Tmp.v1.x
            val ry = height / 2f + Tmp.v1.y
            val maxrad = radius - Tmp.v1.len()
            val rrad = Mathf.rand.random(9f, maxrad / 2f).coerceAtMost(30f)
            roomseq.add(Room(rx.toInt(), ry.toInt(), rrad.toInt()))
        }

        //check positions on the map to place the player spawn. this needs to be in the corner of the map
        var spawn: Room? = null
        val enemies = Seq<Room>()
        val enemySpawns = Mathf.rand.random(1, max((sector.threat * 4).toInt(), 1))
        val offset = Mathf.rand.nextInt(360)
        val length = width / 2.55f - Mathf.rand.random(13, 23)
        val angleStep = 5
        val waterCheckRad = 5
        run {
            var i = 0
            while (i < 360) {
                val angle = offset + i
                val cx = (width / 2 + Angles.trnsx(angle.toFloat(), length)).toInt()
                val cy = (height / 2 + Angles.trnsy(angle.toFloat(), length)).toInt()
                var waterTiles = 0

                //check for water presence
                for (rx in -waterCheckRad..waterCheckRad) {
                    for (ry in -waterCheckRad..waterCheckRad) {
                        val tile = tiles[cx + rx, cy + ry]
                        if (tile == null || tile.floor().liquidDrop != null) {
                            waterTiles++
                        }
                    }
                }
                if (waterTiles <= 4 || i + angleStep >= 360) {
                    roomseq.add(Room(cx, cy, Mathf.rand.random(8, 15)).also { spawn = it })
                    for (j in 0 until enemySpawns) {
                        val enemyOffset = Mathf.rand.range(60f)
                        Tmp.v1.set((cx - width / 2).toFloat(), (cy - height / 2).toFloat())
                            .rotate(180f + enemyOffset)
                            .add(width / 2f, height / 2f)
                        val espawn = Room(Tmp.v1.x.toInt(), Tmp.v1.y.toInt(), Mathf.rand.random(8, 15))
                        roomseq.add(espawn)
                        enemies.add(espawn)
                    }
                    break
                }
                i += angleStep
            }
        }
        for (room in roomseq) {
            erase(room.x, room.y, room.radius)
        }
        val connections = Mathf.rand.random((rooms - 1).coerceAtLeast(1), rooms + 3)
        for (i in 0 until connections) {
            roomseq.random(Mathf.rand).connect(roomseq.random(Mathf.rand))
        }
        for (room in roomseq) {
            spawn!!.connect(room)
        }
        cells(1)
        distort(10f, 6f)
        inverseFloodFill(tiles.getn(spawn!!.x, spawn!!.y))
        val ores = Seq.with(Blocks.oreCopper, Blocks.oreLead)
        val poles = abs(sector.tile.v.y)
        val nmag = 0.5f
        val scl = 1f
        val addscl = 1.3f
        if (noise.octaveNoise3D(
                2.0, 0.5, scl.toDouble(), sector.tile.v.x.toDouble(), sector.tile.v.y.toDouble(),
                sector.tile.v.z.toDouble()
            ) * nmag + poles > 0.25f * addscl
        ) {
            ores.add(Blocks.oreCoal)
        }
        if (noise.octaveNoise3D(
                2.0, 0.5, scl.toDouble(), (sector.tile.v.x + 1).toDouble(), sector.tile.v.y.toDouble(),
                sector.tile.v.z.toDouble()
            ) * nmag + poles > 0.5f * addscl
        ) {
            ores.add(Blocks.oreTitanium)
        }
        if (noise.octaveNoise3D(
                2.0, 0.5, scl.toDouble(), (sector.tile.v.x + 2).toDouble(), sector.tile.v.y.toDouble(),
                sector.tile.v.z.toDouble()
            ) * nmag + poles > 0.7f * addscl
        ) {
            ores.add(Blocks.oreThorium)
        }
        if (Mathf.rand.chance(0.25)) {
            ores.add(Blocks.oreScrap)
        }
        val frequencies = FloatSeq()
        for (i in 0 until ores.size) {
            frequencies.add(Mathf.rand.random(-0.1f, 0.01f) - i * 0.01f + poles * 0.04f)
        }
        pass { x, y ->
            if (!floor.asFloor().hasSurface()) return@pass
            val offsetX = x - 4
            val offsetY = y + 23
            for (i in ores.size - 1 downTo 0) {
                val entry = ores[i]
                val freq = frequencies[i]
                if (abs(
                        0.5f - noise(
                            offsetX.toFloat(),
                            (offsetY + i * 999).toFloat(),
                            2.0,
                            0.7,
                            (40 + i * 2).toDouble()
                        )
                    ) > 0.22f + i * 0.01 &&
                    abs(
                        0.5f - noise(
                            offsetX.toFloat(),
                            (offsetY - i * 999).toFloat(),
                            1.0,
                            1.0,
                            (30 + i * 4).toDouble()
                        )
                    ) > 0.37f + freq
                ) {
                    ore = entry
                    break
                }
            }
            if (ore === Blocks.oreScrap && Mathf.rand.chance(0.33)) {
                floor = Blocks.metalFloorDamaged
            }
        }
        trimDark()
        median(2)
        tech()
        pass { x, y ->
            //random moss
            if (floor === Blocks.sporeMoss) {
                if (abs(0.5f - noise((x - 90).toFloat(), y.toFloat(), 4.0, 0.8, 65.0)) > 0.02) {
                    floor = Blocks.moss
                }
            }

            //tar
            if (floor === Blocks.darksand) {
                if (abs(
                        0.5f - noise(
                            (x - 40).toFloat(),
                            y.toFloat(),
                            2.0,
                            0.7,
                            80.0
                        )
                    ) > 0.25f && abs(
                        0.5f - noise(
                            x.toFloat(),
                            (y + sector.id * 10).toFloat(), 1.0, 1.0, 60.0
                        )
                    ) > 0.41f && !(roomseq.contains { r: Room ->
                        Mathf.within(
                            x.toFloat(), y.toFloat(), r.x.toFloat(), r.y.toFloat(), 15f
                        )
                    })
                ) {
                    floor = Blocks.tar
                    ore = Blocks.air
                }
            }

            //hotrock tweaks
            if (floor === Blocks.hotrock) {
                if (abs(0.5f - noise((x - 90).toFloat(), y.toFloat(), 4.0, 0.8, 80.0)) > 0.035) {
                    floor = Blocks.basalt
                } else {
                    ore = Blocks.air
                    var all = true
                    for (p: Point2 in Geometry.d4) {
                        val other = tiles[x + p.x, y + p.y]
                        if (other == null || (other.floor() !== Blocks.hotrock && other.floor() !== Blocks.magmarock)) {
                            all = false
                        }
                    }
                    if (all) {
                        floor = Blocks.magmarock
                    }
                }
            } else if ((floor !== Blocks.basalt) && (floor !== Blocks.ice) && floor.asFloor()
                    .hasSurface()
            ) {
                val noise: Float = noise((x + 782).toFloat(), y.toFloat(), 5.0, 0.75, 260.0, 1.0)
                if (noise > 0.67f && !roomseq.contains { e: Room ->
                        Mathf.within(
                            x.toFloat(),
                            y.toFloat(), e.x.toFloat(), e.y.toFloat(), 14f
                        )
                    }) {
                    floor = if (noise > 0.72f) {
                        if (noise > 0.78f) Blocks.taintedWater else (if (floor === Blocks.sand) Blocks.sandWater else Blocks.darksandTaintedWater)
                    } else {
                        (if (floor === Blocks.sand) floor else Blocks.darksand)
                    }
                    ore = Blocks.air
                }
            }
            if (Mathf.rand.chance(0.0075)) {
                //random trees
                var any = false
                var all = true
                for (p: Point2 in Geometry.d4) {
                    val other = tiles[x + p.x, y + p.y]
                    if (other != null && other.block() === Blocks.air) {
                        any = true
                    } else {
                        all = false
                    }
                }
                if (any && ((block === Blocks.snowWall || block === Blocks.iceWall) || (all && (block === Blocks.air) && (floor === Blocks.snow) && Mathf.rand.chance(
                        0.03
                    )))
                ) {
                    block = Blocks.whiteTreeDead
                }
            }

            /*random stuff
            dec@{
                for (i in 0..3) {
                    val near = world.tile(x + Geometry.d4[i].x, y + Geometry.d4[i].y)
                    if (near != null && near.block() !== Blocks.air) {
                        break@dec
                    }
                }
                if (Mathf.rand.chance(0.01) && floor.asFloor().hasSurface() && (block === Blocks.air)) {
                    block = dec[floor, floor.asFloor().decoration]
                }
            }*/
        }
        val difficulty = sector.threat
        ints.clear()
        ints.ensureCapacity(width * height / 4)
        val ruinCount = Mathf.rand.random(-2, 4)
        if (ruinCount > 0) {
            val padding = 25

            //create list of potential positions
            for (x in padding until width - padding) {
                for (y in padding until height - padding) {
                    val tile = tiles.getn(x, y)
                    if (!tile.solid() && (tile.drop() != null || tile.floor().liquidDrop != null)) {
                        ints.add(tile.pos())
                    }
                }
            }
            ints.shuffle(Mathf.rand)
            var placed = 0
            val diffRange = 0.4f
            //try each position
            var i = 0
            while (i < ints.size && placed < ruinCount) {
                val `val` = ints.items[i]
                val x = Point2.x(`val`).toInt()
                val y = Point2.y(`val`).toInt()

                //do not overwrite player spawn
                if (Mathf.within(
                        x.toFloat(),
                        y.toFloat(),
                        spawn!!.x.toFloat(),
                        spawn!!.y.toFloat(),
                        18f
                    )
                ) {
                    i++
                    continue
                }
                val range = difficulty + Mathf.rand.random(diffRange)
                val tile = tiles.getn(x, y)
                var part: BaseRegistry.BasePart? = null
                if (tile.overlay().itemDrop != null) {
                    part = Vars.bases.forResource(tile.drop()).getFrac(range)
                } else if (tile.floor().liquidDrop != null && Mathf.rand.chance(0.05)) {
                    part = Vars.bases.forResource(tile.floor().liquidDrop).getFrac(range)
                } else if (Mathf.rand.chance(0.05)) { //ore-less parts are less likely to occur.
                    part = Vars.bases.parts.getFrac(range)
                }

                //actually place the part
                if (part != null && BaseGenerator.tryPlace(
                        part,
                        x,
                        y,
                        Team.derelict
                    ) { cx: Int, cy: Int ->
                        val other: Tile = tiles.getn(cx, cy)
                        if (other.floor().hasSurface()) {
                            other.setOverlay(Blocks.oreScrap)
                            for (j in 1..2) {
                                for (p: Point2 in Geometry.d8) {
                                    val t: Tile? = tiles.get(cx + p.x * j, cy + p.y * j)
                                    if ((t != null) && t.floor()
                                            .hasSurface() && Mathf.rand.chance(if (j == 1) 0.4 else 0.2)
                                    ) {
                                        t.setOverlay(Blocks.oreScrap)
                                    }
                                }
                            }
                        }
                    }
                ) {
                    placed++
                    val debrisRadius = part.schematic.width.coerceAtLeast(part.schematic.height) / 2 + 3
                    Geometry.circle(x, y, tiles.width, tiles.height, debrisRadius) { cx: Int, cy: Int ->
                        val dst: Float = Mathf.dst(cx.toFloat(), cy.toFloat(), x.toFloat(), y.toFloat())
                        val removeChance: Float = Mathf.lerp(0.05f, 0.5f, dst / debrisRadius)
                        val other: Tile = tiles.getn(cx, cy)
                        if (other.build != null && other.isCenter) {
                            if (other.team() === Team.derelict && Mathf.rand.chance(removeChance.toDouble())) {
                                other.remove()
                            } else if (Mathf.rand.chance(0.5)) {
                                other.build.health =
                                    other.build.health - Mathf.rand.random(other.build.health * 0.9f)
                            }
                        }
                    }
                }
                i++
            }
        }
        Schematics.placeLaunchLoadout(spawn!!.x, spawn!!.y)
        for (espawn in enemies) {
            tiles.getn(espawn.x, espawn.y).setOverlay(Blocks.spawn)
        }
        if (sector.hasEnemyBase()) {
            basegen.generate(
                tiles, enemies.map { r: Room -> tiles.getn(r.x, r.y) },
                tiles[spawn!!.x, spawn!!.y], Vars.state.rules.waveTeam, sector, difficulty
            )
            sector.info.attack = true
            Vars.state.rules.attackMode = sector.info.attack
        } else {
            sector.info.winWave = 10 + 5 * (difficulty * 10).coerceAtLeast(1f).toInt()
            Vars.state.rules.winWave = sector.info.winWave
        }
        val waveTimeDec = 0.4f
        Vars.state.rules.waveSpacing = Mathf.lerp(
            (60 * 65 * 2).toFloat(),
            60f * 60f * 1f,
            (difficulty - waveTimeDec).coerceAtLeast(0f) / 0.8f
        )
        sector.info.waves = true
        Vars.state.rules.waves = sector.info.waves
        Vars.state.rules.enemyCoreBuildRadius = 600f
        Vars.state.rules.spawns = Waves.generate(difficulty, Rand(), Vars.state.rules.attackMode)
    }

    override fun postGenerate(tiles: Tiles?) {
        if (sector.hasEnemyBase()) {
            basegen.postGenerate()
        }
    }
}