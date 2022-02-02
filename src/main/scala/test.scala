import swing._
object MyProgram {
    def main(args: Array[String]): Unit = {
        val op1 = new opponent("Gaspard le Canard")
        val pl = new player("Mathis la saucisse")
        op1.pokemons(0) = new Pokemon_test("Pikachu")
        pl.pokemons(0) = new Pokemon_test("Dracaufeu")
        println(op1.pokemons(0).hp)
        pl.pokemons(0).cast_attaque(0,op1.pokemons(0))
        println(op1.pokemons(0).hp)
    }
}

