// Imports

const {circleEffect, squareShieldEffect, lineSquareEffect, particleEffect} = require("lib/util");

// Constants

const arctiColor = Color.valueOf("42E3E3");
const quarkColor = Color.valueOf("E0E0E0");
const purple = Color.valueOf("af4dd6");
const fusionColor = Color.valueOf("55a4e0");
const explosionRadius = 48;

// Effects

const arctifrozen = particleEffect(arctiColor);
const arctifreeze = circleEffect(arctiColor, 40);
const arctifreezeSquare = squareShieldEffect(48, arctiColor, 40);
const arctifreezeLineSquare = lineSquareEffect(48, arctiColor, 40);

const liquefied = particleEffect(quarkColor);
const liquefying = circleEffect(quarkColor, 40);
const liquefyingSquare = squareShieldEffect(48, quarkColor, 40);
const liquefyingLineSquare = lineSquareEffect(48, quarkColor, 40);

const enflamed = particleEffect(Pal.lightOrange);
const pyraSquare = squareShieldEffect(24, Pal.lightOrange, 12);
const pyraLineSquare = lineSquareEffect(24, Pal.lightOrange, 12);

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

const purpleLaserChargeSmall = new Effect(50, 100, e => {
	Draw.color(purple);
	Lines.stroke(e.fin() * 2);
	Lines.circle(e.x, e.y, e.fout() * 30);
});

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
	arctifreezeSquare: arctifreezeSquare,
	arctifreezeLineSquare: arctifreezeLineSquare,
	purpleLaserCharge: purpleLaserCharge,
	purpleLaserChargeSmall: purpleLaserChargeSmall,
	liquefying: liquefying,
	liquefyingSquare: liquefyingSquare,
	liquefyingLineSquare: liquefyingLineSquare,
	fusionCloud: fusionCloud,
	fusionShockwave: fusionShockwave,
	pyraSquare: pyraSquare,
	pyraLineSquare: pyraLineSquare,
	arctifrozen: arctifrozen,
	liquefied: liquefied,
	enflamed: enflamed
};