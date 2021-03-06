// Imports

const mitems = require("content/items");
const munits = require("content/units");

// Assignment

const coreAtom = extend(CoreBlock, "core-atom", {
	size: 6,
	health: 8500,
	category: Category.effect,
	buildVisibility: BuildVisibility.shown,
	requirements: ItemStack.with(
		Items.copper, 12000,
		Items.lead, 12000,
		Items.thorium, 6000,
		Items.silicon, 6000,
		Items.surgeAlloy, 2000,
		mitems.aerogel, 2000
	),
	itemCapacity: 18000,
	unitCapModifier: 32,
	researchCostMultiplier: 0.08,
	unitType: munits.delta
});