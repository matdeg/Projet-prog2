class Seenable {}
object Empty_seenable extends Seenable {}

abstract class Direction {def newx( x : Int) = x; def newy(y : Int) = y }
object N extends Direction {override def newx(x : Int) = x;
                            override def newy(y : Int) = y + 1}
object E extends Direction {override def newx(x : Int) = x + 1;
                            override def newy(y : Int) = y}
object S extends Direction {override def newx(x : Int) = x;
                            override def newy(y : Int) = y - 1}
object O extends Direction {override def newx(x : Int) = x - 1;
                            override def newy(y : Int) = y}

abstract class Area {
    var w : Int = 10
    var h : Int = 10
    var tab : Array[Array[Seenable]] = Array.fill(h,w){ Empty_seenable }
}

object Empty_area extends Area {}

object Atrium extends Area {





}

