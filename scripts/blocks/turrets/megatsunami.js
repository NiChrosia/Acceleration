const functions = require("lib/functions")
const liquidBullets = require("bullets/liquid-bullets")

const megatsunami = extend(LiquidTurret, "megatsunami", {
	solid: true,
	health: 4850,
	size: 4,
	category: Category.turret,
	requirements: ItemStack.with(
		Items.surgeAlloy, 350, 
		functions.citem("aerogel"), 120, 
		Items.thorium, 200, 
		Items.titanium, 150, 
		Items.lead, 150, 
		Items.metaglass, 120
	),
	buildVisibility: BuildVisibility.shown,
	reloadTime: 0.1,
	inaccuracy: 5.5,
	range: 240,
	rotateSpeed: 12,
	extinguish: true
});

megatsunami.ammo(
	Liquids.slag, liquidBullets.massiveSlagShot,
	Liquids.oil, liquidBullets.massiveOilShot,
	functions.cliquid("corrofluid"), liquidBullets.corrofluidShot,
	Liquids.water, liquidBullets.massiveWaterShot,
	Liquids.cryofluid, liquidBullets.massiveCryoShot,
	functions.cliquid("arctifluid"), liquidBullets.arctifluidShot,
	functions.cliquid("glaciafluid"), liquidBullets.glaciafluidShot
);