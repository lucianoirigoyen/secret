package org.example.demo.entities;

import java.io.Serializable;

/**
 * Companion system - Evolutionary combat pet (Egg → Pokémon starter → Evolutions)
 */
public class Companion implements Serializable {
    private static final long serialVersionUID = 1L;

    // Evolution stages
    public enum EvolutionStage {
        EGG(0, "Œuf de Yoshi", "🥚", 1, "Lancer de Coquille"),
        STARTER(5, "Starter", "🐣", 5, "Attaque de Base"),
        EVOLUTION_1(20, "Évolution 1", "💪", 15, "Attaque Puissante"),
        EVOLUTION_2(50, "Évolution Finale", "⭐", 40, "Attaque Ultime");

        private final int battlesRequired;
        private final String displayName;
        private final String emoji;
        private final int baseDamage;
        private final String attackName;

        EvolutionStage(int battles, String display, String emoji, int damage, String attack) {
            this.battlesRequired = battles;
            this.displayName = display;
            this.emoji = emoji;
            this.baseDamage = damage;
            this.attackName = attack;
        }

        public int getBattlesRequired() { return battlesRequired; }
        public String getDisplayName() { return displayName; }
        public String getEmoji() { return emoji; }
        public int getBaseDamage() { return baseDamage; }
        public String getAttackName() { return attackName; }
    }

    // Starter types (after egg hatches)
    public enum StarterType {
        BULBASAUR("Bulbizarre", "🌱", "Fouet Lianes Comique", "Herbizarre", "Florizarre"),
        CHARMANDER("Salamèche", "🔥", "Flammèche Timide", "Reptincel", "Dracaufeu"),
        SQUIRTLE("Carapuce", "💧", "Pistolet à Eau Éclaboussant", "Carabaffe", "Tortank");

        private final String name;
        private final String emoji;
        private final String funnyAttack;
        private final String evolution1Name;
        private final String evolution2Name;

        StarterType(String name, String emoji, String attack, String evo1, String evo2) {
            this.name = name;
            this.emoji = emoji;
            this.funnyAttack = attack;
            this.evolution1Name = evo1;
            this.evolution2Name = evo2;
        }

        public String getName() { return name; }
        public String getEmoji() { return emoji; }
        public String getFunnyAttack() { return funnyAttack; }
        public String getEvolution1Name() { return evolution1Name; }
        public String getEvolution2Name() { return evolution2Name; }
    }

    // Companion state
    private EvolutionStage currentStage;
    private StarterType starterType; // null if still egg
    private int battlesParticipated;
    private String currentName;
    private boolean justEvolved; // Flag for evolution animation

    public Companion() {
        this.currentStage = EvolutionStage.EGG;
        this.starterType = null;
        this.battlesParticipated = 0;
        this.currentName = "Œuf Mystérieux";
        this.justEvolved = false;
    }

    /**
     * Called after each battle to track progression
     */
    public void addBattleExperience() {
        battlesParticipated++;
        checkForEvolution();
    }

    /**
     * Check if companion should evolve
     */
    private void checkForEvolution() {
        EvolutionStage nextStage = getNextStage();

        if (nextStage != null && battlesParticipated >= nextStage.getBattlesRequired()) {
            evolve(nextStage);
        }
    }

    /**
     * Get the next evolution stage
     */
    private EvolutionStage getNextStage() {
        switch (currentStage) {
            case EGG:
                return EvolutionStage.STARTER;
            case STARTER:
                return EvolutionStage.EVOLUTION_1;
            case EVOLUTION_1:
                return EvolutionStage.EVOLUTION_2;
            default:
                return null; // Max evolution
        }
    }

    /**
     * Evolve to next stage
     */
    private void evolve(EvolutionStage newStage) {
        currentStage = newStage;
        justEvolved = true;

        // Special handling for egg hatching
        if (newStage == EvolutionStage.STARTER && starterType == null) {
            hatchEgg();
        } else {
            updateName();
        }
    }

    /**
     * Hatch egg and randomly select starter
     */
    private void hatchEgg() {
        StarterType[] types = StarterType.values();
        starterType = types[(int) (Math.random() * types.length)];
        currentName = starterType.getName();
    }

    /**
     * Update name based on evolution
     */
    private void updateName() {
        if (starterType == null) {
            currentName = currentStage.getDisplayName();
            return;
        }

        switch (currentStage) {
            case STARTER:
                currentName = starterType.getName();
                break;
            case EVOLUTION_1:
                currentName = starterType.getEvolution1Name();
                break;
            case EVOLUTION_2:
                currentName = starterType.getEvolution2Name();
                break;
            default:
                currentName = "Œuf Mystérieux";
        }
    }

    /**
     * Get companion's attack damage
     */
    public int getAttackDamage() {
        return currentStage.getBaseDamage();
    }

    /**
     * Get funny attack description
     */
    public String getAttackDescription() {
        if (currentStage == EvolutionStage.EGG) {
            return "💥 L'œuf roule maladroitement vers l'ennemi!";
        }

        if (starterType == null) {
            return currentStage.getAttackName();
        }

        switch (currentStage) {
            case STARTER:
                return starterType.getFunnyAttack();
            case EVOLUTION_1:
                return getEvolution1Attack();
            case EVOLUTION_2:
                return getEvolution2Attack();
            default:
                return "Attaque!";
        }
    }

    /**
     * Get evolution 1 attack description
     */
    private String getEvolution1Attack() {
        switch (starterType) {
            case BULBASAUR:
                return "🌿 Tempête de Pétales Parfumés!";
            case CHARMANDER:
                return "🔥 Lance-Flammes Dramatique!";
            case SQUIRTLE:
                return "💦 Canon à Eau Haute Pression!";
            default:
                return "Attaque Puissante!";
        }
    }

    /**
     * Get evolution 2 attack description
     */
    private String getEvolution2Attack() {
        switch (starterType) {
            case BULBASAUR:
                return "☀️ Rayon Solaire Aveuglant!";
            case CHARMANDER:
                return "🌪️ Souffle du Dragon Dévastateur!";
            case SQUIRTLE:
                return "🌊 Hydrocanon Tsunami!";
            default:
                return "Attaque Ultime!";
        }
    }

    /**
     * Get display emoji
     */
    public String getEmoji() {
        if (starterType != null) {
            return starterType.getEmoji();
        }
        return currentStage.getEmoji();
    }

    /**
     * Get progress to next evolution
     */
    public String getProgressText() {
        EvolutionStage next = getNextStage();
        if (next == null) {
            return "MAX ⭐";
        }
        return String.format("%d/%d combats", battlesParticipated, next.getBattlesRequired());
    }

    /**
     * Check if companion can attack (not egg stage)
     */
    public boolean canAttack() {
        return true; // Even egg can attack with funny animation
    }

    // Getters
    public EvolutionStage getCurrentStage() { return currentStage; }
    public StarterType getStarterType() { return starterType; }
    public int getBattlesParticipated() { return battlesParticipated; }
    public String getCurrentName() { return currentName; }
    public boolean hasJustEvolved() { return justEvolved; }

    public void clearEvolutionFlag() { justEvolved = false; }
}
