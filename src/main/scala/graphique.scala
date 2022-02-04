import java.awt.{BorderLayout, Dimension, Graphics, Color, Font, Graphics2D, Image}
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JPanel
import javax.imageio.ImageIO
import java.io.File
import java.io.IOException
import java.awt.image.BufferedImage

class Fenetre extends JFrame {

    setLayout(null)
    setUndecorated(true)
    setContentPane(new Affichage)
    setSize(500, 800)
    setLocation(500, 250)
    setVisible(true)
}

class Affichage extends JPanel {

    var devant : BufferedImage = ImageIO.read(getClass.getResource("Devant.png"));
    var derriere : BufferedImage = ImageIO.read(getClass.getResource("Derriere.png"));

    def draw_button (g : Graphics): Unit = {
        g.setColor(Color.GRAY)
        g.fillRect(0, 500, 500, 300)
    }

    def draw_msgbox (g : Graphics) : Unit = {
        g.setColor(Color.BLACK)
        g.fillRect(10, 400, 480, 100)
        g.setColor(Color.WHITE)
        g.fillRect(20, 410, 460, 80)
    }
    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent (g)
        g.setColor(Color.red)
        g.drawImage(devant, 300, 50, null)
        g.drawImage(derriere, 50, 250, null)
        draw_button (g)
        draw_msgbox (g)
    }
}