// Imports
let {citem} = require("lib/util");

// Loading
require("lib/loader")({
    items: {
        aerogel: 0,
        "fusion-compound": 0
    }
});

// Assignments
const aerogel = citem("aerogel");
const fusionCompound = citem("fusion-compound");

// Exports
module.exports = {
    aerogel: aerogel,
    fusionCompound: fusionCompound
};
