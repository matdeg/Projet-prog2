object Test_mathis {

        var choix : Int = -1

        // DEBUT -- initialisation du jeu
        Player.init()
        Louis.init()
        Schwoon.init()
        Chatain.init()
        Goubault.init()
        Jardin_MilieuDroit.add_character(Chatain,5,7)
        Jardin_BasDroit.add_character(Player,5,6)
        Jardin_BasDroit.add_character(Louis,7,7)
        Jardin_MilieuDroit.add_character(Schwoon,6,7)
        Jardin_MilieuDroit.add_character(Goubault,4,8)
        Fenetre.afficher_map ()
        // FIN -- initialisation du jeu

        choix = Fenetre.bas_fenetre.print_menu_base()

        def game() = {
            while (!Game.finished) {

                // si le  joueur a rencontré un adversaire, on lance un combat
                if (Player.in_battle) {
                    var baston = new Battle(Player.opp)
                    Player.current_battle = baston; Player.opp.current_battle = baston
                    var remember = Player.opp
                    baston.start
                    choix = Fenetre.bas_fenetre.print_menu_base()
                }

                else {

                    if (Player.is_fishing) {
                            Player.fish()
                        }

                    else {

                        // si le joueur se balade encore, on traite ses choix du menu (sac,sauvegarde,pokedex,pokemon)
                        choix match {
                            case -2 => {}
                            case -1 => {}
                            case 0 => Pokedex.start
                            case 1 => Func.menu_pokemon_hors_combat()
                            case 2 => {
                                var (a,choix_sac) = Func.menu_sac_hors_combat()
                                var choix_item = choix_sac / 6
                                var choix_pok = choix_sac - 6 * choix_item
                                if (a != -1) {
                                    Player.use_item(Func.id_items(Player.current_items_id(choix_item)),Player.pokemons(choix_pok))
                                }
                            }
                            case 3 => Fenetre.msgbox.print_msg("3")
                        }
                        Fenetre.afficher_map()
                        choix = Fenetre.bas_fenetre.print_menu_base()
                    }
                }
                Thread.sleep(100)
            }
        }
            
}
    