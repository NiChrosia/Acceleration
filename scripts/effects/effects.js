let functions = require("lib/functions")

const arctifreeze = Effect(40, e => {
	Draw.color(Color.valueOf("42E3E3"));
	Draw.z(120)
	Angles.randLenVectors(e.id, 2, 1 + e.fin() * 2, (x, y) => {
		Fill.circle(e.x + x, e.y + y, e.fout() * 1.2);
	});
});

const glaciafreeze = Effect(40, e => {
	Draw.color(Color.valueOf("215B60"));
	Draw.z(120)
	Angles.randLenVectors(e.id, 2, 1 + e.fin() * 2, (x, y) => {
		Fill.circle(e.x + x, e.y + y, e.fout() * 1.2);
	});
});

const corroding = Effect(40, e => {
	Draw.color(Color.valueOf("8FFE09"));
	Draw.z(120)
	Angles.randLenVectors(e.id, 2, 1 + e.fin() * 2, (x, y) => {
		Fill.circle(e.x + x, e.y + y, e.fout() * 1.2);
	});
});

const glaciafreezeSquare = Effect(40, e => {
	let size = 48
	size *= 0.75
	Draw.color(Color.valueOf("2FADAD"));
	Draw.z(Layer.shields);
	Fill.poly(e.x, e.y, 4, size, 45);
});

module.exports = {
	arctifreeze: arctifreeze,
	glaciafreeze: glaciafreeze,
	corroding: corroding,
	glaciafreezeSquare: glaciafreezeSquare
};