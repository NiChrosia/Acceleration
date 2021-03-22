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
				let collision = {};
				collision.x = bullet.x + bulletVec.x;
				collision.y = bullet.y + bulletVec.y;
				
				// Angle to bullet collision
				let angle = this.angleTo(collision.x, collision.y);
				
				// Calculate rotation to bullet
				let rotation = Mathf.round(angle / 90)
				
				// Calculate directions
				let left = bullet.x < this.x;
				let right = bullet.x > this.x;
				let bottom = bullet.y < this.y;
				let top = bullet.y > this.y;
				
				// Calculate difference in x and y
				let diffX = Math.abs(bullet.x - collision.x)
				let diffY = Math.abs(bullet.y - collision.y)
				
				let refractX = bullet.x + diffX * (right ? -1 : 1);
				let refractY = bullet.y + diffY * (top ? -1 : 1);
				
				print("[accent]-==========================================================================-[]")
				print("[accent]Left:[] " + left + ", [accent]Right:[] " + right + ", [accent]Bottom:[] " + bottom + ", [accent]Top:[] " + top)
				print("[accent]DiffX:[] " + (diffX) + ", [accent]DiffY:[] " + (diffY))
				print("[accent]BulletX:[] " + (bullet.x) + ", [accent]BulletY:[] " + (bullet.y))
				print("[accent]X:[] " + refractX + ", [accent]Y:[] " + refractY)
				Fx.explosion.at(refractX, refractY)
				Fx.commandSend.at(refractX, refractY)
				
				// Get new angle
				let refractAngle = this.angleTo(refractX, refractY)
				print(refractAngle)
				
				// Refract the bullet
				const shoot = bullet.type != null ? () => {bullet.type.create({}, this.team, collision.x, collision.y, refractAngle)} : () => {}
				Vars.state.paused ? null : Timer.schedule(shoot, 0.1)
			}
			return !pierce
		}
	});
	
	return wall
}

module.exports = reflectiveWall