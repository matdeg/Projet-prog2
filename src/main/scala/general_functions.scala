object Func {
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
}