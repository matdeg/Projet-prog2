class Seenable {}
object Empty_seenable extends Seenable {}

abstract class Direction {def newx( x : Int) = x; def newy(y : Int) = y }
object N extends Direction {override def newx(x : Int) = x;
                            override def newy(y : Int) = y - 1}
object E extends Direction {override def newx(x : Int) = x - 1;
                            override def newy(y : Int) = y}
object S extends Direction {override def newx(x : Int) = x;
                            override def newy(y : Int) = y + 1}
object O extends Direction {override def newx(x : Int) = x + 1;
                            override def newy(y : Int) = y}

class Revetement {

    var image : String = ""

}

object Empty_Revetement extends Revetement {

    image = "Devant.png"

}

object Brique extends Revetement {

    image = "brique.jpg"

}

object Herbe extends Revetement {

    image = "herbe.jpg"

}

object Lac extends Revetement {

    image = "eau.jpg"

}

object Mur extends Revetement {

    image = "couleur/multicolor.jpeg"

}

object Porte extends Revetement {

    image = "porte.jpeg"

}

abstract class Area {
    var w : Int = 15
    var h : Int = 10
    var rev : Array[Array[Revetement]] = Array.fill(w, h){ Empty_Revetement }
    var tab : Array[Array[Seenable]] = Array.fill(w, h){ Empty_seenable }
    def add_character (p : Character, i : Int, j : Int) = {
        p.current_area = this
        tab(i)(j) = p
        p.x = i
        p.y = j
    }
}

object Empty_area extends Area {}

object Jardin_Kfet extends Area {

    for (i <- 0 to 9) {rev(14)(i) = Mur}
    for (i <- 0 to 5) {
        for (j <- 0 to 8) {
            rev(j)(i) = Herbe
        }
    }

}
