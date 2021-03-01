const planetGen = require("campaign/planet-gen");

const cryogenia = extend(Planet, "cryogenia", Planets.sun, 3, 1, {});
cryogenia.generator = planetGen.cryogenia;
cryogenia.meshLoader = () => new HexMesh(cryogenia, 6);
cryogenia.atmosphereColor = Color.valueOf("2c5391");
cryogenia.atmosphereRadIn = 0.003;
cryogenia.atmosphereRadOut = 0.37;
cryogenia.startSector = 10;

module.exports = {
    cryogenia: cryogenia
};