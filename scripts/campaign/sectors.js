const planets = require("campaign/planets");

// Cryogenia
const glacialWasteland = new SectorPreset("glacial-wasteland", planets.cryogenia, 10);
glacialWasteland.captureWave = 10;
glacialWasteland.alwaysUnlocked = true;

module.exports = {
    glacialWasteland: glacialWasteland
};