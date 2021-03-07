const spark = extend(UnitType, "spark", {
	ammoType: AmmoTypes.power,
	health: 350,
	flying: true,
	speed: 3.2,
	weapons: UnitTypes.quasar.weapons
});

spark.constructor = () => extend(UnitEntity, {});