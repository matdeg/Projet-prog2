object IA {

    def meilleure_atq_direct_selon_joueur(p : Pokemon, q: Pokemon) = {
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
        var (atq_bot,dmg_bot) = meilleure_atq_direct_selon_joueur(q,p) 
        var (atq_joueur, dmg_joueur) = meilleure_atq_direct_selon_joueur(p,q)
        var tours_survie_joueur = p.hp / (dmg_bot + 1)
        var nb_coups_battre_bot = (q.hp / dmg_joueur) + 1 
        (tours_survie_joueur,nb_coups_battre_bot) 
    }

    def fonction_gain_attaque_bot(p : Character,k : Int,f_changement_joueur : Double, loop : Int) = {
        var p_pok = p.pokemons(p.ip)
        var q = Player 
        var q_pok = q.pokemons(q.ip)
        var score = 0.0
        var atq = p_pok.attaques(k)
        var freq_changement = 2.5 * f_changement_joueur
        var p_changement = Array(0.0,0.0,0.0,0.0,0.0,0.0)

        //détermination des scores de changement
        for (i <- 0 to 5) {
            if (q.pokemons(i).alive){
                var (tours_survie_joueur,nb_coups_battre_bot) = joueur_aime_pokemon(q.pokemons(i),p_pok)
                var pi : Double = 0.0                
                if (i == q.ip) {
                    pi = ((tours_survie_joueur + 1).toDouble)/(nb_coups_battre_bot.toDouble)
                }
                else {
                    pi = freq_changement * ((tours_survie_joueur).toDouble)/(nb_coups_battre_bot.toDouble)
                }
                p_changement(i) = scala.math.pow(pi,1.7)
            }
        }

        //détermination des scores de chaque attaque
        for (i <- 0 to 5) {
            if (q.pokemons(i).alive){
            var qi = q.pokemons(i)
            var dmg_dealt = 0.0
            if (atq.dmg > 0) {
                dmg_dealt = (((0.4 * p_pok.lvl.toDouble + 2.0) * atq.dmg.toDouble * p_pok.atk.toDouble / (50.0 * qi.defense.toDouble) + 3.0) * (atq.atype).affinites(qi.ptype))
            }
            var rapport_dmg = (dmg_dealt / qi.hp.toInt) 

            var quotient_vie_restante = (p_pok.hp.toDouble / p_pok.max_hp.toDouble)

            //score associé au buff de défense
            var (atq_bd,p_atq_bd) = atq.buff_defense

            var multiplicateur_buff_def = (Func.mult_a(p_pok.defense_mult + atq_bd) / Func.mult_a(p_pok.defense_mult))
            var score_buff_def = p_atq_bd * scala.math.pow(quotient_vie_restante,2) * (multiplicateur_buff_def - 1.0)

            //score associé au buff d'attaque
            var (atq_ba,p_atq_ba) = atq.buff_atk

            var multiplicateur_buff_atk = (Func.mult_a(p_pok.atk_mult + atq_ba) / Func.mult_a(p_pok.atk_mult))
            var score_buff_atk = p_atq_ba * scala.math.pow(quotient_vie_restante,2) * (multiplicateur_buff_atk - 1.0)

            //score associé au buff de speed
            var (atq_bs,p_atq_bs) = atq.buff_speed

            var multiplicateur_buff_speed_useless = (Func.mult_a(p_pok.speed_mult + atq_bs) / Func.mult_a(p_pok.speed_mult))
            var new_speed = p_pok.speed.toDouble * multiplicateur_buff_speed_useless
            var multiplicateur_buff_speed = 1.0
            if (new_speed.toInt > qi.speed) {
                multiplicateur_buff_speed = 1.5
            }
            var score_buff_speed = p_atq_bs * scala.math.pow(quotient_vie_restante,2) * (multiplicateur_buff_speed - 1.0)

            var quotient_vie_restante_adv = (qi.hp.toDouble/qi.max_hp.toDouble)
            //score associé au debuff de défense
            var (atq_dbd,p_atq_dbd) = atq.debuff_defense

            var multiplicateur_debuff_def = (Func.mult_a(qi.defense_mult - atq_dbd) / Func.mult_a(qi.defense_mult))
            var score_debuff_def = p_atq_dbd * scala.math.pow(quotient_vie_restante_adv,2) * (multiplicateur_debuff_def - 1.0)

            //score associé au debuff d'attaque
            var (atq_dba,p_atq_dba) = atq.debuff_atk

            var multiplicateur_debuff_atk = (Func.mult_a(qi.atk_mult - atq_dba) / Func.mult_a(qi.atk_mult))
            var score_debuff_atk = p_atq_dba * scala.math.pow(quotient_vie_restante_adv,2) * (multiplicateur_debuff_atk - 1.0)
            //if (i == q.ip) {
              //  println("Les stats de l'attaque " + atq.name + " sont : ")
              //  println(rapport_dmg)
              //  println(score_buff_atk)
              //  println(score_buff_def)
              //  println(score_buff_speed)
              //  println(score_debuff_atk)
              //  println(score_debuff_def)  
            //}
            score += p_changement(i) * (rapport_dmg + score_buff_atk + score_buff_def + score_buff_speed + score_debuff_atk + score_debuff_def)
        }
    } 
    
        //println("")

        score
    }

    def choix_attaque(p : Character,f_changement_joueur : Double, loop : Int) = {
        var best = 0.0
        var atq_i = 0
        for (i <- 0 to 3) {
            var score = fonction_gain_attaque_bot(p,i,f_changement_joueur, loop)
            if (score > best) {
                best = score
                atq_i = i
            }
        }
        atq_i
    }

    def danger(p : Character) = {
        var (atq_ind, best_dmg) = meilleure_atq_direct_selon_joueur(Player.pokemons(Player.ip),p.pokemons(p.ip))
        (best_dmg.toInt > p.pokemons(p.ip).hp, best_dmg.toInt)
    }

    def best_move (p : Character,f_changement_joueur : Double, loop : Int) = {
        var (will_die,dmg) = danger(p)
        if (will_die) {
            var best_item = -1 
            var best_page = 0
            var num_on_page : Int = -1
            var score_hp_healed = 0.0
            var hp_to_heal = p.pokemons(p.ip).max_hp - p.pokemons(p.ip).hp
            for {i <- p.bag.indices; if (p.bag(i) > 0 && Func.id_items(i).regen > 0)} {
                num_on_page += 1
                if (num_on_page >= 4) {
                    p.page += 1
                    num_on_page = 0
                } 
                var i_score = Func.min(hp_to_heal,Func.id_items(i).regen).toDouble + (1.0 / Func.id_items(i).regen)
                if (i_score > score_hp_healed) {
                    score_hp_healed = i_score
                    best_item = i
                    best_page = p.page
                }
            }
            if (score_hp_healed.toInt >= dmg) {
                (2,6 * best_item + p.ip)                
            }
            else {
                (0,choix_attaque(p,f_changement_joueur,loop))        
            }
        }
        else {
            (0,choix_attaque(p,f_changement_joueur,loop))
        }
        
        
    }

}