/**
 * @property Thorium     - Has a damaging radioactive field around it.
 * @property Spore Pod   - Has a slowing field around it. Does zero damage.
 * @property Surge Alloy - Has a damaging electric field around it.
 * @property Pyratite    - Has a damaging burning field around it. Starts fires in the field.
*/

const bulletStatusEffectZone = require("lib/events").bulletStatusEffectZone;
const fx = require("content/effects/effects");

function zoneBullet(statusEffect, shieldEffect, lineEffect, particleEffect, size, damageSelf, damage, immuneBlock, type, attributes) {
	bullet = extend(type, attributes);
	Events.run(Trigger.update, () => {bulletStatusEffectZone(statusEffect, shieldEffect, lineEffect, particleEffect, size, bullet, damageSelf, damage, immuneBlock)});
	return bullet;
};

const pyraBullet = zoneBullet(StatusEffects.burning, fx.pyraSquare, fx.pyraLineSquare, fx.enflamed, 12, false, true, new Seq, BasicBulletType, {
	speed: 4,
	damage: 10,
	width: 8,
	height: 11
})

 module.exports = {
	 zoneBullet: zoneBullet,
	 pyraBullet: pyraBullet
 };