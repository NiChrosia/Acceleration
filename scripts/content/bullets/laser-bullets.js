// Constants

const purple = Color.valueOf("af4dd6");

// Laser Bullets

const longPurpleLaser = extend(LaserBulletType, {
	length: 360,
    damage: 650,
    width: 25,
    lifetime: 45,
    lightningSpacing: 10,
    lightningLength: 4,
    lightningDelay: 0.6,
    lightningLengthRand: 8,
    lightningDamage: 60,
    lightningAngleRand: 20,
	lightningColor: purple,
	colors: [purple, purple, Color.white]
});

// Exports

module.exports = {
	longPurpleLaser: longPurpleLaser
};