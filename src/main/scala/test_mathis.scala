
object test_mathis {
        def start()={
            def give(p : Character, pok : Pokemon) = {
                p.pokemons(p.nb_pokemons) = pok
                pok.maitre = p
                p.nb_pokemons += 1
            }
            var op1 = new Opponent("Gaspard le Canard")
            var pl = new Player("Mathis la saucisse")

            give(op1,new Pokemon_test0("Pikachu_op"))
            give(op1,new Pokemon_test0("Miaouss_op"))
            give(op1,new Pokemon_test0("Carapuce_op"))
            give(op1,new Pokemon_test0("Dracaufeu_op"))
            give(op1,new Pokemon_test0("Salamèche_op"))
            give(op1,new Pokemon_test0("Ouisticram_op"))

            give(pl,new Pokemon_test1("Pikachu"))
            give(pl,new Pokemon_test1("Miaouss"))
            give(pl,new Pokemon_test1("Carapuce"))
            give(pl,new Pokemon_test1("Dracaufeu"))
            give(pl,new Pokemon_test1("Salamèche"))
            give(pl,new Pokemon_test1("Ouisticram"))

            var battle1 = new Battle(pl,op1)
            battle1.start
            val r = scala.util.Random; 
            var i1 = 0; var i2 = 0;
            while (!battle1.finished) {
                println("Choisissez une attaque :")
                i1 = readLine().toInt
                i2 = r.nextInt(4)
                battle1.turn(i1,i2)
            }
        }
    }