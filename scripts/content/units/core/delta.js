const healRepeater = require("content/weapons/heal-repeater");

const deltaWeapons = Seq.with(
	healRepeater
)

const delta = extend(UnitType, "delta", {
	defaultController: () => extend(BuilderAI, {}),
	flying: true,
	speed: 3.75,
	rotateSpeed: 21,
	drag: 0.05,
	accel: 0.12,
	health: 250,
	range: 330,
	aimDst: 25, // Change later according to lifetime of bullet
	commandLimit: 6,
	ammoType: AmmoTypes.copper,
	itemCapacity: 90,
	mineTier: 3, // Might change later, can currently mine titanium
	mineSpeed: 10,
	buildSpeed: 1.25,
	hitSize: 13,
	canHeal: true,
	weapons: deltaWeapons
});

delta.constructor = () => extend(UnitEntity, {});