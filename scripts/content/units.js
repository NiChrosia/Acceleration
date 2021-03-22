// Imports
let {cunit} = require("lib/util");

// Loading
require("libs/loader")({
    units: {
        core: {
            delta: 0
        },
        "lightning-air": {
            ion: 0,
            spark: 0,
            energy: 0,
            lightning: 0,
            tempest: 0
        }
    }
});


// Assignments
const delta = cunit("delta");

const ion = cunit("ion");
const spark = cunit("spark");
const energy = cunit("energy");
const lightning = cunit("lightning");
const tempest = cunit("tempest");

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
