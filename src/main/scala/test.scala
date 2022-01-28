abstract class Vehicle {
    var counter:Double = 0
    def move : Unit
    def brake { println("j’arrête") }
}


class Bicycle(name:String) extends Vehicle{
    def move 
        {
        counter += 1
        println("le compteur de " + name + " est à "+counter)
        }
    override def toString : String = "Name : " + name + " counter : " + counter
    }

class Scooter(name:String) extends Vehicle{
    def move 
        {
        counter += 5
        println("le compteur de " + name + " est à "+counter)
        }
    override def toString : String = "Name : " + name + " counter : " + counter
    }

class RoadBicycle(name:String) extends Bicycle(name) {
    override def move {
        counter += 1.5
        println(name+": ∗roule∗")
    }
}
class RustyBicycle(name:String) extends Bicycle(name) {
    override def brake { println("eek") }
}

abstract class Tree {
    def sum : Int
}

case class Leaf(x:Int) extends Tree{
    override def sum : Int = x
}

case class Node(t1:Tree,t2:Tree) extends Tree{
    override def sum : Int = t1.sum + t2.sum
}

object Program {
    def main(args: Array[String]): Unit = {
        def travel(b:Vehicle,d:Int) = {
            while (b.counter < d){
                b.move
            } 
            b.brake
        }
        val b = new RustyBicycle("Velo 1")
        val c = new RoadBicycle("Velo 2")
        val d = new Scooter("Scooter 1")
        travel(b,10)
        travel(c,2)
        travel(d,50)
        println(Node(Node(Leaf(2),Leaf(3)),Leaf(5)).sum)
    }
}