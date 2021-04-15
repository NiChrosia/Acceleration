package acceleration.world.blocks.units

import arc.Events
import arc.math.geom.Geometry
import arc.struct.ObjectMap
import arc.struct.Seq
import arc.util.Log
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

open class Collector(name: String) : Block(name) {
    private val unitMap = ObjectMap<UnitType, Array<out ItemStack>>()
    private val collectors = Seq<CollectorBuild>()

    val range = 120f
    val tier = 5f

    private fun tierScale(x: Float): Float {
        return -((x * 15) + 15) + 100
    }

    init {
        solid = true
        update = true
        destructible = true

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
    }

    override fun drawPlace(x: Int, y: Int, rotation: Int, valid: Boolean) {
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

            collectors.add(this)
        }

        override fun read(read: Reads, revision: Byte) {
            super.read(read, revision)

            collectors.add(this)
        }

        override fun onRemoved() {
            super.onRemoved()

            collectors.remove(this)
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

                val percent = tierScale(tier)

                for (iterItem in unitItems.iterator()) {
                    val item = iterItem.item
                    val amount = iterItem.amount

                    var divideAmount = 1

                    collectors.each { c -> if (c.dst(x, y) <= range && !(c.x == x && c.y == y)) divideAmount++ }

                    if (divideAmount > 1) divideAmount -= (divideAmount / 2)

                    val percentAmount = ((amount * (percent / 100)) / divideAmount).toInt()

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

        override fun acceptItem(source: Building?, item: Item?) = false
    }
}