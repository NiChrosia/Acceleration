let Effects = require("effects/effects");

const arctifluid = extend(Liquid, "arctifluid", {
	heatCapacity: 2.4,
	temperature: 0.05,
	effect: Effects.arctifreezing,
	color: Color.valueOf("42E3E3")
});