object Func {

    // permet de retrouver un item à partir de son id
    var id_items : Array[Item] = Array(new Potion,new Super_potion,new Hyper_potion,new Revive,new Max_revive,new Antidote,new Awakening,new Burn_heal,new Ice_heal,new Paralyze_heal,new Full_heal)

    // multiplicateur de stat en fonction de l'indice de modification
    def mult_a: Int => Double = {
        case 0 => 1.0
        case 1 => 1.5 
        case 2 => 2.0
        case 3 => 2.5
        case 4 => 3.0
        case 5 => 3.5
        case 6 => 4.0
        case -1 => 0.66
        case -2 => 0.5
        case -3 => 0.4
        case -4 => 0.33
        case -5 => 0.28
        case -6 => 0.25  
        case x => if (x > 6) {4} else {0.25}
    }

    def mult(i : Int) = {
        (Func.mult_a(i)).toFloat
    }
    def max(a : Int,b : Int) ={
        if (a >= b) {a} else {b}
    }
    def min(a : Int,b : Int) ={
        if (a >= b) {b} else {a}
    }

    // Selectionne les indices des occurences non nulles d'un tableau, mais seulement de la start-ème à la end-ème (non incluse) (commence à 0)
    def choose(tab : Array[Int],start : Int, end : Int) = {
        var i = 0
        var vu = 0
        var n = end - start
        var selection = new Array[Int](n)
        for (i <- 0 to n-1) selection(i) = -1
        while (i < tab.length && vu < end) {
            if (tab(i) > 0) {
                if (start <= vu) {
                    selection(vu - start) = i
                }
                vu = vu + 1
            }
            i = i + 1
        }
        selection
    }

    def give(p : Character, pok : Pokemon) = {
                pok.init()
                p.pokemons(p.nb_pokemons) = pok
                pok.maitre = p
                p.nb_pokemons += 1
            }

    def give_as_it_is(p : Character, pok : Pokemon) = {
                p.pokemons(p.nb_pokemons) = pok
                pok.maitre = p
                p.nb_pokemons += 1
            }

    def draw [N](tab : Array[Array[N]], x : N, a : Int, b : Int, c : Int, d : Int) = {
        for (i <- a to c){
            for (j <- b to d) {
                tab(i)(j) = x
            }
        }
    }


    def pokemon_herbe(a : Area) = {
        a match {
            case Jardin_BasDroit => var a = new Salatard("Salatard S"); a.lvl = 1; a.init; a
            case _ => var a = new Salatard("Salatard S2"); a.lvl = 1; a.init; a
        }
    }
}