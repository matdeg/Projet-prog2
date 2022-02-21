object test_mathis {
        def start()=
            Player.name = "Mathis la Saucisse"
            Player.init()
            Example_opponent1.init()
            
            for (i <- 0 to 10) Player.bag(i) = 2

            var battle1 = new Battle(Player,Example_opponent1)
            battle1.start
}
    