package org.example.demo.systems;

import org.example.demo.entities.Familiar;
import org.example.demo.entities.Rarity;

import java.util.Random;

/**
 * Système de gestion des familiers : tirage aléatoire, templates.
 */
public class FamiliarSystem {
    private static final Random RANDOM = new Random();

    // Templates de familiers par rareté
    private static final String[][] COMMON_FAMILIARS = {
        {"Slime Vert", "5", "0", "0", "0"},      // nom, hp, dmg, critRate, critDmg
        {"Chat Roux", "0", "1", "0", "0"},
        {"Luciole", "0", "0", "2", "0"},
        {"Souris Grise", "3", "0", "1", "0"},
        {"Crabe Bleu", "4", "0", "0", "0"}
    };

    private static final String[][] RARE_FAMILIARS = {
        {"Loup Gris", "10", "3", "0", "0"},
        {"Oiseau Bleu", "5", "0", "5", "0"},
        {"Tortue Ancienne", "20", "0", "0", "0"},
        {"Renard Rusé", "0", "2", "3", "5"},
        {"Serpent Venimeux", "0", "4", "0", "0"}
    };

    private static final String[][] EPIC_FAMILIARS = {
        {"Dragon Miniature", "15", "6", "0", "0"},
        {"Phénix Ardent", "10", "0", "10", "5"},
        {"Golem de Pierre", "40", "0", "0", "0"},
        {"Tigre Blanc", "15", "5", "5", "0"},
        {"Esprit Spectral", "0", "4", "8", "10"}
    };

    private static final String[][] LEGENDARY_FAMILIARS = {
        {"Dragon Doré", "30", "10", "10", "0", "Majestueux"},
        {"Licorne Céleste", "50", "0", "0", "0", "Régénération 1HP/sec"},
        {"Démon Noir", "20", "15", "0", "15", "Corruption"},
        {"Ange Gardien", "35", "8", "8", "8", "Protection Divine"},
        {"Léviathan", "60", "12", "5", "10", "Marée Destructrice"}
    };

    /**
     * Tire une rareté aléatoire selon les probabilités
     */
    public static Rarity rollRarity() {
        double roll = RANDOM.nextDouble() * 100;
        double cumulative = 0;

        for (Rarity rarity : Rarity.values()) {
            cumulative += rarity.getDropChance();
            if (roll < cumulative) {
                return rarity;
            }
        }

        return Rarity.COMMON; // Fallback
    }

    /**
     * Tire un familier aléatoire
     */
    public static Familiar rollFamiliar() {
        Rarity rarity = rollRarity();
        return createFamiliarByRarity(rarity);
    }

    /**
     * Crée un familier selon sa rareté
     */
    private static Familiar createFamiliarByRarity(Rarity rarity) {
        String[][] templates;

        switch (rarity) {
            case COMMON:
                templates = COMMON_FAMILIARS;
                break;
            case RARE:
                templates = RARE_FAMILIARS;
                break;
            case EPIC:
                templates = EPIC_FAMILIARS;
                break;
            case LEGENDARY:
                templates = LEGENDARY_FAMILIARS;
                break;
            default:
                templates = COMMON_FAMILIARS;
        }

        String[] template = templates[RANDOM.nextInt(templates.length)];
        String name = template[0];
        int hp = Integer.parseInt(template[1]);
        int dmg = Integer.parseInt(template[2]);
        double critRate = Double.parseDouble(template[3]);
        double critDmg = Double.parseDouble(template[4]);
        String specialEffect = template.length > 5 ? template[5] : null;

        return new Familiar(name, rarity, hp, dmg, critRate, critDmg, specialEffect);
    }
}
