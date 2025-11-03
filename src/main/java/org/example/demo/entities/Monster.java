package org.example.demo.entities;

import java.util.Random;

/**
 * Représente un monstre normal dans le jeu.
 */
public class Monster {
    protected static final Random RANDOM = new Random();

    // Constantes de scaling
    private static final int BASE_HP = 50;
    private static final int HP_SCALING = 30; // +30 HP par niveau
    private static final int MIN_GOLD = 10;
    private static final int MAX_GOLD = 20;

    protected int level;
    protected int maxHp;
    protected int currentHp;
    protected int minGoldDrop;
    protected int maxGoldDrop;

    public Monster(int level) {
        this.level = level;
        this.maxHp = calculateHp(level);
        this.currentHp = maxHp;
        this.minGoldDrop = MIN_GOLD;
        this.maxGoldDrop = MAX_GOLD;
    }

    /**
     * Calcule les HP en fonction du niveau
     */
    protected int calculateHp(int level) {
        return BASE_HP + (level - 1) * HP_SCALING;
    }

    /**
     * Inflige des dégâts au monstre
     * @return true si le monstre est mort
     */
    public boolean takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - damage);
        return isDead();
    }

    /**
     * Vérifie si le monstre est mort
     */
    public boolean isDead() {
        return currentHp <= 0;
    }

    /**
     * Retourne l'or lâché à la mort
     */
    public int getGoldDrop() {
        return minGoldDrop + RANDOM.nextInt(maxGoldDrop - minGoldDrop + 1);
    }

    /**
     * Retourne le DPS infligé au joueur (0 pour monstres normaux)
     */
    public int getDps() {
        return 0;
    }

    /**
     * Vérifie si c'est un boss
     */
    public boolean isBoss() {
        return false;
    }

    // Getters
    public int getLevel() { return level; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public double getHpPercentage() {
        return maxHp > 0 ? (double) currentHp / maxHp * 100 : 0;
    }
}

