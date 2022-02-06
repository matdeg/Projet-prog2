import swing._
import scala.io.StdIn.readLine
import java.util.Random

object MyProgram {
    def main(args: Array[String]): Unit = {
        var ecran = new Fenetre
        var p = new Pokemon_test1("pikachu")
        ecran.msgbox.print_msg("test")
        ecran.bataille.print_pok_perso(p)
        ecran.bataille.print_pok_op(p)
    }
}
