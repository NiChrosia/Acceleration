const functions = require("lib/functions");
const blockName = "corrofluid-mixer-";

const corrofluidMixer = extend(LiquidConverter, "corrofluid-mixer", {
	icons() {
		return [
			functions.getRegion(blockName, "bottom"),
			functions.getRegion(blockName, null),
			functions.getRegion(blockName, "top")
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
	outputLiquid: new LiquidStack(functions.cliquid("corrofluid"), 24),
	craftTime: 75,
	buildVisibility: BuildVisibility.shown
});

corrofluidMixer.consumes.power(1.5);
corrofluidMixer.consumes.items(ItemStack.with(functions.citem("sulfur"), 3));
corrofluidMixer.consumes.liquid(Liquids.water, 0.2);

corrofluidMixer.buildType = () => extend(LiquidConverter.LiquidConverterBuild, corrofluidMixer, {
  draw() {
		this.super$draw();
		
		// Bottom region
		Draw.rect(functions.getTextureName(blockName, "bottom"), this.x, this.y);
		
		// Block sprite
		Draw.rect(this.block.region, this.x, this.y);
		
		// Water region
		Draw.color(Liquids.water.color);
		Draw.alpha(functions.percent(this.liquids.get(Liquids.water), this.block.liquidCapacity));
		Draw.rect(functions.getTextureName(blockName, "water"), this.x, this.y);
		
		// Sulfur region
		Draw.color(functions.citem("sulfur").color);
		Draw.alpha(functions.percent(this.items.get(functions.citem("sulfur")), this.block.itemCapacity));
		Draw.rect(functions.getTextureName(blockName, "sulfur"), this.x, this.y);
		
		// Corrofluid region
		Draw.color(functions.cliquid("corrofluid").color);
		Draw.alpha(functions.percent(this.liquids.get(functions.cliquid("corrofluid")), this.block.liquidCapacity));
		Draw.rect(functions.getTextureName(blockName, "corrofluid"), this.x, this.y);
		
		// Top region
		Draw.color();
		Draw.rect(functions.getTextureName(blockName, "top"), this.x, this.y);
  }
});