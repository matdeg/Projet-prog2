import java.awt.{BorderLayout, Dimension, Graphics, Color, Font, Graphics2D, Image}
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JButton
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JPanel
import javax.imageio.ImageIO
import java.io.File
import java.io.IOException
import java.awt.image.BufferedImage
import java.awt.GridLayout
import javax.swing.BoxLayout
import java.text.AttributedString

class AffichageBataille extends JPanel {


    var devant : BufferedImage = ImageIO.read(getClass.getResource("Devant.png"))
    var derriere : BufferedImage = ImageIO.read(getClass.getResource("Derriere.png"))
    var env : BufferedImage = ImageIO.read(getClass.getResource("Atrium_background.png"))

    def print_pok_perso (p : Pokemon)  : Unit = {
        derriere = ImageIO.read(getClass.getResource(p.image))
        repaint ()
    }

    def print_pok_op (p: Pokemon) : Unit = {
        devant = ImageIO.read(getClass.getResource(p.image))
        repaint ()
    }
    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent (g)
        g.drawImage(env, 0, 0, null)
        g.drawImage(devant, 240, 40, null)
        g.drawImage(derriere, 40, 240, null)
    }
}

class MsgBox extends JPanel {

    def print_msg (s: String) : Unit = {
        var texte = new JLabel(s)
        this.add(texte)
    }
}

class Fenetre extends JFrame {

    this.setTitle("Best Game Ever")
    this.setSize(400, 800)
    this.setLocation(500, 250)
    this.setResizable(false)


    var bataille = new AffichageBataille ()


    var msgbox = new MsgBox ()
    

    var interaction = new JPanel ()
    interaction.setLayout(new GridLayout(2,2))

    var boutonhg = new JButton ("Haut Gauche")
    var boutonhd = new JButton ("Haut Droit")
    var boutonbg = new JButton ("Bas Gauche")
    var boutonbd = new JButton ("Bas Droit")
    interaction.add(boutonhg)
    interaction.add(boutonhd)
    interaction.add(boutonbg)
    interaction.add(boutonbd)


    var bas_fenetre = new JPanel
    bas_fenetre.setLayout(new GridLayout(2,1))
    bas_fenetre.add(msgbox)
    bas_fenetre.add(interaction)

    var total = new JPanel
    total.setLayout(new GridLayout(2,1))
    total.add(bataille)
    total.add(bas_fenetre)

    this.setContentPane(total)
    setVisible(true)

}