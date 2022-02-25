import java.nio.channels.SeekableByteChannel
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
}

object Empty_Revetement extends Revetement {
}

object Sol_jardin extends Revetement {
}

object Herbe extends Revetement {
}

object Lac extends Seenable {

}

object Mur extends Seenable {
}

object Buisson extends Seenable {

}

class Porte(d : Direction, de : Area) extends Seenable {
    var direction : Direction = d
    var destination : Area = de
}

class Panneau(msg : String) extends Seenable {

}

abstract class Area {
    var area_nord : Area = Empty_area
    var area_sud : Area = Empty_area
    var area_est : Area = Empty_area
    var area_ouest : Area = Empty_area
    var img = ""
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
    img = "tableaux/Tableau_BasDroit.png"

    area_nord = Jardin_MilieuDroit
    
    for (i <- 0 to 14) {
        tab(i)(9) = Mur
    }
    tab(7)(9) = new Porte(S,Empty_area)
    tab(9)(9) = new Panneau("Bibliothèque")
    for (j <- 0 to 9) {
        tab(14)(j) = Mur
    }
    tab(14)(3) = new Panneau("Kfet")
    tab(14)(5) = new Porte(E,Empty_area)
    tab(14)(6) = new Porte(E,Empty_area)
    for (i <- 0 to 10; if (i <= 2 || 6 <= i)) {
        tab(i)(2) = Buisson
        tab(i)(6) = Buisson
    }
    for (j <- 3 to 5) {
        tab(10)(j) = Buisson
    }
}
object Jardin_MilieuDroit extends Area {
    img = "tableaux/Tableau_MilieuDroit.png"
}
