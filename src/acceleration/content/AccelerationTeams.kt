package acceleration.content

import acceleration.game.MultiColorTeam
import mindustry.ctype.ContentList
import mindustry.game.Team

class AccelerationTeams : ContentList {
    override fun load() {
        arche = MultiColorTeam(6, "arche", AccelerationColors.archeColor,
            AccelerationColors.archePal1, AccelerationColors.archePal2, AccelerationColors.archePal3
        )
    }

    companion object {
        lateinit var arche : Team
    }
}