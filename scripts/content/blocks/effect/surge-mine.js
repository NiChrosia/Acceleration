const surgeMine = extend(ShockMine, "surge-mine", {
	timerDamage: 1,
	cooldown: 120,
	tileDamage: 12,
	damage: 24,
	length: 12,
	tendrils: 8,
	lightningColor: Color.valueOf("F3E979"),
	health: 75,
	size: 1,
	buildVisibility: BuildVisibility.shown,
	category: Category.effect,
	hasShadow: false,
	requirements: ItemStack.with(Items.silicon, 25, Items.phaseFabric, 12, Items.surgeAlloy, 12)
});