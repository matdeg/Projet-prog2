import java.awt.dnd.DragSource
abstract class Ttype {

    var name = "Type"
    // fonction définissant les types efficaces ou non
    def affinites(t:Ttype) = 1.0
}

object Feu extends Ttype {
    
    name = "Feu"
    override def affinites(t:Ttype) = {
        t match {
            case Plante => 2.0
            case Glace => 2.0
            case Acier => 2.0
            case Insecte => 2.0
            case Eau => 0.5
            case Feu => 0.5
            case Roche => 0.5
            case Dragon => 0.5
            case _ => 1.0
        }
    }
}

object Eau extends Ttype {

    name = "Eau"
    override def affinites(t:Ttype) = {
        t match {
            case Feu => 2.0
            case Sol => 2.0
            case Roche => 2.0
            case Eau => 0.5
            case Plante => 0.5
            case Dragon => 0.5
            case _ => 1.0
        }
    }
}

object Plante extends Ttype {

    name = "Plante"
    override def affinites(t:Ttype) = {
        t match {
            case Eau => 2.0
            case Sol => 2.0
            case Roche => 2.0
            case Plante => 0.5
            case Feu => 0.5
            case Poison => 0.5
            case Dragon => 0.5
            case Vol => 0.5
            case Insecte => 0.5
            case Acier => 0.5
            case _ => 1.0
        }
    }
}

object Electrik extends Ttype {

    name = "Elektrik"
    override def affinites(t:Ttype) = {
        t match {
            case Eau => 2.0
            case Vol => 2.0
            case Roche => 2.0
            case Electrik => 0.5
            case Plante => 0.5
            case Dragon => 0.5
            case Sol => 0.0
            case _ => 1.0
        }
    }
}

object Glace extends Ttype {

    name = "Glace"
    override def affinites(t:Ttype) = {
        t match {
            case Plante => 2.0
            case Sol => 2.0
            case Dragon => 2.0
            case Vol => 2.0
            case Eau => 0.5
            case Feu => 0.5
            case Glace => 0.5
            case Acier => 0.5
            case _ => 1.0
        }
    }
}

object Combat extends Ttype {

    name = "Combat"
    override def affinites(t:Ttype) = {
        t match {
            case Normal => 2.0
            case Glace => 2.0
            case Roche => 2.0
            case Tenebres => 2.0
            case Acier => 2.0
            case Poison => 0.5
            case Vol => 0.5
            case Psy => 0.5
            case Insecte => 0.5
            case Spectre => 0.0
            case _ => 1.0
        }
    }
}

object Poison extends Ttype {

    name = "Poison"
    override def affinites(t:Ttype) = {
        t match {
            case Normal => 2.0
            case Poison => 0.5
            case Sol => 0.5
            case Roche => 0.5
            case Spectre => 0.5
            case Acier => 0.0
            case _ => 1.0
        }
    }
}

object Sol extends Ttype {

    name = "Sol"
    override def affinites(t:Ttype) = {
        t match {
            case Feu => 2.0
            case Electrik => 2.0
            case Poison => 2.0
            case Roche => 2.0
            case Acier => 2.0
            case Plante => 0.5
            case Insecte => 0.5
            case Vol => 0.0
            case _ => 1.0
        }
    }
}

object Vol extends Ttype {

    name = "Vol"
    override def affinites(t:Ttype) = {
        t match {
            case Plante => 2.0
            case Combat => 2.0
            case Insecte => 2.0
            case Roche => 0.5
            case Acier => 0.5
            case Electrik => 0.5
            case _ => 1.0
        }
    }
}

object Psy extends Ttype {

    name = "Psy"
    override def affinites(t:Ttype) = {
        t match {
            case Poison => 2.0
            case Combat => 2.0
            case Insecte => 0.5
            case Acier => 0.5
            case Tenebres => 0.0
            case _ => 1.0
        }
    }
}

object Insecte extends Ttype {

    name = "Insecte"
    override def affinites(t:Ttype) = {
        t match {
            case Plante => 2.0
            case Psy => 2.0
            case Tenebres => 2.0
            case Feu => 0.5
            case Combat => 0.5
            case Poison => 0.5
            case Vol => 0.5
            case Spectre => 0.5
            case Acier => 0.5
            case _ => 1.0
        }
    }
}

object Roche extends Ttype {

    name = "Roche"
    override def affinites(t:Ttype) = {
        t match {
            case Feu => 2.0
            case Glace => 2.0
            case Vol => 2.0
            case Insecte => 2.0
            case Combat => 0.5
            case Sol => 0.5
            case Acier => 0.5
            case _ => 1.0
        }
    }
}

object Spectre extends Ttype {

    name = "Roche"
    override def affinites(t:Ttype) = {
        t match {
            case Psy => 2.0
            case Spectre => 2.0
            case Tenebres => 0.5
            case Acier => 0.5
            case Normal => 0.0
            case _ => 1.0
        }
    }
}

object Dragon extends Ttype {

    name = "Dragon"
    override def affinites(t:Ttype) = {
        t match {
            case Dragon => 2.0
            case Acier => 0.5
            case _ => 1.0
        }
    }
}

object Tenebres extends Ttype {

    name = "Tenebres"
    override def affinites(t:Ttype) = {
        t match {
            case Psy => 2.0
            case Spectre => 2.0
            case Tenebres => 0.5
            case Acier => 0.5
            case Combat => 0.5
            case _ => 1.0
        }
    }
}

object Acier extends Ttype {

    name = "Acier"
    override def affinites(t:Ttype) = {
        t match {
            case Roche => 2.0
            case Glace => 2.0
            case Feu => 0.5
            case Acier => 0.5
            case Eau => 0.5
            case _ => 1.0
        }
    }
}


object Normal extends Ttype {

    name = "Normal"
    override def affinites(t:Ttype) = {
        t match {
            case Roche => 0.5
            case Acier => 0.5
            case Spectre => 0
            case _ => 1.0
        }
    }
}