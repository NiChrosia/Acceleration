// Imports

const mitems = require("content/items");
const bullets = require("content/bullets/flak-bullets");

// Assignment

const storm = extend(ItemTurret, "storm", {
	size: 4,
	health: 2750,
	requirements: ItemStack.with(
		Items.copper, 1000,
		Items.titanium, 350,
		Items.plastanium, 350,
		Items.surgeAlloy, 100
	),
	inaccuracy: 7.5,
	reloadTime: 4,
	maxAmmo: 45,
	recoilAmount: 8,
	cooldown: 0.01,
	shootSound: Sounds.shootSnap,
	xRand: 3,
	buildVisibility: BuildVisibility.shown,
	category: Category.turret,
	range: 280
});

storm.ammo(
	Items.plastanium, bullets.explosiveFragPlastic,
	Items.blastCompound, bullets.explosiveFragBomb,
	mitems.fusionCompound, bullets.explosiveFragNuke,
	Items.surgeAlloy, bullets.explosiveFragShock
)