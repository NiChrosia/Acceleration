const tempest = extend(UnitType, "tempest", {
	ammoType: AmmoTypes.powerHigh,
	health: 24000,
	flying: true,
	speed: 6.5,
	weapons: UnitTypes.corvus.weapons,
	drag: 0.01,
	buildSpeed: 5,
	mineSpeed: 12,
	mineTier: 4
});

tempest.constructor = () => extend(UnitEntity, {});