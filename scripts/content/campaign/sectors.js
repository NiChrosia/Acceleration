const planets = require("content/campaign/planets");

// Cryogenia
/// Glacial Wasteland
const glacialGlade = extend(SectorPreset, "glacial-glade", planets.cryogenia, 10, {
	captureWave: 15,
	difficulty: 1,
	description: "An ancient frozen glade situated in a tranquil zone. Contains abnormally high quantities of cryogem for its location. \nCapture the sector. Harness the resources. Move on."
});

module.exports = {
    glacialGlade: glacialGlade
};