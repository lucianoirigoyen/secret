package org.example.demo.entities;

/**
 * Énumération des raretés de familiers.
 */
public enum Rarity {
    COMMON("Commun", 50.0, "#CCCCCC"),
    RARE("Rare", 30.0, "#3498DB"),
    EPIC("Épique", 15.0, "#9B59B6"),
    LEGENDARY("Légendaire", 5.0, "#F39C12");

    private final String displayName;
    private final double dropChance; // en %
    private final String colorHex;

    Rarity(String displayName, double dropChance, String colorHex) {
        this.displayName = displayName;
        this.dropChance = dropChance;
        this.colorHex = colorHex;
    }

    public String getDisplayName() { return displayName; }
    public double getDropChance() { return dropChance; }
    public String getColorHex() { return colorHex; }
}

