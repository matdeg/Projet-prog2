class Battle(p1 : Character,p2 : Character) {
    
    var you : Character = p1
    Fenetre.bataille.you = you
    var other : Character = p2
    Fenetre.bataille.op = other
    var loop : Int = 0
    var f1_changement_joueur : Double = 0
    var f2_changement_joueur : Double = 0
    var f3_changement_joueur : Double = 0
    var finished : Boolean = true
    val r = scala.util.Random;

    // joueur qui joue en premier/second
    var fst : Character = Empty_character
    var snd : Character = Empty_character
    def lose(p : Character):Unit = {finished = true
                                    if (p == you) {Fenetre.msgbox.print_msg("Mince, vous avez perdu")}
                                    else {Fenetre.msgbox.print_msg("Bravo, vous avez gagné !")}
                                    Player.opp = Empty_character
                                    Fenetre.afficher_map ()
                                    Player.in_battle = false
                                    Test_mathis.choix = Fenetre.bas_fenetre.print_menu_base ()}

    // permet de fuir le combat
    def fuite : Unit = {
        lose(you)
    }
    
    // actualise les pokémons sur le terrain si l'un est K.O , l'adversaire lance son premier pokémon vivant 
    def change:Unit = {
        if (!you.pokemons(you.ip).alive){
            if (you.nb_alive == 0) {lose(you)}
            else {
                you.ip = -1
                Fenetre.msgbox.print_msg("Vous changez de Pokemon... \n Il vous en reste " + you.nb_alive.toString)
                Thread.sleep(1500)
                while ((you.ip == -1) || !you.pokemons(you.ip).alive) {
                    if (you.ip == -1) {Fenetre.msgbox.print_msg("Veuillez choisir un pokémon :")}
                    else {Fenetre.msgbox.print_msg("Ce pokémon n'est pas apte à retourner au combat, veuillez choisir un pokémon :")}
                    you.ip = Fenetre.bas_fenetre.print_menu_pokemon(you) 
                }
                Fenetre.msgbox.print_msg("Vous envoyez " + you.pokemons(you.ip).name)
                Fenetre.bataille.print_pok_perso(you.pokemons(you.ip))
            }
        }
        else{
            Fenetre.bataille.print_pok_perso(you.pokemons(you.ip))
        }
        if (!other.pokemons(other.ip).alive){
            if (other.nb_alive == 0) {lose(other)}
            else {
                Fenetre.msgbox.print_msg("Il change de Pokemon... Il lui en reste " + other.nb_alive.toString)
                Thread.sleep(1500)
                while (!other.pokemons(other.ip).alive) {other.ip += 1}
                Fenetre.msgbox.print_msg("Il envoie " + other.pokemons(other.ip).name)
                Thread.sleep(1500)
                Fenetre.bataille.print_pok_op(other.pokemons(other.ip))
            }
        }
        else {
            Fenetre.bataille.print_pok_op(other.pokemons(other.ip))
        }
    }

    def menu_principal() : (Int,Int) = {
        Fenetre.msgbox.print_msg("Que voulez vous faire ?")
        var choix_menu = -2
        choix_menu = Fenetre.bas_fenetre.print_menu_base
        choix_menu match {
            case 0 => menu_attaque()
            case 1 => menu_pokemon()
            case 2 => menu_sac()
            case 3 => (3, 0)
        }
    }

    def menu_attaque() : (Int,Int) = {
        Fenetre.msgbox.print_msg("Choisissez une attaque :")
        var choix_attaque = -2
        choix_attaque = Fenetre.bas_fenetre.print_menu_attaque(you.pokemons(you.ip))
        choix_attaque match {
            case -1 => menu_principal()
            case _ => if (you.pokemons(you.ip).pp_list(choix_attaque) == 0) {
                        Fenetre.msgbox.print_msg("Vous ne pouvez plus lancer cette attaque"); Thread.sleep(1500)
                        menu_attaque()
                      }
                      else {
                        (0,choix_attaque)
                      } 
        }
    }

    def menu_pokemon() : (Int,Int) = {
        Fenetre.msgbox.print_msg("Choisissez un pokémon :")
        var choix_pokemon = -2
        choix_pokemon = Fenetre.bas_fenetre.print_menu_pokemon(you)
        choix_pokemon match {
            case -1 => menu_principal
            case _ => if (!you.pokemons(you.ip).alive) {
                        Fenetre.msgbox.print_msg("Ce pokémon n'est pas apte à retourner au combat"); Thread.sleep(1500)
                        menu_pokemon()
                      }
                      else {
                          if (you.ip == choix_pokemon) {
                            Fenetre.msgbox.print_msg("Ce pokémon est déjà sur le terrain"); Thread.sleep(1500)
                            menu_pokemon()
                          }
                        (1,choix_pokemon)
                      } 
        }
    }

    def menu_sac() : (Int, Int) = {
        Fenetre.msgbox.print_msg("Choisissez un objet :")
        var choix_objet = -2
        choix_objet = Fenetre.bas_fenetre.print_menu_objet(you)
        choix_objet match {
            case -1 => menu_principal
            case 4 => {you.next_page; menu_sac}
            case 5 => {you.back_page; menu_sac}
            case _ => {(2,choix_objet)}
        }
    }

    // Démarre la battle
    def start : Unit = {
        println("start")
        Fenetre.afficher_bataille
        finished = false
        you.ip = 0; other.ip = 0; you.nb_alive = 0; other.nb_alive = 0
        for (i <- 0 to 5) {if (you.pokemons(i).alive) {you.nb_alive += 1};
                           if (other.pokemons(i).alive) {other.nb_alive += 1}}
        change

        // gestion menu - boutons
        while (!finished) {
            var (choix_menu,second_choix) = menu_principal()
            
            var choix_menu_op = 0
            var second_choix_op = 0

            if (choix_menu == 3) {
                lose(you)
            } 
            else {
            other match {
                case nat : Nature => second_choix_op = r.nextInt(4)
                case _ =>  second_choix_op = IA.choix_attaque(other,f1_changement_joueur,loop)
            }
            
            for (i <- 0 to 3) {
                println(IA.fonction_gain_attaque_bot(other,i,f1_changement_joueur,loop))
            }
            println("")
            this.turn(choix_menu,second_choix,choix_menu_op,second_choix_op)
            }
        }
    }
    // détermine qui joue en premier selon les choix des deux joueurs. et lance le tour
    def turn(choix_menu : Int,second_choix : Int,choix_menu_op : Int, second_choix_op : Int):Unit = {
        var a = 0; var b = 0;var c = 0; var d = 0
        fst = you; snd = other; a = choix_menu; b = second_choix; c = choix_menu_op; d = second_choix_op
        if (!(choix_menu_op == 0) || (choix_menu == 0 && you.pokemons(you.ip).speed < other.pokemons(other.ip).speed)) {
            fst = other; snd = you; a = choix_menu_op; b = second_choix_op; c = choix_menu; d = second_choix
        }
        play(fst,snd,a,b)
        play(snd,fst,c,d)
        change
        you.pokemons(you.ip).reload_state
        other.pokemons(other.ip).reload_state
        Fenetre.bataille.print_pok_perso(you.pokemons(you.ip))
        Fenetre.bataille.print_pok_op(other.pokemons(other.ip))
        loop += 1
    }

    // effectue les actions choisies par p (tour de p contre q)
    def play(p : Character,q : Character,choix_menu : Int,second_choix : Int) = {
        if (choix_menu == 0 && p.pokemons(p.ip).alive) {
            p.pokemons(p.ip).cast_attaque(second_choix,q.pokemons(q.ip))
            f1_changement_joueur = f2_changement_joueur
            f2_changement_joueur = f3_changement_joueur
            f3_changement_joueur = (f3_changement_joueur/2.0)
        }
        if (choix_menu == 1) {
            p.ip = second_choix
            Fenetre.msgbox.print_msg(p.name + " envoie " + p.pokemons(p.ip).name + " !")
            if (p == you) {
                Fenetre.bataille.print_pok_perso(p.pokemons(p.ip))
            }
            else {
                
                Fenetre.bataille.print_pok_op(p.pokemons(p.ip))
            }
            f1_changement_joueur = f2_changement_joueur
            f2_changement_joueur = f3_changement_joueur
            f3_changement_joueur = (f3_changement_joueur/2.0) + 0.5
            Thread.sleep(1500)
        }
        if (choix_menu == 2) {
            p.use_item(Func.id_items(p.current_items_id(second_choix)),p.pokemons(p.ip))
            if (p == you) {
                Fenetre.bataille.print_pok_perso(p.pokemons(p.ip))
            }
            else {
                
                Fenetre.bataille.print_pok_op(p.pokemons(p.ip))
            }
            f1_changement_joueur = f2_changement_joueur
            f2_changement_joueur = f3_changement_joueur
            f3_changement_joueur = (f3_changement_joueur/2.0)
        }
        
        
    }
}

object Empty_Battle extends Battle(Empty_character,Empty_character) {}