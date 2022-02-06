abstract class States {
    var name : String = ""
    var mspeed : Int = 0
    var matk : Int = 0
    var mdef : Int = 0
    var stun : Boolean = false 
    var miss : Double = 0.0
    var duration : Int = 0
}

object Freeze extends States {
    name = "Freeze"
    mspeed = 0
    matk = 0
    mdef = 0
    stun = true
    miss = 0.0
    duration = 2
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