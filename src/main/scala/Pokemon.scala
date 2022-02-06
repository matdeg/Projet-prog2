abstract class Pokemon(pname : String) {
    var maitre : Character = Empty_character 
    var alive = false 
    var name = pname
    var image = ""
    var max_hp = 0
    var lvl = 5
    var hp = 0
    var base_atk = 0
    var base_defense = 0
    var base_speed = 0
    var atk_mult = 0
    var defense_mult = 0 
    var speed_mult = 0
    def atk = {(base_atk.toFloat * Func.mult(atk_mult)).toInt}
    def defense = {(base_defense.toFloat * Func.mult(defense_mult)).toInt}
    def speed = {(base_speed.toFloat * Func.mult(speed_mult)).toInt}
    var ptype = new scala
    var attaques = new Array[Attaque](4)
    var state = new none_state
    def cast_attaque(i : Int,defenser : Pokemon) = {
        var a = attaques(i); 
        val r = scala.util.Random; 
        println(this.name + " lance l'attaque " + a.name) 
        if (r.nextFloat() < ((a.precision).toFloat/100)) {
            if (a.dmg > 0) {defenser.receive_attaque(a,this)}
            if (a.buff_atk > 0) {atk_mult = Func.min(a.buff_atk + atk_mult,6)}
            if (a.buff_defense > 0) {defense_mult = Func.min(a.buff_defense + defense_mult,6)}
            if (a.buff_speed > 0) {speed_mult = Func.min(a.buff_speed + speed_mult,6)}
            if (a.buff_atk < 0) {atk_mult = Func.max(a.buff_atk + atk_mult,-6)}
            if (a.buff_defense < 0) {defense_mult = Func.max(a.buff_defense + defense_mult,-6)}
            if (a.buff_speed < 0) {speed_mult = Func.max(a.buff_speed + speed_mult,-6)}
        }
        else {
            println(this.name + " a raté l'attaque...")
        }
    }
    def receive_attaque(a: Attaque, atker : Pokemon):Unit = {
        var dmg_taken = (((0.4 * atker.lvl.toFloat + 2.0) * a.dmg.toFloat * atker.atk.toFloat / (50.0 * defense.toFloat)) * (a.Atype).affinites(ptype)).toInt
        println(this.name + " perd " + dmg_taken.toString() + " PV !")
        hp = hp - dmg_taken
        if (a.debuff_atk > 0) {atk_mult = Func.max(-a.debuff_atk + atk_mult,-6)}
        if (a.debuff_defense > 0) {defense_mult = Func.max(-a.debuff_defense + defense_mult,-6)}
        if (a.debuff_speed > 0) {speed_mult = Func.max(-a.debuff_speed + speed_mult,-6)}
        if (a.debuff_atk < 0) {atk_mult = Func.min(-a.debuff_atk + atk_mult,6)}
        if (a.debuff_defense < 0) {defense_mult = Func.min(-a.debuff_defense + defense_mult,6)}
        if (a.debuff_speed < 0) {speed_mult = Func.min(-a.debuff_speed + speed_mult,6)}
        if (hp <= 0) {println(this.name + " est KO !"); alive = false; maitre.nb_alive -= 1}
    }
}

object Empty_Pokemon extends Pokemon("") {}
class Pokemon_test0(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    hp = 50
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = new scala
    attaques = Array(new shit,new shit,new shit,new shit)
    state = new none_state
}

class Pokemon_test1(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pok_test.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 100
    ptype = new scala
    attaques = Array(new axiom_false,new scala_vibes,new python_vibes,new ocaml_vibes)
    state = new none_state
}