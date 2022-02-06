import java.awt.Taskbar.State
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
    var ptype : Ttype = Scala
    var attaques = new Array[Attaque](4)
    var state : States = None_state
    var remaining_time : Int = 0
    def atk = {(base_atk.toFloat * Func.mult(atk_mult + state.matk)).toInt}
    def defense = {(base_defense.toFloat * Func.mult(defense_mult + state.mdef)).toInt}
    def speed = {(base_speed.toFloat * Func.mult(speed_mult + state.mspeed)).toInt}
    def reload_state = {
        remaining_time = Func.max(remaining_time - 1,0)
        if (remaining_time == 0) {
            state = None_state
        }
    }
    def cast_attaque(i : Int,defenser : Pokemon) = {
        var a = attaques(i); 
        val r = scala.util.Random; 
        if (!state.stun) {
            println(this.name + " lance l'attaque " + a.name) 
            if (r.nextDouble > state.miss) {
                if (r.nextFloat < ((a.precision).toFloat/100)) {
                    if (a.dmg > 0) {defenser.receive_attaque(a,this)}
                    if (a.buff_atk > 0) {atk_mult = Func.min(a.buff_atk + atk_mult,6)
                                        println("L'attaque de " + this.name + " augmente !")}
                    if (a.buff_defense > 0) {defense_mult = Func.min(a.buff_defense + defense_mult,6)
                                            println("La défense de " + this.name + " augmente !")}
                    if (a.buff_speed > 0) {speed_mult = Func.min(a.buff_speed + speed_mult,6)
                                        println("La vitesse de " + this.name + " augmente !")}
                    if (a.buff_atk < 0) {atk_mult = Func.max(a.buff_atk + atk_mult,-6)
                                        println("L'attaque de " + this.name + " diminue...")}
                    if (a.buff_defense < 0) {defense_mult = Func.max(a.buff_defense + defense_mult,-6)
                                            println("La défense de " + this.name + " diminue...")}
                    if (a.buff_speed < 0) {speed_mult = Func.max(a.buff_speed + speed_mult,-6)
                                        println("La vitesse de " + this.name + " diminue...")}
                }
                else {
                    println(this.name + " a raté l'attaque...")
                }
            }
            else{
                println(this.name + " est paralysé, il n'a pas pu attaquer")
            }
        }
        else{
            println(this.name + " est gelé, il ne peut pas attaquer")
        }
    }
    def receive_attaque(a: Attaque, atker : Pokemon):Unit = {
        var dmg_taken = (((0.4 * atker.lvl.toFloat + 2.0) * a.dmg.toFloat * atker.atk.toFloat / (50.0 * defense.toFloat)) * (a.Atype).affinites(ptype)).toInt
        println(this.name + " perd " + dmg_taken.toString() + " PV !")
        hp = hp - dmg_taken
        val r = scala.util.Random
        if (a.debuff_atk > 0) {atk_mult = Func.max(-a.debuff_atk + atk_mult,-6)
                              println("L'attaque de " + this.name + " diminue...")}
        if (a.debuff_defense > 0) {defense_mult = Func.max(-a.debuff_defense + defense_mult,-6)
                                  println("La défense de " + this.name + " diminue...")}
        if (a.debuff_speed > 0) {speed_mult = Func.max(-a.debuff_speed + speed_mult,-6)
                                println("La vitesse de " + this.name + " diminue...")}
        if (a.debuff_atk < 0) {atk_mult = Func.min(-a.debuff_atk + atk_mult,6)
                                println("L'attaque de " + this.name + " augmente !")}
        if (a.debuff_defense < 0) {defense_mult = Func.min(-a.debuff_defense + defense_mult,6)
                                  println("La défense de " + this.name + " augmente !")}
        if (a.debuff_speed < 0) {speed_mult = Func.min(-a.debuff_speed + speed_mult,6)
                                println("La vitesse de " + this.name + " augmente !")}
        var (astate,pstate) = a.pstate 
        if (pstate > 0) {
            if  (r.nextDouble < pstate) {
                println(this.name + "vas subir l'effet " + astate.name)
                if (astate == state || state == None_state) {
                    state = astate 
                    remaining_time = astate.duration
                }
                else {
                    println("Mais " + this.name + " a déjà un effet !")
                }
            }
        } 
        if (hp <= 0) {println(this.name + " est KO !"); alive = false; maitre.nb_alive -= 1}
    }
}

object Empty_Pokemon extends Pokemon("") {}
class Pokemon_test0(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pok_test.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Axiom_false,Shit,Shit,Shit)
    state = None_state
}

class Pokemon_test1(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    hp = 50
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 100
    ptype = Scala
    attaques = Array(Axiom_false,Scala_vibes,Python_vibes,Ocaml_vibes)
    state = None_state
}