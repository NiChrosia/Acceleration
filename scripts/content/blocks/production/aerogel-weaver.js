// Imports

const mitems = require("content/items");
const util = require("lib/util");

// Constants

const blockName = "aerogel-weaver-";

// Assignment

const aerogelWeaver = extend(GenericCrafter, "aerogel-weaver", {
	icons() {
		return [
			util.getRegion(blockName, "bottom"),
			util.getRegion(blockName, null),
			util.getRegion(blockName, "weave")
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
	requirements: ItemStack.with(
		Items.lead, 150,
		Items.titanium, 120, 
		Items.silicon, 180,
		Items.phaseFabric, 60,
		Items.surgeAlloy, 60
	),
	category: Category.crafting,
	outputItem: new ItemStack(mitems.aerogel, 1),
	craftTime: 105,
	buildVisibility: BuildVisibility.shown
});

aerogelWeaver.consumes.power(16);
aerogelWeaver.consumes.items(ItemStack.with(
	Items.sand, 3,
	Items.lead, 2
));
aerogelWeaver.consumes.liquid(Liquids.water, 0.4);

aerogelWeaver.buildType = () => extend(GenericCrafter.GenericCrafterBuild, aerogelWeaver, {
	draw() {
		this.super$draw();
		
		// Bottom Region
		Draw.rect(util.getTextureName(blockName, "bottom"), this.x, this.y);
		
		// Weave Region
		
		// Copied from core/src/mindustry/world/draw/DrawWeave.java
		Draw.reset();
		Draw.rect(util.getTextureName(blockName, "weave"), this.x, this.y, this.totalProgress);
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