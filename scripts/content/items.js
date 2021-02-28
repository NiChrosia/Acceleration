// Imports

const util = require("lib/util");

// Loading

require("content/items/aerogel");
require("content/items/diamond");
require("content/items/fusion-compound");
require("content/items/sulfur");

// Assignments

const aerogel = util.citem("aerogel");
const diamond = util.citem("diamond");
const fusionCompound = util.citem("fusion-compound");
const sulfur = util.citem("sulfur");

// Exports

module.exports = {
	aerogel: aerogel,
	diamond: diamond,
	fusionCompound: fusionCompound,
	sulfur: sulfur
};