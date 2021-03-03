const coldWater = extend(Floor, "cold-water", {
	variants: 0,
	albedo: 0.5,
	liquidDrop: Liquids.water,
	buildVisibility: BuildVisibility.editorOnly,
	isLiquid: true,
	cacheLayer: CacheLayer.water,
	status: StatusEffects.freezing,
	statusDuration: 90
});