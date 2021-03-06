abstract class Attaque {
    var dmg : Int = 0
    var pp : Int = 0

    // modification des stats du lanceur/receveur, l'entier est l'indice de modification
    // le Double est la probabilité du changement 
    var debuff_atk : (Int,Double) = (0,0.0)
    var debuff_speed : (Int,Double) = (0,0.0)
    var debuff_defense : (Int,Double) = (0,0.0)
    var buff_atk : (Int,Double) = (0,0.0)
    var buff_defense : (Int,Double) = (0,0.0)
    var buff_speed : (Int,Double) = (0,0.0)
    var precision : Int = 0
    var atype : Ttype = Normal
    var name : String = ""
    var pstate : (States,Double) = (None_state,0.0)
}

object Hydrocanon extends Attaque{
    dmg = 110
    pp = 5
    name = "Hydrocanon"
    precision = 80
    atype = Eau
}
object Pistolet_a_O extends Attaque{
    dmg = 40
    pp = 35
    precision = 100
    name = "Pistolet à O"
    atype = Eau
}

object Bulles_d_O extends Attaque{
    dmg = 65
    pp = 25
    precision = 100
    name = "Bulles d'O"
    atype = Eau
    debuff_speed = (1,0.4)
}

object Surf extends Attaque{
    dmg = 90
    pp = 15
    precision = 100
    name = "Surf"
    atype = Eau
}

object Trempette extends Attaque{
    dmg = 0
    pp = 42
    precision = 100
    name = "Trempette"
    atype = Eau
}

object Repli extends Attaque{
    dmg = 0
    pp = 25
    precision = 100
    name = "Repli"
    atype = Eau
    buff_defense = (2,1.0)
}

object Déflagration extends Attaque{
    dmg = 110
    pp = 5
    precision = 85
    name = "Déflagration"
    atype = Feu
    pstate = (Burned,0.4)
}

object Fouet_lianes extends Attaque{
    dmg = 90
    pp = 35
    precision = 100
    name = "fouet lianes"
    atype = Plante
}


object Flammèche extends Attaque{
    dmg = 40
    pp = 35
    precision = 100
    name = "Flammèche"
    atype = Feu
    pstate = (Burned,0.4)
}

object Lance_flammes extends Attaque{
    dmg = 90
    pp = 15
    precision = 100
    name = "Lance-flammes"
    atype = Feu
    pstate = (Burned,0.4)
}

object Feu_follet extends Attaque{
    dmg = 0
    pp = 25
    precision = 100
    name = "Feu-follet"
    atype = Feu
    pstate = (Burned,1.0)
}


object Yoga extends Attaque{
    dmg = 0
    pp = 25
    precision = 100
    name = "yoga"
    atype = Psy
    buff_atk = (1,1.0)
}

object Psyko extends Attaque{
    dmg = 90
    pp = 15
    precision = 100
    name = "psyko"
    atype = Psy
    debuff_defense = (1,0.4)
}

object Hypnose extends Attaque{
    dmg = 0
    precision = 60
    pp = 15
    name = "Hypnose"
    atype = Psy
    pstate = (Sommeil,1.0)
}

object Hate extends Attaque{
    dmg = 0
    pp = 25
    precision = 100
    name = "Hate"
    atype = Psy
    buff_speed = (2,1.0)
}

object Bouclier extends Attaque{
    dmg = 0
    pp = 25
    precision = 100
    name = "Bouclier"
    atype = Psy
    buff_defense = (1,1.0)
}

object Blizzard extends Attaque{
    dmg = 110
    pp = 5
    precision = 65
    name = "blizzard"
    atype = Glace
    pstate = (Freeze,0.5)
}

object Jet_pierres extends Attaque{
    dmg = 50
    pp = 35
    precision = 90
    name = "jet_pierres"
    atype = Roche
}

object Tornade extends Attaque{
    dmg = 40
    pp = 35
    precision = 100
    name = "Tornade"
    atype = Vol
}

object Bec_vrille extends Attaque{
    dmg = 80
    pp = 20
    precision = 100
    name = "Bec vrille"
    atype = Vol
}

object Charge extends Attaque{
    dmg = 50
    pp = 35
    precision = 100
    name = "Charge"
    atype = Normal
}

object Poudre_toxik extends Attaque{
    dmg = 0
    pp = 35
    precision = 75
    name = "Poudre Toxik"
    atype = Poison
    pstate = (Empoisonnement,1.0)
}

object Acide extends Attaque{
    dmg = 40
    pp = 30
    precision = 100
    name = "Acide"
    atype = Poison
    pstate = (Empoisonnement,0.4)
}

object Secretion extends Attaque{
    dmg = 0
    pp = 30
    precision = 95
    name = "Sécrétion"
    atype = Insecte
    debuff_speed = (-2,1.0)
}

object Piqure extends Attaque{
    dmg = 60
    pp = 25
    precision = 100
    name = "Piqure"
    atype = Insecte
}

object Draco_Griffes extends Attaque{
    dmg = 80
    pp = 15
    precision = 100
    name = "Draco-griffes"
    atype = Dragon
}

object Mini_Pause extends Attaque{
    dmg = 0
    pp = 100
    precision = 100
    name = "mini-pause"
    atype = Normal
}




