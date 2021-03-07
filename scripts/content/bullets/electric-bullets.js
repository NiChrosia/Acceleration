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

module.exports = {
	deltaHealBullet: deltaHealBullet
}