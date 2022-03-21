object Pokedex {

    var nb_pokemons : Int = 15
    var current_pokemon : Int = 0
    var nb_boutons : Int = 6
    var current_bouton : Int = 0

    var returnn : Boolean = false

    var liste_pokemon : Array[Pokemon] = Array(new Alabri("Alabri"), new Alacool("Alacool"), new Dracarpe("Dracarpe"), new Galopan("Galopan"), new Kokicarpe("Kokicarpe"), new Mcool("Mcool"), new Mherbe("Mherbe"), new Magilangue("Magilangue"), new Poissocarpe("Poissocarpe"), new Poryodin("Poryodin"), new Rhinocarpe("Rhinocarpe"), new Salatard("Salatard"), new Starstar("Starstar"), new Tentapan("Tentapan"), new Torgogo("Torgogo"))

    def next = {
        current_pokemon match {
            case k if (k == nb_pokemons - 1) => {}
            case _ => {
                current_pokemon += 1;
                current_bouton match {
                    case p if (p == nb_boutons - 1) => {}
                    case _ => current_bouton += 1 
                }
            }
        }
    } 

    def previous = {
        current_pokemon match {
            case 0 => {}
            case _ => {
                current_pokemon -= 1;
                current_bouton match {
                    case 0 => {}
                    case _ => current_bouton -= 1 
                }
            }
        }
    } 

    def start : Unit = {
        var in_pokedex = true
        while (in_pokedex) {
            Fenetre.afficher_pokedex()
            var choix = Fenetre.bas_fenetre.print_menu_pokedex()
            choix match {
                case 4 => previous
                case 5 => next
                case -1 => in_pokedex = false
            }
        }
    }
}