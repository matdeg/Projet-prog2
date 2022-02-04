abstract class Pokemon(pname : String) {
    var maitre = Empty_character 
    var alive = false 
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
    def cast_attaque(i : Int,defenser : Pokemon) = {
        var a = attaques(i); 
        val r = scala.util.Random; 
        println(this.name + " lance l'attaque " + a.name) 
        if (r.nextFloat() < ((a.precision).toFloat/100)) {
            defenser.receive_attaque(a,this)
            }
        else {
            println(this.name + " a raté l'attaque...")
        }
    }
    def receive_attaque(a: Attaque, atker : Pokemon):Unit = {
        atk = (atk.toFloat * (1 - ((a.debuff_atk).toFloat)/5)).toInt
        defense = (defense.toFloat * (1 - (a.debuff_defense).toFloat/5)).toInt
        speed = (speed.toFloat * (1 - (a.debuff_speed).toFloat/5)).toInt
        var dmg_taken = (((0.4 * atker.lvl.toFloat + 2.0) * a.dmg.toFloat * atker.atk.toFloat / (50.0 * defense.toFloat)) * (a.Atype).affinites(ptype)).toInt
        println(this.name + " perd " + dmg_taken.toString() + " PV !")
        hp = hp - dmg_taken
        if (hp <= 0) {println(this.name + " est KO !"); alive = false; maitre.nb_alive -= 1}
    }
}

object Empty_Pokemon extends Pokemon("") {}
class Pokemon_test0(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    hp = 50
    lvl = 10
    atk = 50
    defense = 50
    speed = 200
    ptype = new scala
    attaques = Array(new axiom_false,new scala_vibes,new python_vibes,new ocaml_vibes)
    state = new none_state
}

class Pokemon_test1(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    hp = 50
    lvl = 10
    atk = 50
    defense = 50
    speed = 100
    ptype = new scala
    attaques = Array(new axiom_false,new scala_vibes,new python_vibes,new ocaml_vibes)
    state = new none_state
}