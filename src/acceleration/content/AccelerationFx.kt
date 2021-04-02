package acceleration.content

import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Fill
import arc.math.Angles
import arc.math.Mathf
import arc.util.Log
import mindustry.ctype.ContentList
import mindustry.entities.Effect

class AccelerationFx : ContentList {
    override fun load() {
        arctifluidFx = Effect(40f) { e ->
            Draw.color(AccelerationColors.arctifluidColor, Color.white, e.fout() / 5 + Mathf.randomSeedRange(e.id.toLong(), 0.12f))

            Angles.randLenVectors(e.id.toLong(), 2, 1 + e.fin() * 3) { x, y ->
                Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.2f);
            }
        }

        quarkPlasmaFx = Effect(40f) { e ->
            Draw.color(AccelerationColors.quarkPlasmaColor, Color.white, e.fout() / 5 + Mathf.randomSeedRange(e.id.toLong(), 0.12f))

            Angles.randLenVectors(e.id.toLong(), 2, 1 + e.fin() * 3) { x, y ->
                Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.2f);
            }
        }

        Log.info("Loaded [accent]Acceleration[] [sky]Kotlin[] FX successfully.")
    }

    companion object {
        lateinit var arctifluidFx : Effect
        lateinit var quarkPlasmaFx : Effect
    }
}