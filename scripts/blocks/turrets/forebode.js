// const functions = require("lib/functions");
const bullets = require("bullets/bullets");
const effects = require("effects/effects");

const forebode = extend(ItemTurret, "forebode", {
	size: 4,
	health: 2650,
	category: Category.turret,
	buildVisibility: BuildVisibility.shown,
	reloadTime: 250,
	inaccuracy: 1.2,
	range: 450,
	rotateSpeed: 1.8,
	shootSound: Sounds.laserblast,
	chargeEffect: effects.purpleLaserChargeSmall,
	chargeTime: 80,
	shootShake: 5,
	coolantMultiplier: 0.35
});

forebode.ammo(
	Items.surgeAlloy, bullets.longLaser
);