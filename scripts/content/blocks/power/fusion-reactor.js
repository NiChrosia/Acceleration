// Imports

const mitems = require("content/items");
const mliquids = require("content/liquids");
const effects = require("content/effects/effects");
const util = require("lib/util");

// Constants

const blockName = "fusion-reactor-";

// Assignment

const fusionReactor = extend(ImpactReactor, "fusion-reactor", {
	size: 5,
	health: 1250,
	powerProduction: 280,
	itemDuration: 120,
	explosionRadius: 48,
	explosionDamage: 6400,
	category: Category.power,
	buildVisibility: BuildVisibility.shown
});

fusionReactor.consumes.items(ItemStack.with(mitems.fusionCompound, 1))
fusionReactor.consumes.liquid(mliquids.quarkPlasma, 0.25);
fusionReactor.consumes.power(70);

fusionReactor.buildType = () => extend(ImpactReactor.ImpactReactorBuild, fusionReactor, {
	draw() {
		this.super$draw();
		
		// Plasma region code
		
		const plasmaRegionCount = 4;
		let plasmaRegions = [];
		for (let count = 0; count < plasmaRegionCount; count++) {
			plasmaRegions.push(util.getRegion(blockName, "plasma-" + String(count)));
		}
        Draw.rect(util.getTextureName(blockName, "bottom"), this.x, this.y);
        for (let i = 0; i < plasmaRegionCount; i++) {
            let r = this.block.size * Vars.tilesize - 3 + Mathf.absin(Time.time, 2 + i * 1, 5 - i * 0.5);

            Draw.color(plasmaRegions[0], plasmaRegions[1], i / plasmaRegions.length);
            Draw.alpha((0.3 + Mathf.absin(Time.time, 2 + i * 2, 0.3 + i * 0.05)) * this.warmup);
            Draw.blend(Blending.additive);
            Draw.rect(plasmaRegions[i], this.x, this.y, r, r, Time.time * (12 + i * 6) * this.warmup);
            Draw.blend();
        };
		
		Draw.reset();
		
        Draw.rect(this.block.region, this.x, this.y);
		
		// Liquid region code
		
        Draw.color(mliquids.quarkPlasma.color);
		Draw.alpha(util.percent(this.liquids.get(mliquids.quarkPlasma), this.block.liquidCapacity));
		Draw.rect(util.getTextureName(blockName, "liquid"), this.x, this.y);
		
		Draw.reset();
    },
	onDestroyed() {
        this.super$onDestroyed();

        if (this.warmup < 0.4 && !Vars.state.rules.reactorExplosions) return;

        Sounds.explosionbig.at(Vars.world.rawTile(this.x / 8, this.y / 8));
        Effect.shake(24, 16, this.x, this.y);
        effects.fusionShockwave.at(this.x, this.y);
        for (let i = 0; i < 12; i++) {
            Time.run(Mathf.random(80), () => effects.fusionCloud.at(this.x, this.y));
			Effect.shake(4, 4, this.x, this.y);
        }
		
		Damage.damage(this.x, this.y, this.block.explosionRadius * Vars.tilesize, this.block.explosionDamage * 4);
	}
});