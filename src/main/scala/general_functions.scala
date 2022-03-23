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
import java.awt.FontMetrics

object Func {

    val r = scala.util.Random;

    // permet de retrouver un item à partir de son id
    var id_items : Array[Item] = Array(new Potion,new Super_potion,new Hyper_potion,new Revive,new Max_revive,new Antidote,new Awakening,new Burn_heal,new Ice_heal,new Paralyze_heal,new Full_heal)

    // multiplicateur de stat en fonction de l'indice de modification
    def mult_a: Int => Double = {
        case 0 => 1.0
        case 1 => 1.5 
        case 2 => 2.0
        case 3 => 2.5
        case 4 => 3.0
        case 5 => 3.5
        case 6 => 4.0
        case -1 => 0.66
        case -2 => 0.5
        case -3 => 0.4
        case -4 => 0.33
        case -5 => 0.28
        case -6 => 0.25  
        case x => if (x > 6) {4} else {0.25}
    }

    def max(a : Int,b : Int) ={
        if (a >= b) {a} else {b}
    }
    def min(a : Int,b : Int) ={
        if (a >= b) {b} else {a}
    }

    // Selectionne les indices des occurences non nulles d'un tableau, mais seulement de la start-ème à la end-ème (non incluse) (commence à 0)
    def choose(tab : Array[Int],start : Int, end : Int) = {
        var i = 0
        var vu = 0
        var n = end - start
        var selection = new Array[Int](n)
        for (i <- 0 to n-1) selection(i) = -1
        while (i < tab.length && vu < end) {
            if (tab(i) > 0) {
                if (start <= vu) {
                    selection(vu - start) = i
                }
                vu = vu + 1
            }
            i = i + 1
        }
        selection
    }

    def give(p : Character, pok : Pokemon) = {
                pok.init()
                p.pokemons(p.nb_pokemons) = pok
                pok.maitre = p
                p.nb_pokemons += 1
            }

    def give_as_it_is(p : Character, pok : Pokemon) = {
                p.pokemons(p.nb_pokemons) = pok
                pok.maitre = p
                p.nb_pokemons += 1
            }

    def draw [N](tab : Array[Array[N]], x : N, a : Int, b : Int, c : Int, d : Int) = {
        for (i <- a to c){
            for (j <- b to d) {
                tab(i)(j) = x
            }
        }
    }

    def pokemon_herbe(a : Area) = {
        a match {
            case Jardin_BasDroit => var a = new Salatard("Salatard S"); a.lvl = 1; a.init; a
            case _ => var a = new Salatard("Salatard S2"); a.lvl = 1; a.init; a
        }
    }

    // lance le menu de choix de pokémon sans retour en arrière possible
    def menu_pokemon_force() : Unit = {
        Fenetre.msgbox.print_msg("Veuillez choisir un pokémon :")
        Player.ip = Fenetre.bas_fenetre.print_menu_pokemon(Player) 
        Player.ip match {
            case -1 => menu_pokemon_force()
            case i if (!Player.pokemons(i).alive) => {
                Fenetre.msgbox.print_msg("Ce pokémon n'est pas apte à retourner au combat, veuillez choisir un pokémon"); Thread.sleep(800)
                menu_pokemon_force()
            }
            case _ => {}
        }
    }

    // lance le menu principal, attaque, pokemons, sac, fuite
    def menu_principal() : (Int,Int) = {
        Fenetre.msgbox.print_msg("Que voulez vous faire ?")
        var choix_menu = -2
        choix_menu = Fenetre.bas_fenetre.print_menu_base
        choix_menu match {
            case 0 => menu_attaque()
            case 1 => menu_pokemon()
            case 2 => menu_sac()
            case 3 => (3, 0)
            case _ => (0,0)
        }
    }

    // le joueur choisit entre 4 attaques
    def menu_attaque() : (Int,Int) = {
        Fenetre.msgbox.print_msg("Choisissez une attaque :")
        var choix_attaque = -2
        choix_attaque = Fenetre.bas_fenetre.print_menu_attaque(Player.pokemons(Player.ip))
        choix_attaque match {
            case -1 => menu_principal()
            case _ => if (Player.pokemons(Player.ip).pp_list(choix_attaque) == 0) {
                        Fenetre.msgbox.print_msg("Vous ne pouvez plus lancer cette attaque"); Thread.sleep(1500)
                        menu_attaque()
                      }
                      else {
                        (0,choix_attaque)
                      } 
        }
    }

    // le joueur choisit quel pokémon lancer dans le menu
    def menu_pokemon() : (Int,Int) = {
        Fenetre.msgbox.print_msg("Choisissez un pokémon :")
        var choix_pokemon = -2
        choix_pokemon = Fenetre.bas_fenetre.print_menu_pokemon(Player)
        choix_pokemon match {
            case -1 => menu_principal
            case i if (!Player.pokemons(i).alive) => {
                        Fenetre.msgbox.print_msg("Ce pokémon n'est pas apte à retourner au combat"); Thread.sleep(1500)
                        menu_pokemon()
                    }
            case i if (i == Player.ip) => {
                            Fenetre.msgbox.print_msg("Ce pokémon est déjà sur le terrain"); Thread.sleep(1500)
                            menu_pokemon()
                    }
            case i => (1,i) 
        } 
    }

    // le joueur choisit sur quel pokémon utiliser l'objet 
    def menu_pokemon_after_item(choix_objet : Int) : (Int,Int) = {
        Fenetre.msgbox.print_msg("Choisissez un pokémon :")
        var choix_pokemon = -2
        var item_id = Player.current_items_id(choix_objet)
        var item = Func.id_items(item_id)
        choix_pokemon = Fenetre.bas_fenetre.print_menu_pokemon(Player)
        choix_pokemon match {
            case -1 => menu_sac
            case i if (!item.is_usable(Player.pokemons(i))) => {
                        Fenetre.msgbox.print_msg("Cet objet n'est pas utilisable sur ce pokémon"); Thread.sleep(1500)
                        menu_pokemon_after_item(choix_objet)
                      }
            case i => (2,choix_objet * 6 + choix_pokemon)
        }
    }    

    //le joueur choisit un objet, le 2e entier est (6 * id_objet + id_pokemon) selon quel pokémon est choisi pour l'objet
    def menu_sac() : (Int, Int) = {
        Fenetre.msgbox.print_msg("Choisissez un objet :")
        var choix_objet = -2
        choix_objet = Fenetre.bas_fenetre.print_menu_objet(Player)
        choix_objet match {
            case -1 => menu_principal
            case 4 => {Player.next_page; menu_sac}
            case 5 => {Player.back_page; menu_sac}
            case _ => {menu_pokemon_after_item(choix_objet)}
        }
    }

    def menu_sac_hors_combat() : (Int, Int) = {
        Fenetre.msgbox.print_msg("Choisissez un objet :")
        var choix_objet = -2
        choix_objet = Fenetre.bas_fenetre.print_menu_objet(Player)
        choix_objet match {
            case -1 => (-1,0)
            case 4 => {Player.next_page; menu_sac}
            case 5 => {Player.back_page; menu_sac}
            case _ => {menu_pokemon_after_item(choix_objet)}
        }
    }

    // echange les pokémons i et j du joueur p
    def echange(p : Character, i : Int, j : Int) = {
        var pok_tampon = p.pokemons(i)
        p.pokemons(i) = p.pokemons(j)
        p.pokemons(j) = pok_tampon
    }

    def pokemon_lac() = {
        var a = new Poissocarpe("Poissocarpe"); a.lvl = 1; a.init; a
    }


    def print_string(s : String, x : Int, y : Int, g : Graphics, m : FontMetrics) : Unit = {

        var nb_ligne = 1 + (m.stringWidth(s)/(750 - x))
        var dbt = 0
        var hauteur = m.getHeight() + 5
        var new_y = y
        var i = 0
        var last_space = 0

        if (nb_ligne > 1) {
            while (last_space < s.length) {
                while (m.stringWidth(s.substring(dbt, i)) < 700 - x && i < s.length-1) {
                    i += 1
                    if (s(i) == ' ') {
                        last_space = i
                    }
                }
                if (i == s.length - 1) {
                    g.drawString(s.substring(dbt, i+1), x, new_y)
                    last_space = s.length
                }
                else {
                    g.drawString(s.substring(dbt, last_space+1), x, new_y)
                    dbt = last_space+1
                    i = dbt
                    new_y += hauteur
                }
            }
        }
        else {
            g.drawString(s, x, y)
        }
    }
}