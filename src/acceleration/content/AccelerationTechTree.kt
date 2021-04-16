package acceleration.content

import arc.struct.Seq
import arc.util.Log
import mindustry.content.*
import mindustry.ctype.ContentList
import mindustry.ctype.UnlockableContent
import mindustry.game.Objectives
import mindustry.type.ItemStack


class AccelerationTechTree : ContentList {
    private fun node(parent: UnlockableContent, contentType: UnlockableContent, objectives: Seq<Objectives.Objective>?, requirements: ItemStack) {
        val techNode = TechTree.TechNode(TechTree.get(parent), contentType, arrayOf(requirements))
        objectives?: techNode.objectives.addAll(objectives)
    }

    private fun node(parent: UnlockableContent, contentType: UnlockableContent, objectives: Seq<Objectives.Objective>?) {
        val techNode = TechTree.TechNode(TechTree.get(parent), contentType, contentType.researchRequirements())
        objectives?: techNode.objectives.addAll(objectives)
    }

    private fun node(parent: UnlockableContent, contentType: UnlockableContent) {
        TechTree.TechNode(TechTree.get(parent), contentType, contentType.researchRequirements())
    }

    private fun nodeProduce(parent: UnlockableContent, contentType: UnlockableContent, objectives: Seq<Objectives.Objective>?, requirements: ItemStack) {
        val techNode = TechTree.TechNode(TechTree.get(parent), contentType, arrayOf(requirements))

        techNode.objectives.add(Objectives.Produce(contentType))
        objectives?: techNode.objectives.addAll(objectives)

    }

    private fun nodeProduce(parent: UnlockableContent, contentType: UnlockableContent, objectives: Seq<Objectives.Objective>?) {
        val techNode = TechTree.TechNode(TechTree.get(parent), contentType, contentType.researchRequirements())

        techNode.objectives.add(Objectives.Produce(contentType))
        objectives?: techNode.objectives.addAll(objectives)

    }

    private fun nodeProduce(parent: UnlockableContent, contentType: UnlockableContent) {
        val techNode = TechTree.TechNode(TechTree.get(parent), contentType, contentType.researchRequirements())

        techNode.objectives.add(Objectives.Produce(contentType))

    }

    override fun load() {
        // Liquids
        nodeProduce(Liquids.cryofluid, AccelerationLiquids.arctifluid)
        nodeProduce(Liquids.slag, AccelerationLiquids.quarkPlasma)

        // Items
        nodeProduce(Items.thorium, AccelerationItems.velosium)
        nodeProduce(AccelerationItems.velosium, AccelerationItems.arcaneVelosium)
        nodeProduce(AccelerationItems.velosium, AccelerationItems.electricVelosium)

        // Blocks

        /// Cores
        node(Blocks.coreNucleus, AccelerationBlocks.atomCore, Seq.with(
            Objectives.Research(Items.copper),
            Objectives.Research(Items.lead),
            Objectives.Research(Items.thorium),
            Objectives.Research(Items.silicon),
            Objectives.Research(Items.surgeAlloy)
        ))

        /// Walls
        node(Blocks.plastaniumWallLarge, AccelerationBlocks.metaglassWall, Seq.with(
            Objectives.Research(Items.titanium),
            Objectives.Research(Items.metaglass)
        ))
        node(AccelerationBlocks.metaglassWall, AccelerationBlocks.metaglassWallLarge, Seq.with(
            Objectives.Research(Items.titanium),
            Objectives.Research(Items.metaglass)
        ))

        /// Turrets
        node(Blocks.arc, AccelerationBlocks.transistor, Seq.with(
            Objectives.Research(Items.copper),
            Objectives.Research(Items.lead),
            Objectives.Research(Items.silicon)
        ))
        node(AccelerationBlocks.transistor, AccelerationBlocks.gate, Seq.with(
            Objectives.Research(Items.copper),
            Objectives.Research(Items.lead),
            Objectives.Research(Items.silicon),
            Objectives.Research(Items.metaglass)
        ))
        node(AccelerationBlocks.gate, AccelerationBlocks.capacitor, Seq.with(
            Objectives.Research(Items.lead),
            Objectives.Research(Items.titanium),
            Objectives.Research(Items.thorium),
            Objectives.Research(Items.plastanium)
        ))

        /// Projectors
        node(Blocks.forceProjector, AccelerationBlocks.configurableProjector, Seq.with(
            Objectives.Research(Items.lead),
            Objectives.Research(Items.titanium),
            Objectives.Research(Items.silicon),
            Objectives.Research(Items.plastanium),
            Objectives.Research(Items.surgeAlloy),
            Objectives.Research(Blocks.mendProjector), Objectives.Research(Blocks.overdriveDome), Objectives.Research(Blocks.forceProjector)
        ))

        /// Reclaimers
        node(Blocks.container, AccelerationBlocks.reclaimer, Seq.with(
            Objectives.Research(Items.copper),
            Objectives.Research(Items.lead),
            Objectives.Research(Items.graphite),
            Objectives.Research(Items.titanium),
            Objectives.SectorComplete(SectorPresets.windsweptIslands)
        ))

        // Sectors
        node(SectorPresets.planetaryTerminal, AccelerationSectors.glacialGlade, Seq.with(
            Objectives.SectorComplete(SectorPresets.planetaryTerminal)
        ))

        Log.info("Loaded [accent]Acceleration[] tech tree successfully.")
    }
}