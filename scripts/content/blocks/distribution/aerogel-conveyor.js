// Imports

const mitems = require("content/items")

// Assignment

const aerogelConveyor = extend(StackConveyor, "aerogel-conveyor", {
	speed: 0.1,
	albedo: 0.5,
	targetable: false,
	health: 95,
	size: 1,
	recharge: 5,
	itemCapacity: 10,
	buildVisibility: BuildVisibility.shown,
	requirements: ItemStack.with(
		Items.silicon, 2,
		Items.plastanium, 2,
		mitems.aerogel, 3
	)
});