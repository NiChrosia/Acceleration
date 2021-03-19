// Imports

const util = require("lib/util");

// Puddle Status Zone

function puddleStatusEffectZone(statusEffect, effect, alternateEffect, size, liquid, damage, immuneBlock) {
	if (!Vars.state.paused) {
		Groups.puddle.each(puddle => {
			let tile = puddle.tileOn();
			if (puddle.liquid == liquid) {
				Core.settings.getBool("puddle-status-zone") ? effect.at(puddle.x, puddle.y) : alternateEffect.at(puddle.x, puddle.y);
				Units.nearby(puddle.x - (size / 2), puddle.y - (size / 2), size, size, u => {
					if (!u.isDead) {
						u.apply(statusEffect, 40);
					};
				});
				if (damage) {
					let teams = Vars.state.isCampaign() ?  Seq.with(Team.derelict, Team.sharded, Team.crux) : Seq.with(Team.derelict, Team.sharded, Team.crux, Team.green, Team.purple, Team.blue) // Slightly increased performance in campaign.
					teams.each(t => {
						Vars.indexer.eachBlock(t, puddle.x, puddle.y, size, b => true, b => {
							if (immuneBlock instanceof Seq) {
								let nullifyDamage = false;
								immuneBlock.each(ib => {
									nullifyDamage = nullifyDamage ? nullifyDamage : (ib == b.block);
									
									if (nullifyDamage) {
										return;
									};
								});
								nullifyDamage ? null : b.damage(statusEffect.damage)
							} else if (!(immuneBlock instanceof Array)){
								b.block != immuneBlock ? b.damage(statusEffect.damage) : null;
							} else if (immuneBlock instanceof Array){
								Log.err("[lib/events.js] Internal error: arrays not supported.")
							}
						});
					})
				};
			};
		});
	};
};

// Bullet Status Zone

function bulletStatusEffectZone(statusEffect, effect, size, matchBullet) {
	if (!Vars.state.paused) {
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
};

// Exports

module.exports = {
	puddleStatusEffectZone: puddleStatusEffectZone,
	bulletStatusEffectZone: bulletStatusEffectZone
};