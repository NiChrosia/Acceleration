const deltaHealBullet = extend(LaserBoltBulletType, {
	width: 2,
	height: 4,
	lifetime: 36,
	healPercent: 5,
	collidesTeam: true,
	backColor: Pal.heal,
	frontColor: Color.white,
	damage: 12,
	speed: 4,
	buildingDamageMultiplier: 0.01
});

const lightArc = extend(LightningBulletType, {
	lifetime: 1,
	lightningColor: Color.valueOf("3492eb"),
	lightningLength: 12,
	damage: 10,
	healPercent: 1,
	lightningType: extend(BulletType, 0.0001, 0, {
		lifetime: Fx.lightning.lifetime,
		hitEffect: Fx.hitLancer,
		despawnEffect: Fx.none,
		status: StatusEffects.shocked,
		statusDuration: 10,
		hittable: false,
		healPercent: 1,
		collidesTeam: true
	})
});

module.exports = {
	deltaHealBullet: deltaHealBullet,
	lightArc: lightArc
}