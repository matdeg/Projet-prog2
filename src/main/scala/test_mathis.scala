object Test_mathis {

        var choix : Int = -1

        def start()=
            Player.name = "Mathis la Saucisse"
            Player.init()
            Example_opponent1.init()
            for (i <- 0 to 10) Player.bag(i) = 2
            Jardin_BasDroit.add_character(Player,5,6)
            Jardin_BasDroit.add_character(Example_opponent1,7,7)
            Fenetre.afficher_map ()
            choix = Fenetre.bas_fenetre.print_menu_base()
            while (!Game.finished) {
                if (Player.in_battle) {
                    println("baston")
                    var baston = new Battle(Player, Player.opp)
                    baston.start
                }
                else {
                    if (!(choix == -1)) {
                        choix match {
                            case 0 => Fenetre.msgbox.print_msg("0")
                            case 1 => Fenetre.msgbox.print_msg("1")
                            case 2 => Fenetre.msgbox.print_msg("2")
                            case 3 => Fenetre.msgbox.print_msg("3")
                        }
                        Fenetre.afficher_map()
                        choix = -1
                        choix = Fenetre.bas_fenetre.print_menu_base()
                    }
                }
                Thread.sleep(100)
            }
            
}
    