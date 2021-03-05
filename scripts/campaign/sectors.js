const planets = require("campaign/planets");

// Cryogenia
/// Glacial Wasteland
const glacialWasteland = extend(SectorPreset, "glacial-wasteland", planets.cryogenia, 10, {
	captureWave: 15,
	difficulty: 1,
	description: "The origin of the cold. A powerful cooling technology was being researched here. It was destroyed by the crux, resulting in catastrophic cooling of the planet."
});

/// Volcanic Mountains
const volcanicMountains = extend(SectorPreset, "volcanic-mountains", planets.cryogenia, 225, {
	captureWave: 30,
	difficulty: 10,
	description: "An extremely dangerous sector. Plentiful with resources, but enemies here are stronger than even the Desolate Rift. Collect as many resources as possible. Leave."
});

module.exports = {
    glacialWasteland: glacialWasteland,
	volcanicMountains: volcanicMountains
};