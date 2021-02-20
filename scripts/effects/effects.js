// Effects

const arctifreeze = Effect(40, e => {
	Draw.color(Color.valueOf("42E3E3"));
	Angles.randLenVectors(e.id, 2, 1 + e.fin() * 2, (x, y) => {
		Fill.circle(e.x + x, e.y + y, e.fout() * 1.2);
	});
});

const glaciafreeze = Effect(40, e => {
	Draw.color(Color.valueOf("215B60"));
	Angles.randLenVectors(e.id, 2, 1 + e.fin() * 2, (x, y) => {
		Fill.circle(e.x + x, e.y + y, e.fout() * 1.2);
	});
});

const corroding = Effect(40, e => {
	Draw.color(Color.valueOf("8FFE09"));
	Angles.randLenVectors(e.id, 2, 1 + e.fin() * 2, (x, y) => {
		Fill.circle(e.x + x, e.y + y, e.fout() * 1.2);
	});
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
	damage: 10,
	permanent: true,
	healthMultiplier: 1.3,
	effectChance: 0.35,
	effect: glaciafreeze,
	color: Color.valueOf("215B60")
});

const corroded = extend(StatusEffect, "corroded", {
	damage: 0.5,
	effect: corroding,
	color: Color.valueOf("215B60")
});

// Export

module.exports = {
	arctifreezing: arctifreezing,
	glaciafreezing: glaciafreezing,
	corroded: corroded
};