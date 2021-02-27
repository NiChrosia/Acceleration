const functions = require("lib/functions");
const bullets = require("bullets/bullets");
const effects = require("effects/effects");

const harbinger = extend(PowerTurret, "harbinger", {
	size: 4,
	health: 2650,
	category: Category.turret,
	buildVisibility: BuildVisibility.shown,
	reloadTime: 250,
	inaccuracy: 1.2,
	range: 450,
	rotateSpeed: 1.8,
	coolantMultiplier: 0.5,
	shootSound: Sounds.laserblast,
	chargeEffect: effects.purpleLaserChargeSmall,
	chargeTime: 80,
	shootShake: 5,
	liquidCapacity: 50,
	shootType: bullets.longLaser,
	powerUse: 30,
	requirements: ItemStack.with(
		Items.copper, 1200,
		Items.metaglass, 750,
		Items.silicon, 750,
		Items.plastanium, 300,
		Items.surgeAlloy, 450,
		functions.citem("aerogel"), 300
	)
});

harbinger.consumes.add(ConsumeLiquidFilter(liquid => liquid.temperature <= 0.5 && liquid.flammability < 0.1, 0.5));