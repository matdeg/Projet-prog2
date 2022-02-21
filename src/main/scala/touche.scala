import javax.swing.JPanel
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

object Touche extends JPanel with KeyListener {
    
    var aide = false
    
    def keyPressed (evt : KeyEvent) = {}
    
    def keyReleased (evt : KeyEvent) = {}

    def keyTyped (evt : KeyEvent) : Unit = {
        if (evt.getKeyChar() == 'a') {
            aide = !aide
            println("a")
        }
    }
}