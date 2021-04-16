package acceleration.entities

import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Fill
import mindustry.entities.bullet.BasicBulletType
import mindustry.gen.Bullet
import mindustry.graphics.Pal

open class EnergyOrbBulletType : BasicBulletType() {
    var size: Float = 1f
    var color: Color = Pal.lancerLaser

    override fun draw(b: Bullet) {
        Draw.color(color)

        Fill.circle(b.x, b.y, size)

        Draw.reset()
    }
}