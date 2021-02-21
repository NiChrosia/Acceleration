let Effects = require("effects/effects");
let functions = require("functions");

const glaciafluid = extend(Liquid, "glaciafluid", {
	heatCapacity: 4.6,
	temperature: 0.02,
	effect: Effects.permafrost,
	color: Color.valueOf("2FADAD")
});

function statusEffectZone() {
	Groups.puddle.each(puddle => {
		let tile = puddle.tileOn();
		let size = 48;
		Effects.glaciafreezeSquare.at(puddle.x, puddle.y)
		if (puddle.liquid == glaciafluid) {
			Units.nearby(puddle.x - (size / 2), puddle.y - (size / 2), size, size, u => {
				if (!u.isDead) {
					u.apply(Effects.permafrost, 40);
				};
			});
		};
	});
};

Events.run(Trigger.update, statusEffectZone);