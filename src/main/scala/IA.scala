object IA {

    def meilleure_atq_direct(p : Pokemon, q: Pokemon) = {
        var meilleure = 0
        var best_dmg = 0
        for (i <- 0 to 3) {
            var atq = p.attaques(i)
            if (atq.dmg > 0) {
                var dmg_dealt = (((0.4 * p.lvl.toFloat + 2.0) * atq.dmg.toFloat * p.atk.toFloat / (50.0 * q.defense.toFloat) + 3.0)).toInt
                if (dmg_dealt > best_dmg) {
                    best_dmg = dmg_dealt
                    meilleure = i
                }
            }
        }
        (meilleure,best_dmg*p.ptype.affinites(q.ptype))
    }

    def joueur_aime_pokemon(p : Pokemon,q : Pokemon) = {
        var (atq_bot,dmg_bot) = meilleure_atq_direct(q,p) 
        var (atq_joueur, dmg_joueur) = meilleure_atq_direct(p,q)
        var tours_survie_joueur = p.hp / (dmg_bot + 1)
        var nb_coups_battre_bot = (q.hp / dmg_joueur) + 1 
        (tours_survie_joueur,nb_coups_battre_bot) 
    }


    def fonction_gain_attaque_bot(p : Character,k : Int,nb_changement_joueur : Int, loop : Int) = {
        var p_pok = p.pokemons(p.ip)
        var q = Player 
        var q_pok = q.pokemons(q.ip)
        var score = 0.0
        var atq = p_pok.attaques(k)
        var freq_changement = 2.5 * (nb_changement_joueur.toDouble / (loop.toDouble + 1.0)) + 0.1
        
        var p_changement = Array(0.0,0.0,0.0,0.0,0.0,0.0)
        for (i <- 0 to 5) {
            if (q.pokemons(i).alive){
                var (tours_survie,nb_coups_battre_bot) = joueur_aime_pokemon(q.pokemons(i),p_pok)
                var pi : Double = 0.0
                if (i == q.ip) {
                    pi = ((tours_survie + 1).toDouble)/(nb_coups_battre_bot.toDouble)
                }
                else {
                    pi = freq_changement * ((tours_survie).toDouble)/(nb_coups_battre_bot.toDouble)
                }
                p_changement(i) = scala.math.pow(pi,1.7)
            }
        }
        for (i <- 0 to 5) {
            var dmg_dealt = (((0.4 * p_pok.lvl.toDouble + 2.0) * atq.dmg.toDouble * p_pok.atk.toDouble / (50.0 * q.pokemons(i).defense.toDouble) + 3.0) * (atq.atype).affinites(q.pokemons(i).ptype))
            score += dmg_dealt * p_changement(i)
        } 
        for (i <- 0 to 5) {
            if (k == 0) {
                println(p_changement(i))
            }
        }
        println("")
        score
    }

    def choix_attaque(p : Character,nb_changement_joueur : Int, loop : Int) = {
        var best = 0.0
        var atq_i = 0
        for (i <- 0 to 3) {
            var score = fonction_gain_attaque_bot(p,i,nb_changement_joueur, loop)
            if (score > best) {
                best = score
                atq_i = i
            }
        }
        atq_i
    }
}