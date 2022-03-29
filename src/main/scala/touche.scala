import javax.swing.JPanel
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

object Touche extends JPanel with KeyListener {

    var aide = false

    def keyPressed (evt : KeyEvent) = {
        evt.getKeyCode match {
            case 16 if (Player.has_shoes) => Game.speed = 2.5
            case 10 => Game.enter = true
            case _ => {}
        }
    }

    def keyReleased (evt : KeyEvent) = {
        evt.getKeyCode match {
            case 16 => Game.speed = 1.0
            case _ => {}
        }
    }

    def keyTyped (evt : KeyEvent) : Unit = {
        if (!Dialogue.isAlive) {
            evt.getKeyChar.toLower match {
            case 'a' => {aide = !aide}
            case _ => if (!Player.in_battle && !Pokedex.in_pokedex && !Fenetre.map.animation) {
                evt.getKeyChar.toLower match {
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
} 