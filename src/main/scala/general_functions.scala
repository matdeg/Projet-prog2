object Func {

    var id_items : Array[Item] = Array(new Potion,new Potion1,new Potion2,new Potion3,new Potion4,new Potion5,new Potion6,new Potion7,new Potion8)

    def mult_a: Int => Double = {
        case 0 => 1
        case 1 => 1.5 
        case 2 => 2
        case 3 => 2.5
        case 4 => 3
        case 5 => 3.5
        case 6 => 4
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
}