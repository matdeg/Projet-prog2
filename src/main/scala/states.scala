abstract class States {
    var name : String = ""
    def file_name = "effets/" + name + ".png"

    // indice de modification des statistiques
    var mspeed : Int = 0
    var matk : Int = 0
    var mdef : Int = 0

    // true si incapable d'attaquer
    var stun : Boolean = false 

    // probabilité de rater les attaques
    var miss : Double = 0.0

    // durée de l'état 
    var duration : Int = 0

    // true si des dégats sont reçus à chaque tour
    var degat : Boolean = false
}

object Freeze extends States {
    name = "gel"
    mspeed = 0
    matk = 0
    mdef = 0
    stun = true
    miss = 0.0
    duration = 3
}

object Burned extends States {
    name = "burned"
    degat = true
    mspeed = 0
    matk = -1
    mdef = 0
    stun = false
    miss = 0.0
    duration = 1000
}

object Sommeil extends States {
    name = "sleep"
    mspeed = 0
    matk = 0
    mdef = 0
    stun = true
    miss = 0
    duration = 3
}

object Paralysie extends States {
    name = "para"
    mspeed = -1
    matk = 0
    mdef = 0
    stun = false
    miss = 0.5
    duration = 1000
}

object Empoisonnement extends States {
    name = "poison"
    degat = true
    mspeed = 0
    matk = 0
    mdef = 0
    stun = false
    miss = 0.5
    duration = 1000
} 

object None_state extends States {
    name = "none_state"
    mspeed = 0
    matk = 0
    mdef = 0
    stun = false
    miss = 0.0
    duration = 0
}