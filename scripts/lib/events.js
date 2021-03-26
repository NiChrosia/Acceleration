// Imports

const util = require("lib/util");
const teams = (Vars.state.isCampaign() ?  Seq.with(Team.derelict, Team.sharded, Team.crux) : Seq.with(Team.derelict, Team.sharded, Team.crux, Team.green, Team.purple, Team.blue)) // Slightly increased performance in campaign.

// Puddle Status Zone

function puddleStatusEffectZone(statusEffect, effect, alternateEffect, size, liquid, damageSelf, damage, immuneBlock) {
	if (!Vars.state.paused) {
		Groups.puddle.each(puddle => {
			let tile = puddle.tileOn();
			let build;
			teams.each(t => {
				Vars.indexer.eachBlock(t, puddle.x, puddle.y, 5, b=>true, b => {
					build = b;
				});
			});
			let team = (build != null ? build.team : null)
			if (puddle.liquid == liquid) {
				Core.settings.getBool("status-zone") ? effect.at(puddle.x, puddle.y) : alternateEffect.at(puddle.x, puddle.y);
				Units.nearby(puddle.x - (size / 2), puddle.y - (size / 2), size, size, u => {
					if (!u.isDead) {
						damageSelf && (team != null ? (t == team) : false) ? u.apply(statusEffect, 40) : null;
					};
				});
				if (damage) {
					teams.each(t => {
						Vars.indexer.eachBlock(t, puddle.x, puddle.y, size, b => true, b => {
							if (immuneBlock instanceof Seq) {
								let nullifyDamage = false;
								immuneBlock.each(ib => {
									nullifyDamage = nullifyDamage ? nullifyDamage : (ib == b.block) || (damageSelf ? (team != null ? (t == team) : false) : false);
									
									if (nullifyDamage) {
										return;
									};
								});
								nullifyDamage ? null : b.damage(statusEffect.damage / 10)
							} else if (!(immuneBlock instanceof Array)){
								b.block != immuneBlock ? b.damage(statusEffect.damage / 10) : null;
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

function bulletStatusEffectZone(statusEffect, effect, alternateEffect, size, matchBullet, damageSelf, damage, immuneBlock) {
	if (!Vars.state.paused) {
		Groups.bullet.each(bullet => {
			let tile = bullet.tileOn();
			if (bullet.type == matchBullet) {
				Core.settings.getBool("status-zone") ? effect.at(bullet.x, bullet.y) : alternateEffect.at(bullet.x, bullet.y);
				Units.nearby(bullet.x - (size / 2), bullet.y - (size / 2), size, size, u => {
					if (!u.isDead && (damageSelf ? true : !(u.team == bullet.owner.team))) {
						u.apply(statusEffect, 40)
					};
				});
				if (damage) {
					teams.each(t => {
						Vars.indexer.eachBlock(t, bullet.x, bullet.y, size, b => true, b => {
							if (immuneBlock instanceof Seq) {
								let nullifyDamage = false;
								immuneBlock.each(ib => {
									nullifyDamage = nullifyDamage ? nullifyDamage : (ib == b.block) || (t == bullet.owner.team);
									
									if (nullifyDamage) {
										return;
									};
								});
								nullifyDamage ? null : b.damage(statusEffect.damage / 5)
							} else if (!(immuneBlock instanceof Array)){
								b.block != immuneBlock || (t == bullet.owner.team) ? b.damage(statusEffect.damage / 5) : null;
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

// Exports

module.exports = {
	puddleStatusEffectZone: puddleStatusEffectZone,
	bulletStatusEffectZone: bulletStatusEffectZone
};