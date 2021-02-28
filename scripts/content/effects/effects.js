// Constants

const arctiColor = Color.valueOf("42E3E3");
const glaciaColor = Color.valueOf("2FADAD");
const corroColor = Color.valueOf("8FFE09");
const quarkColor = Color.valueOf("E0E0E0");
const purple = Color.valueOf("af4dd6");
const fusionColor = Color.valueOf("55a4e0");

// Effects

const arctifreeze = Effect(40, e => {
	Draw.color(arctiColor);
	Draw.z(120)
	Angles.randLenVectors(e.id, 2, 1 + e.fin() * 2, (x, y) => {
		Fill.circle(e.x + x, e.y + y, e.fout() * 1.2);
	});
});

const glaciafreeze = Effect(40, e => {
	Draw.color(glaciaColor);
	Draw.z(120)
	Angles.randLenVectors(e.id, 2, 1 + e.fin() * 2, (x, y) => {
		Fill.circle(e.x + x, e.y + y, e.fout() * 1.2);
	});
});

const corroding = Effect(40, e => {
	Draw.color(corroColor);
	Draw.z(120)
	Angles.randLenVectors(e.id, 2, 1 + e.fin() * 2, (x, y) => {
		Fill.circle(e.x + x, e.y + y, e.fout() * 1.2);
	});
});

const glaciafreezeSquare = Effect(40, e => {
	let size = 48
	size *= 0.75
	Draw.color(glaciaColor);
	Draw.z(Layer.shields);
	Fill.poly(e.x, e.y, 4, size, 45);
});

const glaciafreezeLineSquare = Effect(40, e => {
	let size = 48
	size *= 0.75
	Draw.color(glaciaColor);
	Draw.z(Layer.shields + 1);
	Lines.poly(e.x, e.y, 4, size, 45);
});

const glaciafreezeSquareSmall = Effect(5, e => {
	let size = 12
	size *= 0.75
	Draw.color(glaciaColor);
	Draw.z(Layer.shields);
	Fill.poly(e.x, e.y, 4, size, 45);
});

const purpleLaserCharge = new Effect(80, 100, e => {
	Draw.color(purple);
	Lines.stroke(e.fin() * 2);
	Lines.circle(e.x, e.y, 4 + e.fout() * 100);

	Fill.circle(e.x, e.y, e.fin() * 20);

	Angles.randLenVectors(e.id, 20, 40 * e.fout(), (x, y) => {
		Fill.circle(e.x + x, e.y + y, e.fin() * 5);
	});

	Fill.circle(e.x, e.y, e.fin() * 10);
});

const purpleLaserChargeSmall = new Effect(80, 100, e => {
	Draw.color(purple);
	Lines.stroke(e.fin() * 2);
	Lines.circle(e.x, e.y, e.fout() * 50);
});

const liquefying = Effect(40, e => {
	Draw.color(quarkColor, Color.white, e.fout() / 5 + Mathf.randomSeedRange(e.id, 0.12));

	Angles.randLenVectors(e.id, 2, 1 + e.fin() * 3, (x, y) => {
		Fill.circle(e.x + x, e.y + y, .2 + e.fout() * 1.2);
	});
});

const liquefyingSquare = Effect(40, e => {
	let size = 48
	size *= 0.75
	Draw.color(quarkColor);
	Draw.z(Layer.shields);
	Fill.poly(e.x, e.y, 4, size, 45);
});

const liquefyingLineSquare = Effect(40, e => {
	let size = 48
	size *= 0.75
	Draw.color(quarkColor);
	Draw.z(Layer.shields + 1);
	Lines.poly(e.x, e.y, 4, size, 45);
});

// Explosion radius for the fusion reactor.
const explosionRadius = 48;

const fusionShockwave = Effect(13, 300, e => {
    Draw.color(fusionColor, Color.lightGray, e.fin());
    Lines.stroke(e.fout() * 4 + 0.2);
    Lines.circle(e.x, e.y, e.fin() * (explosionRadius * 9));
});

const fusionCloud = Effect(140, 400, e => {
    Angles.randLenVectors(e.id, 20, e.finpow() * (explosionRadius * 7), (x, y) => {
        let size = e.fout() * 15;
        Draw.color(fusionColor, Color.lightGray, e.fin());
        Fill.circle(e.x + x, e.y + y, size / 2);
    });
});

// Exports

module.exports = {
	arctifreeze: arctifreeze,
	glaciafreeze: glaciafreeze,
	corroding: corroding,
	glaciafreezeSquare: glaciafreezeSquare,
	glaciafreezeLineSquare: glaciafreezeLineSquare,
	glaciafreezeSquareSmall: glaciafreezeSquareSmall,
	purpleLaserCharge: purpleLaserCharge,
	purpleLaserChargeSmall: purpleLaserChargeSmall,
	liquefying: liquefying,
	liquefyingSquare: liquefyingSquare,
	liquefyingLineSquare: liquefyingLineSquare,
	fusionCloud: fusionCloud,
	fusionShockwave: fusionShockwave
};