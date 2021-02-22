let statusEffects = require("effects/status-effects");

const corrofluid = extend(Liquid, "corrofluid", {
	color: Color.valueOf("8FFE09"),
	explosiveness: 0.15,
	effect: statusEffects.corroded,
	heatCapacity: 0.4,
	flammability: 0.6,
	temperature: 0.3,
	viscosity: 0.4
});