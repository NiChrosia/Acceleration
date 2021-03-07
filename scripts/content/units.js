// Imports

const util = require("lib/util");

// Loading

require("content/units/core/delta");

require("content/units/lightning-air/ion");
require("content/units/lightning-air/spark");
require("content/units/lightning-air/energy");
require("content/units/lightning-air/lightning");
require("content/units/lightning-air/tempest");

// Assignments

const delta = util.cunit("delta");

const ion = util.cunit("ion");
const spark = util.cunit("spark");
const energy = util.cunit("energy");
const lightning = util.cunit("lightning");
const tempest = util.cunit("tempest");

// Exports

module.exports = {
	// Core units
	delta: delta,
	
	// Lightning air
	ion: ion,
	spark: spark,
	energy: energy,
	lightning: lightning,
	tempest: tempest
};