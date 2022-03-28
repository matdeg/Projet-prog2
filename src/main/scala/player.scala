abstract class Character(pname : String) extends Seenable {

    val r = scala.util.Random;

    // si la joueur est en combat, son adversaire
    var opp : Character = Empty_character
    var in_battle = false

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
    var bag : Array[Int] = new Array[Int](13)

    def has_fishing_rod() = {bag(11) > 0}

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
        if (x > 0.9) {
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
                    case Herbe => if (r.nextDouble() < 0.1) {
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
    }


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
        Func.give(this,new Galopan("Galopan"))
        Func.give(this,new Exceloss("Exceloss"))
        Func.give(this,new Mherbe("Mherbe"))
        for (i <- 0 to 5) {Pokedex.encountered(this.pokemons(i).id) = true} 
        for(i <- bag.indices) {bag(i) = 1}
    }

    // Le joueur bénéficie d'une fonction qui lui permet d'interagir avec l'environnement
    def interact() = {
        var x2 = direction.newx(x)
        var y2 = direction.newy(y)
        // s'il ne regarde pas en bord de map, il intéragit avec ce qu'il y a devant lui
        if (0 <= x2 && x2 <= 14 && 0 <= y2 && y2 <= 9) {
            current_area.tab(x2)(y2) match {
                case chara : Character => {
                        opp = chara
                        Fenetre.bas_fenetre.interruption_menu_map = true
                        in_battle = true
                    }
                case panneau : Panneau => {
                        Fenetre.msgbox.print_msg(panneau.msg)
                        Thread.sleep(1000)
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
object Louis extends Character("louis") {

    file_name = "louis"

    ia = new IA(2,1,1,1,1,1,0,1,2,1,0)
    ia.bot = this
    override def init() = {
        Func.give(this,new Salatard("Salatard"))
        Func.give(this,new Kokicarpe("Kokicarpe"))
        Func.give(this,new Rhinocarpe("Rhinocarpe"))
        for(i <- bag.indices) {
            bag(i) = 1
        }
    }
}

object Schwoon extends Character("Stefan Schwoon") {

    file_name = "scientifique"

    ia = new IA(2,10,1,1,1,1,0.15,1,2,1,0)
    ia.bot = this
    override def init() = {
        Func.give(this,new Salatard("Salatard"))
        Func.give(this,new Rhinocarpe("Rhinocarpe"))
        Func.give(this,new Mherbe("M.herbe"))
        for(i <- bag.indices) {
            bag(i) = 1
        }
    }
}