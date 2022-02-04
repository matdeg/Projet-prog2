abstract class Attaque {
    var dmg : Int = 0
    var debuff_atk : Int = 0
    var debuff_speed : Int = 0
    var debuff_defense : Int = 0
    var precision : Int = 0
    var Atype : ttype = new scala
    var name : String = ""
}

class axiom_false extends Attaque{
    dmg = 999999
    name = "axiom_false"
    precision = 50
    Atype = new coq
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
}

class scala_vibes extends Attaque{
    dmg = 50
    precision = 95
    name = "scala_vibes"
    Atype = new scala
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
}

class python_vibes extends Attaque{
    dmg = 50
    precision = 95
    name = "python_vibes"
    Atype = new scala
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
}

class ocaml_vibes extends Attaque{
    dmg = 50
    precision = 95
    name = "ocaml_vibes"
    Atype = new scala
    debuff_atk = 0
    debuff_speed = 0
    debuff_defense = 0
}