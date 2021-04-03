let load = require("lib/loader");

/*
    0 means this is a file to load
    an object means its a folder
    you need to encase it in quotes if the name has a dash/hyphen (-)
    it starts from scripts/content
    check libs/loader.js for the loader itself
*/
load({
    items: 0,
    effects: {
        effects: 0,
        "status-effects": 0
    },
    bullets: {
        "laser-bullets": 0,
        "liquid-bullets": 0
    },
    liquids: 0,
    units: 0,
    "unit-plans": {
        "lightning-air": 0
    },
    blocks: 0,
    events: 0,
    campaign: {
        techtree: 0,
        "planet-gen": 0,
        planets: 0,
        sectors: 0
    }
});

// Successful loading
Events.on(ClientLoadEvent, () => {
    const addCheck = (key, default_value) => {
		Vars.ui.settings.graphics.checkPref(key, Core.settings.getBool(key));
		Core.settings.defaults(key, default_value);
	}
    addCheck("status-zone", true);
	addCheck("status-zone-particles", true);
    
    Log.info("Mod [accent]Acceleration[]  [orange]JavaScript[] loaded successfully.");
});