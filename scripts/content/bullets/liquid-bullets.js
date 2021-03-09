// Imports

const mliquids = require("content/liquids")
const msfx = require("content/effects/status-effects")

// Constants

const dark = Color.valueOf("00000000")

// Liquid Bullets

const arctifluidShot = extend(LiquidBulletType, {
	liquid: mliquids.arctifluid,
	speed: 4,
	drag: 0.0015,
	lifetime: 54,
	puddleSize: 24,
	damage: 45,
	knockback: 3.0,
	orbSize: 7,
	effect: msfx.arctifreezing
});

const quarkPlasmaShot = extend(LiquidBulletType, {
	liquid: mliquids.quarkPlasma,
	speed: 4.5,
	drag: 0.0001,
	damage: 120,
	knockback: 2.2,
	lifetime: 64,
	orbSize: 7,
	effect: msfx.liquefying
});

const massiveWaterShot = extend(LiquidBulletType, {
	liquid: Liquids.water,
	speed: 4.5,
	drag: 0.0001,
	damage: 0.5,
	knockback: 2.8,
	lifetime: 56,
	orbSize: 6,
	puddleSize: 10,
	lightColor: dark,
	status: StatusEffects.wet
});

const massiveSlagShot = extend(LiquidBulletType, {
	liquid: Liquids.slag,
	speed: 4.5,
	drag: 0.0001,
	damage: 12.4,
	knockback: 2.3,
	lifetime: 56,
	orbSize: 6,
	puddleSize: 10,
	status: StatusEffects.melting
});

const massiveOilShot = extend(LiquidBulletType, {
	liquid: Liquids.oil,
	speed: 4.5,
	drag: 0.0001,
	damage: 0.5,
	knockback: 2.8,
	lifetime: 56,
	orbSize: 6,
	puddleSize: 10,
	lightColor: dark,
	status: StatusEffects.tarred
});

const massiveCryoShot = extend(LiquidBulletType, {
	liquid: Liquids.cryofluid,
	speed: 4.5,
	drag: 0.0001,
	damage: 0.5,
	knockback: 2.8,
	lifetime: 56,
	orbSize: 6,
	puddleSize: 10,
	status: StatusEffects.freezing
});

// Exports

module.exports = {
	arctifluidShot: arctifluidShot,
	massiveWaterShot: massiveWaterShot,
	massiveSlagShot: massiveSlagShot,
	massiveOilShot: massiveOilShot,
	massiveCryoShot: massiveCryoShot,
	quarkPlasmaShot: quarkPlasmaShot
};