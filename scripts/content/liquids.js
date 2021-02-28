// Imports

const util = require("lib/util");

// Loading

require("content/liquids/arctifluid");
require("content/liquids/corrofluid");
require("content/liquids/glaciafluid");
require("content/liquids/quark-plasma");

// Assignments

const arctifluid = util.cliquid("arctifluid");
const corrofluid = util.cliquid("corrofluid");
const glaciafluid = util.cliquid("glaciafluid");
const quarkPlasma = util.cliquid("quark-plasma");

// Exports

module.exports = {
	arctifluid: arctifluid,
	corrofluid: corrofluid,
	glaciafluid: glaciafluid,
	quarkPlasma: quarkPlasma
};