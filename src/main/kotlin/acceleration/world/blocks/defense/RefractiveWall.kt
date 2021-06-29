package acceleration.world.blocks.defense

import acceleration.func.math.negative
import arc.math.geom.Vec2
import mindustry.Vars
import mindustry.entities.Damage
import mindustry.entities.bullet.LaserBulletType
import mindustry.gen.Bullet
import mindustry.world.blocks.defense.Wall
import kotlin.math.abs

open class RefractiveWall(name: String) : Wall(name) {
    @Suppress("unused")
    inner class RefractiveWallBuild : Wall.WallBuild() {
        override fun collision(bullet: Bullet): Boolean {
            damage(bullet.damage)

            val type = bullet.type

            if (type is LaserBulletType) {
                val bulletCollisionLength = Damage.findLaserLength(bullet, type.length)
                val bulletVec = Vec2().trns(bullet.rotation(), bulletCollisionLength)

                val collision = Vec2(bullet.x + bulletVec.x, bullet.y + bulletVec.y)
                val diff = Vec2(abs(bullet.x - collision.x), abs(bullet.y - collision.y))
                val refract = Vec2(
                    bullet.x + diff.x.negative(bullet.x > x),
                    bullet.y + diff.y.negative(bullet.y > y)
                )

                val modifiedLaser = type.copy() as LaserBulletType
                modifiedLaser.length -= bulletCollisionLength

                if (!Vars.state.isPaused) {
                    modifiedLaser.create(bullet, team, collision.x, collision.y, angleTo(refract))
                }
            }

            return bullet.type is LaserBulletType
        }
    }
}