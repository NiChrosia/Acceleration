const functions = require("lib/functions")

const arctifluidShot = extend(LiquidBulletType, {
	liquid: functions.cliquid("arctifluid"),
	speed: 4,
	drag: 0.0015,
	lifetime: 54,
	puddleSize: 24,
	damage: 45,
	knockback: 3.0,
	orbSize: 7,
	effect: functions.cstatus("arctifreezing")
});

const glaciafluidShot = extend(LiquidBulletType, {
	liquid: functions.cliquid("glaciafluid"),
	speed: 4.6,
	drag: 0.0025,
	lifetime: 75,
	puddleSize: 64,
	damage: 85,
	knockback: 2.3,
	orbSize: 7,
	effect: functions.cstatus("permafrost")
});

const corrofluidShot = extend(LiquidBulletType, {
	liquid: functions.cliquid("corrofluid"),
	speed: 4.5,
	drag: 0.0001,
	damage: 16.2,
	knockback: 2.3,
	lifetime: 56,
	orbSize: 7,
	puddleSize: 10
});

const massiveWaterShot = extend(LiquidBulletType, {
	liquid: Liquids.water,
	speed: 4.5,
	drag: 0.0001,
	damage: 0.5,
	knockback: 2.8,
	lifetime: 56,
	orbSize: 6,
	puddleSize: 10
});

const massiveSlagShot = extend(LiquidBulletType, {
	liquid: Liquids.slag,
	speed: 4.5,
	drag: 0.0001,
	damage: 12.4,
	knockback: 2.3,
	lifetime: 56,
	orbSize: 6,
	puddleSize: 10
});

const massiveOilShot = extend(LiquidBulletType, {
	liquid: Liquids.oil,
	speed: 4.5,
	drag: 0.0001,
	damage: 0.5,
	knockback: 2.8,
	lifetime: 56,
	orbSize: 6,
	puddleSize: 10
});

const massiveCryoShot = extend(LiquidBulletType, {
	liquid: Liquids.cryofluid,
	speed: 4.5,
	drag: 0.0001,
	damage: 0.5,
	knockback: 2.8,
	lifetime: 56,
	orbSize: 6,
	puddleSize: 10
});

module.exports = {
	arctifluidShot: arctifluidShot,
	glaciafluidShot: glaciafluidShot,
	corrofluidShot: corrofluidShot,
	massiveWaterShot: massiveWaterShot,
	massiveSlagShot: massiveSlagShot,
	massiveOilShot: massiveOilShot,
	massiveCryoShot: massiveCryoShot
};