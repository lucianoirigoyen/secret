package org.example.demo.systems;

import org.example.demo.entities.Player;

import java.util.Random;

/**
 * Gère les types d'améliorations et leur application.
 */
public class UpgradeSystem {
    private static final Random RANDOM = new Random();

    // Valeurs des upgrades
    private static final int HP_UPGRADE = 10;
    private static final int DAMAGE_UPGRADE = 2;
    private static final double CRIT_RATE_UPGRADE = 5.0;
    private static final double CRIT_MULT_UPGRADE = 20.0;

    public enum UpgradeType {
        MAX_HP(30.0, "Vie Max", "+" + HP_UPGRADE + " HP"),
        CLICK_DAMAGE(30.0, "Dégâts par clic", "+" + DAMAGE_UPGRADE + " dégâts"),
        CRIT_RATE(25.0, "Taux de critique", "+" + (int)CRIT_RATE_UPGRADE + "%"),
        CRIT_MULTIPLIER(15.0, "Dégâts critiques", "+" + (int)CRIT_MULT_UPGRADE + "%");

        private final double chance;
        private final String displayName;
        private final String description;

        UpgradeType(double chance, String displayName, String description) {
            this.chance = chance;
            this.displayName = displayName;
            this.description = description;
        }

        public double getChance() { return chance; }
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }

    /**
     * Tire une amélioration aléatoire selon les probabilités
     */
    public static UpgradeType rollRandomUpgrade() {
        double roll = RANDOM.nextDouble() * 100;
        double cumulative = 0;

        for (UpgradeType type : UpgradeType.values()) {
            cumulative += type.getChance();
            if (roll < cumulative) {
                return type;
            }
        }

        // Fallback (ne devrait jamais arriver)
        return UpgradeType.MAX_HP;
    }

    /**
     * Applique une amélioration au joueur
     */
    public static void applyUpgrade(Player player, UpgradeType upgrade) {
        switch (upgrade) {
            case MAX_HP:
                player.upgradeMaxHp(HP_UPGRADE);
                break;
            case CLICK_DAMAGE:
                player.upgradeClickDamage(DAMAGE_UPGRADE);
                break;
            case CRIT_RATE:
                player.upgradeCritRate(CRIT_RATE_UPGRADE);
                break;
            case CRIT_MULTIPLIER:
                player.upgradeCritMultiplier(CRIT_MULT_UPGRADE);
                break;
        }
    }
}

