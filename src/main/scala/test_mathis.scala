object Test_mathis {

        def start()=
            Player.name = "Mathis la Saucisse"
            Player.init()
            Example_opponent1.init()
            for (i <- 0 to 10) Player.bag(i) = 2
            Jardin_BasDroit.add_character(Player,5,6)
            Jardin_BasDroit.add_character(Example_opponent1,7,7)
            Fenetre.map.repaint()
            while (!Game.finished) {
                if (Player.in_battle) {
                    var baston = new Battle(Player, Player.opp)
                    baston.start
                }
                Thread.sleep(100)
            }
            
}
    