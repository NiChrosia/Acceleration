// Imports

const {dst, squareShieldEffect, lineSquareEffect, particleEffect} = require("lib/util");
const teams = (Vars.state.isCampaign() ?  Seq.with(Team.derelict, Team.sharded, Team.crux) : Seq.with(Team.derelict, Team.sharded, Team.crux, Team.green, Team.purple, Team.blue)) // Slightly increased performance in campaign.

// Constants

const fireChance = 0.025;
const lightningChance = 0.05;
const particleChance = 0.025;

const lightningDamage = 14;

// Puddle Status Zone

function puddleStatusEffectZone(statusEffect, shieldEffect, lineEffect, zoneParticleEffect, size, liquid, damage, immuneBlocks) {
	if (!Vars.state.paused) {
		Groups.puddle.each(puddle => { // Iterate over puddles
			let build;
			teams.each(t => {
				Vars.indexer.eachBlock(t, puddle.x, puddle.y, 5, b => true, b => {
					build = b;
				});
			});
			let team = (build != null ? build.team : null) // Calculate team, may be null.
			if (puddle.liquid == liquid) {
				(Core.settings.getBool("status-zone") && Core.settings.getBool("animatedshields")) ? shieldEffect.at(puddle.x, puddle.y) : lineEffect.at(puddle.x, puddle.y); // If animated shields and animated status zone is on, do the shield effect. Otherwise, use the line effect.
				Units.nearby(puddle.x - (size / 2), puddle.y - (size / 2), size, size, u => {
					if (!u.isDead && damage) {
						u.apply(statusEffect, 40);
					};
				});
				
				if (Mathf.chance(particleChance * Time.delta) && Core.settings.getBool("status-zone-particles")) { // Status Zone particles
					let particleX = Mathf.round(Mathf.random(puddle.x - (size / 2), puddle.x + (size / 2)));
					let particleY = Mathf.round(Mathf.random(puddle.y - (size / 2), puddle.y + (size / 2)));
					
					zoneParticleEffect.at(particleX, particleY);
				};
				
				if (damage) { // If damage blocks 
						
					if (Mathf.chance(fireChance * Time.delta) && statusEffect === StatusEffects.burning) {
						let fireX = Mathf.round(Mathf.random(puddle.x - (size / 2), puddle.x + (size / 2)));
						let fireY = Mathf.round(Mathf.random(puddle.y - (size / 2), puddle.y + (size / 2)));
						
						Fires.create(fireX, fireY) // Create fires if status effect is burning
					};
					if (Mathf.chance(lightningChance * Time.delta) && statusEffect === StatusEffects.shocked) {
						let lightningX = Mathf.round(Mathf.random(puddle.x - (size / 2), puddle.x + (size / 2)));
						let lightningY = Mathf.round(Mathf.random(puddle.y - (size / 2), puddle.y + (size / 2)));
						
						Fx.explosion.at(lightningX, lightningY);
						Lightning.create(team != null ? team : Team.derelict, Pal.surge, lightningDamage, lightningX, lightningY, Mathf.random(0, 360), lightningLength);
					};
					
					teams.each(t => { // Iterate over teams
						
						Vars.indexer.eachBlock(t, puddle.x, puddle.y, size, b => true, b => { // Iterate over blocks
							if (immuneBlocks instanceof Seq) { // calculate whether to nullify damage depending on damageSelf and immuneBlocks
								let nullifyDamage = false;
								immuneBlocks.each(ib => {
									nullifyDamage = ib == b.block;
									
									if (nullifyDamage) {
										return;
									};
								});
								nullifyDamage ? null : b.damage(statusEffect.damage / 10)
							} else if (immuneBlocks instanceof Block){
								b.block != immuneBlocks ? b.damage(statusEffect.damage / 10) : null;
							} else if (immuneBlocks instanceof Array){
								Log.err("[Acceleration/lib/events.js] Internal error: arrays not supported.") // log an error as arrays aren't supported.
							} else if (immuneBlocks == null) {
								// Do nothing
							} else {
								Log.err("[Acceleration/lib/events.js] Internal error: unknown immune blocks type.") // log an error if an unknown type 
							}
						});
					})
				};
			};
		});
	};
};

// Bullet Status Zone

function bulletStatusEffectZone(statusEffect, statusLength, effectColor, size, matchBullet, damageSelf, damage, immuneBlocks) {
	const shieldEffect = squareShieldEffect(size, effectColor, 12);
	const lineEffect = lineSquareEffect(size, effectColor, 12);
	const zoneParticleEffect = particleEffect(effectColor);
	if (!Vars.state.paused) {
		Groups.bullet.each(bullet => {			
			if (bullet.type == matchBullet) { // Get whether bullet matches
				(Core.settings.getBool("status-zone") && Core.settings.getBool("animatedshields")) ? shieldEffect.at(bullet.x, bullet.y) : lineEffect.at(bullet.x, bullet.y); // If animated shields and animated status zone is on, do the shield effect. Otherwise, use the line effect.
				Units.nearby(bullet.x - (size / 2), bullet.y - (size / 2), size, size, u => { // Get nearby units within the square of range
					if (!u.isDead && (damageSelf ? true : !(u.team == bullet.owner.team))) {
						u.apply(statusEffect, 40) // Inflict status effect
					};
				});
				
				if (Core.settings.getBool("status-zone-particles")) { // If status zone particles is on, put them there.
					let particleX = Mathf.round(Mathf.random(bullet.x - (size / 2), bullet.x + (size / 2)));
					let particleY = Mathf.round(Mathf.random(bullet.y - (size / 2), bullet.y + (size / 2)));
							
					if (Mathf.chance(particleChance * Time.delta)) {
						zoneParticleEffect.at(particleX, particleY);
					};
				}
				
				if (damage) {
					if (Mathf.chance(fireChance * Time.delta) && statusEffect === StatusEffects.burning && dst(bullet.x, bullet.y, (bullet.shooter != null ? bullet.shooter.x : bullet.x), (bullet.shooter != null ? bullet.shooter.y : bullet.y), 48)) {
						let fireX = Mathf.round(Mathf.random(bullet.x - (size / 2), bullet.x + (size / 2)));
						let fireY = Mathf.round(Mathf.random(bullet.y - (size / 2), bullet.y + (size / 2)));
							
						Fires.create(Vars.world.tile(fireX/Vars.tilesize, fireY/Vars.tilesize))
					};
					
					if (Mathf.chance(lightningChance * Time.delta) && statusEffect === StatusEffects.shocked) {
						let lightningX = Mathf.round(Mathf.random(bullet.x - (size / 2), bullet.x + (size / 2)));
						let lightningY = Mathf.round(Mathf.random(bullet.y - (size / 2), bullet.y + (size / 2)));
						
						Fx.hitFuse.at(lightningX, lightningY);
						teams.each(t => {
							Vars.indexer.eachBlock(t, lightningX, lightningY, 1.5 * Vars.tilesize, b => true, b => {
								b.damage(lightningDamage);
							});
						});
						Units.nearby(lightningX - 1.5 * Vars.tilesize / 2, lightningY - 1.5 * Vars.tilesize / 2, 1.5 * Vars.tilesize, 1.5 * Vars.tilesize, u => {
							u.damage(lightningDamage);
						});
					};
					
					teams.each(t => { // Iterate over teams						
						Vars.indexer.eachBlock(t, bullet.x, bullet.y, size, b => true, b => { // Iterate through nearby blocks
							if (immuneBlocks instanceof Seq) {
								let nullifyDamage = false;
								immuneBlocks.each(ib => {
									nullifyDamage = nullifyDamage ? nullifyDamage : (ib == b.block) || (t == bullet.owner.team); // Calculate whether to nullify damage, depending on damageSelf and whether the block is immune.
									
									if (nullifyDamage) {
										return;
									};
								});
								nullifyDamage ? null : b.damage(statusEffect.damage / 5)
							} else if (immuneBlocks instanceof Block){
								b.block != immuneBlocks || (t == bullet.owner.team) ? b.damage(statusEffect.damage / 10) : null;
							} else if (immuneBlocks instanceof Array){
								Log.err("[Acceleration/lib/events.js] Internal error: arrays not supported.") // log an error as arrays aren't supported.
							} else if (immuneBlocks == null) {
								// Do nothing
							} else {
								Log.err("[Acceleration/lib/events.js] Internal error: unknown immune blocks type.") // log an error if an unknown type 
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