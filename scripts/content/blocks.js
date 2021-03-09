// Imports

const util = require("lib/util");

// Loading

require("content/blocks/distribution/aerogel-conveyor");
require("content/blocks/distribution/colossal-driver");

require("content/blocks/storage/repository");

require("content/blocks/effect/surge-mine");
require("content/blocks/effect/core-atom");

require("content/blocks/environment/liquids/cold-water");

require("content/blocks/power/fusion-reactor");

require("content/blocks/production/aerogel-weaver");
require("content/blocks/production/arctifluid-synthesizer");

require("content/blocks/turrets/flood");
require("content/blocks/turrets/harbinger");
require("content/blocks/turrets/storm");

require("content/blocks/liquid/fortified-conduit");

// Assignments

const aerogelConveyor = util.cblock("aerogel-conveyor");
const colossalDriver = util.cblock("colossal-driver");

const repository = util.cblock("repository");

const surgeMine = util.cblock("surge-mine");
const coreAtom = util.cblock("core-atom");

const coldWater = util.cblock("cold-water");

const fusionReactor = util.cblock("fusion-reactor");

const aerogelWeaver = util.cblock("aerogel-weaver");
const arctifluidSynthesizer = util.cblock("arctifluid-synthesizer");

const flood = util.cblock("flood");
const harbinger = util.cblock("harbinger");
const storm = util.cblock("storm");

const fortifiedConduit = util.cblock("fortified-conduit");

// Exports

module.exports = {
	// Distribution
	
	aerogelConveyor: aerogelConveyor,
	colossalDriver: colossalDriver,
	
	// Storage
	
	repository: repository,
	
	// Effect
	
	surgeMine: surgeMine,
	coreAtom: coreAtom,
	
	// Environment
	
	coldWater: coldWater,
	
	// Power
	
	fusionReactor: fusionReactor,
	
	// Production
	
	aerogelWeaver: aerogelWeaver,
	arctifluidSynthesizer: arctifluidSynthesizer,
	
	// Turrets
	
	flood: flood,
	harbinger: harbinger,
	storm: storm,
	
	// Liquid
	
	fortifiedConduit: fortifiedConduit
};