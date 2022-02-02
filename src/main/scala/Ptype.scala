abstract class ttype {
    def affinites(t:ttype) = 1.0
}

class scala extends ttype {
    override def affinites(t:ttype) =  1.0
}

class coq extends ttype {
    override def affinites(t:ttype) = 1.0
}
