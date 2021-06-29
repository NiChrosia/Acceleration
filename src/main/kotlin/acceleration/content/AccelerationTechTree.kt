package acceleration.content

import acceleration.func.stripIf
import arc.struct.Seq
import arc.util.Log
import arc.util.Strings
import mindustry.Vars
import mindustry.content.*
import mindustry.ctype.ContentList
import mindustry.ctype.UnlockableContent
import mindustry.game.Objectives
import mindustry.type.ItemStack

class AccelerationTechTree : ContentList {
    private fun node(
        parent: UnlockableContent,
        contentType: UnlockableContent,
        objectives: Seq<Objectives.Objective> = Seq.with(),
        requirements: Array<out ItemStack> = arrayOf()
    ): TechTree.TechNode {
        val realRequirements = requirements.ifEmpty { contentType.researchRequirements() }
        val techNode = TechTree.TechNode(TechTree.get(parent), contentType, realRequirements)

        realRequirements.forEach { techNode.objectives.add(Objectives.Research(it.item)) }
        techNode.objectives.addAll(objectives)

        return techNode
    }

    private fun nodeProduce(parent: UnlockableContent, contentType: UnlockableContent, objectives: Seq<Objectives.Objective> = Seq.with(), requirements: Array<out ItemStack> = arrayOf()) {
        node(parent, contentType, objectives.addAll(Objectives.Produce(contentType)), requirements)
    }

    override fun load() {
        // Liquids
        nodeProduce(Liquids.cryofluid, AccelerationLiquids.arctifluid)
        nodeProduce(Liquids.slag, AccelerationLiquids.quarkPlasma)

        // Items
        nodeProduce(Items.thorium, AccelerationItems.velosium)
        nodeProduce(AccelerationItems.velosium, AccelerationItems.arcaneVelosium)
        nodeProduce(AccelerationItems.velosium, AccelerationItems.voltaicVelosium)

        /// Cores
        node(Blocks.coreNucleus, atomCore)

        /// Walls
        node(Blocks.plastaniumWallLarge, metaglassWall)
        node(metaglassWall, metaglassWallLarge)

        /// Projectors
        node(Blocks.overdriveDome, configurableProjector, Seq.with(
            Objectives.Research(Blocks.mendProjector),
            Objectives.Research(Blocks.overdriveDome),
            Objectives.Research(Blocks.forceProjector)
        ))

        /// Reclaimers
        node(Blocks.container, reclaimer)

        Log.info("Mod [accent]Acceleration[] tech tree loaded successfully.".stripIf(Vars.headless))
    }
}