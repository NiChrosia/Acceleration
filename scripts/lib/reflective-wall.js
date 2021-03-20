function reflectiveWall(name, chancePercent, attributes) {
	const wall = extend(Wall, name, attributes)
	wall.absorbLasers = true
	
	wall.buildType = () => extend(Wall.WallBuild, wall, {
		collision(bullet) {
			this.damage(bullet.damage * this.team == Team.derelict ? 2 : 1)
			
			let bulletType = bullet.type.class.getSuperclass();
			let pierce = bulletType == LaserBulletType || bulletType == ContinuousLaserBulletType
			let rot = bullet.rotation() + 180
			rot = rot > 360 ? rot -= 360 : rot
			if (pierce && Mathf.chance(chancePercent / 100)) {
				if (bullet.data === null) {
					bullet.data = {}
				};
				if (bullet.data.collisions == undefined) {
					bullet.data.collisions = 1
				};
				bullet.data.collisions++
				if (bullet.data.collisions <= 2) {
					print(bullet.rotation())
					print((180 * (Mathf.round(bullet.rotation() / 90) % 2)))
					print(360-bullet.rotation())
					
					// Fix for the left and right sides, as they require slightly different handling
					let sideways = (Mathf.round(bullet.rotation() / 90) % 2)
					sideways = sideways == 0 ? 1 : 0
					
					// Refract the bullet
					bullet.type.create({}, this.team, this.x, this.y, 360 - bullet.rotation() + (180 * sideways))
				};
			}
			return !pierce
		}
	});
	
	return wall
}

module.exports = reflectiveWall