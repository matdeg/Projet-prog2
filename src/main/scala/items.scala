abstract class Item {
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
}

object Empty_item extends Item {}

class Potion extends Item {
    name = "potion"
    img = "items/medicine/potion.png"
    regen = 20
    id = 0
}
class Super_potion extends Item {
    name = "super potion"
    img = "items/medicine/super-potion.png"
    regen = 50
    id = 1
}

class Hyper_potion extends Item {
    name = "hyper potion"
    img = "items/medicine/hyper-potion.png"
    regen = 200
    id = 2
}

class Revive extends Item {
    name = "revive"
    img = "items/medicine/revive.png"
    regen = 0
    revive = 0.5
    id = 3
}

class Max_revive extends Item {
    name = "max revive"
    img = "items/medicine/max-revive.png"
    regen = 0
    revive = 1
    id = 4
}

class Antidote extends Item {
    name = "antidote"
    img = "items/medicine/antidote.png"
    regen = 0
    id = 5
    state_heal = Array(Empoisonnement)
}

class Awakening extends Item {
    name = "awakening"
    img = "items/medicine/awakening.png"
    regen = 0
    id = 6
    state_heal = Array(Sommeil)
}

class Burn_heal extends Item {
    name = "burn-heal"
    img = "items/medicine/burn-heal.png"
    regen = 0
    id = 7
    state_heal = Array(Burned)
}

class Ice_heal extends Item {
    name = "ice heal"
    img = "items/medicine/ice-heal.png"
    regen = 0
    id = 8
    state_heal = Array(Freeze)
}

class Paralyze_heal extends Item {
    name = "paralyze heal"
    img = "items/medicine/paralyze-heal.png"
    regen = 0
    id = 9
    state_heal = Array(Paralysie)
}

class Full_heal extends Item {
    name = "full heal"
    img = "items/medicine/full-heal.png"
    regen = 0
    id = 10
    state_heal = Array(Paralysie,Empoisonnement,Sommeil,Burned,Freeze)
}