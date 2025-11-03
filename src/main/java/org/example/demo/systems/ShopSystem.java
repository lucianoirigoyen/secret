package org.example.demo.systems;

import org.example.demo.entities.Familiar;
import org.example.demo.entities.Player;

/**
 * Gère le système d'achat d'améliorations et d'œufs de familiers.
 */
public class ShopSystem {
    private static final int BASE_UPGRADE_COST = 50;
    private static final double COST_SCALING = 0.1;

    private static final int BASE_EGG_COST = 200;
    private static final double EGG_COST_SCALING = 0.15;

    private Player player;
    private int totalUpgradesPurchased;
    private int totalEggsPurchased;

    public ShopSystem(Player player) {
        this.player = player;
        this.totalUpgradesPurchased = 0;
        this.totalEggsPurchased = 0;
    }

    /**
     * Calcule le coût actuel d'une amélioration
     */
    public int getCurrentUpgradeCost() {
        return (int) (BASE_UPGRADE_COST * (1 + totalUpgradesPurchased * COST_SCALING));
    }

    /**
     * Calcule le coût actuel d'un œuf de familier
     */
    public int getCurrentEggCost() {
        return (int) (BASE_EGG_COST * (1 + totalEggsPurchased * EGG_COST_SCALING));
    }

    /**
     * Vérifie si le joueur peut acheter une amélioration
     */
    public boolean canAffordUpgrade() {
        return player.getGold() >= getCurrentUpgradeCost();
    }

    /**
     * Vérifie si le joueur peut acheter un œuf
     */
    public boolean canAffordEgg() {
        return player.getGold() >= getCurrentEggCost();
    }

    /**
     * Achète une amélioration aléatoire
     * @return L'amélioration obtenue, ou null si pas assez d'or
     */
    public UpgradeSystem.UpgradeType purchaseUpgrade() {
        int cost = getCurrentUpgradeCost();

        if (!player.spendGold(cost)) {
            return null;
        }

        // Tire et applique l'amélioration
        UpgradeSystem.UpgradeType upgrade = UpgradeSystem.rollRandomUpgrade();
        UpgradeSystem.applyUpgrade(player, upgrade);

        totalUpgradesPurchased++;

        return upgrade;
    }

    /**
     * Achète un œuf de familier
     * @return Le familier obtenu, ou null si pas assez d'or
     */
    public Familiar purchaseEgg() {
        int cost = getCurrentEggCost();

        if (!player.spendGold(cost)) {
            return null;
        }

        // Tire un familier aléatoire
        Familiar familiar = FamiliarSystem.rollFamiliar();
        player.addFamiliar(familiar);

        totalEggsPurchased++;

        return familiar;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getTotalUpgradesPurchased() {
        return totalUpgradesPurchased;
    }

    public void setTotalUpgradesPurchased(int count) {
        this.totalUpgradesPurchased = count;
    }

    public int getTotalEggsPurchased() {
        return totalEggsPurchased;
    }

    public void setTotalEggsPurchased(int count) {
        this.totalEggsPurchased = count;
    }
}
