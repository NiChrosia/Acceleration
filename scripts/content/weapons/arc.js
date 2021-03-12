// Imports

const util = require("lib/util");
const electricBullets = require("content/bullets/electric-bullets");

// Assignment

const arcWeapon = extend(Weapon, "arc-weapon", {
	bullet: electricBullets.lightArc,
	name: util.ModName + "arc-weapon",
	reload: 35,
	x: 4,
	y: 1,
	shots: 2
});

module.exports = {
	arcWeapon: arcWeapon
}