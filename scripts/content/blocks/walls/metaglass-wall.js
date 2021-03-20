const reflectiveWall = require("lib/reflective-wall");

const metaglassWall = reflectiveWall("metaglass-wall", 100, {
	size: 1,
	health: 360,
	buildVisibility: BuildVisibility.shown,
	category: Category.defense,
	requirements: ItemStack.with(
		Items.metaglass, 5,
		Items.titanium, 2
	)
})