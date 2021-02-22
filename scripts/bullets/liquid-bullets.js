const functions = require("lib/functions")

const arctifluidShot = extend(LiquidBulletType, {
	liquid: functions.cliquid("arctifluid"),
	speed: 4,
	drag: 0.0015,
	lifetime: 54,
	puddleSize: 24,
	damage: 12,
	knockback: 3.0,
	effect: functions.cstatus("arctifreezing")
});

const glaciafluidShot = extend(LiquidBulletType, {
	liquid: functions.cliquid("glaciafluid"),
	speed: 4.6,
	drag: 0.0025,
	lifetime: 75,
	puddleSize: 2048,
	damage: 125,
	knockback: 3.8,
	effect: functions.cstatus("permafrost")
});

const corrofluidShot = extend(LiquidBulletType, {
	liquid: functions.cliquid("corrofluid"),
	speed: 4.5,
	drag: 0.0001,
	damage: 16.2,
	knockback: 2.3,
	lifetime: 56,
	puddleSize: 12
});

const massiveWaterShot = extend(LiquidBulletType, {
	liquid: Liquids.water,
	speed: 4.5,
	drag: 0.0001,
	damage: 0.5,
	knockback: 2.8,
	lifetime: 56,
	puddleSize: 12
});

const massiveSlagShot = extend(LiquidBulletType, {
	liquid: Liquids.slag,
	speed: 4.5,
	drag: 0.0001,
	damage: 12.4,
	knockback: 2.3,
	lifetime: 56,
	puddleSize: 12
});

const massiveOilShot = extend(LiquidBulletType, {
	liquid: Liquids.oil,
	speed: 4.5,
	drag: 0.0001,
	damage: 0.5,
	knockback: 2.8,
	lifetime: 56,
	puddleSize: 12
});

const massiveCryoShot = extend(LiquidBulletType, {
	liquid: Liquids.cryofluid,
	speed: 4.5,
	drag: 0.0001,
	damage: 0.5,
	knockback: 2.8,
	lifetime: 56,
	puddleSize: 12
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