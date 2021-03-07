const lightning = extend(UnitType, "lightning", {
	ammoType: AmmoTypes.powerHigh,
	health: 7500,
	flying: true,
	speed: 4,
	weapons: UnitTypes.vela.weapons
});

lightning.constructor = () => extend(UnitEntity, {});