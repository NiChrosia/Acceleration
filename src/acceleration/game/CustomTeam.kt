package acceleration.game

import arc.graphics.Color
import mindustry.game.Team

class SingleColorTeam(id: Int, name: String, color: Color) : Team(id, name, color)
class MultiColorTeam(id: Int, name: String, color: Color, pal1: Color, pal2: Color, pal3: Color) : Team(id, name, color, pal1, pal2, pal3)