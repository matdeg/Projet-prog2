class Battle(p1 : Character,p2 : Character) {
    var you : Character = p1
    Fenetre.bataille.you = you
    var other : Character = p2
    Fenetre.bataille.op = other
    var loop : Int = 1
    var finished : Boolean = false
    var fst : Character = Empty_character
    var snd : Character = Empty_character
    def lose(p : Character):Unit = {finished = true
                                    if (p == you) {Fenetre.msgbox.print_msg("Mince, vous avez perdu")}
                                    else {Fenetre.msgbox.print_msg("Bravo, vous avez gagné !")}}
    def change:Unit = {
        if (!you.pokemons(you.ip).alive){
            if (you.nb_alive == 0) {lose(you)}
            else {
                you.ip = -1
                Fenetre.msgbox.print_msg("Vous changez de Pokemon... \n Il vous en reste " + you.nb_alive.toString)
                Thread.sleep(1000)
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
                Thread.sleep(1000)
                while (!other.pokemons(other.ip).alive) {other.ip += 1}
                Fenetre.msgbox.print_msg("Il envoie " + other.pokemons(other.ip).name)
                Thread.sleep(1000)
                Fenetre.bataille.print_pok_op(other.pokemons(other.ip))
            }
        }
        else {
            Fenetre.bataille.print_pok_op(other.pokemons(other.ip))
        }
    }
    def start:Unit = {
        finished = false
        you.ip = 0; other.ip = 0; you.nb_alive = 0; other.nb_alive = 0
        for (i <- 0 to 5) {if (you.pokemons(i).alive) {you.nb_alive += 1};
                           if (other.pokemons(i).alive) {other.nb_alive += 1}}
        change
        val r = scala.util.Random; 
            var i0 = 0; 
            while (!this.finished) {
                var i1 = -1
                while (i1 == -1) {
                    Fenetre.msgbox.print_msg("Que voulez vous faire ?")
                    i0 = Fenetre.print_menu_base
                    if (i0 == 0) {
                        Fenetre.msgbox.print_msg("Choisissez une attaque :")
                        i1 = Fenetre.print_menu_attaque(you.pokemons(you.ip))
                    }
                    if (i0 == 1) {
                        Fenetre.msgbox.print_msg("Choisissez un pokémon :")
                        i1 = Fenetre.print_menu_pokemon(you)
                        while (((i1 != -1) && (!you.pokemons(i1).alive)) || (i1 == you.ip) ) {
                            if (i1 == you.ip) {
                                Fenetre.msgbox.print_msg("Ce pokémon est dékà sur le terrain, choisissez un autre pokémon :")
                            }
                            else {
                                Fenetre.msgbox.print_msg("Celui-ci n'est pas apte à retourner au combat, choisissez un autre pokémon :")
                            }
                            i1 = Fenetre.print_menu_pokemon(you)
                        }
                    }
                    if (i0 >= 2 ) {
                        Fenetre.msgbox.print_msg("Ceci n'a pas été codé")
                        Thread.sleep(1000)
                    }
                }
                var j0 = 0
                var j1 = r.nextInt(4)
                this.turn(i0,i1,j0,j1)
            }
    }
    def turn(i0 : Int,i1 : Int,j0 : Int, j1 : Int):Unit = {
        var a = 0; var b = 0;var c = 0; var d = 0
        if (!(i0 == 0)) {fst = you; snd = other; a = i0; b = i1; c = j0; d = j1}
        else {
            if (!(j0 == 0) || you.pokemons(you.ip).speed < other.pokemons(other.ip).speed) {
                fst = other; snd = you; a = j0; b = j1; c = i0; d = i1
            }
            else {
                fst = you; snd = other; a = i0; b = i1; c = j0; d = j1
            }
        }
        play(fst,snd,a,b)
        play(snd,fst,c,d)
        change
        loop += 1
    }

    def play(p : Character,q : Character,i0 : Int,i1 : Int) = {
        if (i0 == 0) {
            if (p.pokemons(p.ip).alive) {
                p.pokemons(p.ip).cast_attaque(i1,q.pokemons(q.ip))
                p.pokemons(p.ip).reload_state
            }
        }
        if (i0 == 1) {
            if (p == you) {
                p.ip = i1
                Fenetre.msgbox.print_msg("Vous envoyez " + p.pokemons(p.ip).name + " !")
                Thread.sleep(1000)
                p.pokemons(p.ip).reload_state
                Fenetre.bataille.print_pok_perso(p.pokemons(p.ip))
            }
            else {
                p.ip = i1
                Fenetre.msgbox.print_msg("Il envoie " + p.pokemons(p.ip).name + " !")
                Thread.sleep(1000)
                p.pokemons(p.ip).reload_state
                Fenetre.bataille.print_pok_op(p.pokemons(p.ip))
            }
        }
    }
}

object Empty_Battle extends Battle(Empty_character,Empty_character) {}
