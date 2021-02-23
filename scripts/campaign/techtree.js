const functions = require("lib/functions");

/**
 * Node for the research tech tree.
 *
 * @property {UnlockableContent}    parent          - The parent of the current node.
 * @property {UnlockableContent}    contentType     - The unlockable content that the current node contains.
 * @property {ItemStack}            requirements    - The research requirements required to unlock this node, will use the default if set to null.
 * @property {Seq}                  objectives      - A sequence of Objectives required to unlock this node. Can be null.
 */
const node = (parent, contentType, requirements, objectives) => {
  if(parent != null && contentType != null){
    const tnode = new TechTree.TechNode(TechTree.get(parent), contentType, requirements != null ? requirements : contentType.researchRequirements());
    let used = new ObjectSet();
    
    if(objectives != null) tnode.objectives.addAll(objectives);
  }else{
    print(parent + " or " + contentType + " is null.");
  }
};

// Items
node(Items.surgeAlloy, functions.citem("aerogel"), null, Seq.with(new Objectives.SectorComplete(SectorPresets.nuclearComplex)));
node(Items.titanium, functions.citem("sulfur"), null, null);
// Liquids
node(Liquids.cryofluid, functions.cliquid("corrofluid"), null, null);
node(Liquids.cryofluid, functions.cliquid("arctifluid"), null, null);
node(functions.cliquid("arctifluid"), functions.cliquid("glaciafluid"), null, null);
// Blocks
node(Blocks.phaseWeaver, functions.cblock("aerogel-weaver"), null, Seq.with(new Objectives.SectorComplete(SectorPresets.nuclearComplex)));
node(Blocks.cryofluidMixer, functions.cblock("corrofluid-mixer"), null, null);
node(Blocks.cryofluidMixer, functions.block("arctifluid-synthesizer"), null, Seq.with(new Objectives.SectorComplete(SectorPresets.impact0078)))
node(functions.block("arctifluid-synthesizer"), functions.cblock("glaciafluid-extractor"), null, Seq.with(new Objectives.SectorComplete(SectorPresets.impact0078)))
// Turrets
node(Blocks.tsunami, functions.cblock("flood"), null, Seq.with(new Objectives.SectorComplete(SectorPresets.nuclearComplex)))