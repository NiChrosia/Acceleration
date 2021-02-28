// Imports

const mitems = require("content/items");

// Assignment

const diamondOre = extend(Floor, "diamond-ore", {
	variants: 1,
	itemDrop: mitems.diamond,
	buildVisibility: BuildVisibility.editorOnly
});