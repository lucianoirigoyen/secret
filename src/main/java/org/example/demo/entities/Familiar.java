package org.example.demo.entities;

import java.io.Serializable;

/**
 * Représente un familier avec ses bonus de stats.
 */
public class Familiar implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Rarity rarity;
    private int hpBonus;
    private int damageBonus;
    private double critRateBonus;
    private double critDamageBonus;
    private String specialEffect; // Pour légendaires (ex: "regen")

    public Familiar(String name, Rarity rarity, int hpBonus, int damageBonus,
                    double critRateBonus, double critDamageBonus, String specialEffect) {
        this.name = name;
        this.rarity = rarity;
        this.hpBonus = hpBonus;
        this.damageBonus = damageBonus;
        this.critRateBonus = critRateBonus;
        this.critDamageBonus = critDamageBonus;
        this.specialEffect = specialEffect;
    }

    /**
     * Retourne une description complète du familier
     */
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(name).append(" (").append(rarity.getDisplayName()).append(")\n");

        if (hpBonus > 0) desc.append("+").append(hpBonus).append(" HP Max\n");
        if (damageBonus > 0) desc.append("+").append(damageBonus).append(" Dégâts\n");
        if (critRateBonus > 0) desc.append("+").append(String.format("%.0f", critRateBonus)).append("% Taux Crit\n");
        if (critDamageBonus > 0) desc.append("+").append(String.format("%.0f", critDamageBonus)).append("% Dégâts Crit\n");
        if (specialEffect != null && !specialEffect.isEmpty()) {
            desc.append("Effet: ").append(specialEffect);
        }

        return desc.toString().trim();
    }

    /**
     * Retourne un résumé court pour l'affichage
     */
    public String getShortDescription() {
        StringBuilder desc = new StringBuilder();
        if (hpBonus > 0) desc.append("+").append(hpBonus).append("HP ");
        if (damageBonus > 0) desc.append("+").append(damageBonus).append("DMG ");
        if (critRateBonus > 0) desc.append("+").append(String.format("%.0f", critRateBonus)).append("%CR ");
        if (critDamageBonus > 0) desc.append("+").append(String.format("%.0f", critDamageBonus)).append("%CD ");
        return desc.toString().trim();
    }

    // Getters
    public String getName() { return name; }
    public Rarity getRarity() { return rarity; }
    public int getHpBonus() { return hpBonus; }
    public int getDamageBonus() { return damageBonus; }
    public double getCritRateBonus() { return critRateBonus; }
    public double getCritDamageBonus() { return critDamageBonus; }
    public String getSpecialEffect() { return specialEffect; }
}

