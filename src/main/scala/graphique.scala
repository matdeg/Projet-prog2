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
    setSize(512, 768)
    setLocation(500, 250)
    setVisible(true)
}

class Affichage extends JPanel {

    var devant : BufferedImage = ImageIO.read(getClass.getResource("Devant.png"));
    
    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent (g)
        g.setColor(Color.red)
        g.drawImage(devant, 0, 0, null)
        println(devant)
    }
}