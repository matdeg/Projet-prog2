
object test_mathis {
        def start()={
            def give(p : Character, pok : Pokemon) = {
                p.pokemons(p.nb_pokemons) = pok
                pok.maitre = p
                p.nb_pokemons += 1
            }
            var op1 = new Opponent("Gaspard le Canard")
            var pl = new Player("Mathis la saucisse")
                
            Func.give(op1,new Poissocarpe("Poissocarpe"))
            Func.give(op1,new Salatard("Salatard"))
            Func.give(op1,new Poryodin("Poryodin"))
            Func.give(op1,new Kokicarpe("Kokicarpe"))
            Func.give(op1,new Alabri("Alabri"))
            Func.give(op1,new Alacool("Alacool"))

            Func.give(pl,new Dracarpe("Dracarpe"))
            Func.give(pl,new Poissocarpe("Poissocarpe"))
            Func.give(pl,new Rhinocarpe("Rhinocarpe"))
            Func.give(pl,new Galopan("Galopan"))
            Func.give(pl,new Dracarpe("Dracarpe"))
            Func.give(pl,new Mherbe("Mherbe"))

            for (i <- 0 to 10) pl.bag(i) = 2

            var battle1 = new Battle(pl,op1)
            battle1.start
        }
    }