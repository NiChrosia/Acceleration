// Imports
let {cblock} = require("lib/util"); // this is like `import {x, y, z} from "asdf"`

// Loading
require("lib/loader")({ // starts from scripts/content
    blocks: {
        distribution: {
            "aerogel-conveyor": 0,
            "colossal-driver": 0
        },
        storage: {
            repository: 0,
        },
        effect: {
            "surge-mine": 0,
            "core-atom": 0
        },
        environment: {
            liquids: {
                "cold-water": 0
            }
        },
        power: {
            "fusion-reactor": 0
        },
        production: {
            "aerogel-weaver": 0,
            "arctifluid-synthesizer": 0
        },
        turrets: {
            flood: 0,
            harbinger: 0,
            storm: 0
        },
        liquid: {
            "fortified-conduit": 0
        },
        walls: {
            "metaglass-wall": 0,
            "metaglass-wall-large": 0
        }
    }
});

// Assignments
const aerogelConveyor = cblock("aerogel-conveyor");
const colossalDriver = cblock("colossal-driver");

const repository = cblock("repository");

const surgeMine = cblock("surge-mine");
const coreAtom = cblock("core-atom");

const coldWater = cblock("cold-water");

const fusionReactor = cblock("fusion-reactor");

const aerogelWeaver = cblock("aerogel-weaver");
const arctifluidSynthesizer = cblock("arctifluid-synthesizer");

const flood = cblock("flood");
const harbinger = cblock("harbinger");
const storm = cblock("storm");

const fortifiedConduit = cblock("fortified-conduit");

const metaglassWall = cblock("metaglass-wall");
const metaglassWallLarge = cblock("metaglass-wall-large");

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
	
	fortifiedConduit: fortifiedConduit,
	
	// Walls
	
	metaglassWall: metaglassWall,
	metaglassWallLarge: metaglassWallLarge
};
