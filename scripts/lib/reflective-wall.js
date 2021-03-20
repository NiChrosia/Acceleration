function reflectiveWall(name, chancePercent, attributes) {
	const wall = extend(Wall, name, attributes)
	wall.absorbLasers = true
	
	wall.buildType = () => extend(Wall.WallBuild, wall, {
		collision(bullet) {
			this.damage(bullet.damage * (this.team == Team.derelict ? 2 : 1))
			
			let bulletType = bullet.type.class.getSuperclass();
			let pierce = bulletType == LaserBulletType
			let rot = bullet.rotation() + 180
			rot = rot > 360 ? rot -= 360 : rot
			if (pierce && Mathf.chance(chancePercent / 100)) {
				
				// Handling for new collisions
				if (bullet.data === null) {
					bullet.data = {}
				};
				if (bullet.data.collisions == undefined) {
					bullet.data.collisions = 1
				};
				
				// Increment collisions
				bullet.data.collisions++
				if (bullet.data.collisions <= 2) {
					// Fix for the left and right sides, as they require slightly different handling
					let sideways = (Mathf.round(bullet.rotation() / 90) % 2)
					sideways = sideways == 0 ? 1 : 0
					
					// Refract the bullet
					const shoot = () => {bullet.type.create({}, this.team, this.x, this.y, 360 - bullet.rotation() + (180 * sideways))}
					Vars.state.paused ? null : Timer.schedule(shoot, 0.1)
				};
			}
			return !pierce
		}
	});
	
	return wall
}

module.exports = reflectiveWall

/** TODO make the mechanics work better, as in calculating the angle via difference in x, for example:
WWWWWWWWWW
  L
 L
T
would be calculated via the difference in x of the turret and the end x to get the angle to the x multiplied by two and the y the same as turret.