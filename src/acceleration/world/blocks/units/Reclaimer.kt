package acceleration.world.blocks.units

import arc.Events
import arc.struct.ObjectMap
import arc.struct.Seq
import arc.util.io.Reads
import mindustry.Vars
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
    private val reclaimers = Seq<CollectorBuild>()

    val range = 120f
    var tier = 1f

    private fun tierScale(x: Float): Float {
        return (x * 15 + 15) / 100
    }

    private fun unitScale(u: UnitType): Float {
        return (u.hitSize) / 100
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

    inner class CollectorBuild : Building() {
        private val units = Seq<Unit>()
        private var queuedItems = Seq<Array<out ItemStack>>()

        override fun created() {
            super.created()

            if (items == null) items = ItemModule()
            Events.on(EventType.UnitDestroyEvent::class.java) { e ->
                if (e.unit.dst(x, y) < range) {
                    units.add(e.unit)
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

        override fun updateTile() {
            super.updateTile()

            updateItems()

            queuedItems.each { i ->
                for (iterItem in i.iterator()) {
                    val item = iterItem.item
                    val amount = iterItem.amount

                    items.add(item, amount)
                    for (unused in 0 until amount) {
                        offload(item)
                    }
                }
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

                    var divideAmount = 1

                    reclaimers.each { c -> if (c.dst(x, y) <= range && !(c.x == x && c.y == y)) divideAmount++ }

                    if (divideAmount > 1) divideAmount -= (divideAmount / 2)

                    val percentAmount = ((amount * tierPercent * unitPercent) / divideAmount).toInt()

                    queuedItems.add(ItemStack.with(item, percentAmount))
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