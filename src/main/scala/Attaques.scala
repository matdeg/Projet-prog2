abstract class Attaque {
    var dmg : Int = 0
    var debuff_atk : Int = 0
    var debuff_speed : Int = 0
    var debuff_defense : Int = 0
    var buff_atk : Int = 0
    var buff_defense : Int = 0
    var buff_speed : Int = 0
    var precision : Int = 0
    var Atype : Ttype = Scala
    var name : String = ""
    var pstate : (States,Double) = (None_state,0.0)
}

object Axiom_false extends Attaque{
    dmg = 1000
    name = "axiom_false"
    precision = 100
    Atype = Coq
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}
object Scala_vibes extends Attaque{
    dmg = 50
    precision = 100
    name = "scala_vibes"
    Atype = Scala
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 1
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
    pstate = (Freeze,1.0)
}

object Python_vibes extends Attaque{
    dmg = 50
    precision = 95
    name = "python_vibes"
    Atype = Scala
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}

object Ocaml_vibes extends Attaque{
    dmg = 50
    precision = 95
    name = "ocaml_vibes"
    Atype = Scala
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}

object Shit extends Attaque{
    dmg = 0
    precision = 100
    name = "shit"
    Atype = Scala
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
    buff_atk = 0
    buff_defense = 0
    buff_speed = 0
}