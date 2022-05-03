import scala.collection.mutable.Queue
import scala.collection.mutable.Stack
abstract class Character(pname : String) extends Seenable {

    val r = scala.util.Random;

    // si la joueur est en combat, son adversaire
    var opp : Character = Empty_character
    var in_battle = false

    var ready_to_battle = false

    var fond_de_pile : Int => Unit = (n : Int) => {
        end_battle.push(fond_de_pile)
    }

    var interactions = Stack[Int => Unit](fond_de_pile)
    var end_battle = Stack[Int => Unit](fond_de_pile)

    def say(s : Array[String]) = {
        Game.en_dialogue = true
        var dial = new Dialogue
        for (i <- s) dial.queue.enqueue(i)
        dial.start
    }

    var file_name = ""

    def img_est = "character/" + file_name + "_est.png"
    def img_nord = "character/" + file_name + "_nord.png"
    def img_ouest = "character/" + file_name + "_ouest.png"
    def img_sud = "character/" + file_name + "_sud.png"
    def img_est_peche = "character/" + file_name + "_peche_est.png"
    def img_nord_peche = "character/" + file_name + "_peche_nord.png"
    def img_ouest_peche = "character/" + file_name + "_peche_ouest.png"
    def img_sud_peche = "character/" + file_name + "_peche_sud.png"
    def img_est_marche1 = "character/" + file_name + "_marche_est_1.png"
    def img_nord_marche1 = "character/" + file_name + "_marche_nord_1.png"
    def img_ouest_marche1 = "character/" + file_name + "_marche_ouest_1.png"
    def img_sud_marche1 = "character/" + file_name + "_marche_sud_1.png"
    def img_est_marche2 = "character/" + file_name + "_marche_est_2.png"
    def img_nord_marche2 = "character/" + file_name + "_marche_nord_2.png"
    def img_ouest_marche2 = "character/" + file_name + "_marche_ouest_2.png"
    def img_sud_marche2 = "character/" + file_name + "_marche_sud_2.png"

    
    var is_fishing = false
    def has_carp(n : Int) : Boolean = {
        var b = false
        for (i <- 0 to 5) {
            if (pokemons(i).is_carp && pokemons(i).lvl >= n) {b = true}
        }
        return b
    }

    def get_carp(n : Int) : Int = {
        var b = 0
        for (i <- 0 to 5) {
            if (pokemons(i).is_carp && pokemons(i).lvl >= n) {b = i}
        }
        return b
    }

    var ia = new IA(1,1,1,1,1,1,0,1,1,1,0)

    // true s'il s'agit du joueur 
    var is_main : Boolean = false

    var name : String = pname

    var nb_pokemons : Int = 0
    var nb_alive : Int = 0

    // indice du pokémon courant
    var ip : Int = 0

    // sac d'objet et page courante dans l'affichage du sac en combat
    var page : Int = 0
    var bag : Array[Int] = new Array[Int](Func.id_items.length)

    def has_fishing_rod() = {bag(11) > 0}
    def has_shoes() = {bag(13) > 0}

    // donne un tableau qui contient les id d'items de la page courante
    def current_items_id = {Func.choose(bag,page * 4, (page + 1) * 4)}

    def nb_distinct_items = {bag.count(_ > 0)}

    var pas_repel = 0
    // facteur de probabilité de rencontre d'un pokémon sauvage
    def proba_sauvage = 1.0 / (1.0 + (if (pas_repel > 0) {9.0} else {0.0})) 

    // passe à la page suivante/précédente
    def next_page = {
        if (nb_distinct_items > 4 * (page + 1)) {page += 1}
        else {page = 0} 
    }
    def back_page = {
        if (page == 0) {page = (nb_distinct_items - 1)/4}
        else {page -= 1} 
    }

    // permet d'utiliser un item sur un pokemon (ou sur le joueur selon l'objet)
    def use_item(it : Item, pok : Pokemon) = {
        if (!it.unique) {bag(it.id) -= 1}
        Fenetre.msgbox.print_msg(name + " utilise " + it.name)
        Thread.sleep(1500)
        it.usable_without_pokemon match {
            case true => it.effect
            case _ => pok.use_item(it)
        }
        page = 0
    }

    var pokemons : Array[Pokemon] = Array (Empty_Pokemon,Empty_Pokemon,Empty_Pokemon,Empty_Pokemon,Empty_Pokemon,Empty_Pokemon)

    var money : Int = 0

    var current_battle : Battle = Empty_Battle

    var current_area : Area = Empty_area
    var x : Int = 0
    var y : Int = 0

    var direction : Direction = S

    // permet de pêcher
    def fish() = {
        var x = r.nextDouble
        if (x > 0.95) {
            opp = new Lac_opp; in_battle = true
        }
    }

    // permet de déplacer le joueur, en gérant les cas où il est face à un obstacle ou en bord de map
    def move(d : Direction) = {
        if ((0 <= d.newx(x)  && d.newx(x) < current_area.w) && (0 <= d.newy(y)&& d.newy(y) < current_area.h) && (!is_fishing)) {
            current_area.tab(d.newx(x))(d.newy(y)) match {
            case Empty_seenable =>
                Fenetre.map.move_player(d, x, y)
                current_area.tab(d.newx(x))(d.newy(y)) = this 
                current_area.tab(x)(y) = Empty_seenable
                x = d.newx(x); y = d.newy(y)
                current_area.rev(x)(y) match {
                    case Herbe => if (r.nextDouble() < (0.1 * proba_sauvage)) {
                                    Fenetre.bas_fenetre.interruption_menu_map = true
                                    opp = new Nature(current_area); in_battle = true
                                }
                    case _ => {}
                }
            case it : Item =>  
                current_area.tab(d.newx(x))(d.newy(y)) = this 
                current_area.tab(x)(y) = Empty_seenable
                x = d.newx(x); y = d.newy(y)
            case _ => {}
            }
        }
        if (!is_fishing) direction = d
        pas_repel -= 1
    }

    var nb_badge : Int = 0

    // cette fonction sert à initialiser les valeurs de certains personnages comme leurs pokémons etc... c'est un peu la définition de chaque joueur
    def init : Unit = {}
}





object Player extends Character(readLine()) {
    is_main = true
    
    file_name = "player"
    override def init() = {
        Func.give(this,new Dracarpe("Dracarpe"))
        Func.give(this,new Poissocarpe("Poissocarpe"))
        Func.give(this,new Rhinocarpe("Rhinocarpe"))
        Func.give(this,new Exceloss("Exceloss"))
        for (i <- 0 to 5) {Pokedex.encountered(this.pokemons(i).id) = true} 
        for(i <- bag.indices) {bag(i) = 1}
        bag(11) = 1
        bag(13) = 0
    }

    // Le joueur bénéficie d'une fonction qui lui permet d'interagir avec l'environnement
    def interact() = {
        var x2 = direction.newx(x)
        var y2 = direction.newy(y)
        // s'il ne regarde pas en bord de map, il intéragit avec ce qu'il y a devant lui
        if (0 <= x2 && x2 <= 14 && 0 <= y2 && y2 <= 9) {
            current_area.tab(x2)(y2) match {
                case chara : Character if (chara.ready_to_battle) => {
                        Fenetre.bas_fenetre.interruption_menu_map = true
                        opp = chara
                        in_battle = true
                    }
                case chara : Character => {
                        var x = chara.interactions.pop
                        x(0)
                    }
                case panneau : Panneau => {
                        Fenetre.msgbox.print_msg(panneau.msg)
                    }
                case Lac if ((has_fishing_rod) && !is_fishing) => {
                        Fenetre.bas_fenetre.interruption_menu_map = true
                        is_fishing = true
                    }
                case Lac if (has_fishing_rod) => is_fishing = false
                case _ => {}
            }
        }
        // sinon, si c'est possible (pas d'obstacle de l'autre coté) il change de map
        else {
            var newx : Int = 0
            var newy : Int = 0  
            var new_area : Area = Empty_area  
            (x2,y2) match {
                case (x2,y2) if (x2 < 0) => newx = 14; newy = y; new_area = current_area.area_ouest
                case (x2,y2) if (y2 < 0) => newx = x; newy = 9; new_area = current_area.area_nord
                case (x2,y2) if (x2 > 14) => newx = 0; newy = y; new_area = current_area.area_est
                case (x2,y2) if (y2 > 9) => newx = x; newy = 0; new_area = current_area.area_sud
            }
            new_area.tab(newx)(newy) match {
                case Empty_seenable => {
                        current_area.tab(x)(y) = Empty_seenable;
                        new_area.add_character(Player,newx,newy); 
                        x = newx; y = newy;
                        current_area = new_area; Fenetre.changement_map()
                    }
                case _ => {}
            }
        }
        
    }
}

// Le joueur nature sert pour les combats avec les pokémons sauvages
class Nature(a : Area) extends Character("Nature") {
    Func.give(this,Func.pokemon_herbe(a))
}


// Le joueur Lac_opp sert pour les combats avec les pokémons sauvages du lac
class Lac_opp extends Character("Nature") {
    Func.give(this,Func.pokemon_lac)
}

object Empty_character extends Character("") {}
object Louis extends Character("Louis") {

    file_name = "louis"

    ia = new IA(2,1,1,1,1,1,0,1,2,1,0)
    ia.bot = this

    val inter0 = (n : Int) => {
        say(Array("Coucou je suis Louis","Tiens voilà des chaussures de running de la Kult","Youhou je suis chauve"))
        Player.bag(13) += 1
        ready_to_battle = true
    }

    val inter1 : Int => Unit = (n : Int) => {
        say(Array("tu m'as déjà battu va te faire foutre"))
        interactions.push(inter1)
    }

    val beaten0 = (n : Int) => {
        say(Array("Oh tu es meilleur que moi sal batard","Tiens, voilà de l'argent","Vous avez gagné 1000£","voilà un poisson aussi","Vous avez obtenu un Poissocarpe"))
        Player.money = Player.money + 1000
        var pokk = new Poissocarpe("Poissocarpe")
        pokk.lvl = 14
        Func.give(Player,pokk)
        ready_to_battle = false
    }
    override def init() = {
        Func.give(this,new Rhinocarpe("Rhinocarpe"))
        for(i <- bag.indices) {
            bag(i) = 1
        }
        interactions.push(inter1)
        interactions.push(inter0)
        end_battle.push(beaten0)
    }
}

object Schwoon extends Character("Stefan Schwoon") {

    file_name = "scientifique"
    ready_to_battle = false

    ia = new IA(2,10,1,1,1,1,0.15,1,0,1,0)
    ia.bot = this

    val inter0 : Int => Unit = (n : Int) => {
        if (Player.money < 1000) {
            say(Array("Coucou je suis M.Schwoon","Je suis la première personne que tu dois vaincre","Malheureusement, il te faut 1000 £ pour me combattre"))
            interactions.push(inter0)
        }
        else {
            say(Array("Oh tu as assez d'argent pour me combattre","Reviens me voir quand tu es prêt pour la bagarre"))
            ready_to_battle = true
        }
    }

    val inter1 : Int => Unit = (n : Int) => {
        say(Array("tu m'as déjà battu, il faut que tu ailles voir M.Chatain maintenant"))
        interactions.push(inter1)
    }

    val beaten0 = (n : Int) => {
        say(Array("Bravo tu es super fort, voilà ton premier badge"))
        Player.nb_badge += 1
        ready_to_battle = false
    }
    override def init() = {
        Func.give(this,new Salatard("Salatard"))
        for(i <- bag.indices) {
            bag(i) = 1
        }
        interactions.push(inter1)
        interactions.push(inter0)
        end_battle.push(beaten0)
    }
}

object Chatain extends Character("Thomas Chatain") {

    file_name = "scientifique"
    ready_to_battle = false

    ia = new IA(2,10,1,1,1,1,0.15,1,0,1,0)
    ia.bot = this

    val inter0 : Int => Unit = (n : Int) => {
        if (Player.nb_badge < 1) {
            say(Array("Il te faut vaincre M.Schwoon avant de m'affronter","reviens me voir quand tu seras assez fort"))
            interactions.push(inter0)
        }
        else {
            if (Player.has_carp(15)) {
                say(Array("Oh tu as une belle carpe dis-donc"))
                Func.echange_pok(Player,Chatain,Player.get_carp(15),1)
                say(Array("AHAH, j'ai échangé nos carpes"))
                Thread.sleep(1000)
                Fenetre.bas_fenetre.interruption_menu_map = true
                Player.opp = this
                Player.in_battle = true
                ready_to_battle = true
            }
            else {
                say(Array("Il te faut une carpe de niveau 15 ou plus","reviens me voir quand tu seras assez fort"))
                interactions.push(inter0)
            }
        }
    }

    val inter1 : Int => Unit = (n : Int) => {
        say(Array("tu m'as déjà battu, il faut que tu ailles voir _____ maintenant"))
        interactions.push(inter1)
    }

    val beaten0 = (n : Int) => {
        say(Array("Bravo tu es super fort, voilà ton deuxième badge"))
        Player.nb_badge += 1
        ready_to_battle = false
    }
    override def init() = {
        Func.give(this,new Salatard("Salatard"))
        var poisson_nul = new Kokicarpe("Poissonul")
        poisson_nul.lvl = 1
        Func.give(this,poisson_nul)
        for(i <- bag.indices) {
            bag(i) = 1
        }
        interactions.push(inter1)
        interactions.push(inter0)
        end_battle.push(beaten0)
    }
}

class Dialogue extends Thread {

    var queue : Queue[String] = Queue()

    override def run = {
        while (!queue.isEmpty) {
            Fenetre.msgbox.print_msg(queue.dequeue)
            Func.wait_for_enter
        }
        Fenetre.msgbox.print_msg("")
        Game.en_dialogue = false
    }
}