object Pokedex {

    var nb_pokemons : Int = 15
    var current_pokemon : Int = 0
    var nb_boutons : Int = 6
    var current_bouton : Int = 0

    var returnn : Boolean = false

    def next = {
        current_pokemon match {
            case n : Int if (n + 1 == nb_pokemons) => {}
            case _ => {
                current_pokemon += 1;
                current_bouton match {
                    case k : Int if (k + 1 == nb_boutons) => {}
                    case _ => current_bouton += 1 
                }
            }
        }
    } 

    def previous = {
        current_pokemon match {
            case 0 => {}
            case _ => {
                current_pokemon += 1;
                current_bouton match {
                    case 0 => {}
                    case _ => current_bouton += 1 
                }
            }
        }
    } 

    def start : Unit = {
        while (!returnn) {
            {}
        }
    }

}