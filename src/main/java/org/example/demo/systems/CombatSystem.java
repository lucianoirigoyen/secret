package org.example.demo.systems;

import org.example.demo.entities.Boss;
import org.example.demo.entities.Companion;
import org.example.demo.entities.Monster;
import org.example.demo.entities.Player;

import java.util.Random;

/**
 * Gère la logique de combat : clics, dégâts, critiques, esquive, bouclier.
 * ENHANCED: Companion attacks, 100% crit, AoE, status effects
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

        // NEW: Enhanced crit features
        public final int companionDamage;
        public final String companionAttack;
        public final boolean isAoE;
        public final CritSystem.StatusEffect statusEffect;
        public final boolean companionEvolved;

        public AttackResult(int damage, boolean isCrit, boolean monsterDied, boolean dodged, boolean blocked,
                          int companionDamage, String companionAttack, boolean isAoE,
                          CritSystem.StatusEffect statusEffect, boolean companionEvolved) {
            this.damage = damage;
            this.isCrit = isCrit;
            this.monsterDied = monsterDied;
            this.dodged = dodged;
            this.blocked = blocked;
            this.companionDamage = companionDamage;
            this.companionAttack = companionAttack;
            this.isAoE = isAoE;
            this.statusEffect = statusEffect;
            this.companionEvolved = companionEvolved;
        }
    }

    /**
     * Effectue une attaque sur le monstre (ENHANCED with companion + crit system)
     */
    public AttackResult attack(Monster monster) {
        // Vérifier si c'est un boss avec capacités spéciales
        if (monster instanceof Boss) {
            Boss boss = (Boss) monster;

            // Vérifier le bouclier d'abord
            if (boss.isShieldActive()) {
                return new AttackResult(0, false, false, false, true, 0, null, false, null, false);
            }

            // Vérifier l'esquive
            if (boss.tryDodge()) {
                return new AttackResult(0, false, false, true, false, 0, null, false, null, false);
            }
        }

        // PLAYER ATTACK
        // Calcul du crit (NO MORE 55% CAP! Can go to 100%)
        boolean isCrit = RANDOM.nextDouble() * 100 < player.getTotalCritRate();

        // Calcul des dégâts (utilise les stats totales avec familiers)
        int baseDamage = player.getTotalClickDamage();
        int finalDamage = baseDamage;

        // Enhanced crit system
        boolean isAoE = false;
        CritSystem.StatusEffect statusEffect = null;

        if (isCrit) {
            // Use enhanced crit multiplier (includes post-100% bonuses)
            double enhancedMultiplier = player.getCritSystem().getFinalCritMultiplier(player.getTotalCritMultiplier());
            finalDamage = (int) (baseDamage * (enhancedMultiplier / 100.0));

            // Check for AoE proc (post-100% bonus)
            isAoE = player.getCritSystem().rollAoE();

            // Check for status effect proc (post-100% bonus)
            statusEffect = player.getCritSystem().rollStatusEffect();
        }

        // COMPANION ATTACK
        Companion companion = player.getCompanion();
        int companionDamage = 0;
        String companionAttack = null;
        boolean companionEvolved = false;

        if (companion != null && companion.canAttack()) {
            // Companion adds extra damage
            companionDamage = companion.getAttackDamage();
            companionAttack = companion.getAttackDescription();
            finalDamage += companionDamage;

            // Track companion progression
            companion.addBattleExperience();

            // Check if companion just evolved
            if (companion.hasJustEvolved()) {
                companionEvolved = true;
                companion.clearEvolutionFlag();
            }
        }

        // Application des dégâts
        boolean died = monster.takeDamage(finalDamage);

        return new AttackResult(finalDamage, isCrit, died, false, false,
                              companionDamage, companionAttack, isAoE, statusEffect, companionEvolved);
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
