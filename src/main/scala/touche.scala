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

object Touche_deplacement extends JPanel with KeyListener {

    def keyPressed (evt : KeyEvent) = {}

    def keyReleased (evt : KeyEvent) = {}

    def keyTyped (evt : KeyEvent) : Unit = {
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