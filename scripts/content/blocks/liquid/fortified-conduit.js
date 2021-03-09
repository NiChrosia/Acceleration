// Imports

const mitems = require("content/items");

// Assignment

const fortifiedConduit = extend(Conduit, "fortified-conduit", {
	health: 440,
	size: 1,
	liquidCapacity: 16,
	liquidPressure: 1.025,
	requirements: ItemStack.with(
		Items.metaglass, 1,
		Items.plastanium, 2,
		mitems.aerogel, 2
	),
	buildVisibility: BuildVisibility.shown,
	category: Category.liquid
});