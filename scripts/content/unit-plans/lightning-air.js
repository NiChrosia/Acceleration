// Imports

const munits = require("content/units");

// Unit Loader made by EoD

const unitPlans = new Seq(UnitFactory.UnitPlan);

const addPlan = (fac, plan) => {
  fac.plans.add(plan);
};

const reconAdd = (recon, planArray) => {
  for(var i = 0; i < planArray.length; i++){
    recon.upgrades.add(Seq(planArray[i]).toArray(UnitType));
  };
};

// Factory plans

const ion = new UnitFactory.UnitPlan(
  munits.ion,
  60 * 25,
  ItemStack.with(
    Items.silicon, 1
  )
);

// Air factory additions

addPlan(Blocks.airFactory, ion);

// Reconstructors

// 1 -> 2

reconAdd(Blocks.additiveReconstructor, [
	[
		munits.ion,
		munits.spark
	]
]);

// 2 -> 3

reconAdd(Blocks.multiplicativeReconstructor, [
	[
		munits.spark,
		munits.energy
	]
]);

// 3 -> 4

reconAdd(Blocks.exponentialReconstructor, [
	[
		munits.energy,
		munits.lightning
	]
]);

// 4 -> 5

reconAdd(Blocks.tetrativeReconstructor, [
	[
		munits.lightning,
		munits.tempest
	]
]);