// Imports

const util = require("lib/util");

// Loading

require("content/liquids/arctifluid");
require("content/liquids/quark-plasma");

// Assignments

const arctifluid = util.cliquid("arctifluid");
const quarkPlasma = util.cliquid("quark-plasma");

// Exports

module.exports = {
	arctifluid: arctifluid,
	quarkPlasma: quarkPlasma
};