abstract class states {
    def mspeed : Float
    def matk : Float
    def mdef : Float
    def stun : Boolean
}

class freeze extends states {
    override def mspeed = 1
    override def matk = 1
    override def mdef = 1
    override def stun = true
}

class none_state extends states {
    override def mspeed = 1
    override def matk = 1
    override def mdef = 1
    override def stun = false
}