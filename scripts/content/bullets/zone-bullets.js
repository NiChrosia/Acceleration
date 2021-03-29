/**
 * @property Thorium     - Has a damaging radioactive field around it.
 * @property Spore Pod   - Has a slowing field around it. Does zero damage.
 * @property Surge Alloy - Has a damaging electric field around it.
 * @property Pyratite    - Has a damaging burning field around it. Starts fires in the field.
*/

// Imports

const bulletStatusEffectZone = require("lib/events").bulletStatusEffectZone;
const fx = require("content/effects/effects");

// Bullets

const pyraBullet = extend(BasicBulletType, {
	speed: 4,
	damage: 10,
	width: 8,
	height: 11
});

const surgeBullet = extend(BasicBulletType, {
	speed: 4,
	damage: 10,
	width: 8,
	height: 11
});

// Bullet Status Zone

Events.run(Trigger.update, () => {
	bulletStatusEffectZone(StatusEffects.burning, fx.pyraSquare, fx.pyraLineSquare, fx.enflamed, 12, pyraBullet, false, true, new Seq);
	bulletStatusEffectZone(StatusEffects.shocked, fx.surgeSquare, fx.surgeLineSquare, Fx.none, 12, surgeBullet, false, true, new Seq);
});

// Exports

 module.exports = {
	pyraBullet: pyraBullet,
	surgeBullet: surgeBullet
 };