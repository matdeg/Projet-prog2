import java.awt.dnd.DragSource
abstract class Ttype {

    var name = "Type"
    var image  = "couleur/multicolor.jpeg"
    // fonction définissant les types efficaces ou non
    def affinites(t:Ttype) = 1.0
}

object Acier extends Ttype {

    name = "Acier"
    image = "types/Acier.jpg"
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

object Combat extends Ttype {

    name = "Combat"
    image = "types/Combat.jpg"
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

object Dragon extends Ttype {

    name = "Dragon"
    image = "types/Dragon.png"
    override def affinites(t:Ttype) = {
        t match {
            case Dragon => 2.0
            case Acier => 0.5
            case _ => 1.0
        }
    }
}

object Eau extends Ttype {

    name = "Eau"
    image = "types/Eau.png"
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

object Electrik extends Ttype {

    name = "Elektrik"
    image = "types/Electrik.jpg"
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

object Feu extends Ttype {
    
    name = "Feu"
    image = "types/Feu.jpeg"
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

object Glace extends Ttype {

    name = "Glace"
    image = "types/Glace.png"
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

object Insecte extends Ttype {

    name = "Insecte"
    image = "types/Insecte.png"
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

object Normal extends Ttype {

    name = "Normal"
    image = "types/Normal.jpg"
    override def affinites(t:Ttype) = {
        t match {
            case Roche => 0.5
            case Acier => 0.5
            case Spectre => 0
            case _ => 1.0
        }
    }
}

object Plante extends Ttype {

    name = "Plante"
    image = "types/Plante.jpg"
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

object Poison extends Ttype {

    name = "Poison"
    image = "types/Poison.png"
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

object Psy extends Ttype {

    name = "Psy"
    image = "types/Psy.png"
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

object Roche extends Ttype {

    name = "Roche"
    image = "types/Roche.png"
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

object Sol extends Ttype {

    name = "Sol"
    image = "types/Sol.jpg"
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

object Spectre extends Ttype {

    name = "Roche"
    image = "types/Spectre.jpg"
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

object Tenebres extends Ttype {

    name = "Tenebres"
    image = "types/Tenebres.jpg"
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

object Vol extends Ttype {

    name = "Vol"
    image = "types/Vol.jpeg"
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
