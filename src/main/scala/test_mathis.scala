object Test_mathis {

        var choix : Int = -1

        // DEBUT -- initialisation du jeu 
        Player.name = "Mathis la Saucisse"
        Player.init()
        Example_opponent1.init()
        for (i <- 0 to 10) Player.bag(i) = 2
        Jardin_BasDroit.add_character(Player,5,6)
        Jardin_BasDroit.add_character(Example_opponent1,7,7)
        Fenetre.afficher_map ()
        // FIN -- initialisation du jeu

        choix = Fenetre.bas_fenetre.print_menu_base()
        def game() : (Int,Int) = {
            while (!Game.finished) {
                if (Player.in_battle) {
                    var baston = new Battle(Player.opp)
                    Player.current_battle = baston; Player.opp.current_battle = baston
                    baston.start
                }
                else {
                    choix match {
                        case -1 => {}
                        case 0 => Pokedex.start
                        case 1 => Fenetre.msgbox.print_msg("1")
                        case 2 => var (a,choix_sac) = Func.menu_sac()
                                  var choix_item = choix_sac / 6
                                  var choix_pok = choix_sac - choix_item
                                  Player.use_item(Func.id_items(Player.current_items_id(choix_item)),Player.pokemons(choix_pok))
                        case 3 => Fenetre.msgbox.print_msg("3")
                    }
                    Fenetre.afficher_map()
                    choix = -1
                    choix = Fenetre.bas_fenetre.print_menu_base()
                }
                Thread.sleep(100)
            }
            (0,0)
        }
            
}
    