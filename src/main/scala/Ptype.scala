abstract class Ttype {
    def affinites(t:Ttype) = 1.0
}

object Scala extends Ttype {
    override def affinites(t:Ttype) =  1.0
}

object Coq extends Ttype {
    override def affinites(t:Ttype) = 1.0
}
