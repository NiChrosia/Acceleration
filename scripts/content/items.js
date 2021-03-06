// Imports

const util = require("lib/util");

// Loading

require("content/items/aerogel");
require("content/items/fusion-compound");

// Assignments

const aerogel = util.citem("aerogel");
const fusionCompound = util.citem("fusion-compound");

// Exports

module.exports = {
	aerogel: aerogel,
	fusionCompound: fusionCompound
};