package acceleration.content

import arc.func.Cons
import arc.func.Floatc2
import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Fill
import arc.graphics.g2d.Lines
import arc.math.Angles
import arc.math.Mathf
import arc.math.geom.Geometry
import mindustry.ctype.ContentList
import mindustry.entities.Effect
import mindustry.entities.Effect.EffectContainer
import mindustry.graphics.Pal

import mindustry.graphics.Drawf

class AccelerationFx : ContentList {
    override fun load() {
        arctifluidFx = Effect(40f) { e ->
            Draw.color(AccelerationPal.arctifluid, Color.white, e.fout() / 5 + Mathf.randomSeedRange(e.id.toLong(), 0.12f))

            Angles.randLenVectors(e.id.toLong(), 2, 1 + e.fin() * 3) { x, y ->
                Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.2f);
            }
        }

        quarkPlasmaFx = Effect(40f) { e ->
            Draw.color(AccelerationPal.quarkPlasma, Color.white, e.fout() / 5 + Mathf.randomSeedRange(e.id.toLong(), 0.12f))

            Angles.randLenVectors(e.id.toLong(), 2, 1 + e.fin() * 3) { x, y ->
                Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.2f);
            }
        }

        shockTrail = Effect(40f) { e ->
            Draw.color(Pal.accent)

            Fill.circle(e.x, e.y, e.rotation * e.fout())
        }

        overloadLaserHit = Effect(8f) { e ->
            Draw.color(Color.white, AccelerationPal.overdrive, e.fin())
            Lines.stroke(0.5f + e.fout())
            Lines.circle(e.x, e.y, e.fin() * 5f)

            Lines.stroke(e.fout() * 2f)
            Lines.poly(e.x, e.y, 12, 12f * e.fout())
        }

        overloadLaserCharge = Effect(8f) { e ->
            Draw.color(Color.white, AccelerationPal.overdrive, e.fin())
            Lines.stroke(0.35f + e.fout())
            Lines.circle(e.x, e.y, e.fin() * 5f)

            Lines.stroke(e.fout() * 2f)
            Lines.poly(e.x, e.y, 12, 6f * e.fout())
        }

        cryoLaserHit = Effect(8f) { e ->
            Draw.color(Color.white, AccelerationPal.arctifluid, e.fin())
            Lines.stroke(0.5f + e.fout())
            Lines.circle(e.x, e.y, e.fin() * 5f)

            Lines.stroke(e.fout() * 2f)
            Lines.poly(e.x, e.y, 12, 12f * e.fout())
        }

        cryoLaserCharge = Effect(8f) { e ->
            Draw.color(Color.white, AccelerationPal.arctifluid, e.fin())
            Lines.stroke(0.35f + e.fout())
            Lines.circle(e.x, e.y, e.fin() * 5f)

            Lines.stroke(e.fout() * 2f)
            Lines.poly(e.x, e.y, 12, 6f * e.fout())
        }

        cryorailShoot = Effect(36f, 250f) { e ->
            Draw.color(AccelerationPal.arctifluid, Color.white, e.fin())
            Fill.poly(e.x, e.y, 8, 16f * e.fout(), 360f * e.fout())

            Draw.color(AccelerationPal.cryo)

            for (i in Mathf.signs) {
                Drawf.tri(e.x, e.y, 15f * (e.fout()), 40f, e.rotation + 90f * i)
            }
        }

        cryorailHit = Effect(16f) { e ->
            Draw.color(AccelerationPal.cryo)

            for (i in Mathf.signs) {
                Drawf.tri(e.x, e.y, 10f * e.fout(), 60f, e.rotation + 140f * i)
            }
        }

        cryorailTrail = Effect(36f) { e ->
            Draw.color(AccelerationPal.cryo)

            for (i in Mathf.signs) {
                Drawf.tri(e.x, e.y, 10f * e.fout(), 24f, e.rotation + 90 + 90f * i)
            }
        }

        cryoHexagon = Effect(96f) { e ->
            Draw.color(AccelerationPal.cryo, Color.white, e.fin())
            Lines.stroke(5f * e.fout())
            Lines.poly(e.x, e.y, 8, 64f * e.fout(), 360f * Mathf.pow(1.1f, e.fout() * 10) % 360)
        }

        cryoHexagonInverted = Effect(48f) { e ->
            Draw.color(AccelerationPal.cryo, Color.white, e.fout())
            Lines.stroke(3f * e.fout())
            Lines.poly(e.x, e.y, 8, 64f * e.fin(), 360f * Mathf.pow(1.1f, e.fin() * 10) % 360)
        }

        voltaicBomb = Effect(15f, 100f) { e ->
            Draw.color(AccelerationPal.cryo)
            Lines.stroke(e.fout() * 4f)
            Lines.circle(e.x, e.y, 4f + e.finpow() * 20f)
            for (i in 0..3) {
                Drawf.tri(e.x, e.y, 6f, 80f * e.fout(), (i * 90 + 45).toFloat())
            }
            Draw.color()
            for (i in 0..3) {
                Drawf.tri(e.x, e.y, 3f, 30f * e.fout(), (i * 90 + 45).toFloat())
            }
        }

        voltaicTrail = Effect(30f
        ) { e ->
            for (i in 0..1) {
                Draw.color(AccelerationPal.cryo)
                val m = if (i == 0) 1f else 0.5f
                val rot = e.rotation + 180f
                val w = 25f * e.fout() * m

                Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id.toLong(), 15f)) * m, rot)
                Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f)
            }
        }

        voltaicShoot = Effect(24f) { e ->
            e.scaled(10f) { b ->
                Draw.color(Color.white, AccelerationPal.cryo, b.fin())
                Lines.stroke(b.fout() * 3f + 0.2f)
                Lines.circle(b.x, b.y, b.fin() * 50f)
            }

            Draw.color(AccelerationPal.cryo)

            for (i in Mathf.signs) {
                Drawf.tri(e.x, e.y, 13f * e.fout(), 85f, e.rotation + 90f * i)
                Drawf.tri(e.x, e.y, 13f * e.fout(), 50f, e.rotation + 20f * i)
            }
        }

        voltaicHit = Effect(20f, 200f) { e ->
            Draw.color(AccelerationPal.cryo)

            for (i in 0..1) {
                Draw.color(AccelerationPal.cryo)
                val m = if (i == 0) 1f else 0.5f
                for (j in 0..4) {
                    val rot = e.rotation + Mathf.randomSeedRange((e.id + j).toLong(), 50f)
                    val w = 23f * e.fout() * m
                    Drawf.tri(e.x, e.y, w, (80f + Mathf.randomSeedRange((e.id + j).toLong(), 40f)) * m, rot)
                    Drawf.tri(e.x, e.y, w, 20f * m, rot + 180f)
                }
            }

            e.scaled(10f) { c ->
                Draw.color(AccelerationPal.cryo)
                Lines.stroke(c.fout() * 2f + 0.2f)
                Lines.circle(e.x, e.y, c.fin() * 30f)
            }

            e.scaled(12f) { c ->
                Draw.color(AccelerationPal.cryo)
                Angles.randLenVectors(
                    e.id.toLong(), 25, 5f + e.fin() * 80f, e.rotation, 60f
                ) { x, y -> Fill.square(e.x + x, e.y + y, c.fout() * 3f, 45f) }
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
        lateinit var cryoHexagon : Effect
        lateinit var cryoHexagonInverted : Effect

        lateinit var voltaicBomb : Effect
        lateinit var voltaicTrail : Effect
        lateinit var voltaicShoot : Effect
        lateinit var voltaicHit : Effect
    }
}