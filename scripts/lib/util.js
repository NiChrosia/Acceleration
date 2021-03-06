Events.on(ClientLoadEvent, () => {
	const addSetting = key => Vars.ui.settings.graphics.checkPref(key, true);
	addSetting("puddle-status-zone");
});

const ModName = "acceleration-";

function getTextureName(blockName, name) {
	let fullName = ModName + blockName;
	return fullName + name;
};

function getRegion(blockName, name) {
	if (name == null) {
		return Core.atlas.find(ModName + blockName.substr(0, blockName.length-1))
	}
	return Core.atlas.find(ModName + blockName + name)
};

const cblock = name => Vars.content.getByName(ContentType.block, ModName + name);
const cliquid = name => Vars.content.getByName(ContentType.liquid, ModName + name);
const cstatus = name => Vars.content.getByName(ContentType.status, ModName + name);
const citem = name => Vars.content.getByName(ContentType.item, ModName + name);
const cunit = name => Vars.content.getByName(ContentType.unit, ModName + name);
const percent = (partial, total) => partial / total;
const hexColor = hexValue => Color.valueOf(hexValue);
const avg = (x, y) => (x + y) / 2;
// const dst = (ax, ay, bx, by, distance) => ((ax * ay) + (bx * by)) < Mathf.pow(distance, 2);
const dst = (ax, ay, bx, by, distance) => Mathf.dst(ax, ay, bx, by) < distance

function circleEffect(color, time) {
	print("[circleEffect] color: " + color + ", time: " + time)
	return Effect(time, e => {
		Draw.color(color, Color.white, e.fout() / 5 + Mathf.randomSeedRange(e.id, 0.12));

		Angles.randLenVectors(e.id, 2, 1 + e.fin() * 3, (x, y) => {
			Fill.circle(e.x + x, e.y + y, .2 + e.fout() * 1.2);
		});
	});
};

function squareShieldEffect(size, color, time) {
	size *= 0.75
	print("[squareShieldEffect] size: " + size + ", color: " + color + ", time: " + time)
	return Effect(time, e => {
		Draw.color(color);
		Draw.z(Layer.shields);
		Fill.poly(e.x, e.y, 4, size, 45);
	});
};

function lineSquareEffect(size, color, time) {
	size *= 0.75
	print("[lineSquareEffect] size: " + size + ", color: " + color + ", time: " + time)
	return Effect(time, e => {
		Draw.color(color);
		Draw.z(Layer.shields + 1);
		Lines.poly(e.x, e.y, 4, size, 45);
	});
};

module.exports = {
	getTextureName: getTextureName,
	getRegion: getRegion,
	cliquid: cliquid,
	citem: citem,
	cblock: cblock,
	cstatus: cstatus,
	percent: percent,
	hexColor: hexColor,
	ModName: ModName,
	avg: avg,
	dst: dst,
	cunit: cunit,
	circleEffect: circleEffect,
	squareShieldEffect: squareShieldEffect,
	lineSquareEffect: lineSquareEffect
};