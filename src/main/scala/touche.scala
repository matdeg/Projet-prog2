import javax.swing.JPanel
import java.awt.event.KeyListener
import java.awt.event.KeyEvent

object Touche extends JPanel with KeyListener {
    
    var aide = false
    
    def keyPressed (evt : KeyEvent) = {
        println("b")
    }
    
    def keyReleased (evt : KeyEvent) = {}

    def keyTyped (evt : KeyEvent) : Unit = {
        if (evt.getKeyChar == 'a') {
            println("a")
        }
    }
}