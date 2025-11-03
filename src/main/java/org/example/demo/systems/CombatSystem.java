package org.example.demo.systems;

import org.example.demo.entities.Boss;
import org.example.demo.entities.Monster;
import org.example.demo.entities.Player;

import java.util.Random;

/**
 * Gère la logique de combat : clics, dégâts, critiques, esquive, bouclier.
 */
public class CombatSystem {
    private static final Random RANDOM = new Random();

    private Player player;

    public CombatSystem(Player player) {
        this.player = player;
    }

    /**
     * Résultat d'une attaque (pour affichage)
     */
    public static class AttackResult {
        public final int damage;
        public final boolean isCrit;
        public final boolean monsterDied;
        public final boolean dodged;      // L'attaque a été esquivée
        public final boolean blocked;     // L'attaque a été bloquée par le bouclier

        public AttackResult(int damage, boolean isCrit, boolean monsterDied, boolean dodged, boolean blocked) {
            this.damage = damage;
            this.isCrit = isCrit;
            this.monsterDied = monsterDied;
            this.dodged = dodged;
            this.blocked = blocked;
        }
    }

    /**
     * Effectue une attaque sur le monstre
     */
    public AttackResult attack(Monster monster) {
        // Vérifier si c'est un boss avec capacités spéciales
        if (monster instanceof Boss) {
            Boss boss = (Boss) monster;

            // Vérifier le bouclier d'abord
            if (boss.isShieldActive()) {
                return new AttackResult(0, false, false, false, true);
            }

            // Vérifier l'esquive
            if (boss.tryDodge()) {
                return new AttackResult(0, false, false, true, false);
            }
        }

        // Calcul du crit (utilise les stats totales avec familiers)
        boolean isCrit = RANDOM.nextDouble() * 100 < player.getTotalCritRate();

        // Calcul des dégâts (utilise les stats totales avec familiers)
        int baseDamage = player.getTotalClickDamage();
        int finalDamage = baseDamage;

        if (isCrit) {
            finalDamage = (int) (baseDamage * (player.getTotalCritMultiplier() / 100.0));
        }

        // Application des dégâts
        boolean died = monster.takeDamage(finalDamage);

        return new AttackResult(finalDamage, isCrit, died, false, false);
    }

    /**
     * Applique les DPS du boss au joueur
     */
    public void applyBossDps(Monster monster, double deltaTime) {
        if (monster.isBoss() && !monster.isDead()) {
            // deltaTime en secondes
            int damage = (int) (monster.getDps() * deltaTime);
            if (damage > 0) {
                player.takeDamage(damage);
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
