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
	reloadMultiplier: 0.6,
	damage: 0.5,
	permanent: true,
	healthMultiplier: 1.2,
	effectChance: 0.3,
	effect: arctifreeze,
	color: Color.valueOf("42E3E3")
});

const glaciafreezing = extend(StatusEffect, "glaciafreezing", {
	speedMultiplier: 0.35,
	reloadMultiplier: 0.25,
	damage: 10,
	permanent: false,
	healthMultiplier: 1.3,
	effectChance: 0.35,
	effect: glaciafreeze,
	color: Color.valueOf("215B60")
});

const permafrost = extend(StatusEffect, "permafrost", {
	update(unit, time) {
		this.super$update(unit, time);
		if (Mathf.chance(0.1 * Time.delta)) {
			let size = 24
			Units.nearby(unit.team, unit.x - (size / 2), unit.y - (size / 2), 24, 24, cons(u => {
				if (Mathf.dst(unit.x, unit.y, u.x, u.y) < 40 && !u.isDead && u.team == unit.team) {
					u.apply(glaciafreezing, time);
					u.apply(arctifreezing, time);
				};
			}));
		};
	}
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
	permafrost: permafrost,
	corroded: corroded
};