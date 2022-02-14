import java.awt.Taskbar.State
abstract class Pokemon(pname : String) {
    var name = pname
    var image = ""
    var lvl = 5
    var xp = 0
    var max_hp = 0
    var hp = 0
    var base_atk = 0
    var base_defense = 0
    var base_speed = 0
    var base_xp_given : Int = 70
    var ptype : Ttype = Scala
    var attaques = new Array[Attaque](4)
    var atk_per_lvl : Double = 0
    var defense_per_lvl : Double = 0
    var speed_per_lvl : Double = 0
    var held_item : Item = Empty_item

    var xp_given : Int = lvl * base_xp_given / 7
    var atk_mult = 0
    var defense_mult = 0 
    var speed_mult = 0
    var maitre : Character = Empty_character 
    var alive = false 
    var state : States = None_state
    var remaining_time : Int = 0
    def next_xp = {3 * lvl * lvl}
    def atk = {((base_atk.toFloat + lvl.toFloat * atk_per_lvl.toFloat) * Func.mult(atk_mult + state.matk + held_item.held_atk)).toInt}
    def defense = {((base_defense.toFloat + lvl.toFloat * defense_per_lvl.toFloat)* Func.mult(defense_mult + state.mdef + held_item.held_defense)).toInt}
    def speed = {((base_speed.toFloat + lvl.toFloat * speed_per_lvl.toFloat) * Func.mult(speed_mult + state.mspeed + held_item.held_speed)).toInt}
    def reload_state = {
        remaining_time = Func.max(remaining_time - 1,0)
        if (remaining_time == 0) {
            state = None_state
        }
    }
    def use_item(i : Item) = {
        hp = Func.max(hp + i.regen,max_hp)
        if (i.regen > 0) {Fenetre.msgbox.print_msg(this.name + " a récupéré de la vie !")
        if (i.buff_atk > 0) {atk_mult = Func.min(i.buff_atk + atk_mult,6)
                            Fenetre.msgbox.print_msg("L'attaque de " + this.name + " augmente !")}
        if (i.buff_defense > 0) {defense_mult = Func.min(i.buff_defense + defense_mult,6)
                                Fenetre.msgbox.print_msg("La défense de " + this.name + " augmente !")}
        if (i.buff_speed > 0) {speed_mult = Func.min(i.buff_speed + speed_mult,6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " augmente !")}
        if (i.buff_atk < 0) {atk_mult = Func.max(i.buff_atk + atk_mult,-6)
                            Fenetre.msgbox.print_msg("L'attaque de " + this.name + " diminue...")}
        if (i.buff_defense < 0) {defense_mult = Func.max(i.buff_defense + defense_mult,-6)
                                Fenetre.msgbox.print_msg("La défense de " + this.name + " diminue...")}
        if (i.buff_speed < 0) {speed_mult = Func.max(i.buff_speed + speed_mult,-6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " diminue...")}
        
}
    }
    def add_xp(exp : Int) = {
        Fenetre.msgbox.print_msg(this.name + " a gagné " + exp.toString + " points d'expérience !")
        Thread.sleep(1000)
        xp += exp
        if (xp >= next_xp){
            while (xp >= next_xp) {
                xp -= next_xp
                lvl += 1
            }
            Fenetre.msgbox.print_msg(this.name + " est monté au niveau " + lvl)
        }
    }
    def cast_attaque(i : Int,defenser : Pokemon) = {
        var a = attaques(i); 
        val r = scala.util.Random; 
        if (!state.stun) {
            Fenetre.msgbox.print_msg(this.name + " lance l'attaque " + a.name)
            Thread.sleep(2000)
            if (r.nextDouble > state.miss) {
                if (r.nextFloat < ((a.precision).toFloat/100)) {
                    if (a.dmg > 0) {defenser.receive_attaque(a,this)}
                    if (a.buff_atk > 0) {atk_mult = Func.min(a.buff_atk + atk_mult,6)
                                        Fenetre.msgbox.print_msg("L'attaque de " + this.name + " augmente !")}
                    if (a.buff_defense > 0) {defense_mult = Func.min(a.buff_defense + defense_mult,6)
                                            Fenetre.msgbox.print_msg("La défense de " + this.name + " augmente !")}
                    if (a.buff_speed > 0) {speed_mult = Func.min(a.buff_speed + speed_mult,6)
                                        Fenetre.msgbox.print_msg("La vitesse de " + this.name + " augmente !")}
                    if (a.buff_atk < 0) {atk_mult = Func.max(a.buff_atk + atk_mult,-6)
                                        Fenetre.msgbox.print_msg("L'attaque de " + this.name + " diminue...")}
                    if (a.buff_defense < 0) {defense_mult = Func.max(a.buff_defense + defense_mult,-6)
                                            Fenetre.msgbox.print_msg("La défense de " + this.name + " diminue...")}
                    if (a.buff_speed < 0) {speed_mult = Func.max(a.buff_speed + speed_mult,-6)
                                        Fenetre.msgbox.print_msg("La vitesse de " + this.name + " diminue...")}
                }
                else {
                    Fenetre.msgbox.print_msg(this.name + " a raté l'attaque...")
                }
            }
            else{
                Fenetre.msgbox.print_msg(this.name + " est paralysé, il n'a pas pu attaquer")
            }
        }
        else{
            Fenetre.msgbox.print_msg(this.name + " est gelé, il ne peut pas attaquer")
        }
        if (!defenser.alive) {add_xp(defenser.xp_given)}
    }
    def receive_attaque(a: Attaque, atker : Pokemon):Unit = {
        var dmg_taken = (((0.4 * atker.lvl.toFloat + 2.0) * a.dmg.toFloat * atker.atk.toFloat / (50.0 * defense.toFloat)) * (a.Atype).affinites(ptype)).toInt
        Fenetre.msgbox.print_msg(this.name + " perd " + dmg_taken.toString() + " PV !")
        hp = Func.max(hp - dmg_taken,0)
        val r = scala.util.Random
        if (a.debuff_atk > 0) {atk_mult = Func.max(-a.debuff_atk + atk_mult,-6)
                              Fenetre.msgbox.print_msg("L'attaque de " + this.name + " diminue...")}
        if (a.debuff_defense > 0) {defense_mult = Func.max(-a.debuff_defense + defense_mult,-6)
                                  Fenetre.msgbox.print_msg("La défense de " + this.name + " diminue...")}
        if (a.debuff_speed > 0) {speed_mult = Func.max(-a.debuff_speed + speed_mult,-6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " diminue...")}
        if (a.debuff_atk < 0) {atk_mult = Func.min(-a.debuff_atk + atk_mult,6)
                                Fenetre.msgbox.print_msg("L'attaque de " + this.name + " augmente !")}
        if (a.debuff_defense < 0) {defense_mult = Func.min(-a.debuff_defense + defense_mult,6)
                                  Fenetre.msgbox.print_msg("La défense de " + this.name + " augmente !")}
        if (a.debuff_speed < 0) {speed_mult = Func.min(-a.debuff_speed + speed_mult,6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " augmente !")}
        var (astate,pstate) = a.pstate 
        if (pstate > 0) {
            if  (r.nextDouble < pstate) {
                Fenetre.msgbox.print_msg(this.name + "vas subir l'effet " + astate.name)
                if (astate == state || state == None_state) {
                    state = astate 
                    remaining_time = astate.duration
                }
                else {
                    Fenetre.msgbox.print_msg("Mais " + this.name + " a déjà un effet !")
                }
            }
        }
        if (this.maitre.is_main) {
            Fenetre.bataille.print_pok_perso(this)
        }
        else {
            Fenetre.bataille.print_pok_op(this)
        }
        if (hp == 0) {Fenetre.msgbox.print_msg(this.name + " est KO !"); alive = false; maitre.nb_alive -= 1}
    }
}

object Empty_Pokemon extends Pokemon("") {}

class Alabri(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/alabri.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Alacool(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/alacool.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Dracarpe(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/dracarpe.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Galopan(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/galopan.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Kokicarpe(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/kokicarpe.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Mcool(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/m.cool.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Mherbe(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/m.herbe.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Magilangue(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/magilangue.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Poissocarpe(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/poissocarpe.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Poryodin(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/poryodin.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Rhinocarpe(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/rhinocarpe.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}
class salatard(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/salatard.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Starstar(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/Starstar.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Salatard(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/salatard.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Tentapan(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/tentapan.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}

class Torgogo(pname : String) extends Pokemon(pname) {
    max_hp = 50
    alive = true
    image = "pokemons/torgogo.png"
    hp = 30
    lvl = 10
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Scala
    attaques = Array(Ocaml_vibes,Ocaml_vibes,Ocaml_vibes,Ocaml_vibes)
    state = None_state
}