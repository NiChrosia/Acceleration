// Effects

const arctifreeze = extend(Effect, {
	color: Color.valueOf("42E3E3"),
	renderer: Fx.freezing.renderer
});

const glaciafreeze = extend(Effect, {
	color: Color.valueOf("215B60"),
	renderer: Fx.freezing.renderer
});

// Status Effects

const arctifreezing = extend(StatusEffect, "cryofreezing", {
	speedMultiplier: 0.65, 
	reloadMultiplier: 0.4,
	damage: 1,
	permanent: false,
	healthMultiplier: 1.2,
	effectChance: 0.3,
	effect: arctifreeze,
	color: Color.valueOf("42E3E3")
});

const glaciafreezing = extend(StatusEffect, "glaciafreezing", {
	speedMultiplier: 0.35,
	reloadMultiplier: 0.25,
	damage: 2.5,
	permanent: true,
	healthMultiplier: 1.3,
	effectChance: 0.35,
	effect: glaciafreeze,
	color: Color.valueOf("215B60")
});

// Export

module.exports = {
	arctifreezing: arctifreezing,
	glaciafreezing: glaciafreezing
};