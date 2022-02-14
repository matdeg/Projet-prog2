abstract class Character(pname : String) {
    var is_main : Boolean = false
    var name : String = pname
    var nb_pokemons : Int = 0
    var nb_alive : Int = 0
    var ip : Int = 0
    var pokemons : Array[Pokemon] = new Array[Pokemon](6)
    var money : Int = 0
    var current_battle : Battle = Empty_Battle
    
}
object Empty_character extends Character("") {}
class Opponent(pname : String) extends Character(pname) {}
class Player(pname : String) extends Character(pname) {
    is_main = true
}
