const sulfurOre = extend(Floor, "sulfur-ore", {
	variants: 1,
	itemDrop: Vars.content.getByName(ContentType.item, "acceleration-sulfur"),
	buildVisibility: BuildVisibility.editorOnly,
	playerUnmineable: true
});