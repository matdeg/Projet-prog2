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
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import java.lang.Thread

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

    var texte = new JLabel("Msgbox")
    this.add(texte)

    def print_msg (s: String) : Unit = {
        texte.setText(s)
    }
}

object Fenetre extends JFrame with ActionListener{
    this.setTitle("Best Game Ever")
    this.setSize(750, 1000)
    this.setResizable(false)

    var compteur = 0


    var bataille = new AffichageBataille ()


    var msgbox = new MsgBox ()
    

    var bouton0 = new JButton ("0")
    var bouton1 = new JButton ("1")
    var bouton2 = new JButton ("2")
    var bouton3 = new JButton ("3")
    var bouton4 = new JButton ("4")
    var bouton5 = new JButton ("5")
    var boutonr = new JButton ("-1")
    
    var rangee_bouton_1 = new JPanel
    rangee_bouton_1.setLayout(new GridLayout(1, 2)) 
    rangee_bouton_1.add(bouton0)
    rangee_bouton_1.add(bouton1)

    var rangee_bouton_2 = new JPanel
    rangee_bouton_2.setLayout(new GridLayout(1, 2))
    rangee_bouton_2.add(bouton2)
    rangee_bouton_2.add(bouton3)

    var rangee_bouton_3 = new JPanel
    rangee_bouton_3.setLayout(new GridLayout(1, 2))
    rangee_bouton_3.add(bouton4)
    rangee_bouton_3.add(bouton5)

    var rangee_retour = new JPanel
    rangee_retour.setLayout(new GridLayout(1, 1))
    rangee_retour.add(boutonr)

    var choix_menu = -2

    var bas_fenetre = new JPanel
    bas_fenetre.setLayout(new BoxLayout(bas_fenetre, BoxLayout.PAGE_AXIS))
    bas_fenetre.add(msgbox)

    def print_menu_base () : Int = {

        bouton0.setText("Attaque")
        bouton1.setText("Pokémon")
        bouton2.setText("Sac")
        bouton3.setText("Fuite")

        bas_fenetre.add(rangee_bouton_1)
        bas_fenetre.add(rangee_bouton_2)

        bouton0.addActionListener(this)
        bouton1.addActionListener(this)
        bouton2.addActionListener(this)
        bouton3.addActionListener(this)

        bas_fenetre.updateUI

        choix_menu = -2

        while (choix_menu == -2) {
            Thread.sleep(100)
        }

        bas_fenetre.remove(rangee_bouton_1)
        bas_fenetre.remove(rangee_bouton_2)

        choix_menu
    }

    def print_menu_attaque (p : Pokemon) : Int = {

        bouton0.setText(p.attaques(0).name)
        bouton1.setText(p.attaques(1).name)
        bouton2.setText(p.attaques(2).name)
        bouton3.setText(p.attaques(3).name)
        boutonr.setText("Retour")

        bas_fenetre.add(rangee_bouton_1)
        bas_fenetre.add(rangee_bouton_2)
        bas_fenetre.add(rangee_retour)

        bas_fenetre.updateUI

        bouton0.addActionListener(this)
        bouton1.addActionListener(this)
        bouton2.addActionListener(this)
        bouton3.addActionListener(this)
        boutonr.addActionListener(this)

        choix_menu = -2

        while (choix_menu == -2) {
            Thread.sleep(100)
        }

        bas_fenetre.remove(rangee_bouton_1)
        bas_fenetre.remove(rangee_bouton_2)
        bas_fenetre.remove(rangee_retour)

        choix_menu
    }

    def print_menu_pokemon (p : Character) : Int = {
        
        bouton0.setText(p.pokemons(0).name)
        bouton1.setText(p.pokemons(1).name)
        bouton2.setText(p.pokemons(2).name)
        bouton3.setText(p.pokemons(3).name)
        bouton4.setText(p.pokemons(4).name)
        bouton5.setText(p.pokemons(5).name)
        boutonr.setText("Retour")

        bas_fenetre.add(rangee_bouton_1)
        bas_fenetre.add(rangee_bouton_2)
        bas_fenetre.add(rangee_bouton_3)
        bas_fenetre.add(rangee_retour)

        bas_fenetre.updateUI

        bouton0.addActionListener(this)
        bouton1.addActionListener(this)
        bouton2.addActionListener(this)
        bouton3.addActionListener(this)
        bouton4.addActionListener(this)
        bouton5.addActionListener(this)
        boutonr.addActionListener(this)

        choix_menu = -2

        while (choix_menu == -2) {
            Thread.sleep(100)
        }

        bas_fenetre.remove(rangee_bouton_1)
        bas_fenetre.remove(rangee_bouton_2)
        bas_fenetre.remove(rangee_bouton_3)
        bas_fenetre.remove(rangee_retour)

        choix_menu
    }
    override def actionPerformed (arg0 : ActionEvent) : Unit = {
        if (arg0.getSource == bouton0) {
            choix_menu = 0
        }
        if (arg0.getSource == bouton1) {
            choix_menu = 1
        }
        if (arg0.getSource == bouton2) {
            choix_menu = 2
        }
        if (arg0.getSource == bouton3) {
            choix_menu = 3
        }
        if (arg0.getSource == bouton4) {
            choix_menu = 4
        }
        if (arg0.getSource == bouton5) {
            choix_menu = 5
        }
        if (arg0.getSource == boutonr) {
            choix_menu = -1
        }
    }

    var total = new JPanel
    total.setLayout(new GridLayout(2,1))
    total.add(bataille)
    total.add(bas_fenetre)

    this.setContentPane(total)
    setVisible(true)

}