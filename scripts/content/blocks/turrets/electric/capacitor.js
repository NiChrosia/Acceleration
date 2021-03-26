const siliconBullet = extend(BasicBulletType, {
	width: 10,
	height: 12,
	damage: 16,
	lifetime: 70,
	speed: 3.8,
	homingRange: 80,
	homingPower: 0.08
});

const phaseBullet = extend(BasicBulletType, {
	width: 10,
	height: 12,
	damage: 36,
	lifetime: 120,
	speed: 3.6,
	homingRange: 200,
	homingPower: 0.08
});

const surgeBullet = extend(BasicBulletType, {
	width: 10,
	height: 12,
	damage: 24,
	lifetime: 90,
	speed: 4,
	lightning: 2,
	lightningLength: 5,
	status: StatusEffects.shocked,
	pierceCap: 4,
	pierceBuilding: true
});

const capacitor = extend(ItemTurret, "capacitor", {
	rotateSpeed: 12,
	health: 1650,
	range: 210,
	reloadTime: 48,
	size: 3,
	buildVisibility: BuildVisibility.shown,
	category: Category.turret
});

capacitor.consumes.power(3)

capacitor.ammo(
	Items.silicon, siliconBullet,
	Items.phaseFabric, phaseBullet,
	Items.surgeAlloy, surgeBullet
)

module.exports = {
	surgeBullet: surgeBullet
}