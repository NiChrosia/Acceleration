const railArtilleryDense = extend(ArtilleryBulletType, {
	lifetime: 80,
	speed: 8,
	damage: 45,
	hitEffect: Fx.flakExplosion,
	splashDamageRadius: 1.9 * Vars.tilesize,
	splashDamage: 22,
	ammoMultiplier: 2,
	knockback: 0.8,
	trailMult: 0.5
});

const railArtilleryHoming = extend(ArtilleryBulletType, {
	lifetime: 80,
	speed: 6.5,
	damage: 40,
	hitEffect: Fx.flakExplosion,
	homingPower: 0.24,
	homingRange: 40,
	splashDamageRadius: 1.9 * Vars.tilesize,
	splashDamage: 22,
	ammoMultiplier: 3,
	knockback: 0.8,
	trailMult: 0.5
});

const railArtilleryIncendiary = extend(ArtilleryBulletType, {
	lifetime: 80,
	speed: 7.5,
	damage: 50,
	hitEffect: Fx.flakExplosion,
	status: StatusEffects.burning,
	frontColor: Pal.lightishOrange,
	backColor: Pal.lightOrange,
	trailEffect: Fx.incendTrail,
	splashDamageRadius: 1.9 * Vars.tilesize,
	splashDamage: 25,
	ammoMultiplier: 2,
	knockback: 0.8,
	trailMult: 0.5
});

railArtilleryGlassFrag = BasicBulletType(3, 5);
JsonIO.copy(Bullets.flakGlassFrag, railArtilleryGlassFrag);
railArtilleryGlassFrag.collidesGround = true;
railArtilleryGlassFrag.collidesAir = false;

const railArtilleryGlass = extend(ArtilleryBulletType, {
	lifetime: 80,
	speed: 9,
	damage: 45,
	hitEffect: Fx.flakExplosion,
	fragBullet: railArtilleryGlassFrag,
	frontColor: Color.white,
	backColor: Color.white,
	splashDamageRadius: 2.7 * Vars.tilesize,
	splashDamage: 33,
	ammoMultiplier: 2,
	knockback: 0.9,
	trailMult: 0.5
});

module.exports = {
	railArtilleryDense: railArtilleryDense,
	railArtilleryHoming: railArtilleryHoming,
	railArtilleryIncendiary: railArtilleryIncendiary,
	railArtilleryGlass: railArtilleryGlass
};