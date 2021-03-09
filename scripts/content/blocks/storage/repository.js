const repository = extend(StorageBlock, "repository", {
	health: 720,
	size: 6,
	itemCapacity: 4000,
	buildVisibility: BuildVisibility.shown,
	requirements: ItemStack.with(
		Items.titanium, 750,
		Items.thorium, 500,
		Items.silicon, 200,
		Items.surgeAlloy, 50
	),
	category: Category.effect
});