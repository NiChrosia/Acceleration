const statusEffects = require("effects/status-effects");

const quarkPlasma = extend(Liquid, "quark-plasma", {
	temperature: 15,
	color: Color.valueOf("E0E0E0"),
	effect: statusEffects.liquefying,
	liquidCapacity: 0
});