abstract class Attaque {
    var dmg : Int = 0
    var debuff_atk : Int = 0
    var debuff_speed : Int = 0
    var debuff_defense : Int = 0
    var buff_atk : Int = 0
    var buff_defense : Int = 0
    var buff_speed : Int = 0
    var precision : Int = 0
    var atype : Ttype = Normal
    var name : String = ""
    var pstate : (States,Double) = (None_state,0.0)
}

object Hydrocanon extends Attaque{
    dmg = 110
    name = "Hydrocanon"
    precision = 80
    atype = Eau
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}
object Pistolet_a_O extends Attaque{
    dmg = 40
    precision = 100
    name = "Pistolet à O"
    atype = Eau
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}

object Bulles_d_O extends Attaque{
    dmg = 65
    precision = 100
    name = "Bulles d'O"
    atype = Eau
    debuff_atk = 0
    debuff_speed = 1
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}

object Surf extends Attaque{
    dmg = 90
    precision = 100
    name = "Surf"
    atype = Eau
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}

object Trempette extends Attaque{
    dmg = 0
    precision = 100
    name = "Trempette"
    atype = Eau
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}

object Repli extends Attaque{
    dmg = 0
    precision = 100
    name = "Repli"
    atype = Eau
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 1
    buff_speed = 0
}

object Déflagration extends Attaque{
    dmg = 110
    precision = 85
    name = "Déflagration"
    atype = Feu
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
    pstate = (Burned,0.4)
}

object Fouet_lianes extends Attaque{
    dmg = 45
    precision = 100
    name = "fouet lianes"
    atype = Plante
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}


object Flammèche extends Attaque{
    dmg = 40
    precision = 100
    name = "Flammèche"
    atype = Feu
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
    pstate = (Burned,0.4)
}

object Lance_flammes extends Attaque{
    dmg = 90
    precision = 100
    name = "Lance-flammes"
    atype = Feu
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
    pstate = (Burned,0.4)
}

object Feu_follet extends Attaque{
    dmg = 0
    precision = 100
    name = "Feu-follet"
    atype = Feu
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
    pstate = (Burned,1.0)
}


object Yoga extends Attaque{
    dmg = 0
    precision = 100
    name = "yoga"
    atype = Psy
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 1
    buff_defense = 0
    buff_speed = 0
}

object Psyko extends Attaque{
    dmg = 90
    precision = 100
    name = "psyko"
    atype = Psy
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 1
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}

object Hypnose extends Attaque{
    dmg = 0
    precision = 60
    name = "Hypnose"
    atype = Psy
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
    pstate = (Sommeil,1.0)
}

object Hate extends Attaque{
    dmg = 0
    precision = 100
    name = "Hate"
    atype = Psy
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 2
}

object Bouclier extends Attaque{
    dmg = 0
    precision = 100
    name = "Bouclier"
    atype = Psy
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 2
    buff_speed = 0
}

object Blizzard extends Attaque{
    dmg = 110
    precision = 65
    name = "blizzard"
    atype = Glace
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
    pstate = (Freeze,0.5)
}

object Jet_pierres extends Attaque{
    dmg = 50
    precision = 90
    name = "jet_pierres"
    atype = Roche
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}

object Tornade extends Attaque{
    dmg = 40
    precision = 100
    name = "Tornade"
    atype = Vol
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}

object Bec_vrille extends Attaque{
    dmg = 80
    precision = 100
    name = "Bec vrille"
    atype = Vol
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}