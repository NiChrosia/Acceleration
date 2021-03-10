const lightning = extend(UnitType, "lightning", {
	ammoType: AmmoTypes.powerHigh,
	health: 7500,
	flying: true,
	speed: 4,
	weapons: UnitTypes.vela.weapons,
	drag: 0.02,
	buildSpeed: 3.5,
	mineSpeed: 8,
	mineTier: 4
});

lightning.constructor = () => extend(UnitEntity, {});