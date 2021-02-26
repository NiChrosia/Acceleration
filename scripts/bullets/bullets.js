const purple = Color.valueOf("af4dd6");

const longLaser = extend(LaserBulletType, {
	length: 460,
    damage: 1850,
    width: 45,
    lifetime: 85,
    lightningSpacing: 15,
    lightningLength: 8,
    lightningDelay: 0.6,
    lightningLengthRand: 15,
    lightningDamage: 120,
    lightningAngleRand: 40,
	lightningColor: purple,
	colors: [purple, purple, Color.white]
});

module.exports = {
	longLaser: longLaser
};