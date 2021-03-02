const planets = require("campaign/planets");

// Cryogenia
/// Glacial Wasteland
const glacialWasteland = extend(SectorPreset, "glacial-wasteland", planets.cryogenia, 10, {
	captureWave: 15,
	alwaysUnlocked: false,
	difficulty: 1,
	description: "The origin of the cold. A derelict power production facility stands here. Rapid expansion of the facility to meet power consumption resulted in the atmosphere thickening far too much, triggering an ice age and freezing the planet."
});

module.exports = {
    glacialWasteland: glacialWasteland
};