abstract class States {
    var name : String = ""
    var mspeed : Int = 0
    var matk : Int = 0
    var mdef : Int = 0
    var stun : Boolean = false 
    var miss : Double = 0.0
    var duration : Int = 0
    var degat : Boolean = false
}

object Freeze extends States {
    name = "Freeze"
    mspeed = 0
    matk = 0
    mdef = 0
    stun = true
    miss = 0.0
    duration = 3
}

object Burned extends States {
    name = "Burned"
    degat = true
    mspeed = 0
    matk = -1
    mdef = 0
    stun = false
    miss = 0.0
    duration = 1000
}

object Sommeil extends States {
    name = "Sommeil"
    mspeed = 0
    matk = 0
    mdef = 0
    stun = true
    miss = 0
    duration = 3
}

object Paralysie extends States {
    name = "Paralysie"
    mspeed = -1
    matk = 0
    mdef = 0
    stun = false
    miss = 0.5
    duration = 1000
}

object Empoisonnement extends States {
    name = "Paralysie"
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