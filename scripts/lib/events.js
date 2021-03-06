// Imports

const util = require("lib/util");

// Puddle Status Zone

function puddleStatusEffectZone(statusEffect, effect, alternateEffect, size, liquid, damage) {
	if (!Vars.state.paused) {
		Groups.puddle.each(puddle => {
			let tile = puddle.tileOn();
			if (puddle.liquid == liquid) {
				Core.settings.getBool("puddle-status-zone") ? effect.at(puddle.x, puddle.y) : alternateEffect.at(puddle.x, puddle.y);
				Units.nearby(puddle.x - (size / 2), puddle.y - (size / 2), size, size, u => {
					if (!u.isDead) {
						u.apply(statusEffect, 40);
					};
				});
				if (damage) {
					Groups.build.each(b => {
						// print("b.x: " + b.x / 8 + ", b.y: " + b.y / 8 + ", tile.x: " + tile.x + ", tile.y: " + tile.y + ", distance: " + size + ", is distance: " + util.dst(b.x / 8, b.y / 8, tile.x, tile.y, size))
						if (util.dst(b.x / 8, b.y / 8, tile.x, tile.y, size / 8)) {
							b.damage(statusEffect.damage)
						};
					});
				};
			};
		});
	};
};

// Bullet Status Zone

function bulletStatusEffectZone(statusEffect, effect, size, matchBullet) {
	if (!Vars.state.paused) {
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
};

// Exports

module.exports = {
	puddleStatusEffectZone: puddleStatusEffectZone,
	bulletStatusEffectZone: bulletStatusEffectZone
};