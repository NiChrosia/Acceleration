const statusEffects = require("effects/status-effects");
const effects = require("effects/effects");
const functions = require("lib/functions");
const liquidBullets = require("bullets/liquid-bullets");

function puddleStatusEffectZone(statusEffect, effect, size, liquid) {
	Groups.puddle.each(puddle => {
		let tile = puddle.tileOn();
		if (puddle.liquid == liquid) {
			effect.at(puddle.x, puddle.y)
			Units.nearby(puddle.x - (size / 2), puddle.y - (size / 2), size, size, u => {
				if (!u.isDead) {
					u.apply(statusEffect, 40);
				};
			});
		};
	});
};

function bulletStatusEffectZone(statusEffect, effect, size, matchBullet) {
	Groups.bullet.each(bullet => {
		let tile = bullet.tileOn();
		if (bullet.type == matchBullet) {
			effect.at(bullet.x, bullet.y)
			Units.nearby(bullet.x - (size / 2), bullet.y - (size / 2), size, size, u => {
				if (!u.isDead) {
					u.apply(statusEffect, 40);
				};
			});
		};
	});
};

function glaciafreezeZone() {
	puddleStatusEffectZone(statusEffects.permafrost, effects.glaciafreezeSquare, 48, functions.cliquid("glaciafluid"));
	// bulletStatusEffectZone(statusEffects.permafrost, effects.glaciafreezeSquareSmall, 24, liquidBullets.glaciafluidShot);
};

Events.run(Trigger.update, glaciafreezeZone);