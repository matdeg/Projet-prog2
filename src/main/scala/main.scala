import swing._
import scala.io.StdIn.readLine
import java.util.Random

object MyProgram {
    def main(args: Array[String]): Unit = {
        var ecran = new Fenetre
        var p_perso = new Pokemon_test1("perso")
        var p_op = new Pokemon_test1("op")
        ecran.msgbox.print_msg("test")
        ecran.bataille.print_pok_perso(p_perso)
        ecran.bataille.print_pok_op(p_op)
    }
}
