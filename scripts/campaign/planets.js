const planetGen = require("campaign/planet-gen");

const cryogenia = extend(Planet, "cryogenia", Planets.sun, 3, 1, {
	generator: planetGen.cryogenia,
	atmosphereColor: Color.valueOf("2c5391"),
	atmosphereRadIn: 0.003,
	atmosphereRadOut: 0.37
});

cryogenia.meshLoader = () => new HexMesh(cryogenia, 6);
// cryogenia.startSector = 10;

module.exports = {
    cryogenia: cryogenia
};