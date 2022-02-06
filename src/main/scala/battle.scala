class Battle(p1 : Character,p2 : Character) {
    var you : Character = p1
    var other : Character = p2
    var loop : Int = 1
    var finished : Boolean = false
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
    }
    def turn(i1 : Int,i2 : Int):Unit = {
        println("Début du tour " + loop.toString + " :")
        if (you.pokemons(you.ip).speed > you.pokemons(other.ip).speed) {
            you.pokemons(you.ip).cast_attaque(i1,other.pokemons(other.ip))
            you.pokemons(you.ip).reload_state
            if (other.pokemons(other.ip).alive) {
                other.pokemons(other.ip).cast_attaque(i2,you.pokemons(you.ip))
                other.pokemons(other.ip).reload_state
            }
        }
        else {
            other.pokemons(other.ip).cast_attaque(i2,you.pokemons(you.ip))
            other.pokemons(other.ip).reload_state
            if (you.pokemons(you.ip).alive) {
                you.pokemons(you.ip).cast_attaque(i1,other.pokemons(other.ip))
                you.pokemons(you.ip).reload_state
            }
        }
        change
        println("")
        loop += 1
    }
}

object Empty_Battle extends Battle(Empty_character,Empty_character) {}
