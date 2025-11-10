package org.example.demo.systems;

import java.io.Serializable;
import java.util.Random;

/**
 * Enhanced Critical Hit System
 * - Allows crit rate up to 100%
 * - Post-100% bonuses: Crit Damage / AoE / Effect Procs
 */
public class CritSystem implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Random RANDOM = new Random();

    // Post-100% bonus types
    public enum PostCritBonus {
        CRIT_DAMAGE("Dégâts Crit +1%", 0.4),      // 40% chance
        AOE_CHANCE("Chance AoE +0.5%", 0.35),      // 35% chance
        EFFECT_PROC("Chance Effet +0.5%", 0.25);  // 25% chance

        private final String description;
        private final double weight;

        PostCritBonus(String desc, double weight) {
            this.description = desc;
            this.weight = weight;
        }

        public String getDescription() { return description; }
        public double getWeight() { return weight; }
    }

    // Post-100% bonus counters
    private double extraCritDamage;    // Additional crit multiplier (e.g., +5% = +5.0)
    private double aoeChance;          // Chance for AoE crit (0-100%)
    private double effectProcChance;   // Chance for status effect (0-100%)

    public CritSystem() {
        this.extraCritDamage = 0;
        this.aoeChance = 0;
        this.effectProcChance = 0;
    }

    /**
     * Apply a post-100% crit bonus point
     * Randomly assigns to one of three bonuses
     */
    public PostCritBonus applyPostCritBonus() {
        double roll = RANDOM.nextDouble();
        double cumulative = 0;

        for (PostCritBonus bonus : PostCritBonus.values()) {
            cumulative += bonus.getWeight();
            if (roll < cumulative) {
                applyBonus(bonus);
                return bonus;
            }
        }

        // Fallback
        applyBonus(PostCritBonus.CRIT_DAMAGE);
        return PostCritBonus.CRIT_DAMAGE;
    }

    /**
     * Apply specific bonus
     */
    private void applyBonus(PostCritBonus bonus) {
        switch (bonus) {
            case CRIT_DAMAGE:
                extraCritDamage += 1.0; // +1% crit damage
                break;
            case AOE_CHANCE:
                aoeChance += 0.5; // +0.5% AoE chance
                break;
            case EFFECT_PROC:
                effectProcChance += 0.5; // +0.5% effect proc chance
                break;
        }
    }

    /**
     * Calculate final critical hit damage multiplier
     * @param baseCritMultiplier Player's base crit multiplier (e.g., 150.0 for 1.5x)
     * @return Final multiplier including post-100% bonuses
     */
    public double getFinalCritMultiplier(double baseCritMultiplier) {
        return baseCritMultiplier + extraCritDamage;
    }

    /**
     * Check if this crit triggers AoE (area damage)
     * @return true if AoE should trigger
     */
    public boolean rollAoE() {
        return RANDOM.nextDouble() * 100 < aoeChance;
    }

    /**
     * Check if this crit triggers a status effect
     * @return Effect type if triggered, null otherwise
     */
    public StatusEffect rollStatusEffect() {
        if (RANDOM.nextDouble() * 100 < effectProcChance) {
            // Randomly select an effect
            StatusEffect[] effects = StatusEffect.values();
            return effects[RANDOM.nextInt(effects.length)];
        }
        return null;
    }

    /**
     * Get summary of post-100% bonuses
     */
    public String getBonusSummary() {
        if (extraCritDamage == 0 && aoeChance == 0 && effectProcChance == 0) {
            return "Aucun bonus post-100%";
        }

        StringBuilder summary = new StringBuilder("Bonus Crit 100%+:\n");
        if (extraCritDamage > 0) {
            summary.append(String.format("  +%.0f%% Dégâts Crit\n", extraCritDamage));
        }
        if (aoeChance > 0) {
            summary.append(String.format("  %.1f%% Chance AoE\n", aoeChance));
        }
        if (effectProcChance > 0) {
            summary.append(String.format("  %.1f%% Chance Effet\n", effectProcChance));
        }
        return summary.toString().trim();
    }

    // Status effects that can proc
    public enum StatusEffect {
        BURN("🔥 Brûlure", "Le monstre prend des dégâts sur la durée!"),
        STUN("⚡ Étourdissement", "Le monstre est paralysé!"),
        POISON("☠️ Poison", "Le monstre est empoisonné!"),
        SLOW("❄️ Ralentissement", "Le monstre attaque plus lentement!");

        private final String name;
        private final String description;

        StatusEffect(String name, String desc) {
            this.name = name;
            this.description = desc;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
    }

    // Getters for UI display
    public double getExtraCritDamage() { return extraCritDamage; }
    public double getAoeChance() { return aoeChance; }
    public double getEffectProcChance() { return effectProcChance; }

    // Setters for save/load
    public void setExtraCritDamage(double value) { this.extraCritDamage = value; }
    public void setAoeChance(double value) { this.aoeChance = value; }
    public void setEffectProcChance(double value) { this.effectProcChance = value; }
}
