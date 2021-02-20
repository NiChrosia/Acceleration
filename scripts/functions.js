let modName = "acceleration-";

function getTextureName(blockName, name) {
	let fullName = modName + blockName;
	return fullName + name;
};

function getRegion(blockName, name) {
	if (name == null) {
		return Core.atlas.find(modName + blockName.substr(0, blockName.length-1))
	}
	return Core.atlas.find(modName + blockName + name)
};

function cliquid(name) {
	return Vars.content.getByName(ContentType.liquid, modName + name)
};

function citem(name) {
	return Vars.content.getByName(ContentType.item, modName + name)
};

function percent(partialValue, totalValue) {
   return partialValue / totalValue;
};

module.exports = {
	modName: modName,
	getTextureName: getTextureName,
	getRegion: getRegion,
	cliquid: cliquid,
	citem: citem,
	percent: percent
};
/*module.exports.modName = modName
module.exports.getTextureName = getTextureName
module.exports.getRegion = getRegion
module.exports.cliquid = cliquid
module.exports.citem = citem
module.exports.percent = percent*/