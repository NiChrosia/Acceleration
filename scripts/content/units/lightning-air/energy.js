const energy = extend(UnitType, "energy", {
	ammoType: AmmoTypes.power,
	health: 650,
	flying: true,
	speed: 3.5,
	weapons: UnitTypes.quasar.weapons,
	drag: 0.03,
	buildSpeed: 2,
	mineSpeed: 6,
	mineTier: 3
});

energy.constructor = () => extend(UnitEntity, {});