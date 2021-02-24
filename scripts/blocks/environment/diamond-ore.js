const functions = require("lib/functions");

const diamondOre = extend(Floor, "diamond-ore", {
	variants: 1,
	itemDrop: functions.citem("diamond"),
	buildVisibility: BuildVisibility.editorOnly
});