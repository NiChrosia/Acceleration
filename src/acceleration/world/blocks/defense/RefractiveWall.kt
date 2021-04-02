package acceleration.world.blocks.defense

import arc.math.geom.Vec2
import mindustry.Vars
import mindustry.entities.Damage
import mindustry.entities.bullet.LaserBulletType
import mindustry.gen.Bullet
import mindustry.io.JsonIO
import mindustry.world.blocks.defense.Wall
import kotlin.math.abs

open class RefractiveWall(name: String) : Wall(name) {
    inner class RefractiveWallBuild : Wall.WallBuild() {
        override fun collision(bullet: Bullet?): Boolean {
            if (bullet != null) {
                damage(bullet.damage)

                val bulletType = bullet.type.javaClass.superclass
                val pierce = bulletType == LaserBulletType().javaClass

                if (pierce) {
                    val bulletCollisionLength = Damage.findLaserLength(bullet, 200f) // Value hardcoded, doesn't matter due
                    val bulletVec = Vec2()

                    bulletVec.trns(bullet.rotation(), bulletCollisionLength)

                    val collision = object {
                        var x: Float = bullet.x + bulletVec.x
                        var y: Float = bullet.y + bulletVec.y
                    }

                    val right: Boolean = bullet.x > x
                    val top: Boolean = bullet.y > y

                    val diffX: Float = abs(bullet.x - collision.x)
                    val diffY: Float = abs(bullet.y - collision.y)

                    val refractX: Float = bullet.x + diffX * if (right) -1 else 1
                    val refractY: Float = bullet.y + diffY * if (top) -1 else 1

                    val refractAngle: Float = angleTo(refractX, refractY)

                    val modifiedLaser = LaserBulletType()
                    JsonIO.copy(bullet.type, modifiedLaser)
                    modifiedLaser.length // = bullet.type.length - bulletCollisionLength // Currently nothing happens, as bullet.type.length cannot be compiled.

                    val shoot = {modifiedLaser.create(bullet, this.team, collision.x, collision.y, refractAngle)}
                    if (!Vars.state.isPaused) shoot()
                }

                return !pierce
            }
            return true
        }
    }
}