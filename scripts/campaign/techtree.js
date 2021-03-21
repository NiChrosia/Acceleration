// Imports

const util = require("lib/util");
const mitems = require("content/items");
const mliquids = require("content/liquids");
const mblocks = require("content/blocks");
const msectors = require("campaign/sectors");
const munits = require("content/units");

/**
 * Node for the research tech tree.
 *
 * @property {UnlockableContent}    parent          - The parent of the current node.
 * @property {UnlockableContent}    contentType     - The unlockable content that the current node contains.
 * @property {ItemStack}            requirements    - The research requirements required to unlock this node, will use the default if set to null.
 * @property {Seq}                  objectives      - A sequence of Objectives required to unlock this node. Can be null.
 */
const node = (parent, contentType, requirements, objectives) => {
	if (parent != null && contentType != null) {
        const tnode = new TechTree.TechNode(TechTree.get(parent), contentType, requirements != null ? requirements : contentType.researchRequirements());
	    let used = new ObjectSet();
		if (objectives != null) tnode.objectives.addAll(objectives);
	}
  
	if (parent == null) {
		Log.err("[ [accent]techtree.js[] ]: parent: [" + parent + "] is null.");
	}
	if (contentType == null) {
		Log.err("[ [accent]techtree.js[] ]: contentType: [" + contentType + "] is null.");
	}
	if (parent == null && contentType == null) {
		Log.err("[ [accent]techtree.js[] ]: parent: [" + parent + "] and contentType: [" + contentType + "] is null.");
	}
};

const nodeProduce = (parent, contentType, requirements, objectives) => {
	if (parent != null && contentType != null){
		const tnode = new TechTree.TechNode(TechTree.get(parent), contentType, requirements != null ? requirements : contentType.researchRequirements());
		let used = new ObjectSet();
    
		tnode.objectives.add(Objectives.Produce(contentType));
		if (objectives != null) tnode.objectives.addAll(objectives);
	}
	
	if (parent == null) {
		Log.err("[ [accent]techtree.js[] ]: parent: [" + parent + "] is null.");
	}
	if (contentType == null) {
		Log.err("[ [accent]techtree.js[] ]: contentType: [" + contentType + "] is null.");
	}
	if (parent == null && contentType == null) {
		Log.err("[ [accent]techtree.js[] ]: parent: [" + parent + "] and contentType: [" + contentType + "] is null.");
	}
};

// Research Nodes

// Items

nodeProduce(Items.phaseFabric, mitems.aerogel, null, null, null);
nodeProduce(Items.blastCompound, mitems.fusionCompound, null, null);

// Liquids

nodeProduce(Liquids.cryofluid, mliquids.arctifluid, null, null);
nodeProduce(Liquids.slag, mliquids.quarkPlasma, null, null);

// Blocks

//   Effect

node(Blocks.coreNucleus, mblocks.coreAtom, null, null);
node(Blocks.shockMine, mblocks.surgeMine, null, null);

//    Power

node(Blocks.impactReactor, mblocks.fusionReactor, null, null);

//    Production

node(Blocks.phaseWeaver, mblocks.aerogelWeaver, null, null);
node(Blocks.cryofluidMixer, mblocks.arctifluidSynthesizer, null, null);

//    Turrets

node(Blocks.tsunami, mblocks.flood, null, null);
node(Blocks.foreshadow, mblocks.harbinger, null, null);
node(Blocks.cyclone, mblocks.storm, null, null);

//    Storage

node(Blocks.vault, mblocks.repository, null, null);

// Units

node(UnitTypes.mono, munits.ion, null, null);
node(munits.ion, munits.spark, null, null);
node(munits.spark, munits.energy, null, null);
node(munits.energy, munits.lightning, null, null);
node(munits.lightning, munits.tempest, null, null);

// Sectors

node(SectorPresets.planetaryTerminal, msectors.glacialGlade, null, Seq.with(new Objectives.SectorComplete(SectorPresets.planetaryTerminal)));
// node(null, msectors.volcanicMountains, null, null);