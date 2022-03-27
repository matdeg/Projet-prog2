import java.nio.channels.SeekableByteChannel

abstract class Direction {def newx( x : Int) = x; def newy(y : Int) = y }
object N extends Direction {override def newx(x : Int) = x;
                            override def newy(y : Int) = y - 1}
object E extends Direction {override def newx(x : Int) = x - 1;
                            override def newy(y : Int) = y}
object S extends Direction {override def newx(x : Int) = x;
                            override def newy(y : Int) = y + 1}
object O extends Direction {override def newx(x : Int) = x + 1;
                            override def newy(y : Int) = y}

// Les revêtement désignent le sol de la map, cela sert par exemple pour savoir si le joueur est sur de l'herbe
class Revetement {}

object Empty_Revetement extends Revetement {}

object Sol_jardin extends Revetement {}

object Herbe extends Revetement {}


// Les Seenable sont tous ce sur quoi on ne peut pas marcher (mur,lac,buisson etc...)
class Seenable {}

object Empty_seenable extends Seenable {}

object Lac extends Seenable {}

object Mur extends Seenable {}

object Buisson extends Seenable {}

class Porte(d : Direction, de : Area) extends Seenable {
    var direction : Direction = d
    var destination : Area = de
}

class Panneau(msg_ : String) extends Seenable {
    var msg : String = msg_
}


abstract class Area {
    var area_nord : Area = Empty_area
    var area_sud : Area = Empty_area
    var area_est : Area = Empty_area
    var area_ouest : Area = Empty_area
    var img = ""
    var background = "background/jardin_flou.png"
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

object Jardin_BasDroit extends Area {
    img = "tableaux/Tableau_BasDroit.png"

    area_nord = Jardin_MilieuDroit
    area_ouest = Jardin_BasMilieu


    Func.draw(tab,Mur,0,9,14,9)
    tab(7)(9) = new Porte(S,Empty_area)
    tab(9)(9) = new Panneau("Bibliothèque")
    Func.draw(tab,Mur,14,0,14,9)
    tab(14)(3) = new Panneau("Kfet")
    tab(14)(5) = new Porte(E,Empty_area)
    tab(14)(6) = new Porte(E,Empty_area)
    for (i <- 0 to 10; if (i <= 2 || 6 <= i)) {
        tab(i)(2) = Buisson
        tab(i)(6) = Buisson
    }
    Func.draw(tab,Buisson,10,3,10,5)

    Func.draw(rev,Sol_jardin,0,0,14,9)
    Func.draw(rev,Herbe,0,2,10,6)
}
object Jardin_BasMilieu extends Area {
    img = "tableaux/Tableau_BasMilieu.png"

    area_nord = Jardin_MilieuMilieu
    area_est = Jardin_BasDroit
    area_ouest = Jardin_BasGauche

    Func.draw(tab,Mur,0,9,14,9)
    tab(7)(9) = new Porte(S,Empty_area)
    tab(9)(9) = new Panneau("Bibliothèque")
    Func.draw(tab,Mur,0,2,0,9)
    for (i <- 4 to 14; if (11 <= i || i <= 7)) {
        tab(i)(2) = Buisson
        tab(i)(6) = Buisson
    }
    Func.draw(tab,Buisson,4,3,4,5)

    Func.draw(rev,Sol_jardin,0,0,14,9)
    Func.draw(rev,Herbe,4,2,14,6)
}

object Jardin_MilieuMilieu extends Area {
    img = "tableaux/Tableau_MilieuMilieu.png"

    area_nord = Jardin_HautMilieu
    area_est = Jardin_MilieuDroit
    area_ouest = Jardin_MilieuGauche
    area_sud = Jardin_BasMilieu

    Func.draw(tab,Buisson,4,0,4,7)
    Func.draw(tab,Buisson,4,7,7,7)
    Func.draw(tab,Buisson,11,7,14,7)
    Func.draw(tab,Lac,5,0,14,2)


    Func.draw(rev,Sol_jardin,0,0,14,9)
    Func.draw(rev,Herbe,4,0,14,7)
    
}

object Jardin_BasGauche extends Area {
    img = "tableaux/Tableau_BasGauche.png"
    area_nord = Jardin_MilieuGauche
    area_est = Jardin_BasMilieu

    Func.draw(tab,Mur,0,0,0,9)
    Func.draw(tab,Mur,0,9,14,9)
    Func.draw(tab,Mur,14,2,14,9)

    Func.draw(tab,Mur,0,2,6,3)
    Func.draw(tab,Mur,8,2,14,3)

    Func.draw(rev,Sol_jardin,0,0,14,9)
}

object Jardin_MilieuGauche extends Area {
    img = "tableaux/Tableau_MilieuGauche.png"
    area_nord = Jardin_HautGauche
    area_est = Jardin_MilieuMilieu
    area_sud = Jardin_BasGauche

    tab(5)(2) = Mur
    tab(5)(5) = Mur
    tab(5)(8) = Mur
    tab(9)(2) = Mur
    tab(9)(5) = Mur
    tab(9)(8) = Mur
    Func.draw(tab,Mur,0,0,0,9)
    Func.draw(rev,Sol_jardin,0,0,14,9)
}

object Jardin_HautGauche extends Area {
    img = "tableaux/Tableau_HautGauche.png"
    area_est = Jardin_HautMilieu
    area_sud = Jardin_MilieuGauche

    tab(5)(2) = Mur
    tab(5)(5) = Mur
    tab(5)(8) = Mur
    tab(9)(2) = Mur
    tab(9)(5) = Mur
    tab(9)(8) = Mur
    Func.draw(tab,Mur,0,0,14,0)
    Func.draw(tab,Mur,0,0,0,9)
    tab(7)(0) = new Porte(N,Empty_area)
    tab(9)(0) = new Panneau("Atrium")

    Func.draw(rev,Sol_jardin,0,0,14,9)
}

object Jardin_HautMilieu extends Area {
    img = "tableaux/Tableau_HautMilieu.png"
    area_est = Jardin_HautDroit
    area_ouest = Jardin_HautGauche
    area_sud = Jardin_MilieuMilieu

    Func.draw(tab,Mur,0,0,14,0)
    Func.draw(tab,Lac,5,7,14,9)

    Func.draw(rev,Sol_jardin,0,0,14,9)
    Func.draw(rev,Herbe,4,6,4,9)
}

object Jardin_HautDroit extends Area {
    img = "tableaux/Tableau_HautDroit.png"
    area_ouest = Jardin_HautMilieu
    area_sud = Jardin_MilieuDroit

    Func.draw(tab,Mur,0,0,14,0)
    Func.draw(tab,Mur,14,0,14,9)

    tab(7)(0) = new Porte(N,Empty_area)
    tab(5)(0) = new Panneau("Labo S.I")

    tab(14)(4) = new Porte(E,Empty_area)
    tab(14)(5) = new Porte(E,Empty_area)
    tab(14)(2) = new Panneau("Je sais pas")

    Func.draw(tab,Lac,0,7,10,9)

    Func.draw(rev,Sol_jardin,0,0,14,9)
    Func.draw(rev,Herbe,11,6,11,9)
}

object Jardin_MilieuDroit extends Area {
    img = "tableaux/Tableau_MilieuDroit.png"
    area_nord = Jardin_HautDroit
    area_ouest = Jardin_MilieuMilieu
    area_sud = Jardin_BasDroit

    Func.draw(tab,Mur,14,0,14,9)

    tab(14)(4) = new Porte(E,Empty_area)
    tab(14)(5) = new Porte(E,Empty_area)
    tab(14)(2) = new Panneau("Je sais pas")

    Func.draw(tab,Lac,0,0,10,2)
    Func.draw(tab,Buisson,10,0,10,7)
    Func.draw(tab,Buisson,7,7,10,7)
    Func.draw(tab,Buisson,0,7,3,7)

    Func.draw(rev,Sol_jardin,0,0,14,9)
    Func.draw(rev,Herbe,0,0,10,7)
}
