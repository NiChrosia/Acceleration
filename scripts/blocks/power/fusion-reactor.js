const functions = require("lib/functions");

const fusionReactor = extend(ImpactReactor, "fusion-reactor", {
	size: 5,
	health: 1250,
	powerProduction: 280,
	category: Category.power,
	buildVisibility: BuildVisibility.shown
});

fusionReactor.consumes.items(ItemStack.with(functions.citem("fusion-compound"), 1))
fusionReactor.consumes.liquid(functions.cliquid("quark-plasma"), 0.3);
fusionReactor.consumes.power(70);