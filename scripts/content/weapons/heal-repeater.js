// Imports

const util = require("lib/util");

// Assignment

const healRepeater = extend(Weapon, "heal-repeater", {
	/*load() {
		this.region = Core.atlas.find(util.ModName + "heal-repeater");
		this.heatRegion = Core.atlas.find(util.ModName + "heal-repeater" + "-heat");
		this.outlineRegion = Core.atlas.find(util.ModName + "heal-repeater" + "-outline");
	},*/
	name: util.ModName + "heal-repeater",
	bullet: UnitTypes.nova.weapons.get(0).bullet,
	reload: 10,
	x: 1,
	y: 2
});

// Exports

module.exports = {
	healRepeater: healRepeater
};