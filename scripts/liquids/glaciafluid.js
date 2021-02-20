let Effects = require("effects/effects");

const glaciafluid = extend(Liquid, "glaciafluid", {
	heatCapacity: 4.4,
	temperature: 0.02,
	effect: Effects.permafrost,
	color: Color.valueOf("2FADAD")
});