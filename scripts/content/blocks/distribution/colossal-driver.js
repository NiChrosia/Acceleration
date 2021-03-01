// Imports

const mitems = require("content/items");

// Assignment

const colossalDriver = extend(MassDriver, "colossal-driver", {
	size: 4,
	health: 750,
	range: 1440,
	rotateSpeed: 0.01,
	minDistribute: 100,
	knockback: 16,
	reloadTime: 600,
	bulletSpeed: 8.5,
	bulletLifetime: 400,
	shake: 6,
	buildVisibility: BuildVisibility.shown,
	requirements: ItemStack.with(
		Items.lead, 250,
		Items.titanium, 250,
		Items.thorium, 150,
		Items.silicon, 200,
		Items.phaseFabric, 75,
		mitems.aerogel, 75
	),
	category: Category.distribution,
	itemCapacity: 600
});

colossalDriver.consumes.power(32);