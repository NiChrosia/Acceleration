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
const cblock = name => Vars.content.getByName(ContentType.block, "acceleration-" + name);
const citem = name => Vars.content.getByName(ContentType.item, "acceleration-" + name);

//items
node(Items.surgeAlloy, citem("aerogel"), null, Seq.with(new Objectives.SectorComplete(SectorPresets.nuclearComplex)));
//blocks
node(Blocks.phaseWeaver, cblock("aerogel-weaver"), null, Seq.with(new Objectives.SectorComplete(SectorPresets.nuclearComplex)));