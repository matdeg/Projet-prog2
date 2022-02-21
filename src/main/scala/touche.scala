import javax.swing.JPanel
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

object Touche_aide extends JPanel with KeyListener {
    
    var aide = false
    
    def keyPressed (evt : KeyEvent) = {}
    
    def keyReleased (evt : KeyEvent) = {}

    def keyTyped (evt : KeyEvent) : Unit = {
        if (evt.getKeyChar() == 'a') {
            aide = !aide
        }
    }
}

object Touche_déplacement extends JPanel with KeyListener {

    var abs = 0
    var ord = 0

    def keyPressed (evt : KeyEvent) = {}

    def keyReleased (evt : KeyEvent) = {}

    def keyTyped (evt : KeyEvent) : Unit = {
        evt.getKeyChar match {
            case 'z' => {ord += 5; println((abs, ord))}
            case 'q' => {abs -= 5; println((abs, ord))}
            case 's' => {ord -= 5; println((abs, ord))}
            case 'd' => {abs += 5; println((abs, ord))}
            case _ => {}
        }
    }

}