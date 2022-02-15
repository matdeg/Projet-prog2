
object test_mathis {
        def start()={
            def give(p : Character, pok : Pokemon) = {
                p.pokemons(p.nb_pokemons) = pok
                pok.maitre = p
                p.nb_pokemons += 1
            }
            var op1 = new Opponent("Gaspard le Canard")
            var pl = new Player("Mathis la saucisse")
                
            give(op1,new Poissocarpe("Poissocarpe"))
            give(op1,new Salatard("Salatard"))
            give(op1,new Poryodin("Poryodin"))
            give(op1,new Kokicarpe("Kokicarpe"))
            give(op1,new Alabri("Alabri"))
            give(op1,new Alacool("Alacool"))

            give(pl,new Dracarpe("Dracarpe"))
            give(pl,new Poissocarpe("Poissocarpe"))
            give(pl,new Rhinocarpe("Rhinocarpe"))
            give(pl,new Galopan("Galopan"))
            give(pl,new Dracarpe("Dracarpe"))
            give(pl,new Mherbe("Mherbe"))

            var battle1 = new Battle(pl,op1)
            battle1.start
        }
    }