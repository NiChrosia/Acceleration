package acceleration.content

import arc.struct.Seq
import arc.util.Log
import mindustry.content.TechTree
import mindustry.ctype.ContentList
import mindustry.ctype.UnlockableContent
import mindustry.game.Objectives
import mindustry.type.ItemStack

import mindustry.content.Blocks

import mindustry.content.Liquids


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
        val techNode = TechTree.TechNode(TechTree.get(parent), contentType, contentType.researchRequirements())
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
        nodeProduce(Liquids.cryofluid, AccelerationLiquids.arctifluid)
        nodeProduce(Liquids.slag, AccelerationLiquids.quarkPlasma)

        node(Blocks.coreNucleus, AccelerationBlocks.atomCore)

        node(Blocks.plastaniumWallLarge, AccelerationBlocks.metaglassWall)
        node(AccelerationBlocks.metaglassWall, AccelerationBlocks.metaglassWallLarge)

        Log.info("Loaded [accent]Acceleration[] [sky]Kotlin[] tech tree successfully.")
    }
}