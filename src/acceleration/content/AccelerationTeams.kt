package acceleration.content

import acceleration.game.MultiColorTeam
import mindustry.ctype.ContentList
import mindustry.game.Team

class AccelerationTeams : ContentList {
    override fun load() {
        arche = MultiColorTeam(6, "arche", AccelerationColors.archaiColor,
            AccelerationColors.archaiPal1, AccelerationColors.archaiPal2, AccelerationColors.archaiPal3
        )
    }

    companion object {
        lateinit var arche : Team
    }
}