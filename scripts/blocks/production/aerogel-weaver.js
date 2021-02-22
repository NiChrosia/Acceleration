const functions = require("lib/functions")
const blockName = "aerogel-weaver-";

const aerogelWeaver = extend(GenericCrafter, "aerogel-weaver", {
	icons() {
		return [
			functions.getRegion(blockName, "bottom"),
			functions.getRegion(blockName, null),
			functions.getRegion(blockName, "weave")
		]
	},
	//drawer: DrawWeave(),
	health: 100,
	size: 3,
	solid: true,
	hasPower: true,
	hasItems: true,
	hasLiquid: true,
	itemCapacity: 30,
	liquidCapacity: 10,
	requirements: ItemStack.with(Items.surgeAlloy, 90, Items.phaseFabric, 30, Items.titanium, 120, Items.lead, 150 ,Items.silicon, 180),
	category: Category.crafting,
	outputItem: new ItemStack(functions.citem("aerogel"), 1),
	craftTime: 105,
	buildVisibility: BuildVisibility.shown
});

aerogelWeaver.consumes.power(16);
aerogelWeaver.consumes.items(ItemStack.with(Items.sand, 5));
aerogelWeaver.consumes.liquid(functions.cliquid("corrofluid"), 0.3);

aerogelWeaver.buildType = () => extend(GenericCrafter.GenericCrafterBuild, aerogelWeaver, {
	draw() {
		this.super$draw();
		
		// Bottom Region
		Draw.rect(functions.getTextureName(blockName, "bottom"), this.x, this.y);
		
		// Corrofluid Region
		Draw.color(functions.cliquid("corrofluid").color);
		Draw.alpha(functions.percent(this.liquids.get(functions.cliquid("corrofluid")), this.block.liquidCapacity));
		Draw.rect(functions.getTextureName(blockName, "liquid"), this.x, this.y);
		
		// Weave Region
		
		// Copied from core/src/mindustry/world/draw/DrawWeave.java
		Draw.reset();
		Draw.rect(functions.getTextureName(blockName, "weave"), this.x, this.y, this.totalProgress);
		Draw.color(Pal.accent);
		Draw.alpha(this.warmup);
		Lines.lineAngleCenter(
		this.x + Mathf.sin(this.totalProgress, 6, Vars.tilesize / 3 * this.block.size),
			this.y,
			90,
			this.block.size * Vars.tilesize / 2
		);
		
		// Main Region
		Draw.reset();
		Draw.rect(this.block.region, this.x, this.y);
	}
});