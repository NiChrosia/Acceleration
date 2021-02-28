// Puddle Status Zone

function puddleStatusEffectZone(statusEffect, effect, alternateEffect, size, liquid) {
	Groups.puddle.each(puddle => {
		let tile = puddle.tileOn();
		if (puddle.liquid == liquid) {
			Core.settings.getBool("puddle-status-zone") ? effect.at(puddle.x, puddle.y) : alternateEffect.at(puddle.x, puddle.y);
			Units.nearby(puddle.x - (size / 2), puddle.y - (size / 2), size, size, u => {
				if (!u.isDead) {
					u.apply(statusEffect, 40);
				};
			});
		};
	});
};

// Bullet Status Zone

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

// Exports

module.exports = {
	puddleStatusEffectZone: puddleStatusEffectZone,
	bulletStatusEffectZone: bulletStatusEffectZone
};