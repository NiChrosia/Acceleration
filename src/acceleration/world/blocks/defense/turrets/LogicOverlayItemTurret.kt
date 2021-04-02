package acceleration.world.blocks.defense.turrets

import arc.Core
import arc.graphics.g2d.Draw
import arc.graphics.g2d.TextureRegion
import arc.math.geom.Vec2
import mindustry.graphics.Layer
import mindustry.world.blocks.defense.turrets.ItemTurret

open class LogicOverlayItemTurret(name: String) : ItemTurret(name) {
    var logicOverlayRegion: TextureRegion? = null

    override fun load() {
        super.load()

        logicOverlayRegion = Core.atlas.find("$name-logic-overlay")
    }

    inner class LogicOverlayItemTurretBuild : ItemTurret.ItemTurretBuild() {
        var tr3 = Vec2()

        override fun draw() {
            tr3.setZero()
            tr3.trns(this.rotation, -this.recoil)
            Draw.z(Layer.turret + 1)

            if (logicControlled()) Draw.alpha(1f) else Draw.alpha(0f)
            Draw.rect(logicOverlayRegion, this.x + this.tr3.x, this.y + this.tr3.y, this.rotation - 90)

            Draw.alpha(1f)

            Draw.z(Layer.block)

            super.draw()
        }
    }
}