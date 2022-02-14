abstract class Item {
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