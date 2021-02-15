/*
seperates hydrogen and oxygen from water
applies electric current to water, resulting in chlorine
combines chlorine and hydrogen to make hydrochloric acid (corrofluid)
*/
const corrofluidMixer = extend(GenericCrafter, "corrofluid-mixer", {
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
	outputLiquid: new LiquidStack(Vars.content.getByName(ContentType.liquid, "acceleration-corrofluid"), 30),
	craftTime: 120,
	buildVisibility: BuildVisibility.shown
});

corrofluidMixer.consumes.power(1.5);
corrofluidMixer.consumes.items(ItemStack.with(Vars.content.getByName(ContentType.item, "acceleration-sulfur"), 3));
corrofluidMixer.consumes.liquid(Liquids.water, 0.2)