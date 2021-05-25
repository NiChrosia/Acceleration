package acceleration.world.blocks.units

import arc.Events
import arc.math.Mathf
import arc.struct.ObjectMap
import arc.struct.Seq
import arc.util.Log
import arc.util.Timer
import arc.util.io.Reads
import mindustry.Vars
import mindustry.content.Fx
import mindustry.game.EventType
import mindustry.gen.Building
import mindustry.gen.Unit as mUnit
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
    private val individualRequirementMap = ObjectMap<UnitType, Array<out ItemStack>>()
    private val requirementMap = ObjectMap<UnitType, Array<out ItemStack>>()
    private val upgradeMap = ObjectMap<UnitType, UnitType>()
    private val tierMap = ObjectMap<UnitType, Int>()
    private var tierOneUnits = Seq<UnitType>()
    private val reclaimers = Seq<ReclaimerBuild>()
    private var nearby = 0

    var range = 120f
    var tier = 1f

    private fun tierScale(tier: Float): Double {
        return (tier * 15 + 15) / 100 % 1.0
    }

    private fun unitScale(tier: Int): Double {
        return tier * 20.0
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
                    individualRequirementMap.put(p.unit, p.requirements)
                    tierOneUnits.add(p.unit)
                }
            }
            if (b is Reconstructor) {
                b.upgrades.each { u ->
                    val items = b.consumes.get<ConsumeItems>(ConsumeType.item).items

                    individualRequirementMap.put(u[1], items)
                    upgradeMap.put(u[0], u[1])
                }
            }
        }

        var index = 0
        var tier = 1

        var lastUpgrade: UnitType? = tierOneUnits[index]
        var prevUnits: Seq<UnitType> = Seq.with(lastUpgrade)

        addRequirements(lastUpgrade, prevUnits)
        tierMap.put(lastUpgrade, tier)

        Log.info("[accent]units:[]")
        while (individualRequirementMap.containsKey(lastUpgrade)) {
            if (upgradeMap.containsKey(lastUpgrade)) {
                lastUpgrade = upgradeMap.get(lastUpgrade)
                prevUnits.add(lastUpgrade)

                tier++
                addRequirements(lastUpgrade, prevUnits)
                tierMap.put(lastUpgrade, tier)
            } else if (index < tierOneUnits.size - 2) {
                index++
                lastUpgrade = tierOneUnits[index]
                prevUnits = Seq.with(lastUpgrade)

                addRequirements(lastUpgrade, prevUnits)
                tier = 1
            } else {
                break
            }
        }

        Log.info(requirementMap)

        Events.on(EventType.ResetEvent::class.java) {
            reclaimers.clear()
        }
    }

    private fun addRequirements(lastUnit: UnitType?, units: Seq<UnitType>) {
        if (lastUnit == null) return

        var items = arrayOf<ItemStack>()
        units.forEach { unit ->
            val unitItems = individualRequirementMap.get(unit)
            unitItems?.forEach {
                items = items.plus(it)
            }
        }

        requirementMap.put(lastUnit, items)
    }

    override fun drawPlace(x: Int, y: Int, rotation: Int, valid: Boolean) {
        super.drawPlace(x, y, rotation, valid)

        Drawf.dashCircle(x * Vars.tilesize + offset, y * Vars.tilesize + offset, range, Pal.placing)
    }

    inner class ReclaimerBuild : Building() {
        private val units = Seq<mUnit>()
        private var queuedItems = Seq<Array<out ItemStack>>()

        override fun created() {
            super.created()

            if (items == null) items = ItemModule()
            Events.on(EventType.UnitDestroyEvent::class.java) { e ->
                if (e.unit.dst(x, y) < range && tile.build is ReclaimerBuild && !dead) {
                    units.add(e.unit)

                    if (individualRequirementMap.containsKey(e.unit.type)) {
                        for (i in 0..e.unit.hitSize.toInt() / (nearby + 1)) {
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
                val unitItems = requirementMap.get(u.type) ?: return@each

                val tierPercent = tierScale(tier) // The scale for the block tier
                val unitPercent = unitScale(tierMap.get(u.type) ?: 1) // The scale for the unit hitSize

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