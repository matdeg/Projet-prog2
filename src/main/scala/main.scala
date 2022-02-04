import swing._
import scala.io.StdIn.readLine
import java.util.Random

object MyProgram {
    def main(args: Array[String]): Unit = {
        var op1 = new Opponent("Gaspard le Canard")
        var pl = new Player("Mathis la saucisse")
        op1.pokemons(0) = new Pokemon_test1("Pikachu_op")
        op1.pokemons(1) = new Pokemon_test1("Miaouss_op")
        op1.pokemons(2) = new Pokemon_test1("Carapuce_op")
        op1.pokemons(3) = new Pokemon_test1("Dracaufeu_op")
        op1.pokemons(4) = new Pokemon_test1("Salamèche_op")
        op1.pokemons(5) = new Pokemon_test1("Ouisticram_op")

        pl.pokemons(0) = new Pokemon_test0("Pikachu")
        pl.pokemons(1) = new Pokemon_test0("Miaouss")
        pl.pokemons(2) = new Pokemon_test0("Carapuce")
        pl.pokemons(3) = new Pokemon_test0("Dracaufeu")
        pl.pokemons(4) = new Pokemon_test0("Salamèche")
        pl.pokemons(5) = new Pokemon_test0("Ouisticram")

        var battle1 = new Battle(pl,op1)
        battle1.start
        val r = scala.util.Random; 
        var i1 = 0; var i2 = 0;
        while (!battle1.finished) {
            println("Choisissez une attaque :")
            i1 = readLine().toInt
            i2 = r.nextInt(4)
            battle1.turn(i1,i2)
        }
    }
}

