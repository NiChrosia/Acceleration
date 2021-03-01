// Imports

const util = require("lib/util");

// Loading

require("content/blocks/distribution/aerogel-conveyor");
require("content/blocks/distribution/colossal-driver");

require("content/blocks/effect/surge-mine");
require("content/blocks/effect/core-atom");

require("content/blocks/environment/diamond");
require("content/blocks/environment/diamond-wall");
require("content/blocks/environment/sulfur");
require("content/blocks/environment/cold-water");

require("content/blocks/power/fusion-reactor");

require("content/blocks/production/aerogel-weaver");
require("content/blocks/production/arctifluid-synthesizer");
require("content/blocks/production/corrofluid-mixer");
require("content/blocks/production/glaciafluid-extractor");

require("content/blocks/turrets/flood");
require("content/blocks/turrets/harbinger");

// Assignments

const aerogelConveyor = util.cblock("aerogel-conveyor");
const colossalDriver = util.cblock("colossal-driver");

const surgeMine = util.cblock("surge-mine");
const coreAtom = util.cblock("core-atom");

const diamond = util.cblock("diamond");
const diamondWall = util.cblock("diamond-wall");
const sulfur = util.cblock("sulfur");
const coldWater = util.cblock("cold-water");

const fusionReactor = util.cblock("fusion-reactor");

const aerogelWeaver = util.cblock("aerogel-weaver");
const arctifluidSynthesizer = util.cblock("arctifluid-synthesizer");
const corrofluidMixer = util.cblock("corrofluid-mixer");
const glaciafluidExtractor = util.cblock("glaciafluid-extractor");

const flood = util.cblock("flood");
const harbinger = util.cblock("harbinger");

// Exports

module.exports = {
	// Distribution
	
	aerogelConveyor: aerogelConveyor,
	colossalDriver: colossalDriver,
	
	// Effect
	
	surgeMine: surgeMine,
	coreAtom: coreAtom,
	
	// Environment
	
	diamond: diamond,
	diamondWall: diamondWall,
	sulfur: sulfur,
	coldWater: coldWater,
	
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