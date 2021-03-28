// Imports

const util = require("lib/util");
const electricBullets = require("content/bullets/electric-bullets");

// Assignment

const healRepeater = extend(Weapon, "heal-repeater", {
	name: util.ModName + "heal-repeater",
	bullet: electricBullets.deltaHealBullet,
	reload: 16,
	x: 1,
	y: 2,
	shootSound: Sounds.lasershoot
});

// Exports

module.exports = {
	healRepeater: healRepeater
};