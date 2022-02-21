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
import java.awt.event.MouseEvent
import java.lang.Thread
import javax.swing.SwingConstants
import java.awt.event.MouseListener
import java.awt.event.KeyListener
import java.awt.event.KeyEvent
import java.awt.RenderingHints.Key
import javax.swing.Timer

class AffichageBataille extends JPanel {

    this.setFont(new Font("Helvetica Neue", Font.BOLD, 13))

    var devant : BufferedImage = ImageIO.read(getClass.getResource("Devant.png"))
    var derriere : BufferedImage = ImageIO.read(getClass.getResource("Derriere.png"))
    var env : BufferedImage = ImageIO.read(getClass.getResource("background/jardin_flou.png"))
    var stat_op : BufferedImage = ImageIO.read(getClass.getResource("HP_bars_op.png"))
    var stat_perso : BufferedImage = ImageIO.read(getClass.getResource("HP_bars_perso.png"))
    var pokeball : BufferedImage = ImageIO.read(getClass.getResource("pokeball_bataille.png"))
    var pokeball_statut : BufferedImage = ImageIO.read(getClass.getResource("pokeball_bataille_statut.png"))
    var pokeball_KO : BufferedImage = ImageIO.read(getClass.getResource("pokeball_bataille_KO.png"))
    var gel : BufferedImage = ImageIO.read(getClass.getResource("effets/gel.png"))
    var burned : BufferedImage = ImageIO.read(getClass.getResource("effets/burned.png"))
    var sleep : BufferedImage = ImageIO.read(getClass.getResource("effets/sleep.png"))
    var para : BufferedImage = ImageIO.read(getClass.getResource("effets/para.png"))
    var poison : BufferedImage = ImageIO.read(getClass.getResource("effets/poison.png"))

    var you : Character = Empty_character
    var op : Character = Empty_character
    var hp_perso = 20
    var max_hp_perso = 100
    var name_perso = "perso"
    var lvl_perso = 100
    var hp_op = 12
    var max_hp_op = 120
    var name_op = "op"
    var lvl_op  = 100
    var exp = 50
    var next_lvl = 150

    def print_pok_perso (p : Pokemon)  : Unit = {
        derriere = ImageIO.read(getClass.getResource(p.image))
        hp_perso = p.hp
        max_hp_perso = p.max_hp
        name_perso = p.name
        lvl_perso = p.lvl
        exp = p.xp
        next_lvl = p.next_xp
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

    def print_ball_op (g : Graphics) : Unit = {
        for (i<-0 to 5) {
            if (!op.pokemons(i).alive) {
                g.drawImage(pokeball_KO, 250 + i*40, 160, 30, 30, null)
            }
            else {
                if (op.pokemons(i).state == None_state) {
                    g.drawImage(pokeball, 250 + i*40, 160, 30, 30, null)
                }
                else {
                    g.drawImage(pokeball_statut, 250 + i*40, 160, 30, 30, null)
                }
            }
            
        }
    }

    def print_ball_perso (g : Graphics) : Unit = {
        for (i<-0 to 5) {
            if (!you.pokemons(i).alive) {
                g.drawImage(pokeball_KO, 285 + i*40, 410, 30, 30, null)
            }
            else {
                if (you.pokemons(i).state == None_state) {
                    g.drawImage(pokeball, 285 + i*40, 410, 30, 30, null)
                }
                else {
                    g.drawImage(pokeball_statut, 285 + i*40, 410, 30, 30, null)
                }
            }
            
        }
    }

    def print_statut_op (g : Graphics) : Unit = {
        op.pokemons(op.ip).state match {
            case Freeze => g.drawImage(gel, 100, 100, 100, 100, null)
            case Burned => g.drawImage(burned, 100, 100, 100, 100, null)
            case Sommeil => g.drawImage(sleep, 100, 100, 100, 100, null)
        }
    }
    
    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent (g)

        g.drawImage(env, 0, 0, null)

        g.drawImage(devant, 500, 50, null)
        g.drawImage(stat_op, 250, 100, 250, 60, null)
        print_ball_op(g)
        g.setColor(Color.WHITE)
        g.drawString(name_op, 280, 131)
        g.drawString(lvl_op.toString, 420, 131)
        if (hp_op <= max_hp_op/5) {
            g.setColor(Color.RED)
        }
        else {
            if (hp_op <= max_hp_op/2) {
                g.setColor(Color.ORANGE)
            }
            else {
                g.setColor(Color.GREEN)
            }
        }
        g.fillRect(356, 142, hp_op*92/max_hp_op, 3)

        g.drawImage(derriere, 100, 450-derriere.getHeight, null)
        g.drawImage(stat_perso, 250, 350, 250, 60, null)
        print_ball_perso(g)
        g.setColor(Color.WHITE)
        g.drawString(name_perso, 310, 374)
        g.drawString(lvl_perso.toString, 448, 374)
        if (hp_perso < 100) {
            g.drawString(hp_perso.toString, 397, 397)
        }
        else {
            g.drawString(hp_perso.toString, 391, 397)
        }
        g.drawString(max_hp_perso.toString, 431, 397)
        if (hp_perso <= max_hp_perso/5) {
            g.setColor(Color.RED)
        }
        else {
            if (hp_perso <= max_hp_perso/2) {
                g.setColor(Color.ORANGE)
            }
            else {
                g.setColor(Color.GREEN)
            }
        }
        g.fillRect(387, 382, hp_perso*90/max_hp_perso, 3)
        g.setColor(Color.CYAN)
        g.fillRect(298, 400, exp*190/next_lvl, 2)
    }
}

class MsgBox extends JPanel {

    var texte = "Msgbox"
    var save = "save"

    this.setFont(new Font("Helvetica Neue", Font.BOLD, 17))
    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent(g)

        g.setColor(Color.BLACK)
        g.fillRect(0, 0, this.getWidth, this.getHeight)
        g.setColor(Color.WHITE)
        g.drawRect(5, 5, this.getWidth-10, this.getHeight-10)
        g.fillRect(10, 10, this.getWidth-20, this.getHeight-20)
        g.setColor(Color.BLACK)
        g.drawString(texte, 15, 30)
    }

    def print_msg (s: String) : Unit = {
        texte = s
        repaint ()
    }

    def print_save () : Unit = {
        texte = save
        repaint ()
    }
}

class Bouton extends JButton with MouseListener {

    var fond : BufferedImage = ImageIO.read(getClass.getResource("couleur/blanc.png"))
    var icone : BufferedImage = null
    var valeur = -2

    var info = "azerty"

    this.addMouseListener(this)

    def mouseEntered (e : MouseEvent) : Unit = {
        if (Touche_aide.aide) {
            Fenetre.msgbox.print_msg(info)
        }
        
    }

    def mouseClicked (e : MouseEvent) : Unit = {
        Fenetre.bas_fenetre.choix_menu = valeur
    }

    def mouseExited (e : MouseEvent) : Unit = {
        Fenetre.msgbox.print_save ()
    }

    def mousePressed (e : MouseEvent) : Unit = {}

    def mouseReleased (e : MouseEvent) : Unit = {}

    def set_font (s : String) : Unit = {
        fond = ImageIO.read(getClass.getResource(s))
        repaint ()
    }
    
    def set_icone (s : String) : Unit = {
        icone = ImageIO.read(getClass.getResource(s))
        repaint ()
    }

    def init_icone () : Unit = {
        icone = null
        repaint ()
    }

    this.setFont(new Font("Helvetica Neue", Font.BOLD, 17))
    var mesure = getFontMetrics(new Font("Helvetica Neue", Font.BOLD, 17))

    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponents(g)

        g.drawImage(fond, 0, 0, this.getWidth, this.getHeight, null)
        g.setColor(Color.BLACK)
        g.drawString(getText, (this.getWidth-mesure.stringWidth(getText))/2, (this.getHeight-mesure.getHeight())/2 + mesure.getAscent())
        if (icone != null) {
            g.drawImage(icone, (this.getWidth-mesure.stringWidth(getText))/2 - 80, (this.getHeight-mesure.getHeight())/2 - mesure.getAscent(), 70, 70, null)
        }
    }

}

class Menu_attaque extends JPanel {

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS))
    add(Fenetre.msgbox)

    var bouton0 = new Bouton
    var bouton1 = new Bouton
    var bouton2 = new Bouton
    var bouton3 = new Bouton
    var bouton4 = new Bouton
    var bouton5 = new Bouton
    var boutonr = new Bouton
    boutonr.valeur = -1

    var bouton : Array[Bouton] = Array(bouton0, bouton1, bouton2, bouton3, bouton4, bouton5, boutonr)
    for (i <- 0 to 5) {
        bouton(i).valeur = i
    }
    
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

    def print_menu_base () : Int = {

        Fenetre.msgbox.save = "Que voulez vous faire ?"
        bouton0.setText("Attaque")
        bouton0.set_font("couleur/rouge.jpeg")
        bouton0.info = "Permet de lancer les attaques"
        bouton1.setText("Pokémon")
        bouton1.set_font("couleur/vert.png")
        bouton1.info = "Permet de changer de Pokéfusion"
        bouton2.setText("Sac")
        bouton2.set_font("couleur/jaune.jpg")
        bouton2.info = "Permet d'utiliser des objets"
        bouton3.setText("Fuite")
        bouton3.set_font("couleur/bleu.jpg")
        bouton3.info = "Permet de fuir le combat"
        for (i <- 0 to 3) {
            bouton(i).init_icone
        }

        this.add(rangee_bouton_1)
        this.add(rangee_bouton_2)

        this.updateUI

        choix_menu = -2

        while (choix_menu == -2) {
            Thread.sleep(100)
        }

        this.remove(rangee_bouton_1)
        this.remove(rangee_bouton_2)

        choix_menu
    }

    def associe_couleur (t : Ttype) : String = {
        t match {
            case Acier => "types/Acier.jpg"
            case Combat => "types/Combat.jpg"
            case Dragon => "types/Dragon.png"
            case Eau => "types/Eau.png"
            case Electrik => "types/Electrik.jpg"
            case Feu => "types/Feu.jpeg"
            case Glace => "types/Glace.png"
            case Insecte => "types/Insecte.png"
            case Vol => "types/Vol.jpeg"
            case Plante => "types/Plante.jpg"
            case Poison => "types/Poison.png"
            case Psy => "types/Psy.png"
            case Roche => "types/Roche.png"
            case Sol => "types/Sol.jpg"
            case Spectre => "types/Spectre.jpg"
            case Tenebres => "types/Tenebres.jpg"
            case Normal => "types/Normal.jpg"
            case _ => "couleur/multicolor.jpeg"
        }
    }

    def print_menu_attaque (p : Pokemon) : Int = {

        Fenetre.msgbox.save = "Choisissez une attaque :"
        for (i <- 0 to 3) {
            bouton(i).setText(p.attaques(i).name + "   " + p.pp_list(i).toString + "/" + p.attaques(i).pp.toString)
            bouton(i).set_font(associe_couleur(p.attaques(i).atype))
            bouton(i).init_icone()
            bouton(i).info = "Dégats bruts : " + p.attaques(i).dmg.toString + "   " + "Precision : " + p.attaques(i).precision.toString 
        }
        boutonr.setText("Retour")
        boutonr.info = "Permet de retourner au menu principal"

        this.add(rangee_bouton_1)
        this.add(rangee_bouton_2)
        this.add(rangee_retour)

        this.updateUI

        choix_menu = -2

        while (choix_menu == -2) {
            Thread.sleep(100)
        }

        this.remove(rangee_bouton_1)
        this.remove(rangee_bouton_2)
        this.remove(rangee_retour)

        choix_menu
    }

    def print_menu_pokemon (p : Character) : Int = {
        
        Fenetre.msgbox.save = "Choisissez un Pokéfusion : "
        for (i <- 0 to 5) {
            bouton(i).setText(p.pokemons(i).name)
            bouton(i).set_font(associe_couleur(p.pokemons(i).ptype))
            bouton(i).set_icone(p.pokemons(i).image)
            bouton(i).info = "Hp : " +  p.pokemons(i).hp.toString + "/" + p.pokemons(i).max_hp.toString + "   " + "Atk : " + p.pokemons(i).atk.toString + "   Dfs : " + p.pokemons(i).defense.toString + "   Spd : " + p.pokemons(i).speed.toString
        }
        boutonr.setText("Retour")
        boutonr.info = "Permet de retourner au menu principal"

        this.add(rangee_bouton_1)
        this.add(rangee_bouton_2)
        this.add(rangee_bouton_3)
        this.add(rangee_retour)

        this.updateUI

        choix_menu = -2

        while (choix_menu == -2) {
            Thread.sleep(100)
        }

        this.remove(rangee_bouton_1)
        this.remove(rangee_bouton_2)
        this.remove(rangee_bouton_3)
        this.remove(rangee_retour)

        choix_menu
    }
}

object Fenetre extends JFrame {
    this.setTitle("Best Game Ever")
    this.setSize(750, 1000)
    this.setResizable(false)

    override def setFocusable(b : Boolean) = {
        super.setFocusable(b)
    }

    setFocusable(true)
    addKeyListener(Touche_aide)

    var bataille = new AffichageBataille ()

    var msgbox = new MsgBox ()

    var bas_fenetre = new Menu_attaque

    var total_bataille = new JPanel
    total_bataille.setLayout(new GridLayout(2,1))
    total_bataille.add(bataille)
    total_bataille.add(bas_fenetre)

    this.setContentPane(total_bataille)
    setVisible(true)

}