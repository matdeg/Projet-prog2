import javax.swing.JPanel
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

object Touche extends JPanel with KeyListener {

    var aide = false

    def keyPressed (evt : KeyEvent) = {}

    def keyReleased (evt : KeyEvent) = {}

    def keyTyped (evt : KeyEvent) : Unit = {
        evt.getKeyChar match {
            case 'a' => {aide = !aide}
            case _ => if (!Player.in_battle && !Pokedex.in_pokedex && !Fenetre.map.animation) {
                evt.getKeyChar match {
                    case 'z' => {Player.move(N)}
                    case 'q' => {Player.move(E)}
                    case 's' => {Player.move(S)}
                    case 'd' => {Player.move(O)}
                    case 'e' => {Player.interact}
                    case _ => {}
                }
                Fenetre.map.repaint()
            }
        }
    }

}