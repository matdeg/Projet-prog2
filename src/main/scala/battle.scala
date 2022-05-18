class Battle(other : Character) {
    
    Fenetre.bataille.you = Player
    Fenetre.bataille.op = other



    // nombres de tours
    var loop : Int = 0

    // fréquence des changements de pokémons du joueur jusqu'à : tour N-2, tour N-1, maintenant
    var f1_changement_joueur : Double = 0
    var f2_changement_joueur : Double = 0
    var f3_changement_joueur : Double = 0

    var finished : Boolean = true

    val r = scala.util.Random;

    // joueur qui joue en premier/second
    var fst : Character = Empty_character
    var snd : Character = Empty_character

    // le personnage p perd, met fin au combat
    def lose(p : Character):Unit = {finished = true
                                    Player.is_fishing = false
                                    p match {
                                        case Player => Fenetre.msgbox.print_msg("Mince, vous avez perdu")
                                        case Empty_character => Fenetre.msgbox.print_msg("Vous êtes vraiment un trouillard")
                                        case _ =>  if (Player.opp.name != "Nature") {Fenetre.msgbox.print_msg("Bravo, vous avez battu " + Player.opp.name + " !")}
                                    }
                                    Thread.sleep(1000)
                                    Fenetre.msgbox.print_msg("")
                                    Player.opp = Empty_character
                                    Fenetre.afficher_map()
                                    Player.in_battle = false
                                    var x = p.end_battle.pop
                                    x(0)
                                }

    // actualise l'affichage des données du combat
    def reload_icon() = {
        Fenetre.bataille.print_pok_perso(Player.pokemons(Player.ip))
        Fenetre.bataille.print_pok_op(other.pokemons(other.ip))
    }


    //actualise le pokémon adverse, change si le sien est K.O
    def change_other() = {
        if (!other.pokemons(other.ip).alive){
            if (other.nb_alive == 0) {lose(other)}
            else {
                Fenetre.msgbox.print_msg("Il change de Pokemon... Il lui en reste " + other.nb_alive.toString)
                Thread.sleep(1500)
                other.ip = other.ia.normal_change
                Fenetre.msgbox.print_msg("Il envoie " + other.pokemons(other.ip).name)
                Thread.sleep(1500)
            }
        }
    }
    
    // acualise le pokémon du joueur, il choisit quel pokémon envoyer
    def change:Unit = {
        if (!Player.pokemons(Player.ip).alive) {
            if (Player.nb_alive == 0) {lose(Player)}
            else {
                Fenetre.msgbox.print_msg("Vous changez de Pokemon... \n Il vous en reste " + Player.nb_alive.toString)
                Thread.sleep(1500)
                Func.menu_pokemon_force()
                Fenetre.msgbox.print_msg("Vous envoyez " + Player.pokemons(Player.ip).name)
            }
        }
        change_other()
        Pokedex.encountered(other.pokemons(other.ip).id) = true
        reload_icon()
    }

    // Démarre la battle
    def start : Unit = {

        // DEBUT -- initialisation des variables 
        Fenetre.afficher_bataille
        finished = false
        Player.ip = 0; other.ip = 0; Player.nb_alive = 0; other.nb_alive = 0
        for (i <- 0 to 5) {if (Player.pokemons(i).alive) {Player.nb_alive += 1};
                           if (other.pokemons(i).alive) {other.nb_alive += 1}}
        change
        // FIN -- initialisation des variables

        // gestion menu - boutons
        while (!finished) {

            var (choix_menu,second_choix) = Func.menu_principal()
            var choix_menu_op = 0
            var second_choix_op = 0

            // on gère la spécialité de goubault
            if (other.is_goubault) {
                other.stack_pause match {
                    case 0 => other.stack_pause += 1
                    case n:Int if (r.nextDouble() < (1.0 - 0.7/n.toDouble)) => 
                        if (n == 1) {Fenetre.msgbox.print_msg("Mini Pause !");}
                        else {Fenetre.msgbox.print_msg("Mince j'ai oublié la mini-pause, on doit la rattraper");}
                        
                        Thread.sleep(1500); other.stack_pause = 0; choix_menu = 0; second_choix = 4
                    case n:Int => other.stack_pause += 1
                }
            } 

            // en cas de fuite, on termine le tour instantanément
            if (choix_menu == 3) {
                Fenetre.msgbox.print_msg(Player.name + " s'enfuit !")
                Thread.sleep(1200)
                lose(Empty_character)
            } 

            // On gère le choix de l'adversaire via une IA, qui dépend de l'adversaire
            else {
            other match {
                case nat : Nature => second_choix_op = r.nextInt(4)
                case lac : Lac_opp => second_choix_op = r.nextInt(4)
                case _ => var (a,b) = other.ia.best_move
                          choix_menu_op = a
                          second_choix_op = b
            }
            this.turn(choix_menu,second_choix,choix_menu_op,second_choix_op)
            }
        }
    }


    // détermine qui joue en premier selon les choix des deux joueurs. et lance le tour
    def turn(choix_menu : Int,second_choix : Int,choix_menu_op : Int, second_choix_op : Int):Unit = {
        var a = 0; var b = 0;var c = 0; var d = 0
        fst = Player; snd = other; a = choix_menu; b = second_choix; c = choix_menu_op; d = second_choix_op
        if (!(choix_menu_op == 0) || (choix_menu == 0 && Player.pokemons(Player.ip).speed < other.pokemons(other.ip).speed)) {
            fst = other; snd = Player; a = choix_menu_op; b = second_choix_op; c = choix_menu; d = second_choix
        }
        play(fst,snd,a,b)
        play(snd,fst,c,d)
        Player.pokemons(Player.ip).reload_state
        other.pokemons(other.ip).reload_state
        change
        reload_icon
        loop += 1
    }



    // effectue les actions choisies par p (tour de p contre q)
    def play(p : Character,q : Character,choix_menu : Int,second_choix : Int) = {

        // actualisation des fréquences de changement du joueur
        if (p == Player) {
            f1_changement_joueur = f2_changement_joueur
            f2_changement_joueur = f3_changement_joueur
            f3_changement_joueur = (f3_changement_joueur/2.0)
            if (choix_menu == 1) {
                f3_changement_joueur += 0.5
            }
        }

        choix_menu match {
            case 0 if (p.pokemons(p.ip).alive) => {
                    p.pokemons(p.ip).cast_attaque(second_choix,q.pokemons(q.ip))
                }
            case 1 => {
                    p.ip = second_choix
                    Fenetre.msgbox.print_msg(p.name + " envoie " + p.pokemons(p.ip).name + " !");Thread.sleep(1500)
                }
            case 2 => {
                    var choix_item = second_choix / 6
                    var choix_pok = second_choix - (choix_item * 6)
                    p.use_item(Func.id_items(p.current_items_id(choix_item)),p.pokemons(choix_pok))
                }
            case _ => {}
        }
        reload_icon()        
    }
}

object Empty_Battle extends Battle(Empty_character) {}