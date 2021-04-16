package acceleration.content

import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Fill
import arc.graphics.g2d.Lines
import arc.math.Angles
import arc.math.Mathf
import mindustry.ctype.ContentList
import mindustry.entities.Effect
import mindustry.graphics.Pal

import mindustry.graphics.Drawf

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

        shockTrail = Effect(40f) { e ->
            Draw.color(Pal.accent)

            Fill.circle(e.x, e.y, e.rotation * e.fout())
        }

        overloadLaserHit = Effect(8f) { e ->
            Draw.color(Color.white, AccelerationColors.overdrive, e.fin())
            Lines.stroke(0.5f + e.fout())
            Lines.circle(e.x, e.y, e.fin() * 5f)

            Lines.stroke(e.fout() * 2f)
            Lines.poly(e.x, e.y, 12, 12f * e.fout())
        }

        overloadLaserCharge = Effect(8f) { e ->
            Draw.color(Color.white, AccelerationColors.overdrive, e.fin())
            Lines.stroke(0.35f + e.fout())
            Lines.circle(e.x, e.y, e.fin() * 5f)

            Lines.stroke(e.fout() * 2f)
            Lines.poly(e.x, e.y, 12, 6f * e.fout())
        }

        cryoLaserHit = Effect(8f) { e ->
            Draw.color(Color.white, AccelerationColors.arctifluidColor, e.fin())
            Lines.stroke(0.5f + e.fout())
            Lines.circle(e.x, e.y, e.fin() * 5f)

            Lines.stroke(e.fout() * 2f)
            Lines.poly(e.x, e.y, 12, 12f * e.fout())
        }

        cryoLaserCharge = Effect(8f) { e ->
            Draw.color(Color.white, AccelerationColors.arctifluidColor, e.fin())
            Lines.stroke(0.35f + e.fout())
            Lines.circle(e.x, e.y, e.fin() * 5f)

            Lines.stroke(e.fout() * 2f)
            Lines.poly(e.x, e.y, 12, 6f * e.fout())
        }

        cryorailShoot = Effect(18f, 250f) { e ->
            e.scaled(10f) { b ->
                Draw.color(Color.white, Color.lightGray, b.fin())
                Lines.stroke(b.fout() * 3f + 0.2f)
                Lines.circle(b.x, b.y, b.fin() * 50f)
            }

            Draw.color(AccelerationColors.arctifluidColor)

            for (i in Mathf.signs) {
                Drawf.tri(e.x, e.y, 13f * e.fout(), 85f, e.rotation + 90f * i)
            }
        }

        cryorailHit = Effect(1f) { e ->
            Draw.color(AccelerationColors.arctifluidColor)

            for (i in Mathf.signs) {
                Drawf.tri(e.x, e.y, 10f * e.fout(), 60f, e.rotation + 140f * i)
            }
        }

        cryorailTrail = Effect(16f) { e ->
            Draw.color(AccelerationColors.arctifluidColor)

            for (i in Mathf.signs) {
                Drawf.tri(e.x, e.y, 10f * e.fout(), 24f, e.rotation + 90 + 90f * i)
            }
        }
    }

    companion object {
        lateinit var arctifluidFx : Effect
        lateinit var quarkPlasmaFx : Effect

        lateinit var shockTrail : Effect

        lateinit var overloadLaserHit : Effect
        lateinit var overloadLaserCharge : Effect

        lateinit var cryoLaserHit : Effect
        lateinit var cryoLaserCharge : Effect

        lateinit var cryorailShoot : Effect
        lateinit var cryorailHit : Effect
        lateinit var cryorailTrail : Effect
    }
}