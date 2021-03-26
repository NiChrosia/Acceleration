const siliconBullet = extend(BasicBulletType, {
	width: 9,
	height: 12,
	damage: 12,
	lifetime: 70,
	speed: 3.5,
	homingRange: 60,
	homingPower: 0.08
});

const phaseBullet = extend(BasicBulletType, {
	width: 9,
	height: 12,
	damage: 28,
	lifetime: 120,
	speed: 3.4,
	homingRange: 180,
	homingPower: 0.08
});

const gate = extend(ItemTurret, "gate", {
	rotateSpeed: 8,
	health: 1150,
	range: 195,
	reloadTime: 30,
	size: 2,
	buildVisibility: BuildVisibility.shown,
	category: Category.turret
});

gate.consumes.power(1)

gate.ammo(
	Items.silicon, siliconBullet,
	Items.phaseFabric, phaseBullet
)