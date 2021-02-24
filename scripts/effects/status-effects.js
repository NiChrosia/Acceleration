const effects = require("effects/effects")

const arctifreezing = extend(StatusEffect, "arctifreezing", {
	speedMultiplier: 0.75, 
	reloadMultiplier: 0.8,
	damage: 0.5,
	permanent: true,
	healthMultiplier: 1.2,
	effectChance: 0.85,
	effect: effects.arctifreeze,
	color: Color.valueOf("42E3E3")
});

const glaciafreezing = extend(StatusEffect, "glaciafreezing", {
	speedMultiplier: 0.55,
	reloadMultiplier: 0.6,
	permanent: false,
	damage: 5,
	healthMultiplier: 1.3,
	effectChance: 0.75,
	effect: effects.glaciafreeze,
	color: Color.valueOf("215B60")
});

const permafrost = extend(StatusEffect, "permafrost", {
	update(unit, time) {
		this.super$update(unit, time);
		if (Mathf.chance(0.85 * Time.delta)) {
			unit.apply(glaciafreezing, time);
			unit.apply(arctifreezing, time);
		};
	}
});

const corroded = extend(StatusEffect, "corroded", {
	damage: 0.5,
	effect: effects.corroding,
	color: Color.valueOf("215B60")
});

module.exports = {
	arctifreezing: arctifreezing,
	glaciafreezing: glaciafreezing,
	permafrost: permafrost,
	corroded: corroded
};