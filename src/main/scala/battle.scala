class Battle(p1 : Character,p2 : Character) {
    var you : Character = p1
    Fenetre.bataille.you = you
    var other : Character = p2
    Fenetre.bataille.op = other
    var loop : Int = 1
    var finished : Boolean = false

    // joueur qui joue en premier/second
    var fst : Character = Empty_character
    var snd : Character = Empty_character
    def lose(p : Character):Unit = {finished = true
                                    if (p == you) {Fenetre.msgbox.print_msg("Mince, vous avez perdu")}
                                    else {Fenetre.msgbox.print_msg("Bravo, vous avez gagné !")}}

    // actualise les pokémons sur le terrain si l'un est K.O , l'adversaire lance son premier pokémon vivant 
    def change:Unit = {
        if (!you.pokemons(you.ip).alive){
            if (you.nb_alive == 0) {lose(you)}
            else {
                you.ip = -1
                Fenetre.msgbox.print_msg("Vous changez de Pokemon... \n Il vous en reste " + you.nb_alive.toString)
                Thread.sleep(2000)
                while ((you.ip == -1) || !you.pokemons(you.ip).alive) {
                    if (you.ip == -1) {Fenetre.msgbox.print_msg("Veuillez choisir un pokémon :")}
                    else {Fenetre.msgbox.print_msg("Ce pokémon n'est pas apte à retourner au combat, veuillez choisir un pokémon :")}
                    you.ip = Fenetre.print_menu_pokemon(you) 
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
                Thread.sleep(2000)
                while (!other.pokemons(other.ip).alive) {other.ip += 1}
                Fenetre.msgbox.print_msg("Il envoie " + other.pokemons(other.ip).name)
                Thread.sleep(2000)
                Fenetre.bataille.print_pok_op(other.pokemons(other.ip))
            }
        }
        else {
            Fenetre.bataille.print_pok_op(other.pokemons(other.ip))
        }
    }

    // Démarre la battle
    def start:Unit = {
        finished = false
        you.ip = 0; other.ip = 0; you.nb_alive = 0; other.nb_alive = 0
        for (i <- 0 to 5) {if (you.pokemons(i).alive) {you.nb_alive += 1};
                           if (other.pokemons(i).alive) {other.nb_alive += 1}}
        change

        // gestion menu - boutons
        val r = scala.util.Random; 
            var choix_menu = 0; 
            while (!this.finished) {
                var second_choix = -1
                while (second_choix == -1) {
                    Fenetre.msgbox.print_msg("Que voulez vous faire ?")
                    choix_menu = Fenetre.print_menu_base
                    if (choix_menu == 0) {
                        Fenetre.msgbox.print_msg("Choisissez une attaque :")
                        second_choix = Fenetre.print_menu_attaque(you.pokemons(you.ip))
                        while ((second_choix != -1) && (you.pokemons(you.ip).pp_list(second_choix) == 0)) {
                            if (you.pokemons(you.ip).pp_list(second_choix) == 0) {
                                Fenetre.msgbox.print_msg(you.pokemons(you.ip).name + " ne peut plus lancer cette attaque")
                                second_choix = Fenetre.print_menu_attaque(you.pokemons(you.ip))
                            }
                        }
                    }
                    if (choix_menu == 1) {
                        Fenetre.msgbox.print_msg("Choisissez un pokémon :")
                        second_choix = Fenetre.print_menu_pokemon(you)
                        while (((second_choix != -1) && (!you.pokemons(second_choix).alive)) || (second_choix == you.ip) ) {
                            if (second_choix == you.ip) {
                                Fenetre.msgbox.print_msg("Ce pokémon est dékà sur le terrain, choisissez un autre pokémon :")
                            }
                            else {
                                Fenetre.msgbox.print_msg("Celui-ci n'est pas apte à retourner au combat, choisissez un autre pokémon :")
                            }
                            second_choix = Fenetre.print_menu_pokemon(you)
                        }
                    }
                    if (choix_menu >= 2 ) {
                        Fenetre.msgbox.print_msg("Ceci n'a pas été codé")
                        Thread.sleep(2000)
                    }
                }
                var choix_menu_op = 0
                var second_choix_op = r.nextInt(4)
                this.turn(choix_menu,second_choix,choix_menu_op,second_choix_op)
            }
    }

    // détermine qui joue en premier selon les choix des deux joueurs. et lance le tour
    def turn(choix_menu : Int,second_choix : Int,choix_menu_op : Int, second_choix_op : Int):Unit = {
        var a = 0; var b = 0;var c = 0; var d = 0
        fst = you; snd = other; a = choix_menu; b = second_choix; c = choix_menu_op; d = second_choix_op
        if (!(choix_menu_op == 0) || you.pokemons(you.ip).speed < other.pokemons(other.ip).speed) {
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
        }
        if (choix_menu == 1) {
            p.ip = second_choix
            if (p == you) {
                Fenetre.msgbox.print_msg("Vous envoyez " + p.pokemons(p.ip).name + " !")
                Fenetre.bataille.print_pok_perso(p.pokemons(p.ip))
            }
            else {
                Fenetre.msgbox.print_msg("Il envoie " + p.pokemons(p.ip).name + " !")
                Fenetre.bataille.print_pok_op(p.pokemons(p.ip))
            }
            Thread.sleep(2000)
        }
    }
}

object Empty_Battle extends Battle(Empty_character,Empty_character) {}
