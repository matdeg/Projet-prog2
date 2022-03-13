abstract class Character(pname : String) extends Seenable {

    var opp : Character = Empty_character
    var in_battle = false
    val r = scala.util.Random;
    var img_est = ""
    var img_nord = ""
    var img_ouest = ""
    var img_sud = ""

    // true s'il s'agit du joueur 
    var is_main : Boolean = false

    var name : String = pname
    var nb_pokemons : Int = 0
    var nb_alive : Int = 0

    // indice du pokémon courant
    var ip : Int = 0

    // sac d'objet et page courante dans l'affichage du sac en combat
    var page : Int = 0
    var bag : Array[Int] = new Array[Int](11)

    // donne un tableau qui contient les id d'items de la page courante
    def current_items_id = {Func.choose(bag,page * 4, (page + 1) * 4)}

    def nb_distinct_items = {bag.count(_ > 0)}

    // passe à la page suivante/précédente
    def next_page = {
        if (nb_distinct_items > 4 * (page + 1)) {page += 1}
        else {page = 0} 
    }
    def back_page = {
        if (page == 0) {page = (nb_distinct_items - 1)/4}
        else {page -= 1} 
    }
    def use_item(it : Item, pok : Pokemon) = {
        bag(it.id) -= 1
        Fenetre.msgbox.print_msg(name + " utilise " + it.name)
        Thread.sleep(1500)
        pok.use_item(it)
    }

    var pokemons : Array[Pokemon] = Array (Empty_Pokemon,Empty_Pokemon,Empty_Pokemon,Empty_Pokemon,Empty_Pokemon,Empty_Pokemon)

    var money : Int = 0

    var current_battle : Battle = Empty_Battle

    var current_area : Area = Empty_area
    var x : Int = 0
    var y : Int = 0

    var direction : Direction = S

    def move(d : Direction) = {
        if ((0 <= d.newx(x)  && d.newx(x) < current_area.w) && (0 <= d.newy(y)&& d.newy(y) < current_area.h)) {

            current_area.tab(d.newx(x))(d.newy(y)) match {
            case Empty_seenable => 
                current_area.tab(d.newx(x))(d.newy(y)) = this 
                current_area.tab(x)(y) = Empty_seenable
                x = d.newx(x); y = d.newy(y)

                current_area.rev(x)(y) match {
                    case Herbe => if (r.nextDouble() < 0.1) {
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
        direction = d
    }

    def init : Unit = {}
}





object Player extends Character(readLine()) {
    is_main = true
    
    img_est = "player_est.png"
    img_nord = "player_nord.png"
    img_ouest = "player_ouest.png"
    img_sud = "player_sud.png"
    override def init() = {
        Func.give(this,new Dracarpe("Dracarpe"))
        Func.give(this,new Poissocarpe("Poissocarpe"))
        Func.give(this,new Rhinocarpe("Rhinocarpe"))
        Func.give(this,new Galopan("Galopan"))
        Func.give(this,new Dracarpe("Dracarpe"))
        Func.give(this,new Mherbe("Mherbe"))
    }
    def interact() = {
        var x2 = direction.newx(x)
        var y2 = direction.newy(y)
        if (0 <= x2 && x2 <= 14 && 0 <= y2 && y2 <= 9) {
            current_area.tab(x2)(y2) match {
                case chara : Character => opp = chara; in_battle = true;
                case _ => {}
            }
        }
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
                case Empty_seenable => {current_area.tab(x)(y) = Empty_seenable;
                                        new_area.add_character(Player,newx,newy); 
                                       x = newx; y = newy;
                                       current_area = new_area; Fenetre.changement_map()}
                case _ => {}
            }
        }
        
    }
}


class Nature(a : Area) extends Character("Nature") {
    Func.give(this,Func.pokemon_herbe(a))
}

object Empty_character extends Character("") {}
object Example_opponent1 extends Character("Serge") {

    img_est = "random_guy.png"
    img_nord = "random_guy.png"
    img_ouest = "random_guy.png"
    img_sud = "random_guy.png"
    override def init() = {
        Func.give(this,new Salatard("Salatard"))
    }
}