// Imports

const util = require("lib/util");

// Loading

require("content/blocks/effect/surge-mine");
require("content/blocks/effect/core-atom");

require("content/blocks/environment/diamond");
require("content/blocks/environment/sulfur");

require("content/blocks/power/fusion-reactor");

require("content/blocks/production/aerogel-weaver");
require("content/blocks/production/arctifluid-synthesizer");
require("content/blocks/production/corrofluid-mixer");
require("content/blocks/production/glaciafluid-extractor");

require("content/blocks/turrets/flood");
require("content/blocks/turrets/harbinger");

// Assignments

const surgeMine = util.cblock("surge-mine");
const coreAtom = util.cblock("core-atom");

const diamond = util.cblock("diamond");
const sulfur = util.cblock("sulfur");

const fusionReactor = util.cblock("fusion-reactor");

const aerogelWeaver = util.cblock("aerogel-weaver");
const arctifluidSynthesizer = util.cblock("arctifluid-synthesizer");
const corrofluidMixer = util.cblock("corrofluid-mixer");
const glaciafluidExtractor = util.cblock("glaciafluid-extractor");

const flood = util.cblock("flood");
const harbinger = util.cblock("harbinger");

// Exports

module.exports = {
	// Effect
	
	surgeMine: surgeMine,
	coreAtom: coreAtom,
	
	// Environment
	
	diamond: diamond,
	sulfur: sulfur,
	
	// Power
	
	fusionReactor: fusionReactor,
	
	// Production
	
	aerogelWeaver: aerogelWeaver,
	arctifluidSynthesizer: arctifluidSynthesizer,
	corrofluidMixer: corrofluidMixer,
	glaciafluidExtractor: glaciafluidExtractor,
	
	// Turrets
	
	flood: flood,
	harbinger: harbinger
};