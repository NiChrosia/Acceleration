const tempest = extend(UnitType, "tempest", {
	ammoType: AmmoTypes.powerHigh,
	health: 24000,
	flying: true,
	speed: 6.5,
	weapons: UnitTypes.corvus.weapons
});

tempest.constructor = () => extend(UnitEntity, {});