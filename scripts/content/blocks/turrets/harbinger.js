// Imports

const effects = require("content/effects/effects");
const laserBullets = require("content/bullets/laser-bullets");
const mitems = require("content/items");

// Assignment

const harbinger = extend(PowerTurret, "harbinger", {
	size: 4,
	health: 2650,
	category: Category.turret,
	buildVisibility: BuildVisibility.shown,
	reloadTime: 350,
	inaccuracy: 1.2,
	range: 450,
	rotateSpeed: 1.8,
	coolantMultiplier: 0.5,
	shootSound: Sounds.laserblast,
	chargeEffect: effects.purpleLaserChargeSmall,
	chargeTime: 80,
	shootShake: 5,
	liquidCapacity: 50,
	shootType: laserBullets.longPurpleLaser,
	powerUse: 30,
	requirements: ItemStack.with(
		Items.copper, 1200,
		Items.metaglass, 750,
		Items.silicon, 750,
		Items.plastanium, 300,
		Items.surgeAlloy, 450,
		mitems.diamond, 300,
		mitems.aerogel, 300
	)
});

harbinger.consumes.add(ConsumeLiquidFilter(liquid => liquid.temperature <= 0.5 && liquid.flammability < 0.1, 0.5));