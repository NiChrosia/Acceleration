// Imports

const munits = require("content/units");
const util = require("lib/util");

// Constants

const offset = 0;
const range = 50 * Vars.tilesize;
const baseColor = Color.valueOf("84f491");
const healPercent = 45;
const efficiency = 1;
const reload = 400;
const blockName = "core-atom-";
const sizeCap = 24;
const lineCap = 5;
const sizeSpeed = 0.15;
const lineSizeSpeed = 0.17 / 6;
const lightningColor = Color.valueOf("f3e979");
const lightningLength = 50;
const lightningDamage = 20;
const turretReload = 30;
const turretRotateSpeed = 2;
const recoilRate = 0.3;
const turretRecoil = 5;

// Assignment

const coreAtom = extend(CoreBlock, "core-atom", {
	drawPlace(x, y, rotation, valid) {
		if(!this.canPlaceOn(Vars.world.tile(x, y), Vars.player.team())){

            this.drawPlaceText(Core.bundle.get((Vars.player.team().core() != null && Vars.player.team().core().items.has(this.requirements, Vars.state.rules.buildCostMultiplier)) || Vars.state.rules.infiniteResources ?
                "bar.corereq" :
                "bar.noresources"
            ), x, y, valid);
        };
		
		Drawf.dashCircle(x * Vars.tilesize + offset, y * Vars.tilesize + offset, range, baseColor);

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
		Items.surgeAlloy, 8000
	),
	itemCapacity: 32000,
	unitCapModifier: 48,
	researchCostMultiplier: 0.25,
	unitType: munits.delta
});

coreAtom.buildType = () => extend(CoreBlock.CoreBuild, coreAtom, {
	charge: 0,
	squareSize: 0,
	lineSize: 0,
	turretCooldown: 0,
	turretRotation: 0,
	turretTempRotation: 0,
	target: {
		team: Team.sharded,
		x: 0,
		y: 0
	},
	vec: Vec2(),
	findTarget(team, x, y, range, sort) {
		return Units.bestTarget(team, x, y, range, e => !e.dead, b => true, sort)
	},
	posTarget(target) {
		let turretTempRotation = target.team != this.team ? this.angleTo(target.x, target.y) : this.turretRotation;
		if (this.turretRotation > turretTempRotation) {
			this.turretRotation -= turretRotateSpeed
		};
		if (this.turretRotation < turretTempRotation) {
			this.turretRotation += turretRotateSpeed
		};
	},
	validateShoot(target, rotation, cooldown) {
		let angleValid = util.equalByAmount(this.angleTo(target.x, target.y), rotation, 5);
		let cooldownValid = cooldown >= turretReload
		return angleValid && cooldownValid
	},
	shoot(target) {
		this.turretCooldown = 0;
		target.team != this.team ? Lightning.create(this.team, lightningColor, lightningDamage, this.x, this.y, this.turretRotation, lightningLength) : null
		target.team != this.team ? this.vec.trns(this.turretRotation + 180, turretRecoil) : null;
	},
	validateMend(charge) {
		return charge >= reload
	},
	mend() {
		let realRange = range
		this.charge = 0;

		Vars.indexer.eachBlock(this, realRange, other => other.damaged(), other => {
			other.heal(other.maxHealth * (healPercent / 100));
			Fx.healBlockFull.at(other.x, other.y, other.block.size, baseColor);
		});
	},
	updateRecoil() {
		if (this.vec.x > recoilRate * 2) {
			this.vec.x -= recoilRate
		} else if (this.vec.x > 0) {
			this.vec.x -= recoilRate / 2
		};
		
		if (this.vec.y > recoilRate * 2) {
			this.vec.y -= recoilRate
		} else if (this.vec.y > 0) {
			this.vec.y -= recoilRate / 2
		};
		
		if (this.vec.x < -(recoilRate * 2)) {
			this.vec.x += recoilRate
		} else if (this.vec.x < 0) {
			this.vec.x += recoilRate / 2
		};
		
		if (this.vec.y < -(recoilRate * 2)) {
			this.vec.y += recoilRate
		} else if (this.vec.y < 0) {
			this.vec.y += recoilRate / 2
		};
	},
	updateTile() {
		this.target = {
			team: this.team,
			x: this.target.x,
			y: this.target.y
		};
		// Adjust recoil
		this.updateRecoil();
		
		// Increase charge
		this.charge += 1 * Time.delta;
		
		// If mend is valid, mend
		this.validateMend(this.charge) ? this.mend() : null
		
		// Increase turret charge
		this.turretCooldown++;
		
		// Calculate target
		let potentialTarget = this.findTarget(this.team, this.x, this.y, range, e => e.maxHealth)
		this.target = potentialTarget != null ? potentialTarget : this.target
		/*Log.info("[accent]-")
		Log.info(this.team)
		Log.info(potentialTarget)
		Log.info(this.target)*/
		
		// Rotate towards target
		this.posTarget(this.target);
		
		// If shoot valid, shoot
		this.validateShoot(this.target, this.turretRotation, this.turretCooldown) ? this.shoot(this.target) : null
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
		Draw.rect(util.getTextureName(blockName, "turret"), this.x + this.vec.x, this.y + this.vec.y, this.turretRotation);
		Draw.reset();
		
		if (!Vars.state.paused) {
			this.squareSize += sizeSpeed
			if (this.squareSize >= sizeCap) {
				this.squareSize = 0;
				this.lineSize = lineCap;
			}
		};
		
		if (!Vars.state.paused) {
			this.lineSize -= lineSizeSpeed
			if (this.lineSize <= 0.001) {
				this.lineSize = lineCap;
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
		Lines.stroke(this.lineSize);
		Lines.square(this.x, this.y, this.squareSize);
		Draw.reset();
	}
});