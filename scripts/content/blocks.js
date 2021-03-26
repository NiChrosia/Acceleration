// Imports

let {cblock} = require("lib/util"); // let {x} from y - from y import x

// Loading

require("lib/loader")({ // Starts from scripts/content
    blocks: {
        effect: {
            "core-atom": 0
        },
        environment: {
            liquids: {
                "cold-water": 0
            }
        },
        production: {
            "arctifluid-synthesizer": 0
        },
        walls: {
            "metaglass-wall": 0,
            "metaglass-wall-large": 0
        },
		turrets: {
			electric: {
				transistor: 0,
				gate: 0
			}
		}
    }
});

// Assignments

const coreAtom = cblock("core-atom");

const coldWater = cblock("cold-water");

const arctifluidSynthesizer = cblock("arctifluid-synthesizer");

const metaglassWall = cblock("metaglass-wall");
const metaglassWallLarge = cblock("metaglass-wall-large");

const transistor = cblock("transistor");
const gate = cblock("gate");

// Exports
module.exports = {
	// Effect
	coreAtom: coreAtom,
	
	// Environment
	
	coldWater: coldWater,
	
	// Production
	
	arctifluidSynthesizer: arctifluidSynthesizer,
	
	// Walls
	
	metaglassWall: metaglassWall,
	metaglassWallLarge: metaglassWallLarge,
	
	// Turrets
	
	transistor: transistor,
	gate: gate
};
