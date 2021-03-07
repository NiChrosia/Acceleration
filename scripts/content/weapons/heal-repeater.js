// Imports

const util = require("lib/util");
const electricBullets = require("content/bullets/electric-bullets");

// Assignment

const healRepeater = extend(Weapon, "heal-repeater", {
	/*load() {
		this.region = Core.atlas.find(util.ModName + "heal-repeater");
		this.heatRegion = Core.atlas.find(util.ModName + "heal-repeater" + "-heat");
		this.outlineRegion = Core.atlas.find(util.ModName + "heal-repeater" + "-outline");
	},*/
	name: util.ModName + "heal-repeater",
	bullet: electricBullets.deltaHealBullet,
	reload: 16,
	x: 1,
	y: 2
});

// Exports

module.exports = {
	healRepeater: healRepeater
};