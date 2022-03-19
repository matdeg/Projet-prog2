object Test_mathis {

        def menu_sac() : (Int, Int) = {
            Fenetre.msgbox.print_msg("Choisissez un objet :")
            var choix_objet = -2
            choix_objet = Fenetre.bas_fenetre.print_menu_objet(Player)
            choix_objet match {
                case -1 => game()
                case 4 => {Player.next_page; menu_sac}
                case 5 => {Player.back_page; menu_sac}
                case _ => {menu_pokemon_after_item(choix_objet)}
            }
        }

        def menu_pokemon_after_item(choix_objet : Int) : (Int,Int) = {
            Fenetre.msgbox.print_msg("Choisissez un pokémon :")
            var choix_pokemon = -2
            var item_id = Player.current_items_id(choix_objet)
            var item = Func.id_items(item_id)
            choix_pokemon = Fenetre.bas_fenetre.print_menu_pokemon(Player)
            choix_pokemon match {
                case -1 => menu_sac
                case _ => if (!item.is_usable(Player.pokemons(choix_pokemon))) {
                            Fenetre.msgbox.print_msg("Cet objet n'est pas utilisable sur ce pokémon"); Thread.sleep(1500)
                            menu_pokemon_after_item(choix_objet)
                        }
                        else {
                            (2,choix_objet * 6 + choix_pokemon)
                        } 
            }
        } 

        var choix : Int = -1

        Player.name = "Mathis la Saucisse"
        Player.init()
        Example_opponent1.init()
        for (i <- 0 to 10) Player.bag(i) = 2
        Jardin_BasDroit.add_character(Player,5,6)
        Jardin_BasDroit.add_character(Example_opponent1,7,7)
        Fenetre.afficher_map ()
        choix = Fenetre.bas_fenetre.print_menu_base()

        def game() : (Int,Int) = {
            while (!Game.finished) {
                if (Player.in_battle) {
                    println("baston")
                    var baston = new Battle(Player, Player.opp)
                    baston.start
                }
                else {
                    if (choix != -1) {
                        choix match {
                            case 0 => Fenetre.msgbox.print_msg("0")
                            case 1 => Fenetre.msgbox.print_msg("1")
                            case 2 => var (a,choix_sac) = menu_sac()
                                      var choix_item = choix_sac / 6
                                      var choix_pok = choix_sac - choix_item
                                      Player.use_item(Func.id_items(Player.current_items_id(choix_item)),Player.pokemons(choix_pok))
                            case 3 => Fenetre.msgbox.print_msg("3")
                        }
                        Fenetre.afficher_map()
                        choix = -1
                        choix = Fenetre.bas_fenetre.print_menu_base()
                    }
                }
                Thread.sleep(100)
            }
            (0,0)
        }
            
}
    