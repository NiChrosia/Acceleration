const bullet = extend(LaserBoltBulletType, {
	damage: 6,
	frontColor: Color.white,
	backColor: Pal.lancerLaser,
	lifetime: 30,
	speed: 5.4
});

const transistor = extend(PowerTurret, "transistor", {
	rotateSpeed: 8,
	health: 360,
	range: 110,
	powerUse: 0.25,
	reloadTime: 30,
	size: 1,
	alternate: true,
	spread: 6,
	buildVisibility: BuildVisibility.shown,
	category: Category.turret,
	shootType: bullet,
	shootSound: Sounds.lasershoot
});