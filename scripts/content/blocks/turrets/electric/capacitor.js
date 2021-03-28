const zoneBullets = require("content/bullets/zone-bullets");

const capacitor = extend(ItemTurret, "capacitor", {
	rotateSpeed: 12,
	health: 1650,
	range: 210,
	reloadTime: 48,
	size: 3,
	buildVisibility: BuildVisibility.shown,
	category: Category.turret
});

capacitor.consumes.power(3)

capacitor.ammo(
	Items.pyratite, zoneBullets.pyraBullet
)