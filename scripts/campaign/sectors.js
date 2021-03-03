const planets = require("campaign/planets");

// Cryogenia
/// Glacial Wasteland
const glacialWasteland = extend(SectorPreset, "glacial-wasteland", planets.cryogenia, 10, {
	captureWave: 15,
	alwaysUnlocked: false,
	difficulty: 1,
	description: "The origin of the cold. A powerful cooling technology was being researched here. It was destroyed by the crux, resulting in catastrophic cooling of the planet."
});

module.exports = {
    glacialWasteland: glacialWasteland
};