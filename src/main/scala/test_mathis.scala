object test_mathis {
        def start()=
            Player.name = "Mathis la Saucisse"
            Player.init()
            Example_opponent1.init()
            for (i <- 0 to 10) Player.bag(i) = 2
            Atrium.add_character(Player,5,5)
            Atrium.add_character(Example_opponent1,7,7)
            Player.move(N)
            Player.move(N)
            Example_opponent1.move(O)
            Player.move(E)
            Player.interact()
}
    