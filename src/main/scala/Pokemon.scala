import java.awt.Taskbar.State
abstract class Pokemon(pname : String) {
    var name = pname
    var image = ""
    var lvl = 5
    var xp = 0
    var base_max_hp = 0
    var hp = 0
    var base_atk = 0
    var base_defense = 0
    var base_speed = 0
    var base_xp_given : Int = 70
    var ptype : Ttype = Normal
    var attaques = new Array[Attaque](4)
    var hp_per_lvl : Double = 0
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
    def max_hp = {(base_max_hp.toFloat + lvl.toFloat * hp_per_lvl.toFloat).toInt}
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
        if (i.regen > 0) {Fenetre.msgbox.print_msg(this.name + " a récupéré de la vie !");Thread.sleep(2000)}
        if (i.buff_atk > 0) {atk_mult = Func.min(i.buff_atk + atk_mult,6)
                            Fenetre.msgbox.print_msg("L'attaque de " + this.name + " augmente !");Thread.sleep(2000)}
        if (i.buff_defense > 0) {defense_mult = Func.min(i.buff_defense + defense_mult,6)
                                Fenetre.msgbox.print_msg("La défense de " + this.name + " augmente !");Thread.sleep(2000)}
        if (i.buff_speed > 0) {speed_mult = Func.min(i.buff_speed + speed_mult,6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " augmente !");Thread.sleep(2000)}
        if (i.buff_atk < 0) {atk_mult = Func.max(i.buff_atk + atk_mult,-6)
                            Fenetre.msgbox.print_msg("L'attaque de " + this.name + " diminue...");Thread.sleep(2000)}
        if (i.buff_defense < 0) {defense_mult = Func.max(i.buff_defense + defense_mult,-6)
                                Fenetre.msgbox.print_msg("La défense de " + this.name + " diminue...");Thread.sleep(2000)}
        if (i.buff_speed < 0) {speed_mult = Func.max(i.buff_speed + speed_mult,-6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " diminue...");Thread.sleep(2000)}
        }
    def add_xp(exp : Int) = {
        Fenetre.msgbox.print_msg(this.name + " a gagné " + exp.toString + " points d'expérience !");Thread.sleep(2000)
        xp += exp
        if (xp >= next_xp){
            while (xp >= next_xp) {
                xp -= next_xp
                lvl += 1
            }
            Fenetre.msgbox.print_msg(this.name + " est monté au niveau " + lvl);Thread.sleep(2000)
        }
    }
    def cast_attaque(i : Int,defenser : Pokemon) = {
        var a = attaques(i); 
        val r = scala.util.Random; 
        if (!state.stun) {
            Fenetre.msgbox.print_msg(this.name + " lance l'attaque " + a.name);Thread.sleep(2000)
            if (r.nextDouble > state.miss) {
                if (r.nextFloat < ((a.precision).toFloat/100)) {
                    if (a.dmg > 0) {defenser.receive_attaque(a,this)}
                    if (a.buff_atk > 0) {atk_mult = Func.min(a.buff_atk + atk_mult,6)
                                        Fenetre.msgbox.print_msg("L'attaque de " + this.name + " augmente !");Thread.sleep(2000)}
                    if (a.buff_defense > 0) {defense_mult = Func.min(a.buff_defense + defense_mult,6)
                                            Fenetre.msgbox.print_msg("La défense de " + this.name + " augmente !");Thread.sleep(2000)}
                    if (a.buff_speed > 0) {speed_mult = Func.min(a.buff_speed + speed_mult,6)
                                        Fenetre.msgbox.print_msg("La vitesse de " + this.name + " augmente !");Thread.sleep(2000)}
                    if (a.buff_atk < 0) {atk_mult = Func.max(a.buff_atk + atk_mult,-6)
                                        Fenetre.msgbox.print_msg("L'attaque de " + this.name + " diminue...");Thread.sleep(2000)}
                    if (a.buff_defense < 0) {defense_mult = Func.max(a.buff_defense + defense_mult,-6)
                                            Fenetre.msgbox.print_msg("La défense de " + this.name + " diminue...");Thread.sleep(2000)}
                    if (a.buff_speed < 0) {speed_mult = Func.max(a.buff_speed + speed_mult,-6)
                                        Fenetre.msgbox.print_msg("La vitesse de " + this.name + " diminue...");Thread.sleep(2000)}
                }
                else {
                    Fenetre.msgbox.print_msg(this.name + " a raté l'attaque...");Thread.sleep(2000)
                }
            }
            else{
                Fenetre.msgbox.print_msg(this.name + " est paralysé, il n'a pas pu attaquer");Thread.sleep(2000)
            }
        }
        else{
            Fenetre.msgbox.print_msg(this.name + " est gelé, il ne peut pas attaquer");Thread.sleep(2000)
        }
        if (!defenser.alive) {add_xp(defenser.xp_given)}
    }
    def receive_attaque(a: Attaque, atker : Pokemon):Unit = {
        var dmg_taken = (((0.4 * atker.lvl.toFloat + 2.0) * a.dmg.toFloat * atker.atk.toFloat / (50.0 * defense.toFloat)) * (a.atype).affinites(ptype)).toInt
        if ((a.atype).affinites(ptype) == 0.0) {Fenetre.msgbox.print_msg("Cette attaque n'est pas efficace...");Thread.sleep(2000)}
        if ((a.atype).affinites(ptype) == 0.5) {Fenetre.msgbox.print_msg("Cette attaque n'est pas très efficace...");Thread.sleep(2000)}
        if ((a.atype).affinites(ptype) == 2.0) {Fenetre.msgbox.print_msg("Cette attaque est super efficace!");Thread.sleep(2000)}
        Fenetre.msgbox.print_msg(this.name + " perd " + dmg_taken.toString() + " PV !");Thread.sleep(2000)
        hp = Func.max(hp - dmg_taken,0)
        val r = scala.util.Random
        if (a.debuff_atk > 0) {atk_mult = Func.max(-a.debuff_atk + atk_mult,-6)
                              Fenetre.msgbox.print_msg("L'attaque de " + this.name + " diminue...");Thread.sleep(2000)}
        if (a.debuff_defense > 0) {defense_mult = Func.max(-a.debuff_defense + defense_mult,-6)
                                  Fenetre.msgbox.print_msg("La défense de " + this.name + " diminue...");Thread.sleep(2000)}
        if (a.debuff_speed > 0) {speed_mult = Func.max(-a.debuff_speed + speed_mult,-6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " diminue...");Thread.sleep(2000)}
        if (a.debuff_atk < 0) {atk_mult = Func.min(-a.debuff_atk + atk_mult,6)
                                Fenetre.msgbox.print_msg("L'attaque de " + this.name + " augmente !");Thread.sleep(2000)}
        if (a.debuff_defense < 0) {defense_mult = Func.min(-a.debuff_defense + defense_mult,6)
                                  Fenetre.msgbox.print_msg("La défense de " + this.name + " augmente !");Thread.sleep(2000)}
        if (a.debuff_speed < 0) {speed_mult = Func.min(-a.debuff_speed + speed_mult,6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " augmente !");Thread.sleep(2000)}
        var (astate,pstate) = a.pstate 
        if (pstate > 0) {
            if  (r.nextDouble < pstate) {
                Fenetre.msgbox.print_msg(this.name + "vas subir l'effet " + astate.name);Thread.sleep(2000)
                if (astate == state || state == None_state) {
                    state = astate 
                    remaining_time = astate.duration
                }
                else {
                    Fenetre.msgbox.print_msg("Mais " + this.name + " a déjà un effet !");Thread.sleep(2000)
                }
            }
        }
        if (this.maitre.is_main) {
            Fenetre.bataille.print_pok_perso(this)
        }
        else {
            Fenetre.bataille.print_pok_op(this)
        }
        if (hp == 0) {Fenetre.msgbox.print_msg(this.name + " est KO !");Thread.sleep(2000); alive = false; maitre.nb_alive -= 1}
    }
}

object Empty_Pokemon extends Pokemon("") {}

class Alabri(pname : String) extends Pokemon(pname) {
    base_max_hp = 50
    hp_per_lvl = 2.0
    alive = true
    image = "pokemons/alabri.png"
    hp = max_hp
    lvl = 5
    base_atk = 50
    base_defense = 56
    base_speed = 47
    atk_per_lvl = 1.8
    defense_per_lvl = 2.2
    speed_per_lvl = 1.9
    ptype = Psy
    attaques = Array(Bouclier,Hate,Psyko,Pistolet_a_O)
}

class Alacool(pname : String) extends Pokemon(pname) {
    base_max_hp = 40
    hp_per_lvl = 1.9
    alive = true
    image = "pokemons/alacool.png"
    hp = max_hp
    lvl = 5
    base_atk = 47
    base_defense = 45
    base_speed = 56
    atk_per_lvl = 2.1
    defense_per_lvl = 1.7
    speed_per_lvl = 2.2
    ptype = Psy
    attaques = Array(Tornade,Hate,Psyko,Bec_vrille)
}

class Dracarpe(pname : String) extends Pokemon(pname) {
    base_max_hp = 44
    hp_per_lvl = 1.85
    alive = true
    image = "pokemons/dracarpe.png"
    hp = max_hp
    lvl = 5
    base_atk = 55
    base_defense = 40
    base_speed = 40
    atk_per_lvl = 2.1
    defense_per_lvl = 1.7
    speed_per_lvl = 1.6
    ptype = Feu
    attaques = Array(Déflagration,Lance_flammes,Pistolet_a_O,Trempette)
}

class Galopan(pname : String) extends Pokemon(pname) {
    base_max_hp = 44
    hp_per_lvl = 1.9
    alive = true
    image = "pokemons/galopan.png"
    hp = max_hp
    lvl = 5
    base_atk = 55
    base_defense = 50
    base_speed = 40
    atk_per_lvl = 1.9
    defense_per_lvl = 1.9
    speed_per_lvl = 1.5
    ptype = Feu
    attaques = Array(Feu_follet,Flammèche,Hate,Fouet_lianes)
}

class Kokicarpe(pname : String) extends Pokemon(pname) {
    base_max_hp = 40
    hp_per_lvl = 1.9
    alive = true
    image = "pokemons/kokicarpe.png"
    lvl = 5
    hp = max_hp
    base_atk = 40
    base_defense = 55
    base_speed = 40
    atk_per_lvl = 1.6
    defense_per_lvl = 2.2
    speed_per_lvl = 1.4
    ptype = Eau
    attaques = Array(Pistolet_a_O,Surf,Repli,Trempette)
}

class Mcool(pname : String) extends Pokemon(pname) {
    base_max_hp = 45
    hp_per_lvl = 2.0
    alive = true
    image = "pokemons/m.cool.png"
    lvl = 5
    hp = max_hp
    base_atk = 40
    base_defense = 50
    base_speed = 45
    atk_per_lvl = 1.6
    defense_per_lvl = 1.8
    speed_per_lvl = 1.8
    ptype = Psy
    attaques = Array(Tornade,Psyko,Bec_vrille,Hate)
}

class Mherbe(pname : String) extends Pokemon(pname) {
    base_max_hp = 40
    hp_per_lvl = 1.9
    alive = true
    image = "pokemons/m.herbe.png"
    lvl = 5
    hp = max_hp
    base_atk = 46
    base_defense = 47
    base_speed = 44
    atk_per_lvl = 1.7
    defense_per_lvl = 1.7
    speed_per_lvl = 1.7
    ptype = Psy
    attaques = Array(Fouet_lianes,Hate,Yoga,Psyko)
}

class Magilangue(pname : String) extends Pokemon(pname) {
    base_max_hp = 56
    hp_per_lvl = 2.3
    alive = true
    image = "pokemons/magilangue.png"
    lvl = 5
    hp = max_hp
    base_atk = 43
    base_defense = 51
    base_speed = 40
    atk_per_lvl = 1.8
    defense_per_lvl = 2.2
    speed_per_lvl = 1.4
    ptype = Psy
    attaques = Array(Bouclier,Pistolet_a_O,Psyko,Trempette)
}

class Poissocarpe(pname : String) extends Pokemon(pname) {
    base_max_hp = 44
    hp_per_lvl = 1.8
    alive = true
    image = "pokemons/poissocarpe.png"
    lvl = 5
    hp = max_hp
    base_atk = 45
    base_defense = 47
    base_speed = 43
    atk_per_lvl = 1.8
    defense_per_lvl = 1.8
    speed_per_lvl = 1.7
    ptype = Eau
    attaques = Array(Surf,Pistolet_a_O,Repli,Trempette)
}

class Poryodin(pname : String) extends Pokemon(pname) {
    base_max_hp = 60
    hp_per_lvl = 1.9
    alive = true
    image = "pokemons/poryodin.png"
    lvl = 5
    hp = max_hp
    base_atk = 51
    base_defense = 50
    base_speed = 50
    atk_per_lvl = 1.9
    defense_per_lvl = 1.9
    speed_per_lvl = 1.9
    ptype = Psy
    attaques = Array(Tornade,Blizzard,Psyko,Hate)
}

class Rhinocarpe(pname : String) extends Pokemon(pname) {
    base_max_hp = 45
    hp_per_lvl = 1.8
    alive = true
    image = "pokemons/rhinocarpe.png"
    lvl = 5
    hp = max_hp
    base_atk = 55
    base_defense = 45
    base_speed = 45
    atk_per_lvl = 2.1
    defense_per_lvl = 1.7
    speed_per_lvl = 1.7
    ptype = Roche
    attaques = Array(Jet_pierres,Pistolet_a_O,Repli,Trempette)
}
class Salatard(pname : String) extends Pokemon(pname) {
    base_max_hp = 45
    hp_per_lvl = 1.8
    alive = true
    image = "pokemons/salatard.png"
    lvl = 5
    hp = max_hp
    base_atk = 50
    base_defense = 47
    base_speed = 47
    atk_per_lvl = 1.9
    defense_per_lvl = 1.8
    speed_per_lvl = 1.8
    ptype = Feu
    attaques = Array(Flammèche,Pistolet_a_O,Feu_follet,Bulles_d_O)
}

class Starstar(pname : String) extends Pokemon(pname) {
   base_max_hp = 50
    alive = true
    image = "pokemons/starstar.png"
    hp = 30
    lvl = 5
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Eau
    attaques = Array(Trempette,Trempette,Trempette,Trempette)
    state = None_state
}

class Tentapan(pname : String) extends Pokemon(pname) {
   base_max_hp = 50
    alive = true
    image = "pokemons/tentapan.png"
    hp = 30
    lvl = 5
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Eau
    attaques = Array(Trempette,Trempette,Trempette,Trempette)
    state = None_state
}

class Torgogo(pname : String) extends Pokemon(pname) {
   base_max_hp = 50
    alive = true
    image = "pokemons/torgogo.png"
    hp = 30
    lvl = 5
    base_atk = 500
    base_defense = 50
    base_speed = 200
    ptype = Poison
    attaques = Array(Trempette,Trempette,Trempette,Trempette)
    state = None_state
}