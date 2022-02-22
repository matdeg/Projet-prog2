abstract class Character(pname : String) extends Seenable {

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

    var pokemons : Array[Pokemon] = new Array[Pokemon](6)

    var money : Int = 0

    var current_battle : Battle = Empty_Battle

    var current_area : Area = Empty_area
    var x : Int = 0
    var y : Int = 0

    var direction : Direction = S

    def move(d : Direction) = {
        current_area.tab(d.newx(x))(d.newy(y)) match {
            case Empty_seenable => x = d.newx(x); y = d.newy(y)
        }
        direction = d
    }

    def init : Unit = {}
}





object Player extends Character(readLine()) {
    is_main = true
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
        current_area.tab(x2)(y2) match {
            case chara : Character => var new_battle = new Battle(this,chara); new_battle.start
        }
    }

    
}






object Empty_character extends Character("") {}
object Example_opponent1 extends Character("Serge") {
    override def init() = {
        Func.give(this,new Poissocarpe("Poissocarpe"))
        Func.give(this,new Salatard("Salatard"))
        Func.give(this,new Poryodin("Poryodin"))
        Func.give(this,new Kokicarpe("Kokicarpe"))
        Func.give(this,new Alabri("Alabri"))
        Func.give(this,new Alacool("Alacool"))
    }
}