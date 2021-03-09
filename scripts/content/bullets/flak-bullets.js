// Assignment

const explosiveFragPlasticFrag = extend(BasicBulletType, {
	speed: 3,
	damage: 15,
	width: 12,
	height: 15,
	shrinkY: 1,
	lifetime: 20,
	frontColor: Pal.plastaniumFront,
	backColor: Pal.plastaniumBack,
	despawnEffect: Fx.none
});

const explosiveFragPlastic = extend(FlakBulletType, {
	shootEffect: Fx.shootBig,
	splashDamageRadius: 20,
	splashDamage: 35 * 1.5,
	fragBullet: explosiveFragPlasticFrag,
	fragBullets: 8,
	explodeRange: 25,
	collidesGround: true,
	frontColor: Pal.plastaniumFront,
	backColor: Pal.plastaniumBack,
	damage: 18,
	speed: 6,
	width: 9,
	height: 12,
	lifetime: 60
});

const explosiveFragShockFrag = extend(BasicBulletType, {
	speed: 5,
	damage: 20,
	width: 12,
	height: 15,
	shrinkY: 1,
	lifetime: 5,
	frontColor: Pal.surge,
	backColor: Pal.surge,
	despawnEffect: Fx.none,
	status: StatusEffects.shocked
});

const explosiveFragShock = extend(FlakBulletType, {
	shootEffect: Fx.shootBig,
	splashDamageRadius: 30,
	splashDamage: 45 * 1.5,
	fragBullet: explosiveFragShockFrag,
	fragBullets: 12,
	explodeRange: 30,
	collidesGround: true,
	frontColor: Pal.surge,
	backColor: Pal.surge,
	damage: 28,
	speed: 6,
	width: 12,
	height: 15,
	status: StatusEffects.shocked,
	lifetime: 70,
	lightning: 5,
	lightningLength: 12
});

const explosiveFragBomb = extend(FlakBulletType, {
	shootEffect: Fx.shootBig,
	splashDamageRadius: 90,
	splashDamage: 55 * 1.5,
	explodeRange: 40,
	collidesGround: true,
	damage: 21,
	speed: 4.5,
	width: 9,
	height: 12,
	status: StatusEffects.blasted,
	lifetime: 60
});

const explosiveFragNuke = extend(FlakBulletType, {
	shootEffect: Fx.shootBig,
	splashDamageRadius: 110,
	splashDamage: 60 * 1.5,
	explodeRange: 50,
	collidesGround: true,
	damage: 23,
	speed: 4,
	width: 9,
	height: 12,
	status: StatusEffects.blasted,
	lifetime: 60
});

// Exports

module.exports = {
	explosiveFragPlastic: explosiveFragPlastic,
	explosiveFragBomb: explosiveFragBomb,
	explosiveFragNuke: explosiveFragNuke,
	explosiveFragShock: explosiveFragShock
};