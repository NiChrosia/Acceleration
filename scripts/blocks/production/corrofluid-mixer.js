const corrofluidMixer = extend(LiquidConverter, "corrofluid-mixer", {
	health: 100,
	size: 2,
	solid: true,
	hasPower: true,
	hasLiquids: true,
	hasItems: true,
	itemCapacity: 15,
	liquidCapacity: 10,
	requirements: ItemStack.with(Items.copper, 1),
	category: Category.crafting,
	// make 6 liquid in 1 second
	outputLiquid: new LiquidStack(Vars.content.getByName(ContentType.liquid, "acceleration-corrofluid"), 24),
	craftTime: 120,
	buildVisibility: BuildVisibility.shown,
	drawer: DrawMixer()
});

corrofluidMixer.consumes.power(1.5);
corrofluidMixer.consumes.items(ItemStack.with(Vars.content.getByName(ContentType.item, "acceleration-sulfur"), 3));
corrofluidMixer.consumes.liquid(Liquids.water, 0.2);