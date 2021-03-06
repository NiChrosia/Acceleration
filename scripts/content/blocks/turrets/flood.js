// Imports

const liquidBullets = require("content/bullets/liquid-bullets");
const mitems = require("content/items");
const mliquids = require("content/liquids");

// Assignment

const flood = extend(LiquidTurret, "flood", {
	solid: true,
	health: 4850,
	size: 4,
	category: Category.turret,
	requirements: ItemStack.with(
		Items.copper, 250,
		Items.lead, 150, 
		Items.thorium, 200, 
		Items.titanium, 150, 
		Items.metaglass, 120,
		Items.surgeAlloy, 350, 
		mitems.aerogel, 120, 
	),
	buildVisibility: BuildVisibility.shown,
	reloadTime: 0,
	inaccuracy: 5.5,
	range: 240,
	rotateSpeed: 12,
	extinguish: true,
	liquidCapacity: 160
});

// Ammo

flood.ammo(
	Liquids.slag, liquidBullets.massiveSlagShot,
	mliquids.quarkPlasma, liquidBullets.quarkPlasmaShot,
	Liquids.oil, liquidBullets.massiveOilShot,
	Liquids.water, liquidBullets.massiveWaterShot,
	Liquids.cryofluid, liquidBullets.massiveCryoShot,
	mliquids.arctifluid, liquidBullets.arctifluidShot
);