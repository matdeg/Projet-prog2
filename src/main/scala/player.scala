abstract class character(Pname : String) {
    def name : String
    def nb_pokemons : Int
    def pokemons : Array[Pokemon]
    def money : Int
}

class opponent(Pname : String) extends character(Pname) {
    var name = Pname
    var nb_pokemons: Int = 0
    var pokemons = new Array[Pokemon](6)
    var money = 0
}

class player(Pname : String) extends character(Pname) {
    var name = Pname
    var nb_pokemons: Int = 0
    var pokemons = new Array[Pokemon](6)
    var money = 0
}