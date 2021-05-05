package acceleration.content

import arc.struct.Seq
import arc.util.Log
import mindustry.Vars
import mindustry.content.*
import mindustry.ctype.ContentList
import mindustry.ctype.UnlockableContent
import mindustry.game.Objectives
import mindustry.type.ItemStack

class AccelerationTechTree : ContentList {
    private fun node(parent: UnlockableContent, contentType: UnlockableContent, objectives: Seq<Objectives.Objective>?, requirements: ItemStack?) {
        val realRequirements = if (requirements != null) {
            arrayOf(requirements)
        } else {
            contentType.researchRequirements()
        }

        val techNode = TechTree.TechNode(TechTree.get(parent), contentType, realRequirements)

        realRequirements.forEach { i ->
            techNode.objectives.add(Objectives.Research(i.item))
        }
        objectives?: techNode.objectives.addAll(objectives)
    }

    private fun node(parent: UnlockableContent, contentType: UnlockableContent, objectives: Seq<Objectives.Objective>?) {
        node(parent, contentType, objectives, null)
    }

    private fun node(parent: UnlockableContent, contentType: UnlockableContent) {
        node(parent, contentType, null)
    }

    private fun nodeProduce(parent: UnlockableContent, contentType: UnlockableContent, objectives: Seq<Objectives.Objective>?, requirements: ItemStack?) {
        val realRequirements = if (requirements != null) {
            arrayOf(requirements)
        } else {
            contentType.researchRequirements()
        }

        val techNode = TechTree.TechNode(TechTree.get(parent), contentType, realRequirements)

        realRequirements.forEach { i ->
            techNode.objectives.add(Objectives.Research(i.item))
        }
        techNode.objectives.add(Objectives.Produce(contentType))
        objectives?: techNode.objectives.addAll(objectives)

    }

    private fun nodeProduce(parent: UnlockableContent, contentType: UnlockableContent, objectives: Seq<Objectives.Objective>?) {
        nodeProduce(parent, contentType, objectives, null)
    }

    private fun nodeProduce(parent: UnlockableContent, contentType: UnlockableContent) {
        nodeProduce(parent, contentType, null)
    }

    override fun load() {
        // Liquids
        nodeProduce(Liquids.cryofluid, AccelerationLiquids.arctifluid)
        nodeProduce(Liquids.slag, AccelerationLiquids.quarkPlasma)

        // Items
        nodeProduce(Items.thorium, AccelerationItems.velosium)
        nodeProduce(AccelerationItems.velosium, AccelerationItems.arcaneVelosium)
        nodeProduce(AccelerationItems.velosium, AccelerationItems.voltaicVelosium)

        // Blocks

        /// Cores
        node(Blocks.coreNucleus, AccelerationBlocks.atomCore)

        /// Walls
        node(Blocks.plastaniumWallLarge, AccelerationBlocks.metaglassWall)
        node(AccelerationBlocks.metaglassWall, AccelerationBlocks.metaglassWallLarge)

        /// Projectors
        node(Blocks.forceProjector, AccelerationBlocks.configurableProjector, Seq.with(
            Objectives.Research(Blocks.mendProjector),
            Objectives.Research(Blocks.overdriveDome),
            Objectives.Research(Blocks.forceProjector)
        ))

        /// Reclaimers
        node(Blocks.container, AccelerationBlocks.reclaimer)

        // Sectors
        node(SectorPresets.planetaryTerminal, AccelerationSectors.glacialGlade, Seq.with(
            Objectives.SectorComplete(SectorPresets.planetaryTerminal)
        ))

        if (Vars.net.client()) Log.info("Mod [accent]Acceleration[] tech tree loaded successfully.") else Log.info("Mod Acceleration tech tree loaded successfully.")
    }
}