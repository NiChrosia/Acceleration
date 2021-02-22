const statusEffects = require("effects/status-effects");
const effects = require("effects/effects")

function statusEffectZone(statusEffect, effect, size) {
	Groups.puddle.each(puddle => {
		let tile = puddle.tileOn();
		effect.at(puddle.x, puddle.y)
		if (puddle.liquid == glaciafluid) {
			Units.nearby(puddle.x - (size / 2), puddle.y - (size / 2), size, size, u => {
				if (!u.isDead) {
					u.apply(statusEffect, 40);
				};
			});
		};
	});
};

function glaciafreezeZone() {
	statusEffectZone(statusEffects.permafrost, effects.glaciafreezeSquare, 48)
};

Events.run(Trigger.update, glaciafreezeZone);