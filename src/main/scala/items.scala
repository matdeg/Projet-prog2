abstract class Item {
    var id : Int = 0
    var name : String = ""
    var img : String = ""
    var regen : Int = 0
    var buff_atk : Int = 0
    var buff_defense : Int = 0
    var buff_speed : Int = 0
    var held : Boolean = false
    var held_atk : Int = 0
    var held_defense : Int = 0
    var held_speed : Int = 0
}

object Empty_item extends Item {}

class Potion extends Item {
    name = "potion"
    regen = 20
    id = 0
}

class Potion1 extends Item {
    name = "potion"
    regen = 20
    id = 1
}
class Potion2 extends Item {
    name = "potion"
    regen = 20
    id = 2
}
class Potion3 extends Item {
    name = "potion"
    regen = 20
    id = 3
}
class Potion4 extends Item {
    name = "potion"
    regen = 20
    id = 4
}
class Potion5 extends Item {
    name = "potion"
    regen = 20
    id = 5
}
class Potion6 extends Item {
    name = "potion"
    regen = 20
    id = 6
}
class Potion7 extends Item {
    name = "potion"
    regen = 20
    id = 7
}
class Potion8 extends Item {
    name = "potion"
    regen = 20
    id = 8
}