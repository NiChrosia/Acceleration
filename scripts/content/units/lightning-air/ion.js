// Constants

const lightningDamage = 5;
const lightningLength = 10;
const lightningChance = 0.1;
const lightningOffset = 0;
const lightningColor = Color.valueOf("71a6de");
const minSpeed = 3;
const maxSpeed = 5;

// Assignment

const ion = extend(UnitType, "ion", {
	ammoType: AmmoTypes.power,
	flying: true,
	health: 100,
	speed: 3,
	weapons: UnitTypes.pulsar.weapons
});

ion.constructor = () => extend(UnitEntity, {});

ion.abilities.add(new MoveLightningAbility(
	lightningDamage * Vars.state.rules.unitDamageMultiplier, 
	lightningLength, 
	lightningChance, 
	lightningOffset, 
	minSpeed, 
	maxSpeed, 
	lightningColor/*, 
	"ion-heat"*/
));