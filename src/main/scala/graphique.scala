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
import java.net.CookieStore

class AffichageBataille extends JPanel {

    this.setFont(new Font("Helvetica Neue", Font.BOLD, 15))

    var devant : BufferedImage = ImageIO.read(getClass.getResource("Devant.png"))
    var derriere : BufferedImage = ImageIO.read(getClass.getResource("Derriere.png"))
    var env : BufferedImage = ImageIO.read(getClass.getResource("Atrium.png"))

    var hp_perso = 50
    var max_hp_perso = 100
    var name_perso = "perso"
    var lvl_perso = 5
    var hp_op = 30
    var max_hp_op = 120
    var name_op = "op"
    var lvl_op  = 7

    def print_pok_perso (p : Pokemon)  : Unit = {
        derriere = ImageIO.read(getClass.getResource(p.image))
        hp_perso = p.hp
        max_hp_perso = p.max_hp
        name_perso = p.name
        lvl_perso = p.lvl
        repaint ()
    }

    def print_pok_op (p: Pokemon) : Unit = {
        devant = ImageIO.read(getClass.getResource(p.image))
        hp_op = p.hp
        max_hp_op = p.max_hp
        name_op = p.name
        lvl_op = p.lvl
        repaint ()
    }
    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent (g)

        g.drawImage(env, 0, 0, null)

        g.drawImage(devant, 230, 50, null)
        g.setColor(Color.BLACK)
        g.drawString(name_op + " lvl." + lvl_op, 230, 170)
        g.drawRect(220, 50, 10, 120)
        g.drawString(hp_op.toString + "/" + max_hp_op.toString, 220, 45)
        g.setColor(Color.GREEN)
        g.fillRect(220, 170-(120*hp_op/max_hp_op), 10, 120*hp_op/max_hp_op)

        g.drawImage(derriere, 50, 230, null)
        g.setColor(Color.BLACK)
        g.drawString(name_perso + " lvl." + lvl_perso.toString, 50, 350)
        g.drawRect(40, 230, 10, 120)
        g.drawString(hp_perso.toString + "/" + max_hp_perso.toString, 40, 225)
        g.setColor(Color.GREEN)
        g.fillRect(40, 350-120*hp_perso/max_hp_perso, 10, 120*hp_perso/max_hp_perso)
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