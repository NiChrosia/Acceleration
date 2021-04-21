package acceleration.content

import acceleration.game.MultiColorTeam
import mindustry.ctype.ContentList
import mindustry.game.Team

class AccelerationTeams : ContentList {
    override fun load() {
        arche = MultiColorTeam(6, "arche", AccelerationPal.arche,
            AccelerationPal.archePal1, AccelerationPal.archePal2, AccelerationPal.archePal3
        )
    }

    companion object {
        lateinit var arche : Team
    }
}