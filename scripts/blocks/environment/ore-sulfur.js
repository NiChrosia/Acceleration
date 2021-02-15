const sulfurOre = extend(OreBlock, "ore-sulfur", {
	variants: 3,
	itemDrop: Vars.content.getByName(ContentType.item, "acceleration-sulfur"),
	buildVisibility: BuildVisibility.editorOnly
});