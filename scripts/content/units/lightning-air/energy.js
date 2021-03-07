const energy = extend(UnitType, "energy", {
	ammoType: AmmoTypes.power,
	health: 650,
	flying: true,
	speed: 3.5,
	weapons: UnitTypes.quasar.weapons
});

energy.constructor = () => extend(UnitEntity, {});