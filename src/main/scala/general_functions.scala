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
    var id_items : Array[Item] = Array(new Potion,new Super_potion,new Hyper_potion,new Revive,new Max_revive,new Antidote,new Awakening,new Burn_heal,new Ice_heal,new Paralyze_heal,new Full_heal, new Fishing_rod, new Repel, new Chaussures, new Rare_candy)

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

    // renvoie si un objet peut être utiliser dans le contexte actuel,
    // Par exemple, un repel ne peut pas être utilisé en combat, ou une potion si tous les pokémons sont full hp
    def can_be_used(it : Item) = {
        if (!Player.in_battle && it.usable_without_pokemon) {true}
        else {
            var soluce = false
            for (i <- 0 to (Player.nb_alive - 1)) {
                var pok = Player.pokemons(i)
                if (it.is_usable(pok)) {
                    soluce = true
                }
            }
            soluce
        }
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


    // donne un pokémon qui vient d'être créé au joueur (initialise les statistiques)
    def give(p : Character, pok : Pokemon) = {
                pok.init()
                give_as_it_is(p,pok)
            }
        
    def give_as_it_is(p : Character, pok : Pokemon) = {
                p.pokemons(p.nb_pokemons) = pok
                pok.maitre = p
                p.nb_pokemons += 1
            }


    // rempli une portion de tableau (rectangle (a,b) (c,d)) avec l'élément x
    def draw [N](tab : Array[Array[N]], x : N, a : Int, b : Int, c : Int, d : Int) = {
        for (i <- a to c){
            for (j <- b to d) {
                tab(i)(j) = x
            }
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

    def menu_pokemon_after_item_hors_combat(choix_objet : Int) : (Int,Int) = {
        Fenetre.msgbox.print_msg("Choisissez un pokémon :")
        var choix_pokemon = -2
        var item_id = Player.current_items_id(choix_objet)
        var item = Func.id_items(item_id)
        choix_pokemon = Fenetre.bas_fenetre.print_menu_pokemon(Player)
        choix_pokemon match {
            case -1 => menu_sac_hors_combat
            case i if (!item.is_usable(Player.pokemons(i))) => {
                        Fenetre.msgbox.print_msg("Cet objet n'est pas utilisable sur ce pokémon"); Thread.sleep(1500)
                        menu_pokemon_after_item_hors_combat(choix_objet)
                      }
            case i => (2,choix_objet * 6 + choix_pokemon)
        }
    }

    def menu_pokemon_hors_combat () = {
        Fenetre.msgbox.print_msg("Choisissez deux pokémons :")
        var choix_pokemon = -2
        choix_pokemon = Fenetre.bas_fenetre.print_menu_pokemon(Player)
        choix_pokemon match {
            case -1 => {}
            case i => {
                choix_pokemon = -2
                choix_pokemon = Fenetre.bas_fenetre.print_menu_pokemon(Player)
                choix_pokemon match {
                    case -1 => {}
                    case j if (i != j && Player.pokemons(i).name != "" && Player.pokemons(j).name != "") => {
                        var res = Player.pokemons(i)
                        Player.pokemons(i) = Player.pokemons(j)
                        Player.pokemons(j) = res
                    }
                    case j => {}
                }
            }
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

    // Pareil mais en dehors des combats 
    def menu_sac_hors_combat() : (Int, Int) = {
        Fenetre.msgbox.print_msg("Choisissez un objet :")
        var choix_objet = -2
        choix_objet = Fenetre.bas_fenetre.print_menu_objet(Player)
        choix_objet match {
            case -1 => (-1,0)
            case 4 => {Player.next_page; menu_sac_hors_combat}
            case 5 => {Player.back_page; menu_sac_hors_combat}
            case _ => {
                if (id_items(Player.current_items_id(choix_objet)).usable_without_pokemon) {
                    (2,choix_objet * 6)
                }
                else {
                    menu_pokemon_after_item_hors_combat(choix_objet)
                }
            }
        }
    }

    // echange les pokémons i et j du joueur p
    def echange(p : Character, i : Int, j : Int) = {
        var pok_tampon = p.pokemons(i)
        p.pokemons(i) = p.pokemons(j)
        p.pokemons(j) = pok_tampon
    }

    // détermine un niveau de pokémon sauvage
    def random_level() = {
        var avg = 0
        for (i <- 0 to (Player.nb_pokemons - 1)) {
            avg += Player.pokemons(i).lvl
        }
        avg = avg / Player.nb_pokemons
        var low = max(0,avg - 7)
        var high = min(100,avg - 3)
        r.nextInt(high - low + 1) + low
    }

    // renvoie un pokémon pour la pêche
    def pokemon_lac() = {
        var a = new Poissocarpe("Poissocarpe"); a.lvl = random_level; a.init; a
    }

    // renvoie un pokémon sauvage dépendant de la zone
    def pokemon_herbe(a : Area) = {
        a match {
            case _ => var a = new Salatard("Salatard S"); a.lvl = random_level; a.init; a
        }
    }

    def wait_for_enter = {
        Game.enter = false
        while (!Game.enter) {
            Thread.sleep(10)
        } 
    }

    def echange_pok(p1 : Character,p2 : Character,i1 : Int, i2 : Int) = {
        var tmp = p1.pokemons(i1)
        p1.pokemons(i1) = p2.pokemons(i2)
        p2.pokemons(i2) = tmp
        p1.pokemons(i1).maitre = p1
        p2.pokemons(i2).maitre = p2
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