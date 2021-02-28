// Imports

const mitems = require("content/items");
const mliquids = require("content/liquids");
const util = require("lib/util");

// Constants

const blockName = "corrofluid-mixer-";

// Assignment

const corrofluidMixer = extend(LiquidConverter, "corrofluid-mixer", {
	icons() {
		return [
			util.getRegion(blockName, "bottom"),
			util.getRegion(blockName, null),
			util.getRegion(blockName, "top")
		]
	},
	health: 240,
	size: 3,
	solid: true,
	hasPower: true,
	hasLiquids: true,
	hasItems: true,
	itemCapacity: 15,
	liquidCapacity: 10,
	requirements: ItemStack.with(Items.lead, 80, Items.titanium, 50, Items.silicon, 50, Items.phaseFabric, 15),
	category: Category.crafting,
	outputLiquid: new LiquidStack(mliquids.corrofluid, 24),
	craftTime: 75,
	buildVisibility: BuildVisibility.shown
});

corrofluidMixer.consumes.power(1.5);
corrofluidMixer.consumes.items(ItemStack.with(mitems.sulfur, 3));
corrofluidMixer.consumes.liquid(Liquids.water, 0.2);

corrofluidMixer.buildType = () => extend(LiquidConverter.LiquidConverterBuild, corrofluidMixer, {
	draw() {
		this.super$draw();
		
		// Bottom region
		Draw.rect(util.getTextureName(blockName, "bottom"), this.x, this.y);
		
		// Block sprite
		Draw.rect(this.block.region, this.x, this.y);
		
		// Water region
		Draw.color(Liquids.water.color);
		Draw.alpha(util.percent(this.liquids.get(Liquids.water), this.block.liquidCapacity));
		Draw.rect(util.getTextureName(blockName, "water"), this.x, this.y);
		
		// Sulfur region
		Draw.color(util.citem("sulfur").color);
		Draw.alpha(util.percent(this.items.get(mitems.sulfur), this.block.itemCapacity));
		Draw.rect(util.getTextureName(blockName, "sulfur"), this.x, this.y);
	
		// Corrofluid region
		Draw.color(util.cliquid("corrofluid").color);
		Draw.alpha(util.percent(this.liquids.get(mliquids.corrofluid), this.block.liquidCapacity));
		Draw.rect(util.getTextureName(blockName, "corrofluid"), this.x, this.y);
		
		// Top region
		Draw.color();
		Draw.rect(util.getTextureName(blockName, "top"), this.x, this.y);
	}
});