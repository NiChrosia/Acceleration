// Imports

const arcWeapon = require("content/weapons/arc").arcWeapon;

// Constants

const ionWeapons = Seq.with(
	arcWeapon
);

// Assignment

const ion = extend(UnitType, "ion", {
	ammoType: AmmoTypes.power,
	flying: true,
	health: 100,
	speed: 3,
	weapons: ionWeapons,
	drag: 0.06,
	buildSpeed: 0.25,
});

ion.constructor = () => extend(UnitEntity, {});