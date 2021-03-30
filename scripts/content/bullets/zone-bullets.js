/**
 * @property Thorium     - Has a damaging radioactive field around it.
 * @property Spore Pod   - Has a slowing field around it. Does zero damage.
 * @property Surge Alloy - Has a damaging electric field around it.
 * @property Pyratite    - Has a damaging burning field around it. Starts fires in the field.
*/

// Imports

const bulletStatusEffectZone = require("lib/events").bulletStatusEffectZone;

// Bullets

const pyraBullet = extend(BasicBulletType, {
	speed: 5.5,
	damage: 10,
	width: 10,
	height: 13
});

const surgeBullet = extend(BasicBulletType, {
	speed: 5.25,
	damage: 10,
	width: 10,
	height: 13
});

const sporeBullet = extend(BasicBulletType, {
	speed: 3.75,
	damage: 2,
	width: 10,
	height: 13
});

const thoriumBullet = extend(BasicBulletType, {
	speed: 5,
	damage: 6,
	width: 10,
	height: 13
});

// Bullet Status Zone

Events.run(Trigger.update, () => {
	bulletStatusEffectZone(StatusEffects.burning, 60, Pal.lightOrange, 24, pyraBullet, false, true, new Seq);
	bulletStatusEffectZone(StatusEffects.shocked, 60, Pal.surge, 24, surgeBullet, false, true, new Seq);
	bulletStatusEffectZone(StatusEffects.sporeSlowed, 300, Pal.spore, 36, sporeBullet, false, false, new Seq);
	bulletStatusEffectZone(StatusEffects.corroded, 60, Pal.thoriumPink, 24, thoriumBullet, false, false, new Seq);
});

// Exports

 module.exports = {
	pyraBullet: pyraBullet,
	surgeBullet: surgeBullet,
	sporeBullet: sporeBullet,
	thoriumBullet: thoriumBullet
 };