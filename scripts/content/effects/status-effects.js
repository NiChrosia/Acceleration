// Imports

const meffects = require("content/effects/effects")

// Status Effects

const arctifreezing = extend(StatusEffect, "arctifreezing", {
	speedMultiplier: 0.75, 
	reloadMultiplier: 0.8,
	damage: 2.5,
	permanent: true,
	healthMultiplier: 1.2,
	effectChance: 0.85,
	effect: meffects.arctifreeze,
	color: Color.valueOf("42E3E3")
});

const liquefying = extend(StatusEffect, "liquefying", {
	damage: 10,
	effectChance: 1,
	color: Color.valueOf("E0E0E0"),
	effect: meffects.liquefying
});

module.exports = {
	arctifreezing: arctifreezing,
	liquefying: liquefying
};

/*
const effect = extend(StatusEffect, "name", {
	update(unit, time) {
		this.super$update(unit, time);
		code();
	}
});
*/