const functions = require("lib/functions")

const glaciafluidExtractor = extend(LiquidConverter, "glaciafluid-extractor", {
	health: 360,
	size: 4,
	solid: true,
	hasPower: true,
	hasLiquid: true,
	itemCapacity: 0,
	liquidCapacity: 10,
	requirements: ItemStack.with(
		Items.copper, 120,
		Items.lead, 135,
		Items.silicon, 210,
		Items.metaglass, 120,
		Items.titanium, 120,
		Items.surgeAlloy, 80,
		functions.citem("aerogel"), 75
	),
	category: Category.crafting,
	outputLiquid: new LiquidStack(functions.cliquid("glaciafluid"), 1),
	craftTime: 120,
	attribute: Attribute.water,
	buildVisibility: BuildVisibility.shown
});

glaciafluidExtractor.consumes.power(1100);
glaciafluidExtractor.consumes.liquid(functions.cliquid("arctifluid"), 0.2)