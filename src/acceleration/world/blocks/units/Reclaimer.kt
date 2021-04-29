package acceleration.world.blocks.units

import arc.Events
import arc.math.Mathf
import arc.struct.ObjectMap
import arc.struct.Seq
import arc.util.Timer
import arc.util.io.Reads
import mindustry.Vars
import mindustry.content.Fx
import mindustry.game.EventType
import mindustry.gen.Building
import mindustry.gen.Unit
import mindustry.graphics.Drawf
import mindustry.graphics.Pal
import mindustry.type.Item
import mindustry.type.ItemStack
import mindustry.type.UnitType
import mindustry.world.Block
import mindustry.world.blocks.units.Reconstructor
import mindustry.world.blocks.units.UnitFactory
import mindustry.world.consumers.ConsumeType
import mindustry.world.consumers.ConsumeItems
import mindustry.world.modules.ItemModule

open class Reclaimer(name: String) : Block(name) {
    private val unitMap = ObjectMap<UnitType, Array<out ItemStack>>()
    private val reclaimers = Seq<ReclaimerBuild>()
    private var nearby = 0

    var range = 120f
    var tier = 1f

    private fun tierScale(x: Float): Float {
        return (x * 15 + 15) / 100
    }

    private fun unitScale(u: UnitType): Float {
        val divideAmount = 20

        var output = u.hitSize / divideAmount

        if (output > 1) {
            output = 1f
        }

        return output
    }

    init {
        super.init()

        solid = true
        update = true
        destructible = true
        hasItems = true

        Vars.content.blocks().each { b ->
            if (b is UnitFactory) {
                b.plans.each { p ->
                    unitMap.put(p.unit, p.requirements)
                }
            }
            if (b is Reconstructor) {
                b.upgrades.each { u ->
                    val items = b.consumes.get<ConsumeItems>(ConsumeType.item).items

                    unitMap.put(u[1], items)
                }
            }
        }

        Events.on(EventType.ResetEvent::class.java) {
            reclaimers.clear()
        }
    }

    override fun drawPlace(x: Int, y: Int, rotation: Int, valid: Boolean) {
        super.drawPlace(x, y, rotation, valid)

        Drawf.dashCircle(x * Vars.tilesize + offset, y * Vars.tilesize + offset, range, Pal.placing)
    }

    inner class ReclaimerBuild : Building() {
        private val units = Seq<Unit>()
        private var queuedItems = Seq<Array<out ItemStack>>()

        override fun created() {
            super.created()

            if (items == null) items = ItemModule()
            Events.on(EventType.UnitDestroyEvent::class.java) { e ->
                if (e.unit.dst(x, y) < range && tile.build is ReclaimerBuild) {
                    units.add(e.unit)

                    if (unitMap.containsKey(e.unit.type)) {
                        for (i in 0..e.unit.hitSize.toInt()) {
                            Timer.schedule({
                                Fx.itemTransfer.at(
                                    e.unit.x + Mathf.random(-e.unit.hitSize / 3, e.unit.hitSize / 3),
                                    e.unit.y + Mathf.random(-e.unit.hitSize / 3, e.unit.hitSize / 3),
                                    0f,
                                    if (tile.build is ReclaimerBuild) this else null
                                )
                            }, i / 10f)
                        }
                    }
                }
            }

            reclaimers.add(this)
        }

        override fun read(read: Reads, revision: Byte) {
            super.read(read, revision)

            reclaimers.add(this)
        }

        override fun onRemoved() {
            super.onRemoved()

            reclaimers.remove(this)
        }

        override fun onDestroyed() {
            super.onDestroyed()

            reclaimers.remove(this)
        }

        override fun updateTile() {
            super.updateTile()

            updateItems()

            nearby = 0
            reclaimers.each {
                if (it.dst(this) <= range && it.tile != tile) {
                    nearby++
                }
            }

            queuedItems.each { i ->
                for (iterItem in i.iterator()) { items.add(iterItem.item, iterItem.amount) }
            }
            queuedItems.clear()
        }

        private fun updateItems() {
            units.each { u ->
                val unitItems = unitMap.get(u.type) ?: return@each

                val tierPercent = tierScale(tier) // The scale for the block tier
                val unitPercent = unitScale(u.type) // The scale for the unit hitSize

                for (iterItem in unitItems.iterator()) {
                    val item = iterItem.item
                    val amount = iterItem.amount

                    var percentAmount = ((amount * tierPercent * unitPercent) / (nearby + 1)).toInt()

                    if (percentAmount > amount) {
                        percentAmount = amount
                    }

                    for (i in 1..percentAmount) {
                        val seconds = (u.hitSize / 10)
                        val time = seconds / percentAmount * i

                        Timer.schedule({
                            queuedItems.add(ItemStack.with(item, 1))
                        }, time)
                    }
                }
            }

            units.clear()
        }

        override fun getMaximumAccepted(item: Item?): Int {
            return itemCapacity
        }

        override fun drawSelect() {
            super.drawSelect()

            Drawf.dashCircle(x, y, range, team.color)
        }

        override fun acceptItem(source: Building?, item: Item?): Boolean {
            return false
        }
    }
}