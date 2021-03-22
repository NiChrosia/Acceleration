// Imports
let {cliquid} = require("lib/util");

// Loading
require("lib/loader")({
    liquids: {
        arctifluid: 0,
        "quark-plasma": 0
    }
});

// Assignments
const arctifluid = cliquid("arctifluid");
const quarkPlasma = cliquid("quark-plasma");

// Exports
module.exports = {
    arctifluid: arctifluid,
    quarkPlasma: quarkPlasma
};
