const spark = extend(UnitType, "spark", {
	ammoType: AmmoTypes.power,
	health: 350,
	flying: true,
	speed: 3.2,
	weapons: UnitTypes.quasar.weapons,
	drag: 0.05,
	buildSpeed: 1,
	mineSpeed: 4,
	mineTier: 2
});

spark.constructor = () => extend(UnitEntity, {});