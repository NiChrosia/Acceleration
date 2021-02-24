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
		Items.lead, 85,
		Items.silicon, 70,
		Items.metaglass, 80,
		Items.titanium, 90
	),
	category: Category.crafting,
	outputLiquid: new LiquidStack(functions.cliquid("arctifluid"), 1),
	craftTime: 120,
	buildVisibility: BuildVisibility.shown
});

arctifluidSynthesizer.consumes.power(3);
arctifluidSynthesizer.consumes.liquid(Liquids.cryofluid, 0.2)