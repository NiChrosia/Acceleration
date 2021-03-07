// Imports

const mitems = require("content/items");
const munits = require("content/units");
const util = require("lib/util");

// Constants

const offset = 0;
const range = 50 * Vars.tilesize;
const baseColor = Color.valueOf("84f491");
let charge = 0;
const healPercent = 45;
const efficiency = 1;
const reload = 400;
const blockName = "core-atom-";
let squareSize = 0;
const sizeCap = 24;

// Assignment

const coreAtom = extend(CoreBlock, "core-atom", {
	drawPlace(x, y, rotation, valid) {
		this.super$drawPlace(x, y, rotation, valid);
		
		Drawf.dashCircle(x * Vars.tilesize + offset, y * Vars.tilesize + offset, range * Vars.tilesize, baseColor);

		Vars.indexer.eachBlock(Vars.player.team(), x * Vars.tilesize + offset, y * Vars.tilesize + offset, range * Vars.tilesize, other => true, other => Drawf.selected(other, Color(
			baseColor.r, 
			baseColor.g, 
			baseColor.b, 
			Mathf.absin(4, 1)
		)));
	},
	setStats() {
		this.super$setStats();
		
		// Add
		
        this.stats.add(Stat.repairTime, (100 / healPercent * reload / 60), StatUnit.seconds);
        this.stats.add(Stat.range, range / Vars.tilesize, StatUnit.blocks);
		
		// Remove
		
		// this.stats.remove(Stat.buildTime)
	},
	size: 6,
	health: 15000,
	category: Category.effect,
	buildVisibility: BuildVisibility.shown,
	requirements: ItemStack.with(
		Items.copper, 12000,
		Items.lead, 12000,
		Items.thorium, 6000,
		Items.silicon, 6000,
		Items.surgeAlloy, 2000,
		mitems.aerogel, 2000
	),
	itemCapacity: 18000,
	unitCapModifier: 32,
	researchCostMultiplier: 0.08,
	unitType: munits.delta
});

coreAtom.buildType = () => extend(CoreBlock.CoreBuild, coreAtom, {
	updateTile() {	 
		charge += 1 * Time.delta;

		if(charge >= reload) {
			let realRange = range
			charge = 0;

			Vars.indexer.eachBlock(this, realRange, other => other.damaged(), other => {
				other.heal(other.maxHealth * (healPercent / 100));
				Fx.healBlockFull.at(other.x, other.y, other.block.size, baseColor);
			});
		}
	},
	drawSelect() {
		//print("x: " + this.x + ", y: " + this.y)
		Drawf.dashCircle(this.x + offset, this.y + offset, range, baseColor);

		Vars.indexer.eachBlock(Vars.player.team(), this.x + offset, this.y + offset, range, other => true, other => Drawf.selected(other, Color(
			baseColor.r, 
			baseColor.g, 
			baseColor.b, 
			Mathf.absin(4, 1)
		)));
	},
	draw() {
		this.super$draw();
		
		Draw.rect(util.getTextureName(blockName, null), this.x, this.y);
		
		Draw.color(this.team.color);
		Draw.rect(util.getTextureName(blockName, "team"), this.x, this.y);
		
		squareSize += 0.15
		if (squareSize >= sizeCap) {
			squareSize = 0
		}
		
		Draw.color(baseColor);
		Draw.alpha(Mathf.absin(Time.time, 10, 1));
		Draw.rect(util.getTextureName(blockName, "mend"), this.x, this.y);
		Draw.alpha(1);
		Lines.stroke(squareSize / 6);
		print("x: " + this.x + ", y: " + this.y + "\nsquareSize: " + squareSize)
		Lines.square(this.x, this.y, squareSize);
		
		Draw.reset();
	}
});