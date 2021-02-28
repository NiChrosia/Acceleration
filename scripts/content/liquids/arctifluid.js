const msfx = require("content/effects/status-effects");

const arctifluid = extend(Liquid, "arctifluid", {
	heatCapacity: 1.3,
	temperature: 0.05,
	effect: msfx.arctifreezing,
	color: Color.valueOf("42E3E3")
});