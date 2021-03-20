const reflectiveWall = require("lib/reflective-wall");

const metaglassWallLarge = reflectiveWall("metaglass-wall-large", 100, {
	size: 2,
	health: 1440,
	buildVisibility: BuildVisibility.shown,
	category: Category.defense,
	requirements: ItemStack.with(
		Items.metaglass, 20,
		Items.titanium, 8
	)
})