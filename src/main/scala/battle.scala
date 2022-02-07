class Battle(p1 : Character,p2 : Character) {
    var you : Character = p1
    var other : Character = p2
    var loop : Int = 1
    var finished : Boolean = false
    var fst : Character = Empty_character
    var snd : Character = Empty_character
    def lose(p : Character):Unit = {finished = true
                                    if (p == you) {println("Mince, vous avez perdu")}
                                    else {println("Bravo, vous avez gagné !")}}
    def change:Unit = {
        if (!you.pokemons(you.ip).alive){
            if (you.nb_alive == 0) {lose(you)}
            else {
                println("Vous changez de Pokemon... Il vous en reste " + you.nb_alive.toString)
                println("Veuillez choisir un pokémon :")
                you.ip = readLine().toInt 
                while (!you.pokemons(you.ip).alive) {
                    println("Ce pokémon n'est pas apte à retourner au combat")
                    you.ip = readLine().toInt
                }
                println("Vous envoyez " + you.pokemons(you.ip).name)
            }
        }
        if (!other.pokemons(other.ip).alive){
            if (other.nb_alive == 0) {lose(other)}
            else {
                println("Il change de Pokemon... Il lui en reste " + other.nb_alive.toString)
                while (!other.pokemons(other.ip).alive) {other.ip += 1}
                println("Il envoie " + other.pokemons(other.ip).name)
            }
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
                    println("Que voulez vous faire ?")
                    i0 = readLine().toInt
                    if (i0 == 0) {
                        println("Choisissez une attaque :")
                        i1 = readLine().toInt
                    }
                    if (i0 == 1) {
                        println("Choisissez un pokémon :")
                        i1 = readLine().toInt
                        while (((i1 != -1) && (!you.pokemons(i1).alive)) || (i1 == you.ip) ) {
                            if (i1 == you.ip) {
                                println("Ce pokémon est dékà sur le terrain, choisissez un autre pokémon :")
                            }
                            else {
                                println("Celui-ci n'est pas apte à retourner au combat, choisissez un autre pokémon :")
                            }
                            i1 = readLine().toInt
                        }
                    }
                }
                var j0 = 0
                var j1 = r.nextInt(4)
                this.turn(i0,i1,j0,j1)
            }
    }
    def turn(i0 : Int,i1 : Int,j0 : Int, j1 : Int):Unit = {
        println("Début du tour " + loop.toString + " :")
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
        println("")
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
            p.ip = i1
            println("Vous envoyez " + p.pokemons(p.ip).name + " !")
            p.pokemons(p.ip).reload_state
        }
    }
}

object Empty_Battle extends Battle(Empty_character,Empty_character) {}
