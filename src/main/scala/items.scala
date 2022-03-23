abstract class Item extends Seenable {
    var x : Int = 0
    var y : Int = 0
    var current_area : Area = Empty_area
    var id : Int = 0
    var name : String = ""
    var img : String = ""
    var regen : Int = 0
    var revive : Double = 0
    var buff_atk : Int = 0
    var buff_defense : Int = 0
    var buff_speed : Int = 0
    var held : Boolean = false
    var held_atk : Int = 0
    var held_defense : Int = 0
    var held_speed : Int = 0
    var state_heal : Array[States] = Array()
    var info = ""
    def is_usable(p : Pokemon) : Boolean = {
        p.alive
    } 
}

object Empty_item extends Item {}

class Potion extends Item {
    name = "potion"
    img = "items/medicine/potion.png"
    regen = 20
    id = 0
    info = "Régénère 20 PV"
}
class Super_potion extends Item {
    name = "super potion"
    img = "items/medicine/super-potion.png"
    regen = 50
    id = 1
    info = "Régénère 50 PV"
}

class Hyper_potion extends Item {
    name = "hyper potion"
    img = "items/medicine/hyper-potion.png"
    regen = 200
    id = 2
    info = "Régénère 200 PV"
}

class Revive extends Item {
    name = "revive"
    img = "items/medicine/revive.png"
    revive = 0.5
    id = 3
    info = "Résurrecte le Pokéfusion avec la moitié de sa vie"
    override def is_usable (p : Pokemon) = {
        !p.alive
    }
}

class Max_revive extends Item {
    name = "max revive"
    img = "items/medicine/max-revive.png"
    revive = 1
    id = 4
    info = "Résurrecte le Pokéfusion avec la totalité de sa vie"
    override def is_usable (p : Pokemon) = {
        !p.alive
    }
}

class Antidote extends Item {
    name = "antidote"
    img = "items/medicine/antidote.png"
    id = 5
    state_heal = Array(Empoisonnement)
    info = "Soigne l'empoisonnement"
    override def is_usable (p : Pokemon) = {
        (p.state == Empoisonnement) 
    }
}

class Awakening extends Item {
    name = "awakening"
    img = "items/medicine/awakening.png"
    id = 6
    state_heal = Array(Sommeil)
    info = "Réveille le Pokéfusion"
    override def is_usable (p : Pokemon) = {
        (p.state == Sommeil) 
    }
}

class Burn_heal extends Item {
    name = "burn-heal"
    img = "items/medicine/burn-heal.png"
    id = 7
    state_heal = Array(Burned)
    info = "Soigne la brulure"
    override def is_usable (p : Pokemon) = {
        (p.state == Burned) 
    }
}

class Ice_heal extends Item {
    name = "ice heal"
    img = "items/medicine/ice-heal.png"
    id = 8
    state_heal = Array(Freeze)
    info = "Soigne le gel"
    override def is_usable (p : Pokemon) = {
        (p.state == Freeze) 
    }
}

class Paralyze_heal extends Item {
    name = "paralyze heal"
    img = "items/medicine/paralyze-heal.png"
    id = 9
    state_heal = Array(Paralysie)
    info = "Soigne la paralysie"
    override def is_usable (p : Pokemon) = {
        (p.state == Paralysie) 
    }
}

class Full_heal extends Item {
    name = "full heal"
    img = "items/medicine/full-heal.png"
    id = 10
    state_heal = Array(Paralysie,Empoisonnement,Sommeil,Burned,Freeze)
    info = "Soigne tous les états"
    override def is_usable (p : Pokemon) = {
        (p.state != None_state) 
    }
}

class Fishing_rod extends Item {
    name = "fishing-rod"
    img = "items/key-item/fishing-rod.png"
    id = 11
    info = "Permet de pêcher des carpes"
    override def is_usable (p : Pokemon) = {
        false
    }
}