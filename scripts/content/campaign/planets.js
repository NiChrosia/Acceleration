const planetGen = require("content/campaign/planet-gen");

const cryogenia = extend(Planet, "cryogenia", Planets.sun, 3, 1, {
	generator: planetGen.cryogenia,
	atmosphereColor: Color.valueOf("55b7e0"),
	atmosphereRadIn: 0.003,
	atmosphereRadOut: 0.45,
	bloom: false
});

cryogenia.meshLoader = () => new HexMesh(cryogenia, 6);
cryogenia.startSector = 10;

module.exports = {
    cryogenia: cryogenia
};