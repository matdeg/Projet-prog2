abstract class Character(pname : String) {

    // true s'il s'agit du joueur 
    var is_main : Boolean = false

    var name : String = pname
    var nb_pokemons : Int = 0
    var nb_alive : Int = 0

    // indice du pokémon courant
    var ip : Int = 0

    var page : Int = 0
    var bag : Array[Int] = new Array[Int](40)

    def current_items_id = {Func.choose(bag,page * 4, (page + 1) * 4)}

    var pokemons : Array[Pokemon] = new Array[Pokemon](6)
    var money : Int = 0
    var current_battle : Battle = Empty_Battle
}

object Empty_character extends Character("") {}
class Opponent(pname : String) extends Character(pname) {}
class Player(pname : String) extends Character(pname) {
    is_main = true
}
