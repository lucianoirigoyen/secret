package org.example.demo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente le joueur avec toutes ses statistiques et progression.
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    // Stats de base
    private static final int BASE_HP = 100;
    private static final int BASE_DAMAGE = 10;
    private static final double BASE_CRIT_RATE = 5.0;
    private static final double BASE_CRIT_MULTIPLIER = 150.0;

    // Stats actuelles
    private int currentHp;
    private int maxHp;
    private int clickDamage;
    private double critRate;           // en %
    private double critMultiplier;     // en % (150 = x1.5)
    private int gold;
    private int totalBossesKilled;
    private boolean autoClickerUnlocked;

    // Système de familiers
    private List<Familiar> familiarCollection;
    private Familiar equippedFamiliar1;
    private Familiar equippedFamiliar2;

    public Player() {
        this.maxHp = BASE_HP;
        this.currentHp = BASE_HP;
        this.clickDamage = BASE_DAMAGE;
        this.critRate = BASE_CRIT_RATE;
        this.critMultiplier = BASE_CRIT_MULTIPLIER;
        this.gold = 0;
        this.totalBossesKilled = 0;
        this.autoClickerUnlocked = false;
        this.familiarCollection = new ArrayList<>();
        this.equippedFamiliar1 = null;
        this.equippedFamiliar2 = null;
    }

    /**
     * Inflige des dégâts au joueur (par les boss)
     */
    public void takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - damage);
    }

    /**
     * Vérifie si le joueur est mort
     */
    public boolean isDead() {
        return currentHp <= 0;
    }

    /**
     * Réinitialise la vie du joueur (après une mort)
     */
    public void revive() {
        currentHp = getTotalMaxHp();
    }

    /**
     * Ajoute de l'or au joueur
     */
    public void addGold(int amount) {
        gold += amount;
    }

    /**
     * Retire de l'or (pour achats)
     * @return true si l'achat était possible
     */
    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }

    /**
     * Incrémente le compteur de boss tués
     */
    public void incrementBossKills() {
        totalBossesKilled++;
        if (totalBossesKilled >= 5 && !autoClickerUnlocked) {
            autoClickerUnlocked = true;
        }
    }

    /**
     * Ajoute un familier à la collection
     */
    public void addFamiliar(Familiar familiar) {
        familiarCollection.add(familiar);
    }

    /**
     * Équipe un familier dans le slot 1
     */
    public void equipFamiliar1(Familiar familiar) {
        // Recalculer les HP si nécessaire
        int oldMaxHp = getTotalMaxHp();
        equippedFamiliar1 = familiar;
        int newMaxHp = getTotalMaxHp();

        // Ajuster les HP actuels proportionnellement
        if (oldMaxHp > 0) {
            double hpRatio = (double) currentHp / oldMaxHp;
            currentHp = (int) (newMaxHp * hpRatio);
        }
    }

    /**
     * Équipe un familier dans le slot 2
     */
    public void equipFamiliar2(Familiar familiar) {
        int oldMaxHp = getTotalMaxHp();
        equippedFamiliar2 = familiar;
        int newMaxHp = getTotalMaxHp();

        if (oldMaxHp > 0) {
            double hpRatio = (double) currentHp / oldMaxHp;
            currentHp = (int) (newMaxHp * hpRatio);
        }
    }

    /**
     * Déséquipe le familier du slot 1
     */
    public void unequipFamiliar1() {
        equipFamiliar1(null);
    }

    /**
     * Déséquipe le familier du slot 2
     */
    public void unequipFamiliar2() {
        equipFamiliar2(null);
    }

    /**
     * Calcule les HP max totaux (base + upgrades + familiers)
     */
    public int getTotalMaxHp() {
        int total = maxHp;
        if (equippedFamiliar1 != null) total += equippedFamiliar1.getHpBonus();
        if (equippedFamiliar2 != null) total += equippedFamiliar2.getHpBonus();
        return total;
    }

    /**
     * Calcule les dégâts totaux (base + upgrades + familiers)
     */
    public int getTotalClickDamage() {
        int total = clickDamage;
        if (equippedFamiliar1 != null) total += equippedFamiliar1.getDamageBonus();
        if (equippedFamiliar2 != null) total += equippedFamiliar2.getDamageBonus();
        return total;
    }

    /**
     * Calcule le taux de crit total (base + upgrades + familiers)
     */
    public double getTotalCritRate() {
        double total = critRate;
        if (equippedFamiliar1 != null) total += equippedFamiliar1.getCritRateBonus();
        if (equippedFamiliar2 != null) total += equippedFamiliar2.getCritRateBonus();
        return Math.min(100.0, total); // Cap à 100%
    }

    /**
     * Calcule le multiplicateur de crit total (base + upgrades + familiers)
     */
    public double getTotalCritMultiplier() {
        double total = critMultiplier;
        if (equippedFamiliar1 != null) total += equippedFamiliar1.getCritDamageBonus();
        if (equippedFamiliar2 != null) total += equippedFamiliar2.getCritDamageBonus();
        return total;
    }

    // Améliorations permanentes
    public void upgradeMaxHp(int amount) {
        maxHp += amount;
        currentHp += amount; // Bonus instantané
    }

    public void upgradeClickDamage(int amount) {
        clickDamage += amount;
    }

    public void upgradeCritRate(double amount) {
        critRate = Math.min(100.0, critRate + amount); // Cap à 100%
    }

    public void upgradeCritMultiplier(double amount) {
        critMultiplier += amount;
    }

    // Getters
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public int getClickDamage() { return clickDamage; }
    public double getCritRate() { return critRate; }
    public double getCritMultiplier() { return critMultiplier; }
    public int getGold() { return gold; }
    public int getTotalBossesKilled() { return totalBossesKilled; }
    public boolean isAutoClickerUnlocked() { return autoClickerUnlocked; }

    public List<Familiar> getFamiliarCollection() { return familiarCollection; }
    public Familiar getEquippedFamiliar1() { return equippedFamiliar1; }
    public Familiar getEquippedFamiliar2() { return equippedFamiliar2; }

    // Setters (pour sauvegarde)
    public void setCurrentHp(int hp) { this.currentHp = hp; }
    public void setGold(int gold) { this.gold = gold; }
}
