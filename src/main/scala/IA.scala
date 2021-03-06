// x0 : aime faire des dégats (2 de base)
// x1 : aime buff l'attaque (1 de base)
// x2 : aime buff la défense (1 de base)
// x3 : aime buff la vitesse (1 de base)
// x4 : aime debuff la défense (1 de base)
// x5 : aime debuff l'attaque (1 de base)
// x6 : marge de sécurité lors du choix du soin (0 de base)
// x7 : choisit des attaques peu importe la précision (1 de base)
// x8 : aime lancer des états (3 de base)
// x9 : aime les attaques qui tuent directement l'adversaire
// x10 : visionnaire

class IA(x0 : Double,x1 : Double,x2 : Double,x3 : Double,x4 : Double,x5 : Double, x6 : Double, x7 : Double,x8 : Double, x9 : Double, x10 : Double) {

    var bot : Character = Empty_character
    def f_changement_joueur = bot.current_battle.f1_changement_joueur

    // renvoie la meilleure attaque et les dégats de celle ci de p contre q (au sens des dégats), mais avec connaissance incomplète
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

    // renvoie la meilleure attaque et les dégats de celle ci de p contre q (au sens des dégats)
    def meilleure_atq_direct(p : Pokemon, q: Pokemon) = {
        var meilleure = 0
        var best_dmg = 0 
        for (i <- 0 to 3) {
            var atq = p.attaques(i)
            if (atq.dmg > 0) {
                var dmg_dealt = (((0.4 * p.lvl.toFloat + 2.0) * atq.dmg.toFloat * p.atk.toFloat / (50.0 * q.defense.toFloat) + 3.0) * atq.atype.affinites(q.ptype)).toInt
                if (dmg_dealt > best_dmg) {
                    best_dmg = dmg_dealt
                    meilleure = i
                }
            }
        }
        (meilleure,best_dmg)
    }

    //renvoie le pokémon que le bot peut lancer pour s'assurer de prendre un minimum de dégats contre le pokémon du joueur
    def min_max_degat = {
        var min = 10000
        var imax = 0
        var p_pok = Player.pokemons(Player.ip)
        for (i <- 0 to 5 if bot.pokemons(i).alive) {
            var (i_atq,dmg_atq) = meilleure_atq_direct(p_pok,bot.pokemons(i))
            if (dmg_atq < min) {
                min = dmg_atq 
                imax = i
            } 
        }
        (imax,min) 
    }

    def change_score(i : Int) = {
        var max_score_atq = 0.0
        for (k <- 0 to 3) {
            var score_atq = fonction_gain_attaque_bot(i,k)
            if (score_atq > max_score_atq) {
                max_score_atq = score_atq
            }
        }
        max_score_atq * (if (bot.pokemons(i).speed > Player.pokemons(Player.ip).speed) {2.0} else {1.0})
    }

    def normal_change = {
        var max_score = 0.0
        var i_change = 0
        for (i <- 0 to 5 if (bot.pokemons(i).alive)) {
            var score = change_score(i)
            if (score > max_score) {
                max_score = score
                i_change = i
            }
        }
        i_change
    } 

    //donne un score au pokémon p contre le pokémon q pour le joueur
    def joueur_aime_pokemon(p : Pokemon,q : Pokemon) = {
        var (atq_bot,dmg_bot) = meilleure_atq_direct_selon_joueur(q,p) 
        var (atq_joueur, dmg_joueur) = meilleure_atq_direct_selon_joueur(p,q)
        var tours_survie_joueur = p.hp / (dmg_bot + 1)
        var nb_coups_battre_bot = (q.hp / dmg_joueur) + 1 
        (tours_survie_joueur,nb_coups_battre_bot) 
    }

    //donne un score à l'attaque k du bot
    def fonction_gain_attaque_bot(pok_i : Int, atq_i : Int) = {
        var bot_pok = bot.pokemons(pok_i)
        var q = Player 
        var q_pok = q.pokemons(q.ip)
        var score = 0.0
        var atq = bot_pok.attaques(atq_i)
        var freq_changement = 2.5 * f_changement_joueur
        var p_changement = Array(0.0,0.0,0.0,0.0,0.0,0.0)

        //détermination des scores de changement
        // l'IA estime à quel point le joueur est enclin à changer de pokémon
        for (i <- 0 to 5) {
            var qi = q.pokemons(i)
            if (qi.alive){
                var (tours_survie_joueur,nb_coups_battre_bot) = joueur_aime_pokemon(qi,bot_pok)
                var pi : Double = 0.0                
                if (i == q.ip) {
                    pi = ((tours_survie_joueur + 1).toDouble)/(nb_coups_battre_bot.toDouble)
                }
                else {
                    pi = x10 * (freq_changement + x10) * ((tours_survie_joueur).toDouble)/(nb_coups_battre_bot.toDouble)
                }
                p_changement(i) = scala.math.pow(pi,1.7)
            }
        }

        //détermination des scores de chaque attaque
        for (i <- 0 to 5) {
            var qi = q.pokemons(i)
            if (qi.alive){
            var quotient_vie_restante = (bot_pok.hp.toDouble / bot_pok.max_hp.toDouble)
            var quotient_vie_restante_adv = (qi.hp.toDouble/qi.max_hp.toDouble)


            // score associé aux dégats
            var dmg_dealt = 0.0
            if (atq.dmg > 0) {
                dmg_dealt = (((0.4 * bot_pok.lvl.toDouble + 2.0) * atq.dmg.toDouble * bot_pok.atk.toDouble / (50.0 * qi.defense.toDouble) + 3.0) * (atq.atype).affinites(qi.ptype))
            }
            var prob_atq = (atq.precision.toDouble/100.0)
            var rapport_dmg =  (1 - scala.math.pow((1 - prob_atq),x7)) * (dmg_dealt / qi.hp) 
            if (dmg_dealt.toInt > qi.hp) {
                score += x9
            } 


            //score associé au buff de défense
            var (atq_bd,p_atq_bd) = atq.buff_defense
            var multiplicateur_buff_def = (Func.mult_a(bot_pok.defense_mult + atq_bd) / Func.mult_a(bot_pok.defense_mult))
            var score_buff_def = p_atq_bd * scala.math.pow(quotient_vie_restante,2) * (multiplicateur_buff_def - 1.0)

            //score associé au buff d'attaque
            var (atq_ba,p_atq_ba) = atq.buff_atk
            var multiplicateur_buff_atk = (Func.mult_a(bot_pok.atk_mult + atq_ba) / Func.mult_a(bot_pok.atk_mult))
            var score_buff_atk = p_atq_ba * scala.math.pow(quotient_vie_restante,2) * (multiplicateur_buff_atk - 1.0)

            //score associé au buff de speed
            var (atq_bs,p_atq_bs) = atq.buff_speed
            var multiplicateur_buff_speed_useless = (Func.mult_a(bot_pok.speed_mult + atq_bs) / Func.mult_a(bot_pok.speed_mult))
            var new_speed = bot_pok.speed.toDouble * multiplicateur_buff_speed_useless
            var multiplicateur_buff_speed = 1.0
            if (new_speed.toInt > qi.speed) {
                multiplicateur_buff_speed = 1.5
            }
            var score_buff_speed = p_atq_bs * scala.math.pow(quotient_vie_restante,2) * (multiplicateur_buff_speed - 1.0)
            

            //score associé au debuff de défense
            var (atq_dbd,p_atq_dbd) = atq.debuff_defense
            var multiplicateur_debuff_def = (Func.mult_a(qi.defense_mult - atq_dbd) / Func.mult_a(qi.defense_mult))
            var score_debuff_def = p_atq_dbd * scala.math.pow(quotient_vie_restante_adv,2) * (multiplicateur_debuff_def - 1.0)

            //score associé aux états
            var (state_to_apply,prob_state) = atq.pstate 
            var score_state = 0.0
            if (Player.pokemons(Player.ip).state == None_state) {
                score_state = prob_state * scala.math.pow(quotient_vie_restante_adv,2)
            }

            //score associé au debuff d'attaque
            var (atq_dba,p_atq_dba) = atq.debuff_atk
            var multiplicateur_debuff_atk = (Func.mult_a(qi.atk_mult - atq_dba) / Func.mult_a(qi.atk_mult))
            var score_debuff_atk = p_atq_dba * scala.math.pow(quotient_vie_restante_adv,2) * (multiplicateur_debuff_atk - 1.0)


            score += p_changement(i) * (x0 * rapport_dmg + x1 * score_buff_atk + x2 * score_buff_def + x3 * score_buff_speed + x4 * score_debuff_atk + x5 * score_debuff_def + x8 * score_state)
        }
    } 
        score
    }

    //renvoie la meilleure attaque pour le bot
    def choix_attaque = {
        var best = 0.0
        var atq_i = 0
        for (i <- 0 to 3) {
            var score = fonction_gain_attaque_bot(bot.ip,i)
            if (score > best) {
                best = score
                atq_i = i
            }
        }
        atq_i
    }

    // renvoie un booléen : 'est ce que le bot peut se faire one-shot lors de ce tour ?'
    def danger = {
        var (atq_ind, best_dmg) = meilleure_atq_direct(Player.pokemons(Player.ip),bot.pokemons(bot.ip))
        (best_dmg.toInt > bot.pokemons(bot.ip).hp, best_dmg.toInt)
    }

    // renvoie le meilleur pokémon à envoyer 
    def change_pokemon_urgent = {
        var (imax,dmg) = min_max_degat
        if (imax != bot.ip) {(1,imax)}
        else {(0,choix_attaque)}
    }

    // renvoie un booléen : 'est ce que le bot peut tuer en un coup le pokémon adverse ?'
    def can_kill = {
        var (i_atq,best_dmg) = meilleure_atq_direct(bot.pokemons(bot.ip),Player.pokemons(Player.ip))
        (best_dmg >= Player.pokemons(Player.ip).hp,i_atq)
    }

    // renvoie le coup à faire pour le bot
    def best_move () = {
        var bot_pok = bot.pokemons(bot.ip)
        var player_pok = Player.pokemons(Player.ip)
        var (will_die,dmg) = danger
        var (peut_tuer,i_atq_kill) = can_kill
        if (will_die) {
            var best_num = -1 
            var best_page = 0
            var num_on_page : Int = -1
            var score_hp_healed = 0.0
            var hp_to_heal = bot_pok.max_hp - bot_pok.hp
            for {i <- bot.bag.indices; if (bot.bag(i) > 0 && Func.id_items(i).regen > 0)} {
                num_on_page += 1
                if (num_on_page >= 4) {
                    bot.page += 1
                    num_on_page = 0
                } 
                var i_score = Func.min(hp_to_heal,Func.id_items(i).regen).toDouble + (1.0 / Func.id_items(i).regen)
                if (i_score > score_hp_healed) {
                    score_hp_healed = i_score
                    best_num = num_on_page
                    best_page = bot.page
                }
            }
            if (peut_tuer && bot_pok.speed > player_pok.speed) {
                (0,choix_attaque)
            }
            else {
                if (score_hp_healed.toInt >= (dmg.toDouble + x6 * bot_pok.max_hp).toInt) {
                    (2,6 * best_num + bot.ip)                
                }
                else {
                    if (bot_pok.speed > player_pok.speed) {
                        (0,choix_attaque)  
                    }
                    else {
                        change_pokemon_urgent
                    }     
                }
            }
        }
        else {
            (0,choix_attaque)
        }
        
        
    }

}