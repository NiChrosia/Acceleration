const {getTextureName} = require("lib/util");
const arBullets = require("content/bullets/artillery-bullets");

const gate = extend(ItemTurret, "gate", {
	rotateSpeed: 8,
	health: 1150,
	range: 270,
	reloadTime: 85,
	size: 2,
	recoilAmount: 4,
	buildVisibility: BuildVisibility.shown,
	category: Category.turret,
	inaccuracy: 1.5,
	shootSound: Sounds.artillery,
	targetAir: false
});

gate.ammo(
	Items.graphite, arBullets.railArtilleryDense,
	Items.silicon, arBullets.railArtilleryHoming,
	Items.pyratite, arBullets.railArtilleryIncendiary,
	Items.metaglass, arBullets.railArtilleryGlass
)

gate.consumes.power(1);

let blockName = "gate-"

gate.buildType = () => extend(ItemTurret.ItemTurretBuild, gate, {
	tr3: new Vec2,
	draw() {
		this.tr3 = new Vec2;
		this.tr3.trns(this.rotation, -this.recoil)
		
		Draw.z(Layer.turret + 1)
		
		Draw.alpha(this.logicControlled() ? 1 : 0);
		Draw.rect(getTextureName(blockName, "logic-overlay"), this.x + this.tr3.x, this.y + this.tr3.y, this.rotation - 90);
		
		Draw.alpha(1);
		
		Draw.z(Layer.block)
		
		this.super$draw();
	}
});