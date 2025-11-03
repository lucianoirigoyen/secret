package org.example.demo.entities;

/**
 * Représente un boss (apparaît tous les 5 monstres).
 * Plus de HP, plus d'or, et inflige des DPS au joueur.
 * Possède des capacités spéciales : Esquive et Bouclier.
 */
public class Boss extends Monster {
    private static final int HP_MULTIPLIER = 3;
    private static final int MIN_GOLD = 100;
    private static final int MAX_GOLD = 150;
    private static final int BASE_DPS = 5;

    private int dps;

    // Capacités spéciales
    private double dodgeChance;        // Probabilité d'esquive (0.15 = 15%)
    private double shieldActivationChance; // Probabilité d'activation du bouclier
    private boolean shieldActive;      // Le bouclier est-il actuellement actif ?
    private double shieldTimer;        // Temps restant du bouclier
    private double shieldCooldown;     // Cooldown avant prochaine tentative d'activation
    private double shieldDuration;     // Durée du bouclier

    public Boss(int level) {
        super(level);
        // Override HP et gold
        this.maxHp = calculateHp(level) * HP_MULTIPLIER;
        this.currentHp = maxHp;
        this.minGoldDrop = MIN_GOLD;
        this.maxGoldDrop = MAX_GOLD;
        this.dps = BASE_DPS + (level - 1) * 3; // DPS augmente avec le niveau

        // Initialisation des capacités selon le niveau
        initializeAbilities(level);
    }

    /**
     * Initialise les capacités du boss selon son niveau
     */
    private void initializeAbilities(int level) {
        if (level == 1) {
            // Boss 1 : Esquive seulement
            dodgeChance = 0.15;
            shieldActivationChance = 0.0;
            shieldDuration = 0.0;
        } else if (level == 2) {
            // Boss 2 : Esquive + Bouclier faible
            dodgeChance = 0.15;
            shieldActivationChance = 0.05;
            shieldDuration = 2.0;
        } else {
            // Boss 3+ : Esquive + Bouclier fort
            dodgeChance = 0.20;
            shieldActivationChance = 0.08;
            shieldDuration = 3.0;
        }

        shieldActive = false;
        shieldTimer = 0;
        shieldCooldown = 0;
    }

    /**
     * Tente d'esquiver une attaque
     * @return true si l'attaque est esquivée
     */
    public boolean tryDodge() {
        return RANDOM.nextDouble() < dodgeChance;
    }

    /**
     * Met à jour le système de bouclier
     * @param deltaTime Temps écoulé en secondes
     */
    public void updateShield(double deltaTime) {
        // Décompte du cooldown
        if (shieldCooldown > 0) {
            shieldCooldown -= deltaTime;
        }

        // Si le bouclier est actif, décompter sa durée
        if (shieldActive) {
            shieldTimer -= deltaTime;
            if (shieldTimer <= 0) {
                deactivateShield();
            }
        } else {
            // Tentative d'activation si cooldown terminé
            if (shieldCooldown <= 0 && shieldActivationChance > 0) {
                if (RANDOM.nextDouble() < shieldActivationChance) {
                    activateShield();
                }
                shieldCooldown = 2.0; // 2 secondes de cooldown
            }
        }
    }

    /**
     * Active le bouclier
     */
    private void activateShield() {
        shieldActive = true;
        shieldTimer = shieldDuration;
    }

    /**
     * Désactive le bouclier
     */
    private void deactivateShield() {
        shieldActive = false;
        shieldTimer = 0;
    }

    @Override
    public int getDps() {
        return dps;
    }

    @Override
    public boolean isBoss() {
        return true;
    }

    // Getters pour les capacités
    public double getDodgeChance() { return dodgeChance; }
    public boolean isShieldActive() { return shieldActive; }
    public double getShieldTimeRemaining() { return shieldTimer; }
}
