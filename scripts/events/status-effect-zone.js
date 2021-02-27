const statusEffects = require("effects/status-effects");
const effects = require("effects/effects");
const functions = require("lib/functions");
const liquidBullets = require("bullets/liquid-bullets");

function puddleStatusEffectZone(statusEffect, effect, alternateEffect, size, liquid) {
	Groups.puddle.each(puddle => {
		let tile = puddle.tileOn();
		if (puddle.liquid == liquid) {
			Core.settings.getBool("animatedshields") ? effect.at(puddle.x, puddle.y) : alternateEffect.at(puddle.x, puddle.y);
			Units.nearby(puddle.x - (size / 2), puddle.y - (size / 2), size, size, u => {
				if (!u.isDead) {
					u.apply(statusEffect, 40);
				};
			});
		};
	});
};

function bulletStatusEffectZone(statusEffect, effect, size, matchBullet) {
	Groups.bullet.each(bullet => {
		let tile = bullet.tileOn();
		if (bullet.type == matchBullet) {
			effect.at(bullet.x, bullet.y)
			Units.nearby(bullet.x - (size / 2), bullet.y - (size / 2), size, size, u => {
				if (!u.isDead) {
					u.apply(statusEffect, 40);
				};
			});
		};
	});
};

function glaciafreezeZone() {
	puddleStatusEffectZone(statusEffects.permafrost, effects.glaciafreezeSquare, effects.glaciafreezeLineSquare, 48, functions.cliquid("glaciafluid"));
	puddleStatusEffectZone(statusEffects.liquefying, effects.liquefyingSquare, effects.liquefyingLineSquare, 48, functions.cliquid("quark-plasma"));
	// bulletStatusEffectZone(statusEffects.permafrost, effects.glaciafreezeSquareSmall, 24, liquidBullets.glaciafluidShot);
};

Events.run(Trigger.update, glaciafreezeZone);

/*
Code that could theoretically be used to canonically merge liquid effect zones, discontinued. If for some reason someone decides they want to work on this and has a decent working version of it, DM me on discord with the code. (NiChrosia#3367)

const statusEffects = require("effects/status-effects");
const effects = require("effects/effects");
const functions = require("lib/functions");
const liquidBullets = require("bullets/liquid-bullets");

function puddleStatusEffectZone(statusEffect, effectColor, size, liquid) {
	// Creation of the puddles
	
	let groupPuddles = new Seq();
	Groups.puddle.each(p => {
		groupPuddles.add(p)
	});
	let puddles = groupPuddles.filter(puddle => puddle.liquid === liquid)
	
	// Merging for puddles
	
	let puddlesMerged = new Seq();
	puddles.each(p => {
		function isClose(puddle) {
			return puddle.dst(p.x, p.y) < 5
		};
		let x = 0;
		let xCount = 0;
		let y = 0;
		let yCount = 0;
		let puddleSize = size;
		let duplicatePuddles = puddles;
		duplicatePuddles.filter(isClose);
		duplicatePuddles.each(puddle => {
			x += puddle.x;
			xCount += 1;
			y += puddle.y;
			yCount += 1;
			puddleSize += puddle.amount / 35
		});
		let mergedPuddle = {
			x: x / xCount,
			y: y / yCount,
			size: puddleSize
		};
		puddlesMerged.add(mergedPuddle);
	});
	
	// Handling for the merged puddles
	
	puddlesMerged.each(puddle => {
		Effect(40, e => {
			let eSize = puddle.size
			eSize *= 0.75
			Draw.color(effectColor);
			Draw.z(Layer.shields);
			Fill.poly(e.x, e.y, 4, eSize, 45);
		}).at(puddle.x, puddle.y);
		
		Units.nearby(puddle.x - (puddle.size / 2), puddle.y - (puddle.size / 2), puddle.size, puddle.size, u => {
			if (!u.isDead) {
				u.apply(statusEffect, 40);
			};
		});
	});
};

function bulletStatusEffectZone(statusEffect, effect, size, matchBullet) {
	Groups.bullet.each(bullet => {
		let tile = bullet.tileOn();
		if (bullet.type == matchBullet) {
			effect.at(bullet.x, bullet.y)
			Units.nearby(bullet.x - (size / 2), bullet.y - (size / 2), size, size, u => {
				if (!u.isDead) {
					u.apply(statusEffect, 40);
				};
			});
		};
	});
};

function glaciafreezeZone() {
	puddleStatusEffectZone(statusEffects.permafrost, Color.valueOf("2FADAD"), 48, functions.cliquid("glaciafluid"));
	// bulletStatusEffectZone(statusEffects.permafrost, effects.glaciafreezeSquareSmall, 24, liquidBullets.glaciafluidShot);
};

Events.run(Trigger.update, glaciafreezeZone);
*/