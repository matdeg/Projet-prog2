import java.awt.Taskbar.State

abstract class Pokemon(pname : String) {
    val r = scala.util.Random;
    var name = pname
    var image = ""
    var description = ""

    // stats en lien avec le niveau du pokémon
    var lvl = 5
    var xp = 0
    var base_xp_given = 70
    var xp_given = lvl * base_xp_given / 7
    def next_xp = {3 * lvl * lvl}

    // stats de base de combat propre au pokémon
    var base_max_hp = 0
    var base_atk = 0
    var base_defense = 0
    var base_speed = 0

    // PV du pokémon, non fixe 
    var hp = 0
    def alive = {!(hp == 0)}
    
    // type du pokémon
    var ptype : Ttype = Normal

    // attaques du pokémons, nombre de lancer possibles
    var attaques = new Array[Attaque](4)
    var pp_list = new Array[Int](4)

    // objet tenu par le pokémon
    var held_item : Item = Empty_item

    // multiplicateur de statistiques
    var atk_mult = 0
    var defense_mult = 0 
    var speed_mult = 0

    // dresseur du pokémon
    var maitre : Character = Empty_character 

    // état du pokémon (brûlure etc...)
    var state : States = None_state
    var remaining_time : Int = 0
    
    // iv = individual value -> different stats between pokemons of the same specy
    val iv_hp : Int = r.nextInt(30) 
    val iv_atk : Int = r.nextInt(30) 
    val iv_defense : Int = r.nextInt(30) 
    val iv_speed : Int = r.nextInt(30) 

    // calcul des statistiques
    def max_hp = {((((base_max_hp + iv_hp) * 2 + 10).toDouble * lvl.toDouble) / 100.0).toInt + lvl + 10}
    def atk = {((((((base_atk + iv_atk) * 2 + 10).toDouble * lvl.toDouble) / 100.0) + lvl.toDouble + 10.0) * Func.mult_a(atk_mult + state.matk)).toInt} 
    def defense = {((((((base_defense + iv_defense) * 2 + 10).toDouble * lvl.toDouble) / 100.0) + lvl.toDouble + 10.0) * Func.mult_a(defense_mult + state.mdef)).toInt} 
    def speed = {((((((base_speed + iv_speed) * 2 + 10).toDouble * lvl.toDouble) / 100.0) + lvl.toDouble + 10.0) * Func.mult_a(speed_mult + state.mspeed)).toInt} 




    // reload each information linked to state
    def reload_state = {
        if (remaining_time > 0){
            remaining_time = Func.max(remaining_time - 1,0)
            if (state.degat) {
                var dmg_taken = (max_hp.toDouble * (1.0/16.0)).toInt
                hp = Func.max(0,hp - dmg_taken)
                state match {
                    case Burned => Fenetre.msgbox.print_msg(this.name + " est brulé, il perd " + dmg_taken.toString + " PV");Thread.sleep(1500)
                    case Empoisonnement => Fenetre.msgbox.print_msg(this.name + " est empoisonné, il perd " + dmg_taken.toString + " PV");Thread.sleep(1500)
                } 
            }
        } 
        if (remaining_time == 0) {
            state = None_state
        }
    }



    // utilise l'item i sur soi-même, ce morceau de code n'est malheureusement pas factoriable, bien qu'il en ait l'air SE
    def use_item(i : Item) = {
        var hp_regened = Func.min(i.regen, max_hp - hp)
        hp = Func.min(hp + i.regen,max_hp)
        if (i.regen > 0) {Fenetre.msgbox.print_msg(this.name + " a récupéré " + hp_regened.toString + " PV !");Thread.sleep(1500)}
        if (hp == 0 && i.revive > 0) {hp = (max_hp.toDouble * i.revive).toInt;Fenetre.msgbox.print_msg(this.name + " peut retourner au combat !");
                                      Thread.sleep(1500)}
        if (i.buff_atk > 0) {atk_mult = Func.min(i.buff_atk + atk_mult,6)
                            Fenetre.msgbox.print_msg("L'attaque de " + this.name + " augmente !");Thread.sleep(1500)}
        if (i.buff_defense > 0) {defense_mult = Func.min(i.buff_defense + defense_mult,6)
                                Fenetre.msgbox.print_msg("La défense de " + this.name + " augmente !");Thread.sleep(1500)}
        if (i.buff_speed > 0) {speed_mult = Func.min(i.buff_speed + speed_mult,6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " augmente !");Thread.sleep(1500)}
        if (i.buff_atk < 0) {atk_mult = Func.max(i.buff_atk + atk_mult,-6)
                            Fenetre.msgbox.print_msg("L'attaque de " + this.name + " diminue...");Thread.sleep(1500)}
        if (i.buff_defense < 0) {defense_mult = Func.max(i.buff_defense + defense_mult,-6)
                                Fenetre.msgbox.print_msg("La défense de " + this.name + " diminue...");Thread.sleep(1500)}
        if (i.buff_speed < 0) {speed_mult = Func.max(i.buff_speed + speed_mult,-6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " diminue...");Thread.sleep(1500)}
        i.state_heal.foreach(x => if (state == x) {state = None_state;Fenetre.msgbox.print_msg(this.name + " n'a plus l'effet " + x.name);Thread.sleep(1500)})
    }



    // ajoute un montant d'xp au pokémon, il monte de niveau si nécessaire
    def add_xp(exp : Int) = {
        Fenetre.msgbox.print_msg(this.name + " a gagné " + exp.toString + " points d'expérience !");Thread.sleep(1500)
        var ex_max_hp = max_hp
        xp += exp
        if (xp >= next_xp){
            while (xp >= next_xp) {
                xp -= next_xp
                lvl += 1
            }
            Fenetre.msgbox.print_msg(this.name + " est monté au niveau " + lvl);Thread.sleep(1500)
        }
        hp += max_hp - ex_max_hp
    }



    // applique les effets de l'attaque a sur le pokémon lanceur
    def buffs_attack(a : Attaque) = {
        var (ba,bap) = a.buff_atk
        var (bd,bdp) = a.buff_defense
        var (bs,bsp) = a.buff_speed
        if (ba > 0 && r.nextDouble < bap) {atk_mult = Func.min(ba + atk_mult,6)
                    Fenetre.msgbox.print_msg("L'attaque de " + this.name + " augmente !");Thread.sleep(1500)}
        if (bd > 0 && r.nextDouble < bdp) {defense_mult = Func.min(bd + defense_mult,6)
                    Fenetre.msgbox.print_msg("La défense de " + this.name + " augmente !");Thread.sleep(1500)}
        if (bs > 0 && r.nextDouble < bsp) {speed_mult = Func.min(bs + speed_mult,6)
                    Fenetre.msgbox.print_msg("La vitesse de " + this.name + " augmente !");Thread.sleep(1500)}
        if (ba < 0 && r.nextDouble < bap) {atk_mult = Func.max(ba + atk_mult,-6)
                    Fenetre.msgbox.print_msg("L'attaque de " + this.name + " diminue...");Thread.sleep(1500)}
        if (bd < 0 && r.nextDouble < bdp) {defense_mult = Func.max(bd + defense_mult,-6)
                    Fenetre.msgbox.print_msg("La défense de " + this.name + " diminue...");Thread.sleep(1500)}
        if (bs < 0 && r.nextDouble < bsp) {speed_mult = Func.max(bs + speed_mult,-6)
                    Fenetre.msgbox.print_msg("La vitesse de " + this.name + " diminue...");Thread.sleep(1500)}
    }



    // tentes de lancer l'attaque i sur defenser, si l'attaque n'échoue pas, lance lands_attack et receive_attack
    def cast_attaque(i : Int,defenser : Pokemon) = {
        var a = attaques(i); 
        pp_list(i) -= 1 
        if (!state.stun) {
            Fenetre.msgbox.print_msg(this.name + " lance l'attaque " + a.name);Thread.sleep(1500)
            if (r.nextDouble > state.miss) {
                if (r.nextFloat < ((a.precision).toFloat/100)) {
                    defenser.receive_attaque(a,this)
                    buffs_attack(a)
                }
                else {
                    Fenetre.msgbox.print_msg(this.name + " a raté l'attaque...");Thread.sleep(1500)
                }
            }
            else{
                Fenetre.msgbox.print_msg(this.name + " est paralysé, il n'a pas pu attaquer");Thread.sleep(1500)
            }
        }
        else{
            state match {
                case Freeze => Fenetre.msgbox.print_msg(this.name + " est gelé, il ne peut pas attaquer");Thread.sleep(1500)
                case Sommeil => Fenetre.msgbox.print_msg(this.name + " est endormi, il ne peut pas attaquer");Thread.sleep(1500)
            }
        }
        if (!defenser.alive) {add_xp(defenser.xp_given)}
    }



    def debuffs_attack(a : Attaque) = {
        var (da,dap) = a.debuff_atk
        var (dd,ddp) = a.debuff_defense
        var (ds,dsp) = a.debuff_speed
        if (da > 0 && r.nextDouble < dap) {atk_mult = Func.max(-da + atk_mult,-6)
                              Fenetre.msgbox.print_msg("L'attaque de " + this.name + " diminue...");Thread.sleep(1500)}
        if (dd > 0 && r.nextDouble < ddp) {defense_mult = Func.max(-dd + defense_mult,-6)
                                  Fenetre.msgbox.print_msg("La défense de " + this.name + " diminue...");Thread.sleep(1500)}
        if (ds > 0 && r.nextDouble < dsp) {speed_mult = Func.max(-ds + speed_mult,-6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " diminue...");Thread.sleep(1500)}
        if (da < 0 && r.nextDouble < dap) {atk_mult = Func.min(-da + atk_mult,6)
                                Fenetre.msgbox.print_msg("L'attaque de " + this.name + " augmente !");Thread.sleep(1500)}
        if (dd < 0 && r.nextDouble < ddp) {defense_mult = Func.min(-dd + defense_mult,6)
                                  Fenetre.msgbox.print_msg("La défense de " + this.name + " augmente !");Thread.sleep(1500)}
        if (ds < 0 && r.nextDouble < dsp) {speed_mult = Func.min(-ds + speed_mult,6)
                                Fenetre.msgbox.print_msg("La vitesse de " + this.name + " augmente !");Thread.sleep(1500)}
    }



    // applique les changements d'états dûs à l'attaque a
    def state_attack(a : Attaque) = {
        var (astate,pstate) = a.pstate 
        if  (r.nextDouble < pstate) {
            astate match{
                case Sommeil => Fenetre.msgbox.print_msg(this.name + " va s'endormir !");Thread.sleep(1500)
                case Freeze => Fenetre.msgbox.print_msg(this.name + " va être gelé!");Thread.sleep(1500)
                case Burned => Fenetre.msgbox.print_msg(this.name + " s'enflamme !");Thread.sleep(1500)
                case Paralysie => Fenetre.msgbox.print_msg(this.name + " est maintenant paralysé !");Thread.sleep(1500)
                case Empoisonnement => Fenetre.msgbox.print_msg(this.name + " est empoisonné !");Thread.sleep(1500)
            }
            if (astate == state || state == None_state) {
                state = astate 
                remaining_time = astate.duration
            }
            else {
                Fenetre.msgbox.print_msg("Mais " + this.name + " a déjà un effet !");Thread.sleep(1500)
            }
        }
    }



    //applique les effets de l'attaque sur le receveur
    def receive_attaque(a: Attaque, atker : Pokemon):Unit = {

        //gestion des dégats/efficacité de l'attaque
        var dmg_taken = (((0.4 * atker.lvl.toFloat + 2.0) * a.dmg.toFloat * atker.atk.toFloat / (50.0 * defense.toFloat) + 3.0) * (a.atype).affinites(ptype)).toInt
        if (a.dmg > 0) {
            if ((a.atype).affinites(ptype) == 0.0) {Fenetre.msgbox.print_msg("Cette attaque n'est pas efficace...");Thread.sleep(1500)}
            if ((a.atype).affinites(ptype) == 0.5) {Fenetre.msgbox.print_msg("Cette attaque n'est pas très efficace...");Thread.sleep(1500)}
            if ((a.atype).affinites(ptype) == 2.0) {Fenetre.msgbox.print_msg("Cette attaque est super efficace!");Thread.sleep(1500)}
            Fenetre.msgbox.print_msg(this.name + " perd " + dmg_taken.toString() + " PV !");Thread.sleep(1500)
            hp = Func.max(hp - dmg_taken,0)
        }
        
        debuffs_attack(a)
        state_attack(a)
        maitre.current_battle.reload_icon
        if (hp == 0) {Fenetre.msgbox.print_msg(this.name + " est KO !");Thread.sleep(1500); maitre.nb_alive -= 1}
    }



    def init() = {
        hp = max_hp
    }
}

object Empty_Pokemon extends Pokemon("") {}

class Alabri(pname : String) extends Pokemon(pname) {
    base_max_hp = 50
    image = "pokemons/alabri.png"
    hp = max_hp
    lvl = 5
    base_atk = 50
    base_defense = 56
    base_speed = 47
    ptype = Psy
    attaques = Array(Bouclier,Hate,Psyko,Pistolet_a_O)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "Maintenir la situation financière de sa daronne est son principal objectif de vie."
}

class Alacool(pname : String) extends Pokemon(pname) {
    base_max_hp = 40
    image = "pokemons/alacool.png"
    hp = max_hp
    lvl = 5
    base_atk = 47
    base_defense = 45
    base_speed = 56
    ptype = Psy
    attaques = Array(Tornade,Hate,Psyko,Bec_vrille)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "Très peu stressé, il vote Philippe Poutou et Mélenchon au second tour."
}

class Dracarpe(pname : String) extends Pokemon(pname) {
    base_max_hp = 44
    image = "pokemons/dracarpe.png"
    hp = max_hp
    lvl = 5
    base_atk = 55
    base_defense = 40
    base_speed = 40
    ptype = Feu
    attaques = Array(Déflagration,Lance_flammes,Pistolet_a_O,Trempette)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "Les expériences foireuses du département de Chimie ont fait naître cette carpe cracheuse de feu"
}

class Galopan(pname : String) extends Pokemon(pname) {
    base_max_hp = 44
    image = "pokemons/galopan.png"
    hp = max_hp
    lvl = 5
    base_atk = 55
    base_defense = 50
    base_speed = 40
    ptype = Feu
    attaques = Array(Feu_follet,Flammèche,Hate,Fouet_lianes)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "Le département de biologie avait pour mission de créer la chenille la plus rapide du monde, une 1 chevau"
}

class Kokicarpe(pname : String) extends Pokemon(pname) {
    base_max_hp = 40
    image = "pokemons/kokicarpe.png"
    lvl = 5
    hp = max_hp
    base_atk = 40
    base_defense = 55
    base_speed = 40
    ptype = Eau
    attaques = Array(Pistolet_a_O,Surf,Repli,Trempette)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "Il est le résultat d'une commande de carpe blindée de l'armée à l'ENS"
}

class Mcool(pname : String) extends Pokemon(pname) {
    base_max_hp = 45
    image = "pokemons/m.cool.png"
    lvl = 5
    hp = max_hp
    base_atk = 40
    base_defense = 50
    base_speed = 45
    ptype = Psy
    attaques = Array(Tornade,Psyko,Bec_vrille,Hate)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "Papa de Alacool"
}

class Mherbe(pname : String) extends Pokemon(pname) {
    base_max_hp = 40
    image = "pokemons/m.herbe.png"
    lvl = 5
    hp = max_hp
    base_atk = 46
    base_defense = 47
    base_speed = 44
    ptype = Psy
    attaques = Array(Fouet_lianes,Hate,Yoga,Psyko)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "Papa de Alaherbe"
}

class Magilangue(pname : String) extends Pokemon(pname) {
    base_max_hp = 56
    image = "pokemons/magilangue.png"
    lvl = 5
    hp = max_hp
    base_atk = 43
    base_defense = 51
    base_speed = 40
    ptype = Psy
    attaques = Array(Bouclier,Pistolet_a_O,Psyko,Trempette)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "Pokémon dont les performances sont connues de la moitié de l'humanité"
}

class Poissocarpe(pname : String) extends Pokemon(pname) {
    base_max_hp = 44
    image = "pokemons/poissocarpe.png"
    lvl = 6
    hp = max_hp
    base_atk = 45
    base_defense = 47
    base_speed = 43
    ptype = Eau
    attaques = Array(Surf,Pistolet_a_O,Repli,Trempette)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "Il s'agit de la carpe originelle, la carpe cosmique, la grande carpe, la care verte, la réincarantion de la carpe enchantrice, qui s'est proposée comme candidat à l'élection présidentielle de l'étang Carpe"
}

class Poryodin(pname : String) extends Pokemon(pname) {
    base_max_hp = 60
    image = "pokemons/poryodin.png"
    lvl = 5
    hp = max_hp
    base_atk = 51
    base_defense = 50
    base_speed = 50
    ptype = Psy
    attaques = Array(Tornade,Blizzard,Psyko,Hate)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "Personne ne veut savoir comment ce truc est né"
}

class Rhinocarpe(pname : String) extends Pokemon(pname) {
    base_max_hp = 45
    image = "pokemons/rhinocarpe.png"
    lvl = 5
    hp = max_hp
    base_atk = 55
    base_defense = 45
    base_speed = 45
    ptype = Roche
    attaques = Array(Jet_pierres,Pistolet_a_O,Repli,Trempette)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "expérience ratée d'une fusion entre un rhinocéros et une carpe"
}
class Salatard(pname : String) extends Pokemon(pname) {
    base_max_hp = 45
    image = "pokemons/salatard.png"
    lvl = 5
    hp = max_hp
    base_atk = 50
    base_defense = 47
    base_speed = 47
    ptype = Feu
    attaques = Array(Flammèche,Pistolet_a_O,Fouet_lianes,Bulles_d_O)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    description = "gnnnnééé : le seul son qui peut sortir de sa bouche"
}

class Starstar(pname : String) extends Pokemon(pname) {
   base_max_hp = 50
    
    image = "pokemons/starstar.png"
    hp = 30
    lvl = 5
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Eau
    attaques = Array(Trempette,Trempette,Trempette,Trempette)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    state = None_state
    description = "Le saviez-vous ? Star veut dire étoile en anglais, Le saviez-vous ? Star veut dire étoile en anglais"
}

class Tentapan(pname : String) extends Pokemon(pname) {
   base_max_hp = 50
    image = "pokemons/tentapan.png"
    hp = 30
    lvl = 5
    base_atk = 50
    base_defense = 50
    base_speed = 200
    ptype = Eau
    attaques = Array(Trempette,Trempette,Trempette,Trempette)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    state = None_state
    description = ""
}

class Torgogo(pname : String) extends Pokemon(pname) {
   base_max_hp = 50
    image = "pokemons/torgogo.png"
    hp = 30
    lvl = 5
    base_atk = 500
    base_defense = 50
    base_speed = 200
    ptype = Poison
    attaques = Array(Trempette,Trempette,Trempette,Trempette)
    pp_list = Array(attaques(0).pp,attaques(1).pp,attaques(2).pp,attaques(3).pp)
    state = None_state
    description = "Vous étiez vous déjà demandé à quoi ressemblait un Smogogo rempli d'eau ?" 
}