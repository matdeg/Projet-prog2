abstract class Pokemon(pname : String) {
    var alive = true 
    var name = pname
    var max_hp = 0
    var lvl = 5
    var hp = 0
    var atk = 0
    var defense = 0
    var speed = 0
    var ptype = new scala
    var attaques = new Array[Attaque](4)
    var state = new none_state
    def cast_attaque(i : Int,p2 : Pokemon) = {var a = attaques(i); val r = scala.util.Random; if (r.nextFloat() < ((a.precision).toFloat/100)) {p2.receive_attaque(a,this)}}
    def receive_attaque(a: Attaque, p2 : Pokemon):Unit = {
        atk = (atk.toFloat * (1 - ((a.debuff_atk).toFloat)/5)).toInt
        defense = (defense.toFloat * (1 - (a.debuff_defense).toFloat/5)).toInt
        speed = (speed.toFloat * (1 - (a.debuff_speed).toFloat/5)).toInt
        hp = (hp.toFloat - ((0.4 * p2.lvl.toFloat + 2.0) * a.dmg.toFloat * p2.atk.toFloat / (50.0 * defense.toFloat)) * (a.Atype).affinites(ptype)).toInt
    }
}

class Pokemon_test(pname : String) extends Pokemon(pname) {
    max_hp = 50
    hp = 50
    lvl = 10
    atk = 50
    defense = 50
    speed = 100
    ptype = new scala
    name = pname 
    attaques = Array(new axiom_false,new scala_vibes,new scala_vibes,new scala_vibes)
    state = new none_state
}