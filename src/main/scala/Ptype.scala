import java.awt.dnd.DragSource
abstract class Ttype {
    // fonction définissant les types efficaces ou non
    def affinites(t:Ttype) = 1.0
}

object Feu extends Ttype {
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
    override def affinites(t:Ttype) = {
        t match {
            case Dragon => 2.0
            case Acier => 0.5
            case _ => 1.0
        }
    }
}

object Tenebres extends Ttype {
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
    override def affinites(t:Ttype) = {
        t match {
            case Roche => 0.5
            case Acier => 0.5
            case Spectre => 0
            case _ => 1.0
        }
    }
}