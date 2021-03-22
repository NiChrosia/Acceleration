function reflectiveWall(name, chancePercent, attributes) {
	const wall = extend(Wall, name, attributes)
	wall.absorbLasers = true
	
	wall.buildType = () => extend(Wall.WallBuild, wall, {
		chanceRefract: chancePercent,
		collision(bullet) {
			this.damage(bullet.damage * (this.team == Team.derelict ? 2 : 1))
			
			let bulletType = bullet.type.class.getSuperclass();
			let pierce = bulletType == LaserBulletType
			let rot = bullet.rotation() + 180
			rot = rot > 360 ? rot -= 360 : rot
			if (pierce && Mathf.chance(this.chanceRefract / 100)) {
				// Calculate collision point
				let bulletCollisionLength = Damage.findLaserLength(bullet, bullet.type.length);
				let bulletVec = Vec2();
				bulletVec.trns(bullet.rotation(), bulletCollisionLength);
				let collisionX = bullet.x + bulletVec.x;
				let collisionY = bullet.y + bulletVec.y;
				
				// Angle to bullet collision
				let angle = this.angleTo(collisionX, collisionY);
				
				// Calculate rotation to bullet
				let rotation = Mathf.round(angle / 90)
				
				// Calculate directions
				let left = rotation == 2;
				let right = rotation == 0;
				let bottom = rotation == 3;
				let top = rotation == 1;
				
				// Calculate difference in x and y
				let diffX = (left ? bullet.x - collisionX : (right ? collisionX - bullet.x : 0)) * Vars.tilesize
				let diffY = (bottom ? bullet.y - collisionY : (top ? collisionY - bullet.y : 0)) * Vars.tilesize
				
				// Get new angle
				let refractAngle = this.angleTo(bullet.x + diffX, bullet.y + diffY)
				print(refractAngle)
				
				// Refract the bullet
				const shoot = bullet.type != null ? () => {bullet.type.create({}, this.team, collisionX, collisionY, refractAngle)} : () => {}
				Vars.state.paused ? null : Timer.schedule(shoot, 0.1)
			}
			return !pierce
		}
	});
	
	return wall
}

module.exports = reflectiveWall