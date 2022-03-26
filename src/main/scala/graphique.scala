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
import java.awt.event.FocusEvent
import javax.swing.LookAndFeel
import java.awt.Label

class AffichageBataille extends JPanel {

    this.setFont(new Font("Helvetica Neue", Font.BOLD, 13))

    var devant : BufferedImage = ImageIO.read(getClass.getResource("Devant.png"))
    var derriere : BufferedImage = ImageIO.read(getClass.getResource("Derriere.png"))
    def env : BufferedImage = ImageIO.read(getClass.getResource(Player.current_area.background))
    var stat_op : BufferedImage = ImageIO.read(getClass.getResource("HP_bars_op.png"))
    var stat_perso : BufferedImage = ImageIO.read(getClass.getResource("HP_bars_perso.png"))
    var pokeball : BufferedImage = ImageIO.read(getClass.getResource("pokeball_bataille.png"))
    var pokeball_statut : BufferedImage = ImageIO.read(getClass.getResource("pokeball_bataille_statut.png"))
    var pokeball_KO : BufferedImage = ImageIO.read(getClass.getResource("pokeball_bataille_KO.png"))
    def img_state(p : Pokemon) : BufferedImage = ImageIO.read(getClass.getResource(p.state.file_name))

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
        if (op.pokemons(op.ip).state != None_state) {
            g.drawImage(img_state(op.pokemons(op.ip)), 100, 100, 100, 100, null)
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
    var pv = 0
    var max_pv = 100

    var info = "azerty"

    this.addMouseListener(this)

    def mouseEntered (e : MouseEvent) : Unit = {
        if (Touche.aide) {
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
    
    def set_icone (s : String, p : Int, m : Int) : Unit = {
        icone = ImageIO.read(getClass.getResource(s))
        pv = p
        max_pv = m
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
            if (max_pv > 0) {
                g.drawRect(this.getWidth/2 - 50, (this.getHeight-mesure.getHeight())/2 + 2*mesure.getAscent(), 100, 10)
                g.setColor(Color.GREEN)
                g.fillRect(this.getWidth/2 - 49, (this.getHeight-mesure.getHeight())/2 + 2*mesure.getAscent()+1, 100*pv/max_pv -1, 9)
            }
            
        }
    }

}

class Menu extends JPanel {

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS))
    add(Fenetre.msgbox)
    this.setFont(new Font("Helvetica Neue", Font.BOLD, 17))

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
    rangee_retour.setLayout(new GridLayout(1, 3))
    rangee_retour.add(boutonr)

    var choix_menu = -2
    var interruption_menu_map = false

    def print_menu_base () : Int = {

        if (Player.in_battle) {
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
        }
        else {
            Fenetre.msgbox.save = ""
            bouton0.setText("Pokédex")
            bouton0.set_font("couleur/rouge.jpeg")
            bouton0.info = "Permet d'accéder au Pokédex'"
            bouton1.setText("Pokémon")
            bouton1.set_font("couleur/vert.png")
            bouton1.info = "Permet d'interagir avec les Pokémons'"
            bouton2.setText("Sac")
            bouton2.set_font("couleur/jaune.jpg")
            bouton2.info = "Permet d'utiliser des objets"
            bouton3.setText("Sauvegarde")
            bouton3.set_font("couleur/bleu.jpg")
            bouton3.info = "Permet de sauvegarder le jeu"
            for (i <- 0 to 3) {
                bouton(i).init_icone
            }
        }

        this.add(rangee_bouton_1)
        this.add(rangee_bouton_2)

        this.updateUI

        choix_menu = -2

        while (choix_menu == -2 && !interruption_menu_map) {
            Thread.sleep(100)
        }

        this.remove(rangee_bouton_1)
        this.remove(rangee_bouton_2)

        interruption_menu_map = false
        choix_menu
    }

    def print_menu_attaque (p : Pokemon) : Int = {

        Fenetre.requestFocus

        Fenetre.msgbox.save = "Choisissez une attaque :"
        for (i <- 0 to 3) {
            bouton(i).setText(p.attaques(i).name + "   " + p.pp_list(i).toString + "/" + p.attaques(i).pp.toString)
            bouton(i).set_font(p.attaques(i).atype.image)
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
        
        Fenetre.requestFocus
        
        Fenetre.msgbox.save = "Choisissez un Pokéfusion : "
        for (i <- 0 to 5) {
            bouton(i).setText(p.pokemons(i).name)
            bouton(i).set_font(p.pokemons(i).ptype.image)
            bouton(i).set_icone(p.pokemons(i).image, p.pokemons(i).hp, p.pokemons(i).max_hp)
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

    def vider (l : List[Int]) : Unit = {
        if (!l.isEmpty) {
            vider(l.tail)
            if (l.head < 2) (
                rangee_bouton_1.add(bouton(l.head))
            )
            else {
                rangee_bouton_2.add(bouton(l.head))
            }
        }
    }

    def print_menu_objet (p : Character) : Int = {

        Fenetre.requestFocus

        Fenetre.msgbox.save = "Choisissez un objet :"
        var bouton_enleve : List[Int] = List() 
        for (i <- 0 to 3) {
            if (p.current_items_id(i) != -1) {
                bouton(i).setText(Func.id_items(p.current_items_id(i)).name + " : " + p.bag(p.current_items_id(i)))
                bouton(i).set_icone(Func.id_items(p.current_items_id(i)).img, 0, -1)
                bouton(i).info = Func.id_items(p.current_items_id(i)).info
                if (Func.id_items(p.current_items_id(i)).is_usable(p.pokemons(p.ip))) {
                    bouton(i).set_font("couleur/blanc.png")
                }
                else {
                    bouton(i).set_font("types/Acier.jpg")
                }
                
            }
            else {
                if (i > 1) {
                    rangee_bouton_2.remove(bouton(i))
                    bouton_enleve = i :: bouton_enleve
                }
                else {
                    rangee_bouton_1.remove(bouton(i))
                    bouton_enleve = i :: bouton_enleve
                }
            }
        }
        bouton4.setText("Suivant")
        bouton4.info = "Affiche la page suivante"
        bouton4.init_icone
        bouton4.set_font("couleur/blanc.png")
        bouton5.setText("Précédent")
        bouton5.info = "Affiche la page précédente"
        bouton5.init_icone
        bouton5.set_font("couleur/blanc.png")
        boutonr.setText("Retour")
        boutonr.info = "Permet de retourner au menu principal"
        boutonr.init_icone
        boutonr.set_font("couleur/blanc.png")

        this.add(rangee_bouton_1)
        this.add(rangee_bouton_2)
        if (p.page == 0) {
            if (p.nb_distinct_items > 4) {
                rangee_retour.add(bouton4)
            }
        }
        else {
            rangee_retour.remove(boutonr)
            rangee_retour.add(bouton5)
            rangee_retour.add(boutonr)
            if (p.nb_distinct_items > 4 * (p.page + 1)) {
                rangee_retour.add(bouton4)
            }
        }
        this.add(rangee_retour)

        this.updateUI

        choix_menu = -2

        while (choix_menu == -2) {
            Thread.sleep(100)
        }

        this.remove(rangee_bouton_1)
        this.remove(rangee_bouton_2)
        this.remove(rangee_retour)
        rangee_retour.remove(bouton4)
        rangee_retour.remove(bouton5)
        rangee_bouton_3.add(bouton4)
        rangee_bouton_3.add(bouton5)

        vider(bouton_enleve)
        
        choix_menu
    }

    var label_list = Array(new Label("label0"), new Label("label1"), new Label("label2"), new Label("label3"), new Label("label4"), new Label("label4"), new Label("label5"))

    def ajouter_menu_pokedex () = {

        Fenetre.requestFocus
        
        Fenetre.msgbox.save = "Bienvenue dans le Pokédex"

        for (i <- 1 to (6 - Pokedex.nb_boutons + Pokedex.current_bouton)) {
            label_list(i-1).setText(Pokedex.liste_pokemon(Pokedex.current_pokemon + i + Pokedex.nb_boutons - Pokedex.current_bouton - 7).species_name)
            label_list(i-1).setBackground(Color.WHITE)
            this.add(label_list(i-1))
        }
        for (i <- 0 to (Pokedex.nb_boutons - Pokedex.current_bouton -1)) {
            label_list(Pokedex.current_bouton + i).setText(Pokedex.liste_pokemon(Pokedex.current_pokemon + i).species_name)
            if (i == 0) {
                label_list(Pokedex.current_bouton).setBackground(Color.CYAN)
            }
            else {
                label_list(Pokedex.current_bouton + i).setBackground(Color.WHITE)
            }
            this.add(label_list(Pokedex.current_bouton + i))
        }


        bouton4.setText("Suivant")
        bouton4.info = "Affiche la page suivante"
        bouton4.init_icone
        bouton4.set_font("couleur/blanc.png")
        bouton5.setText("Précédent")
        bouton5.info = "Affiche la page précédente"
        bouton5.init_icone
        bouton5.set_font("couleur/blanc.png")
        boutonr.setText("Retour")
        boutonr.info = "Retour menu principal"
        boutonr.init_icone
        boutonr.set_font("couleur/blanc.png")

        rangee_retour.remove(boutonr)
        rangee_retour.add(bouton5)
        rangee_retour.add(boutonr)
        rangee_retour.add(bouton4)

        this.add(rangee_retour)

        this.updateUI
    }

    def refresh_menu_pokedex () = {

        Fenetre.requestFocus

        for (i <- 1 to (6 - Pokedex.nb_boutons + Pokedex.current_bouton)) {
            label_list(i-1).setText(Pokedex.liste_pokemon(Pokedex.current_pokemon + i + Pokedex.nb_boutons - Pokedex.current_bouton - 7).species_name)
        }
        for (i <- 0 to (Pokedex.nb_boutons - Pokedex.current_bouton -1)) {
            label_list(Pokedex.current_bouton + i).setText(Pokedex.liste_pokemon(Pokedex.current_pokemon + i).species_name)
        }
        label_list(Pokedex.current_bouton).setBackground(Color.CYAN)

        choix_menu = -2

        while (choix_menu == -2) {
            Thread.sleep(100)
        }

        label_list(Pokedex.current_bouton).setBackground(Color.WHITE)

        choix_menu
    }

    def enlever_menu_pokedex () = {

        rangee_retour.remove(bouton4)
        rangee_retour.remove(bouton5)
        rangee_bouton_3.add(bouton4)
        rangee_bouton_3.add(bouton5)
        this.removeAll
        this.add(Fenetre.msgbox)

    }
}

class Animation extends Thread {
    override def run : Unit = {
        Fenetre.map.updateUI()
        Fenetre.map.repaint()
        Thread.sleep(10)
    }
}

class AffichageMap extends JPanel {

    var tableau : Area = Jardin_BasDroit
    var p_x : Int = 0
    var p_y : Int = 0
    var dx : Int = 0
    var dy : Int = 0
    var animation : Boolean = false
    var image_animation1 : BufferedImage = null
    var image_animation2 : BufferedImage = null
    var which_frame : Int = 1

    def move_player(d : Direction) : Unit = {
        animation = false
        p_x = Player.x*50
        p_y = Player.y*50
        d match {
            case N => {dx = 0; dy = -5; image_animation1 = ImageIO.read(getClass.getResource(Player.img_nord_marche1)); image_animation2 = ImageIO.read(getClass.getResource(Player.img_nord_marche2))}
            case O => {dx = 5; dy = 0; image_animation1 = ImageIO.read(getClass.getResource(Player.img_est_marche1)); image_animation2 = ImageIO.read(getClass.getResource(Player.img_est_marche2))}
            case E => {dx = -5; dy = 0; image_animation1 = ImageIO.read(getClass.getResource(Player.img_ouest_marche1)); image_animation2 = ImageIO.read(getClass.getResource(Player.img_ouest_marche2))}
            case S => {dx = 0; dy = 5; image_animation1 = ImageIO.read(getClass.getResource(Player.img_sud_marche1)); image_animation2 = ImageIO.read(getClass.getResource(Player.img_sud_marche2))}
        }
        for (i<-1 to 10) {
            var anim = new Animation
            anim.run
        }
        
    }
    
    override def paintComponent (g : Graphics) : Unit = {

        super.paintComponent(g)
        
        if (animation) {
            g.drawImage(ImageIO.read(getClass.getResource(tableau.img)), 0, 0, 750, 500, null)
            for (i <- 0 to 9){
                for (j <- 0 to 14){
                    tableau.tab(j)(i) match {
                        case chara : Character if (!chara.is_main) => {
                            var image_chara = chara.direction match {
                                case N if (chara.is_fishing)=> chara.img_nord_peche
                                case S if (chara.is_fishing)=> chara.img_sud_peche
                                case E if (chara.is_fishing)=> chara.img_ouest_peche
                                case O if (chara.is_fishing)=> chara.img_est_peche
                                case N => chara.img_nord
                                case S => chara.img_sud
                                case E => chara.img_ouest
                                case O => chara.img_est
                            } 
                            g.drawImage(ImageIO.read(getClass.getResource(image_chara)), chara.x*50, chara.y*50, 50, 50, null)
                        }
                        case chara : Character if (chara.is_main) => {
                            p_x += dx
                            p_y += dy
                            if (which_frame == 1) {
                                g.drawImage(image_animation1, p_x, p_y, 50, 50, null)
                            }
                            else {
                                g.drawImage(image_animation2, p_x, p_y, 50, 50, null)
                            }
                            which_frame = 3 - which_frame
                        }
                        case _ => {}
                    }
                }
            }
            animation = false
        }
        else {
            g.drawImage(ImageIO.read(getClass.getResource(tableau.img)), 0, 0, 750, 500, null)
            for (i <- 0 to 9){
                for (j <- 0 to 14){
                    tableau.tab(j)(i) match {
                        case chara : Character => {
                            var image_chara = chara.direction match {
                                case N if (chara.is_fishing)=> chara.img_nord_peche
                                case S if (chara.is_fishing)=> chara.img_sud_peche
                                case E if (chara.is_fishing)=> chara.img_ouest_peche
                                case O if (chara.is_fishing)=> chara.img_est_peche
                                case N => chara.img_nord
                                case S => chara.img_sud
                                case E => chara.img_ouest
                                case O => chara.img_est
                            } 
                            g.drawImage(ImageIO.read(getClass.getResource(image_chara)), chara.x*50, chara.y*50, 50, 50, null)
                        }
                        case _ => {}
                    }
                }
            }
        }
    }

}

class AffichagePokedex extends JPanel {

    this.setFont(new Font("Helvetica Neue", Font.BOLD, 17))
    var mesure = getFontMetrics(new Font("Helvetica Neue", Font.BOLD, 17))
    this.setBackground(Color.BLACK)

    override def paintComponent (g : Graphics) : Unit = {
        super.paintComponent(g)

        g.setColor(Color.CYAN)
        g.drawRect(5, 5, this.getWidth-10, this.getHeight-10)
        g.drawRect(100, 100, 150, 150)
        var c_pok = Pokedex.liste_pokemon(Pokedex.current_pokemon)
        if (Pokedex.encountered(c_pok.id)) {
            g.drawString(c_pok.name, (this.getWidth-mesure.stringWidth(c_pok.name))/2, 50)
            g.fillRect((this.getWidth-mesure.stringWidth(c_pok.name))/2 +5, 50+mesure.getHeight/10, mesure.stringWidth(c_pok.name), 2)
            g.drawString(c_pok.ptype.name, 500, 175)
            g.drawRect(25, 275, 700, 200)
            Func.print_string(c_pok.description, 45, 300, g, mesure)
            g.drawImage(ImageIO.read(getClass.getResource(c_pok.image)), 105, 105, 140, 140, null)
        }
        else {
            g.drawString("unknown name", (this.getWidth-mesure.stringWidth("unknow name"))/2, 50)
            g.fillRect((this.getWidth-mesure.stringWidth("unknown name"))/2 +5, 50+mesure.getHeight/10, mesure.stringWidth("unknown name"), 2)
            g.drawString("unknown type", 500, 175)
            g.drawRect(25, 275, 700, 200)
            Func.print_string("you have to see that pokemon somewhere to complete your pokedex", 45, 300, g, mesure)
            g.drawImage(ImageIO.read(getClass.getResource(c_pok.image_blanche)), 105, 105, 140, 140, null)
        }
        
    }

}

object Fenetre extends JFrame {
    this.setTitle("Best Game Ever")
    this.setSize(750, 1000)
    this.setResizable(false)
    this.setUndecorated(true)
    this.setLocation(100,50)

    override def setFocusable(b : Boolean) = {
        super.setFocusable(b)
    }

    setFocusable(true)
    addKeyListener(Touche)

    var bataille = new AffichageBataille ()

    var info = new AffichagePokedex ()

    var map = new AffichageMap ()

    def changement_map() = {
        map.tableau = Player.current_area
    }

    var msgbox = new MsgBox ()

    var bas_fenetre = new Menu

    var total = new JPanel
    total.setLayout(new GridLayout(2,1))
    total.add(map)
    map.repaint()

    def afficher_bataille () = {
        total.remove(bas_fenetre)
        total.remove(map)
        total.add(bataille)
        total.add(bas_fenetre)
    }

    def afficher_map () = {
        total.remove(bataille)
        total.remove(info)
        total.remove(bas_fenetre)
        total.add(map)
        total.add(bas_fenetre)
        total.repaint()
        this.requestFocus ()
    }

    def afficher_pokedex () = {
        total.remove(bas_fenetre)
        total.remove(map)
        total.add(info)
        total.add(bas_fenetre)
        total.repaint()
    }


    this.setContentPane(total)
    setVisible(true)

}