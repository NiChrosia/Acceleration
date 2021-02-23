const functions = require("lib/functions")

const arctifluidSynthesizer = extend(LiquidConverter, "arctifluid-synthesizer", {
	health: 160,
	size: 3,
	solid: true,
	hasPower: true,
	hasLiquid: true,
	itemCapacity: 0,
	liquidCapacity: 10,
	requirements: ItemStack.with(
		Items.copper, 60,
		Items.lead, 40,
		Items.silicon, 60,
		Items.metaglass, 40,
		Items.titanium, 45
	),
	category: Category.crafting,
	outputLiquid: new LiquidStack(functions.cliquid("arctifluid"), 1),
	craftTime: 120,
	buildVisibility: BuildVisibility.shown
});

arctifluidSynthesizer.consumes.power(32);
arctifluidSynthesizer.consumes.liquid(Liquids.cryofluid, 0.2)