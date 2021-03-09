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
let lineSize = 5;
const sizeCap = 24;
const lineCap = 5;
const sizeSpeed = 0.15;
const lineSizeSpeed = 0.17 / 6;
const lightningColor = Color.valueOf("f3e979");
const lightningLength = 50;
const lightningDamage = 20;
const turretReload = 30;
let turretCooldown = 0;
let turretRotation = 270;
let turretTempRotation;
const turretRotateSpeed = 2;
const recoilRate = 0.3;
let unit = {
	team: Team.sharded,
	x: 0,
	y: 0
};
let vec = Vec2();
const turretRecoil = 5;

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
	icons() {
		return [
			util.getRegion(blockName, null),
			util.getRegion(blockName, "team")
		]
	},
	size: 6,
	health: 48000,
	category: Category.effect,
	buildVisibility: BuildVisibility.shown,
	requirements: ItemStack.with(
		Items.copper, 24000,
		Items.lead, 24000,
		Items.thorium, 12000,
		Items.silicon, 12000,
		Items.surgeAlloy, 8000,
		mitems.aerogel, 8000
	),
	itemCapacity: 32000,
	unitCapModifier: 48,
	researchCostMultiplier: 0.08,
	unitType: munits.delta
});

coreAtom.buildType = () => extend(CoreBlock.CoreBuild, coreAtom, {
	updateTile() {
		unit = {
			team: this.team,
			x: unit.x,
			y: unit.y
		};
		if (vec.x > recoilRate * 2) {
			vec.x -= recoilRate
		} else if (vec.x > 0) {
			vec.x -= recoilRate / 2
		};
		
		if (vec.y > recoilRate * 2) {
			vec.y -= recoilRate
		} else if (vec.y > 0) {
			vec.y -= recoilRate / 2
		};
		
		if (vec.x < -(recoilRate * 2)) {
			vec.x += recoilRate
		} else if (vec.x < 0) {
			vec.x += recoilRate / 2
		};
		
		if (vec.y < -(recoilRate * 2)) {
			vec.y += recoilRate
		} else if (vec.y < 0) {
			vec.y += recoilRate / 2
		};
		
		charge += 1 * Time.delta;

		if(charge >= reload) {
			let realRange = range
			charge = 0;

			Vars.indexer.eachBlock(this, realRange, other => other.damaged(), other => {
				other.heal(other.maxHealth * (healPercent / 100));
				Fx.healBlockFull.at(other.x, other.y, other.block.size, baseColor);
			});
		};
		
		// Set Stats
		turretCooldown++;
		
		Units.nearby(this.x - (range / 2), this.y - (range / 2), range, range, u => {
			unit = u.team != this.team ? u : unit;
		});
		
		turretTempRotation = unit.team != this.team ? this.angleTo(unit.x, unit.y) : turretTempRotation;
		if (turretRotation > turretTempRotation) {
			//while (turretRotation > turretTempRotation) {
			turretRotation -= turretRotateSpeed
			//};
		};
		if (turretRotation < turretTempRotation) {
			//while (turretRotation < turretTempRotation) {
			turretRotation += turretRotateSpeed
			//};
		};
		
		if (turretCooldown >= turretReload) {
			// reset cooldown
			turretCooldown = 0;
			unit.team != this.team ? Lightning.create(this.team, lightningColor, lightningDamage, this.x, this.y, turretRotation, lightningLength) : null
			unit.team != this.team ? vec.trns(turretRotation + 180, turretRecoil) : null;
		};
	},
	drawSelect() {
		Drawf.dashCircle(this.x + offset, this.y + offset, range, baseColor);

		Vars.indexer.eachBlock(this.team, this.x + offset, this.y + offset, range, other => true, other => Drawf.selected(other, Color(
			baseColor.r, 
			baseColor.g, 
			baseColor.b, 
			Mathf.absin(4, 1)
		)));
	},
	draw() {
		this.super$draw();
		
		// Base sprite
		Draw.rect(util.getTextureName(blockName, null), this.x, this.y);
		
		// Team sprite
		Draw.color(this.team.color);
		Draw.rect(util.getTextureName(blockName, "team"), this.x, this.y);
		Draw.reset();
		
		// Turret sprite
		Draw.z(Layer.turret);
		Draw.rect(util.getTextureName(blockName, "turret"), this.x + vec.x, this.y + vec.y, turretRotation);
		Draw.reset();
		
		if (!Vars.state.paused) {
			squareSize += sizeSpeed
			if (squareSize >= sizeCap) {
				squareSize = 0;
				lineSize = lineCap;
			}
		};
		
		if (!Vars.state.paused) {
			lineSize -= lineSizeSpeed
			if (lineSize <= 0.001) {
				lineSize = lineCap;
			}
		};
		
		// Mend sprite (pulses)
		Draw.z(Layer.block);
		Draw.color(baseColor);
		Draw.alpha(Mathf.absin(Time.time, 10, 1));
		Draw.rect(util.getTextureName(blockName, "mend"), this.x, this.y);
		Draw.reset();
		
		// Mend overlay, goes in to out, with line size decreasing
		Draw.z(Layer.turret - 1);
		Draw.color(baseColor);
		Draw.alpha(1);
		Lines.stroke(lineSize);
		Lines.square(this.x, this.y, squareSize);
		Draw.reset();
	}
});