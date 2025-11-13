package org.example.demo.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Générateur de sprites Pixel Art pour les familiers et compagnons.
 * Crée des sprites programmatiquement pour éviter la dépendance aux fichiers images.
 */
public class PixelArtSprite {

    private static final int PIXEL_SIZE = 3; // Taille d'un "pixel" en pixels réels
    private static final int SPRITE_WIDTH = 16; // Largeur en "pixels"
    private static final int SPRITE_HEIGHT = 16; // Hauteur en "pixels"

    /**
     * Crée un Canvas avec le sprite du familier
     */
    public static Canvas createFamiliarSprite(String familiarName) {
        Canvas canvas = new Canvas(SPRITE_WIDTH * PIXEL_SIZE, SPRITE_HEIGHT * PIXEL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Fond transparent
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Sélectionner le sprite selon le nom
        if (familiarName.contains("Slime")) {
            drawSlime(gc);
        } else if (familiarName.contains("Chat")) {
            drawCat(gc);
        } else if (familiarName.contains("Luciole")) {
            drawFirefly(gc);
        } else if (familiarName.contains("Souris")) {
            drawMouse(gc);
        } else if (familiarName.contains("Crabe")) {
            drawCrab(gc);
        } else if (familiarName.contains("Loup")) {
            drawWolf(gc);
        } else if (familiarName.contains("Oiseau")) {
            drawBird(gc);
        } else if (familiarName.contains("Tortue")) {
            drawTurtle(gc);
        } else if (familiarName.contains("Renard")) {
            drawFox(gc);
        } else if (familiarName.contains("Serpent")) {
            drawSnake(gc);
        } else if (familiarName.contains("Dragon")) {
            drawDragon(gc);
        } else if (familiarName.contains("Phénix") || familiarName.contains("Phoenix")) {
            drawPhoenix(gc);
        } else if (familiarName.contains("Golem")) {
            drawGolem(gc);
        } else if (familiarName.contains("Tigre")) {
            drawTiger(gc);
        } else if (familiarName.contains("Esprit")) {
            drawSpirit(gc);
        } else if (familiarName.contains("Licorne")) {
            drawUnicorn(gc);
        } else if (familiarName.contains("Démon")) {
            drawDemon(gc);
        } else if (familiarName.contains("Ange")) {
            drawAngel(gc);
        } else if (familiarName.contains("Léviathan") || familiarName.contains("Leviathan")) {
            drawLeviathan(gc);
        } else {
            // Sprite par défaut (point d'interrogation)
            drawDefault(gc);
        }

        return canvas;
    }

    /**
     * Crée un Canvas avec le sprite du compagnon
     */
    public static Canvas createCompanionSprite(String companionType, String stage) {
        Canvas canvas = new Canvas(SPRITE_WIDTH * PIXEL_SIZE, SPRITE_HEIGHT * PIXEL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (stage.equals("EGG")) {
            drawEgg(gc);
        } else if (companionType.contains("Bulb")) {
            if (stage.equals("EVOLUTION_2")) {
                drawBulbasaurFinal(gc);
            } else if (stage.equals("EVOLUTION_1")) {
                drawBulbasaurEvo1(gc);
            } else {
                drawBulbasaurStarter(gc);
            }
        } else if (companionType.contains("Char") || companionType.contains("Salam")) {
            if (stage.equals("EVOLUTION_2")) {
                drawCharmanderFinal(gc);
            } else if (stage.equals("EVOLUTION_1")) {
                drawCharmanderEvo1(gc);
            } else {
                drawCharmanderStarter(gc);
            }
        } else if (companionType.contains("Squir") || companionType.contains("Carap")) {
            if (stage.equals("EVOLUTION_2")) {
                drawSquirtleFinal(gc);
            } else if (stage.equals("EVOLUTION_1")) {
                drawSquirtleEvo1(gc);
            } else {
                drawSquirtleStarter(gc);
            }
        } else {
            drawDefault(gc);
        }

        return canvas;
    }

    // ========== HELPER POUR DESSINER DES PIXELS ==========

    private static void drawPixel(GraphicsContext gc, int x, int y, Color color) {
        gc.setFill(color);
        gc.fillRect(x * PIXEL_SIZE, y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
    }

    // ========== FAMILIERS COMMUNS ==========

    private static void drawSlime(GraphicsContext gc) {
        Color body = Color.web("#4CAF50"); // Vert
        Color dark = Color.web("#2E7D32"); // Vert foncé
        Color shine = Color.web("#81C784"); // Vert clair
        Color eye = Color.BLACK;

        // Corps du slime (forme de goutte)
        drawPixel(gc, 6, 8, body);
        drawPixel(gc, 7, 8, body);
        drawPixel(gc, 8, 8, body);
        drawPixel(gc, 9, 8, body);

        drawPixel(gc, 5, 9, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 9, body);
        drawPixel(gc, 10, 9, body);

        drawPixel(gc, 4, 10, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 10, body);
        drawPixel(gc, 11, 10, body);

        drawPixel(gc, 4, 11, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 11, body);
        drawPixel(gc, 11, 11, body);

        for (int i = 4; i <= 11; i++) drawPixel(gc, i, 12, body);

        // Ombre en bas
        for (int i = 4; i <= 11; i++) drawPixel(gc, i, 13, dark);

        // Yeux
        drawPixel(gc, 6, 10, eye);
        drawPixel(gc, 9, 10, eye);

        // Reflets
        drawPixel(gc, 7, 9, shine);
        drawPixel(gc, 10, 9, shine);
    }

    private static void drawCat(GraphicsContext gc) {
        Color body = Color.web("#FF8C42"); // Orange roux
        Color dark = Color.web("#D35400"); // Orange foncé
        Color eye = Color.web("#4CAF50"); // Yeux verts
        Color nose = Color.web("#FF6B6B"); // Nez rose

        // Oreilles
        drawPixel(gc, 5, 6, body);
        drawPixel(gc, 6, 5, body);
        drawPixel(gc, 9, 5, body);
        drawPixel(gc, 10, 6, body);

        // Tête
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 7, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 8, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 9, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 10, body);

        // Corps
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 12, body);

        // Yeux
        drawPixel(gc, 6, 8, eye);
        drawPixel(gc, 9, 8, eye);

        // Nez
        drawPixel(gc, 7, 9, nose);
        drawPixel(gc, 8, 9, nose);

        // Rayures (détail)
        drawPixel(gc, 7, 7, dark);
    }

    private static void drawFirefly(GraphicsContext gc) {
        Color body = Color.web("#FFD700"); // Or
        Color glow = Color.web("#FFEB3B"); // Jaune lumineux
        Color wing = Color.web("#B0E0E6"); // Bleu pâle

        // Ailes
        drawPixel(gc, 5, 8, wing);
        drawPixel(gc, 6, 7, wing);
        drawPixel(gc, 9, 7, wing);
        drawPixel(gc, 10, 8, wing);

        // Corps
        drawPixel(gc, 7, 8, body);
        drawPixel(gc, 8, 8, body);
        drawPixel(gc, 7, 9, body);
        drawPixel(gc, 8, 9, body);

        // Lumière (abdomen)
        drawPixel(gc, 7, 10, glow);
        drawPixel(gc, 8, 10, glow);
        drawPixel(gc, 7, 11, glow);
        drawPixel(gc, 8, 11, glow);

        // Antennes
        drawPixel(gc, 7, 7, body);
        drawPixel(gc, 8, 7, body);
        drawPixel(gc, 6, 6, glow);
        drawPixel(gc, 9, 6, glow);
    }

    private static void drawMouse(GraphicsContext gc) {
        Color body = Color.web("#9E9E9E"); // Gris
        Color dark = Color.web("#616161"); // Gris foncé
        Color pink = Color.web("#FFB6C1"); // Rose
        Color eye = Color.BLACK;

        // Oreilles
        drawPixel(gc, 5, 7, body);
        drawPixel(gc, 6, 6, body);
        drawPixel(gc, 6, 7, pink);
        drawPixel(gc, 9, 7, pink);
        drawPixel(gc, 9, 6, body);
        drawPixel(gc, 10, 7, body);

        // Tête
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 8, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 9, body);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 10, body);

        // Corps
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, body);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 12, body);

        // Yeux
        drawPixel(gc, 6, 8, eye);
        drawPixel(gc, 9, 8, eye);

        // Nez
        drawPixel(gc, 7, 9, pink);
        drawPixel(gc, 8, 9, pink);

        // Queue
        drawPixel(gc, 9, 12, dark);
        drawPixel(gc, 10, 13, dark);
    }

    private static void drawCrab(GraphicsContext gc) {
        Color body = Color.web("#FF6347"); // Rouge tomate
        Color claw = Color.web("#DC143C"); // Rouge foncé
        Color eye = Color.BLACK;

        // Pinces gauche
        drawPixel(gc, 3, 9, claw);
        drawPixel(gc, 4, 8, claw);
        drawPixel(gc, 4, 9, claw);

        // Pinces droite
        drawPixel(gc, 11, 8, claw);
        drawPixel(gc, 11, 9, claw);
        drawPixel(gc, 12, 9, claw);

        // Corps
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 9, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 10, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, body);

        // Yeux sur tiges
        drawPixel(gc, 6, 8, body);
        drawPixel(gc, 9, 8, body);
        drawPixel(gc, 6, 7, eye);
        drawPixel(gc, 9, 7, eye);

        // Pattes
        drawPixel(gc, 5, 11, body);
        drawPixel(gc, 7, 11, body);
        drawPixel(gc, 8, 11, body);
        drawPixel(gc, 10, 11, body);
    }

    // ========== FAMILIERS RARES ==========

    private static void drawWolf(GraphicsContext gc) {
        Color body = Color.web("#757575"); // Gris loup
        Color dark = Color.web("#424242"); // Gris foncé
        Color eye = Color.web("#FFA000"); // Jaune/orange

        // Oreilles pointues
        drawPixel(gc, 5, 6, body);
        drawPixel(gc, 6, 5, body);
        drawPixel(gc, 9, 5, body);
        drawPixel(gc, 10, 6, body);

        // Tête
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 7, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 8, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 9, body);

        // Museau
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 10, body);
        drawPixel(gc, 7, 11, body);
        drawPixel(gc, 8, 11, body);

        // Corps
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 12, body);

        // Yeux brillants
        drawPixel(gc, 6, 8, eye);
        drawPixel(gc, 9, 8, eye);

        // Nez
        drawPixel(gc, 7, 10, dark);
        drawPixel(gc, 8, 10, dark);
    }

    private static void drawBird(GraphicsContext gc) {
        Color body = Color.web("#2196F3"); // Bleu
        Color wing = Color.web("#1976D2"); // Bleu foncé
        Color beak = Color.web("#FFA726"); // Orange
        Color eye = Color.BLACK;

        // Corps
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 8, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 9, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 10, body);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 11, body);

        // Ailes déployées
        drawPixel(gc, 4, 9, wing);
        drawPixel(gc, 5, 9, wing);
        drawPixel(gc, 5, 10, wing);

        drawPixel(gc, 10, 9, wing);
        drawPixel(gc, 11, 9, wing);
        drawPixel(gc, 10, 10, wing);

        // Tête
        drawPixel(gc, 7, 7, body);
        drawPixel(gc, 8, 7, body);

        // Œil
        drawPixel(gc, 7, 8, eye);

        // Bec
        drawPixel(gc, 9, 7, beak);
        drawPixel(gc, 10, 7, beak);

        // Queue
        drawPixel(gc, 6, 11, wing);
        drawPixel(gc, 5, 12, wing);
    }

    private static void drawTurtle(GraphicsContext gc) {
        Color shell = Color.web("#8D6E63"); // Marron
        Color body = Color.web("#A5D6A7"); // Vert clair
        Color dark = Color.web("#6D4C41"); // Marron foncé
        Color eye = Color.BLACK;

        // Carapace
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 9, shell);
        for (int i = 4; i <= 11; i++) drawPixel(gc, i, 10, shell);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 11, shell);

        // Motif carapace
        drawPixel(gc, 6, 10, dark);
        drawPixel(gc, 8, 10, dark);
        drawPixel(gc, 9, 10, dark);

        // Tête
        drawPixel(gc, 7, 7, body);
        drawPixel(gc, 8, 7, body);
        drawPixel(gc, 7, 8, body);
        drawPixel(gc, 8, 8, body);

        // Œil
        drawPixel(gc, 7, 7, eye);

        // Pattes
        drawPixel(gc, 4, 11, body);
        drawPixel(gc, 11, 11, body);
        drawPixel(gc, 5, 12, body);
        drawPixel(gc, 10, 12, body);
    }

    private static void drawFox(GraphicsContext gc) {
        Color body = Color.web("#FF6F00"); // Orange vif
        Color white = Color.WHITE;
        Color dark = Color.web("#BF360C"); // Orange très foncé
        Color eye = Color.web("#2E7D32"); // Vert

        // Oreilles triangulaires
        drawPixel(gc, 5, 6, body);
        drawPixel(gc, 6, 5, body);
        drawPixel(gc, 9, 5, body);
        drawPixel(gc, 10, 6, body);
        drawPixel(gc, 6, 6, white);
        drawPixel(gc, 9, 6, white);

        // Tête
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 7, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 8, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 9, body);

        // Museau blanc
        drawPixel(gc, 7, 9, white);
        drawPixel(gc, 8, 9, white);
        drawPixel(gc, 7, 10, white);
        drawPixel(gc, 8, 10, white);

        // Corps
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, body);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 12, body);

        // Yeux
        drawPixel(gc, 6, 8, eye);
        drawPixel(gc, 9, 8, eye);

        // Nez
        drawPixel(gc, 7, 10, dark);
        drawPixel(gc, 8, 10, dark);

        // Queue touffue
        drawPixel(gc, 9, 12, body);
        drawPixel(gc, 10, 12, body);
        drawPixel(gc, 10, 13, white);
    }

    private static void drawSnake(GraphicsContext gc) {
        Color body = Color.web("#7B1FA2"); // Violet
        Color pattern = Color.web("#9C27B0"); // Violet clair
        Color eye = Color.web("#FFEB3B"); // Jaune
        Color tongue = Color.web("#F44336"); // Rouge

        // Corps en S
        drawPixel(gc, 7, 6, body);
        drawPixel(gc, 8, 6, body);

        drawPixel(gc, 6, 7, body);
        drawPixel(gc, 7, 7, body);
        drawPixel(gc, 8, 7, body);

        drawPixel(gc, 7, 8, body);
        drawPixel(gc, 8, 8, body);
        drawPixel(gc, 9, 8, body);

        drawPixel(gc, 7, 9, body);
        drawPixel(gc, 8, 9, body);
        drawPixel(gc, 9, 9, body);

        drawPixel(gc, 6, 10, body);
        drawPixel(gc, 7, 10, body);
        drawPixel(gc, 8, 10, body);

        drawPixel(gc, 7, 11, body);
        drawPixel(gc, 8, 11, body);

        drawPixel(gc, 7, 12, body);

        // Motifs
        drawPixel(gc, 7, 7, pattern);
        drawPixel(gc, 8, 9, pattern);
        drawPixel(gc, 7, 11, pattern);

        // Tête
        drawPixel(gc, 7, 6, body);
        drawPixel(gc, 8, 6, body);

        // Œil
        drawPixel(gc, 7, 6, eye);

        // Langue
        drawPixel(gc, 8, 5, tongue);
        drawPixel(gc, 9, 5, tongue);
    }

    // ========== FAMILIERS ÉPIQUES ==========

    private static void drawDragon(GraphicsContext gc) {
        Color body = Color.web("#D32F2F"); // Rouge dragon
        Color wing = Color.web("#C62828"); // Rouge foncé
        Color horn = Color.web("#FFEB3B"); // Jaune/or
        Color eye = Color.web("#FFA000"); // Orange
        Color fire = Color.web("#FF6F00"); // Orange feu

        // Ailes
        drawPixel(gc, 4, 8, wing);
        drawPixel(gc, 5, 7, wing);
        drawPixel(gc, 5, 8, wing);

        drawPixel(gc, 10, 7, wing);
        drawPixel(gc, 10, 8, wing);
        drawPixel(gc, 11, 8, wing);

        // Corps
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 8, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 9, body);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 10, body);

        // Tête avec cornes
        drawPixel(gc, 6, 6, horn);
        drawPixel(gc, 9, 6, horn);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 7, body);

        // Yeux
        drawPixel(gc, 7, 8, eye);
        drawPixel(gc, 8, 8, eye);

        // Queue
        drawPixel(gc, 9, 10, body);
        drawPixel(gc, 10, 11, body);
        drawPixel(gc, 11, 11, horn);

        // Flammes (souffle)
        drawPixel(gc, 5, 9, fire);
        drawPixel(gc, 4, 9, fire);
        drawPixel(gc, 3, 10, fire);
    }

    private static void drawPhoenix(GraphicsContext gc) {
        Color body = Color.web("#FF5722"); // Orange/rouge
        Color fire = Color.web("#FFC107"); // Jaune/or
        Color wing = Color.web("#FF9800"); // Orange
        Color eye = Color.web("#FFEB3B"); // Jaune brillant

        // Flammes autour (aura)
        drawPixel(gc, 5, 6, fire);
        drawPixel(gc, 10, 6, fire);
        drawPixel(gc, 4, 8, fire);
        drawPixel(gc, 11, 8, fire);

        // Corps
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 7, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 8, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 9, body);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 10, body);

        // Ailes de feu
        drawPixel(gc, 5, 8, wing);
        drawPixel(gc, 4, 9, fire);
        drawPixel(gc, 5, 9, wing);

        drawPixel(gc, 10, 8, wing);
        drawPixel(gc, 11, 9, fire);
        drawPixel(gc, 10, 9, wing);

        // Tête
        drawPixel(gc, 7, 6, body);
        drawPixel(gc, 8, 6, body);

        // Crête de feu
        drawPixel(gc, 7, 5, fire);
        drawPixel(gc, 8, 5, fire);
        drawPixel(gc, 9, 5, fire);

        // Œil
        drawPixel(gc, 7, 7, eye);

        // Queue de feu
        drawPixel(gc, 7, 11, wing);
        drawPixel(gc, 8, 11, wing);
        drawPixel(gc, 6, 12, fire);
        drawPixel(gc, 7, 12, fire);
        drawPixel(gc, 8, 12, fire);
        drawPixel(gc, 9, 12, fire);
    }

    private static void drawGolem(GraphicsContext gc) {
        Color stone = Color.web("#78909C"); // Gris pierre
        Color dark = Color.web("#455A64"); // Gris foncé
        Color moss = Color.web("#66BB6A"); // Vert mousse
        Color eye = Color.web("#FF6F00"); // Orange magma

        // Corps massif
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 8, stone);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 9, stone);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 10, stone);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, stone);

        // Tête
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 6, stone);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 7, stone);

        // Fissures
        drawPixel(gc, 7, 7, dark);
        drawPixel(gc, 8, 9, dark);
        drawPixel(gc, 6, 10, dark);

        // Mousse
        drawPixel(gc, 9, 7, moss);
        drawPixel(gc, 10, 9, moss);
        drawPixel(gc, 5, 10, moss);

        // Yeux lumineux (magma)
        drawPixel(gc, 6, 7, eye);
        drawPixel(gc, 9, 7, eye);

        // Bras
        drawPixel(gc, 4, 9, stone);
        drawPixel(gc, 3, 10, stone);
        drawPixel(gc, 11, 9, stone);
        drawPixel(gc, 12, 10, stone);

        // Jambes
        drawPixel(gc, 6, 12, stone);
        drawPixel(gc, 9, 12, stone);
    }

    private static void drawTiger(GraphicsContext gc) {
        Color body = Color.WHITE;
        Color stripe = Color.BLACK;
        Color eye = Color.web("#2196F3"); // Bleu
        Color nose = Color.web("#FFB6C1"); // Rose

        // Oreilles
        drawPixel(gc, 5, 6, body);
        drawPixel(gc, 6, 5, body);
        drawPixel(gc, 9, 5, body);
        drawPixel(gc, 10, 6, body);

        // Tête
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 7, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 8, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 9, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 10, body);

        // Rayures noires caractéristiques
        drawPixel(gc, 6, 7, stripe);
        drawPixel(gc, 9, 7, stripe);
        drawPixel(gc, 5, 8, stripe);
        drawPixel(gc, 10, 8, stripe);
        drawPixel(gc, 7, 8, stripe);

        // Corps
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, body);
        drawPixel(gc, 7, 11, stripe);
        drawPixel(gc, 8, 11, stripe);

        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 12, body);
        drawPixel(gc, 6, 12, stripe);
        drawPixel(gc, 9, 12, stripe);

        // Yeux
        drawPixel(gc, 6, 8, eye);
        drawPixel(gc, 9, 8, eye);

        // Nez
        drawPixel(gc, 7, 9, nose);
        drawPixel(gc, 8, 9, nose);
    }

    private static void drawSpirit(GraphicsContext gc) {
        Color body = Color.web("#9FA8DA"); // Bleu/violet pâle spectral
        Color glow = Color.web("#C5CAE9"); // Bleu très pâle
        Color eye = Color.web("#00E5FF"); // Cyan lumineux
        Color trail = Color.web("#E8EAF6"); // Presque blanc

        // Forme vaporeuse du corps
        drawPixel(gc, 7, 6, glow);
        drawPixel(gc, 8, 6, glow);

        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 7, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 8, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 9, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 10, body);

        // Traînée spectrale
        drawPixel(gc, 5, 11, trail);
        drawPixel(gc, 7, 11, glow);
        drawPixel(gc, 8, 11, glow);
        drawPixel(gc, 10, 11, trail);

        drawPixel(gc, 6, 12, trail);
        drawPixel(gc, 7, 12, trail);
        drawPixel(gc, 8, 12, trail);
        drawPixel(gc, 9, 12, trail);

        // Yeux lumineux
        drawPixel(gc, 6, 8, eye);
        drawPixel(gc, 9, 8, eye);

        // Aura
        drawPixel(gc, 5, 7, glow);
        drawPixel(gc, 10, 7, glow);
    }

    // ========== FAMILIERS LÉGENDAIRES ==========

    private static void drawUnicorn(GraphicsContext gc) {
        Color body = Color.WHITE;
        Color horn = Color.web("#FFD700"); // Or
        Color mane = Color.web("#FF69B4"); // Rose
        Color eye = Color.web("#4FC3F7"); // Bleu ciel
        Color magic = Color.web("#E1BEE7"); // Violet clair (magie)

        // Corne dorée (spirale)
        drawPixel(gc, 7, 4, horn);
        drawPixel(gc, 8, 5, horn);

        // Tête
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 6, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 7, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 8, body);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 9, body);

        // Crinière magique
        drawPixel(gc, 6, 6, mane);
        drawPixel(gc, 5, 7, mane);
        drawPixel(gc, 5, 8, magic);
        drawPixel(gc, 10, 6, mane);
        drawPixel(gc, 10, 7, magic);

        // Corps
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 10, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 11, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 12, body);

        // Œil
        drawPixel(gc, 7, 7, eye);

        // Particules magiques autour
        drawPixel(gc, 4, 6, magic);
        drawPixel(gc, 11, 8, magic);
        drawPixel(gc, 5, 10, magic);
        drawPixel(gc, 10, 12, magic);
    }

    private static void drawDemon(GraphicsContext gc) {
        Color body = Color.web("#311B92"); // Violet très foncé/noir
        Color horn = Color.web("#B71C1C"); // Rouge sang
        Color eye = Color.web("#F44336"); // Rouge lumineux
        Color claw = Color.web("#880E4F"); // Rouge foncé
        Color aura = Color.web("#4A148C"); // Violet foncé aura

        // Aura maléfique
        drawPixel(gc, 5, 6, aura);
        drawPixel(gc, 10, 6, aura);
        drawPixel(gc, 4, 8, aura);
        drawPixel(gc, 11, 8, aura);

        // Cornes
        drawPixel(gc, 5, 5, horn);
        drawPixel(gc, 6, 6, horn);
        drawPixel(gc, 9, 6, horn);
        drawPixel(gc, 10, 5, horn);

        // Tête
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 7, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 8, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 9, body);

        // Corps
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 10, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 11, body);

        // Yeux rouges brillants
        drawPixel(gc, 6, 8, eye);
        drawPixel(gc, 9, 8, eye);

        // Griffes
        drawPixel(gc, 4, 11, claw);
        drawPixel(gc, 3, 12, claw);
        drawPixel(gc, 11, 11, claw);
        drawPixel(gc, 12, 12, claw);

        // Queue avec pointe
        drawPixel(gc, 9, 12, body);
        drawPixel(gc, 10, 12, claw);
        drawPixel(gc, 11, 13, horn);
    }

    private static void drawAngel(GraphicsContext gc) {
        Color body = Color.WHITE;
        Color wing = Color.web("#FFF9C4"); // Jaune très pâle/doré
        Color halo = Color.web("#FFD700"); // Or
        Color eye = Color.web("#64B5F6"); // Bleu ciel
        Color light = Color.web("#FFFDE7"); // Lumière divine

        // Halo doré
        drawPixel(gc, 6, 4, halo);
        drawPixel(gc, 7, 4, halo);
        drawPixel(gc, 8, 4, halo);
        drawPixel(gc, 9, 4, halo);

        // Rayons de lumière
        drawPixel(gc, 5, 5, light);
        drawPixel(gc, 10, 5, light);

        // Tête
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 6, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 7, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 8, body);

        // Ailes majestueuses
        drawPixel(gc, 4, 8, wing);
        drawPixel(gc, 5, 8, wing);
        drawPixel(gc, 5, 9, wing);
        drawPixel(gc, 4, 9, wing);
        drawPixel(gc, 3, 9, wing);
        drawPixel(gc, 4, 10, wing);

        drawPixel(gc, 10, 8, wing);
        drawPixel(gc, 11, 8, wing);
        drawPixel(gc, 10, 9, wing);
        drawPixel(gc, 11, 9, wing);
        drawPixel(gc, 12, 9, wing);
        drawPixel(gc, 11, 10, wing);

        // Corps
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 9, body);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 10, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, body);

        // Œil
        drawPixel(gc, 7, 7, eye);

        // Lumière divine autour
        drawPixel(gc, 5, 6, light);
        drawPixel(gc, 10, 6, light);
        drawPixel(gc, 6, 12, light);
        drawPixel(gc, 9, 12, light);
    }

    private static void drawLeviathan(GraphicsContext gc) {
        Color body = Color.web("#006064"); // Bleu océan profond
        Color scale = Color.web("#00838F"); // Bleu écailles
        Color eye = Color.web("#00E5FF"); // Cyan lumineux
        Color fin = Color.web("#0097A7"); // Bleu-vert
        Color water = Color.web("#4DD0E1"); // Bleu eau

        // Vagues d'eau
        drawPixel(gc, 4, 12, water);
        drawPixel(gc, 5, 13, water);
        drawPixel(gc, 10, 13, water);
        drawPixel(gc, 11, 12, water);

        // Corps serpentin massif
        drawPixel(gc, 6, 6, body);
        drawPixel(gc, 7, 6, body);

        for (int i = 5; i <= 8; i++) drawPixel(gc, i, 7, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 8, body);
        for (int i = 6; i <= 10; i++) drawPixel(gc, i, 9, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 10, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, body);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 12, body);

        // Écailles
        drawPixel(gc, 6, 8, scale);
        drawPixel(gc, 8, 8, scale);
        drawPixel(gc, 7, 10, scale);
        drawPixel(gc, 9, 10, scale);

        // Nageoires dorsales
        drawPixel(gc, 7, 5, fin);
        drawPixel(gc, 8, 4, fin);
        drawPixel(gc, 7, 7, fin);
        drawPixel(gc, 9, 7, fin);

        // Œil lumineux
        drawPixel(gc, 6, 7, eye);

        // Nageoires latérales
        drawPixel(gc, 4, 9, fin);
        drawPixel(gc, 3, 10, fin);
        drawPixel(gc, 11, 9, fin);
        drawPixel(gc, 12, 10, fin);
    }

    // ========== COMPAGNONS ==========

    private static void drawEgg(GraphicsContext gc) {
        Color shell = Color.web("#F5F5F5"); // Blanc cassé
        Color spot = Color.web("#E0E0E0"); // Gris très clair
        Color crack = Color.web("#BDBDBD"); // Gris

        // Forme d'œuf
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 7, shell);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 8, shell);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 9, shell);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 10, shell);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, shell);
        for (int i = 7; i <= 8; i++) drawPixel(gc, i, 12, shell);

        // Taches
        drawPixel(gc, 6, 8, spot);
        drawPixel(gc, 9, 9, spot);
        drawPixel(gc, 7, 11, spot);

        // Fissures (signe d'éclosion proche)
        drawPixel(gc, 7, 8, crack);
        drawPixel(gc, 8, 9, crack);
    }

    // ========== BULBASAUR (Type Plante) ==========

    private static void drawBulbasaurStarter(GraphicsContext gc) {
        Color body = Color.web("#4CAF50"); // Vert
        Color bulb = Color.web("#81C784"); // Vert clair (bulbe)
        Color spot = Color.web("#2E7D32"); // Vert foncé
        Color eye = Color.web("#D32F2F"); // Rouge

        // Bulbe sur le dos
        drawPixel(gc, 8, 7, bulb);
        drawPixel(gc, 9, 7, bulb);
        drawPixel(gc, 8, 8, bulb);
        drawPixel(gc, 9, 8, bulb);

        // Taches sur bulbe
        drawPixel(gc, 8, 7, spot);

        // Corps
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 9, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 10, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, body);

        // Tête
        for (int i = 6; i <= 7; i++) drawPixel(gc, i, 8, body);

        // Yeux
        drawPixel(gc, 6, 8, eye);

        // Pattes
        drawPixel(gc, 5, 11, body);
        drawPixel(gc, 10, 11, body);
    }

    private static void drawBulbasaurEvo1(GraphicsContext gc) {
        Color body = Color.web("#4CAF50"); // Vert
        Color flower = Color.web("#FF69B4"); // Rose (fleur qui pousse)
        Color leaf = Color.web("#66BB6A"); // Vert clair
        Color spot = Color.web("#2E7D32"); // Vert foncé
        Color eye = Color.web("#D32F2F"); // Rouge

        // Fleur en croissance sur le dos
        drawPixel(gc, 8, 6, flower);
        drawPixel(gc, 9, 6, flower);
        drawPixel(gc, 7, 7, leaf);
        drawPixel(gc, 8, 7, flower);
        drawPixel(gc, 9, 7, flower);
        drawPixel(gc, 10, 7, leaf);
        drawPixel(gc, 8, 8, leaf);
        drawPixel(gc, 9, 8, leaf);

        // Taches
        drawPixel(gc, 8, 7, spot);

        // Corps plus grand
        for (int i = 5; i <= 9; i++) drawPixel(gc, i, 9, body);
        for (int i = 4; i <= 10; i++) drawPixel(gc, i, 10, body);
        for (int i = 5; i <= 9; i++) drawPixel(gc, i, 11, body);

        // Tête
        for (int i = 5; i <= 7; i++) drawPixel(gc, i, 8, body);

        // Yeux
        drawPixel(gc, 6, 8, eye);

        // Pattes
        drawPixel(gc, 4, 11, body);
        drawPixel(gc, 10, 11, body);
    }

    private static void drawBulbasaurFinal(GraphicsContext gc) {
        Color body = Color.web("#4CAF50"); // Vert
        Color flower = Color.web("#E91E63"); // Rose vif (grande fleur)
        Color petal = Color.web("#F48FB1"); // Rose clair
        Color leaf = Color.web("#66BB6A"); // Vert clair
        Color spot = Color.web("#1B5E20"); // Vert très foncé
        Color eye = Color.web("#D32F2F"); // Rouge

        // Grande fleur épanouie
        drawPixel(gc, 7, 5, petal);
        drawPixel(gc, 8, 5, flower);
        drawPixel(gc, 9, 5, petal);
        drawPixel(gc, 6, 6, petal);
        drawPixel(gc, 7, 6, flower);
        drawPixel(gc, 8, 6, flower);
        drawPixel(gc, 9, 6, flower);
        drawPixel(gc, 10, 6, petal);
        drawPixel(gc, 7, 7, leaf);
        drawPixel(gc, 8, 7, leaf);
        drawPixel(gc, 9, 7, leaf);

        // Taches multiples
        drawPixel(gc, 7, 6, spot);
        drawPixel(gc, 9, 7, spot);

        // Corps massif
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 9, body);
        for (int i = 4; i <= 11; i++) drawPixel(gc, i, 10, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 11, body);

        // Tête
        for (int i = 5; i <= 7; i++) drawPixel(gc, i, 8, body);

        // Yeux
        drawPixel(gc, 5, 8, eye);
        drawPixel(gc, 6, 8, eye);

        // Pattes larges
        drawPixel(gc, 4, 11, body);
        drawPixel(gc, 4, 12, body);
        drawPixel(gc, 11, 11, body);
        drawPixel(gc, 11, 12, body);
    }

    // ========== CHARMANDER (Type Feu) ==========

    private static void drawCharmanderStarter(GraphicsContext gc) {
        Color body = Color.web("#FF6F00"); // Orange vif
        Color belly = Color.web("#FFF9C4"); // Jaune pâle (ventre)
        Color flame = Color.web("#F44336"); // Rouge (flamme queue)
        Color eye = Color.web("#2E7D32"); // Vert

        // Corps
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 9, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 10, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, body);

        // Ventre
        drawPixel(gc, 7, 10, belly);
        drawPixel(gc, 8, 10, belly);

        // Tête
        for (int i = 6; i <= 8; i++) drawPixel(gc, i, 8, body);
        drawPixel(gc, 7, 7, body);

        // Yeux
        drawPixel(gc, 6, 8, eye);

        // Queue avec flamme
        drawPixel(gc, 10, 10, body);
        drawPixel(gc, 11, 10, flame);
        drawPixel(gc, 11, 9, flame);
        drawPixel(gc, 12, 9, flame);

        // Pattes
        drawPixel(gc, 5, 11, body);
        drawPixel(gc, 10, 11, body);
    }

    private static void drawCharmanderEvo1(GraphicsContext gc) {
        Color body = Color.web("#D84315"); // Orange rouge
        Color belly = Color.web("#FFECB3"); // Jaune
        Color flame = Color.web("#D32F2F"); // Rouge vif
        Color horn = Color.web("#BF360C"); // Rouge foncé
        Color eye = Color.web("#1B5E20"); // Vert foncé

        // Corps plus grand
        for (int i = 5; i <= 9; i++) drawPixel(gc, i, 9, body);
        for (int i = 4; i <= 10; i++) drawPixel(gc, i, 10, body);
        for (int i = 5; i <= 9; i++) drawPixel(gc, i, 11, body);

        // Ventre
        drawPixel(gc, 6, 10, belly);
        drawPixel(gc, 7, 10, belly);
        drawPixel(gc, 8, 10, belly);

        // Tête avec petite corne
        drawPixel(gc, 6, 7, horn);
        for (int i = 5; i <= 8; i++) drawPixel(gc, i, 8, body);

        // Yeux
        drawPixel(gc, 6, 8, eye);

        // Queue avec flamme plus grosse
        drawPixel(gc, 10, 10, body);
        drawPixel(gc, 11, 10, body);
        drawPixel(gc, 11, 9, flame);
        drawPixel(gc, 12, 9, flame);
        drawPixel(gc, 11, 8, flame);
        drawPixel(gc, 12, 8, flame);

        // Pattes
        drawPixel(gc, 4, 11, body);
        drawPixel(gc, 10, 11, body);
    }

    private static void drawCharmanderFinal(GraphicsContext gc) {
        Color body = Color.web("#BF360C"); // Orange très foncé
        Color belly = Color.web("#FFD54F"); // Jaune doré
        Color flame = Color.web("#D50000"); // Rouge intense
        Color wing = Color.web("#F4511E"); // Orange rouge
        Color horn = Color.web("#880E4F"); // Rouge sombre
        Color eye = Color.web("#FFC107"); // Jaune vif

        // Ailes
        drawPixel(gc, 4, 8, wing);
        drawPixel(gc, 5, 8, wing);
        drawPixel(gc, 4, 9, wing);

        drawPixel(gc, 10, 8, wing);
        drawPixel(gc, 11, 8, wing);
        drawPixel(gc, 11, 9, wing);

        // Corps massif
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 9, body);
        for (int i= 4; i <= 11; i++) drawPixel(gc, i, 10, body);
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 11, body);

        // Ventre doré
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 10, belly);

        // Tête avec cornes
        drawPixel(gc, 5, 6, horn);
        drawPixel(gc, 8, 6, horn);
        for (int i = 5; i <= 8; i++) drawPixel(gc, i, 8, body);
        drawPixel(gc, 6, 7, body);
        drawPixel(gc, 7, 7, body);

        // Yeux féroces
        drawPixel(gc, 6, 8, eye);

        // Queue avec flamme intense
        drawPixel(gc, 10, 11, body);
        drawPixel(gc, 11, 11, body);
        drawPixel(gc, 11, 10, flame);
        drawPixel(gc, 12, 10, flame);
        drawPixel(gc, 11, 9, flame);
        drawPixel(gc, 12, 9, flame);
        drawPixel(gc, 12, 8, flame);
        drawPixel(gc, 13, 8, flame);

        // Pattes robustes
        drawPixel(gc, 4, 11, body);
        drawPixel(gc, 4, 12, body);
        drawPixel(gc, 11, 12, body);
    }

    // ========== SQUIRTLE (Type Eau) ==========

    private static void drawSquirtleStarter(GraphicsContext gc) {
        Color body = Color.web("#42A5F5"); // Bleu
        Color shell = Color.web("#8D6E63"); // Marron (carapace)
        Color belly = Color.web("#FFF9C4"); // Jaune pâle
        Color eye = Color.web("#5D4037"); // Marron foncé
        Color tail = Color.web("#1976D2"); // Bleu foncé

        // Carapace
        for (int i = 7; i <= 9; i++) drawPixel(gc, i, 8, shell);
        for (int i = 6; i <= 10; i++) drawPixel(gc, i, 9, shell);
        for (int i = 7; i <= 9; i++) drawPixel(gc, i, 10, shell);

        // Tête
        for (int i = 5; i <= 7; i++) drawPixel(gc, i, 9, body);
        drawPixel(gc, 6, 8, body);

        // Yeux
        drawPixel(gc, 6, 9, eye);

        // Corps/ventre
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, body);
        drawPixel(gc, 7, 11, belly);
        drawPixel(gc, 8, 11, belly);

        // Queue
        drawPixel(gc, 10, 10, tail);
        drawPixel(gc, 11, 10, tail);
        drawPixel(gc, 11, 9, tail);

        // Pattes
        drawPixel(gc, 6, 12, body);
        drawPixel(gc, 9, 12, body);
    }

    private static void drawSquirtleEvo1(GraphicsContext gc) {
        Color body = Color.web("#1E88E5"); // Bleu plus foncé
        Color shell = Color.web("#6D4C41"); // Marron foncé
        Color belly = Color.web("#FFECB3"); // Jaune
        Color spike = Color.WHITE;
        Color eye = Color.web("#3E2723"); // Marron très foncé
        Color tail = Color.web("#0D47A1"); // Bleu très foncé

        // Carapace avec pointes
        drawPixel(gc, 7, 7, spike);
        drawPixel(gc, 9, 7, spike);

        for (int i = 6; i <= 10; i++) drawPixel(gc, i, 8, shell);
        for (int i = 5; i <= 11; i++) drawPixel(gc, i, 9, shell);
        for (int i = 6; i <= 10; i++) drawPixel(gc, i, 10, shell);

        // Tête
        for (int i = 4; i <= 6; i++) drawPixel(gc, i, 9, body);
        drawPixel(gc, 5, 8, body);

        // Yeux
        drawPixel(gc, 5, 9, eye);

        // Oreilles/extensions
        drawPixel(gc, 4, 8, body);
        drawPixel(gc, 3, 8, tail);

        // Corps
        for (int i = 5; i <= 10; i++) drawPixel(gc, i, 11, body);
        for (int i = 6; i <= 9; i++) drawPixel(gc, i, 11, belly);

        // Queue large
        drawPixel(gc, 11, 10, tail);
        drawPixel(gc, 12, 10, tail);
        drawPixel(gc, 11, 9, tail);
        drawPixel(gc, 12, 9, tail);
        drawPixel(gc, 12, 8, tail);

        // Pattes
        drawPixel(gc, 5, 12, body);
        drawPixel(gc, 10, 12, body);
    }

    private static void drawSquirtleFinal(GraphicsContext gc) {
        Color body = Color.web("#0D47A1"); // Bleu profond
        Color shell = Color.web("#5D4037"); // Marron très foncé
        Color belly = Color.web("#FFD54F"); // Jaune doré
        Color spike = Color.web("#E0E0E0"); // Gris clair
        Color cannon = Color.web("#616161"); // Gris (canons)
        Color eye = Color.web("#D32F2F"); // Rouge
        Color water = Color.web("#4FC3F7"); // Bleu eau

        // Carapace massive avec grandes pointes
        drawPixel(gc, 6, 6, spike);
        drawPixel(gc, 8, 6, spike);
        drawPixel(gc, 10, 6, spike);

        for (int i = 5; i <= 11; i++) drawPixel(gc, i, 8, shell);
        for (int i = 4; i <= 12; i++) drawPixel(gc, i, 9, shell);
        for (int i = 5; i <= 11; i++) drawPixel(gc, i, 10, shell);

        // Canons à eau
        drawPixel(gc, 6, 7, cannon);
        drawPixel(gc, 10, 7, cannon);
        drawPixel(gc, 6, 6, water);
        drawPixel(gc, 10, 6, water);

        // Tête
        for (int i = 3; i <= 5; i++) drawPixel(gc, i, 9, body);
        drawPixel(gc, 4, 8, body);

        // Yeux
        drawPixel(gc, 4, 9, eye);

        // Corps robuste
        for (int i = 4; i <= 11; i++) drawPixel(gc, i, 11, body);
        for (int i = 6; i <= 10; i++) drawPixel(gc, i, 11, belly);

        // Queue puissante
        for (int i = 12; i <= 13; i++) {
            drawPixel(gc, i, 10, body);
            drawPixel(gc, i, 9, body);
        }

        // Pattes massives
        drawPixel(gc, 4, 12, body);
        drawPixel(gc, 5, 12, body);
        drawPixel(gc, 10, 12, body);
        drawPixel(gc, 11, 12, body);
    }

    // ========== SPRITE PAR DÉFAUT ==========

    private static void drawDefault(GraphicsContext gc) {
        Color bg = Color.web("#9E9E9E");
        Color mark = Color.web("#424242");

        // Point d'interrogation pixelisé
        drawPixel(gc, 7, 6, mark);
        drawPixel(gc, 8, 6, mark);
        drawPixel(gc, 6, 7, mark);
        drawPixel(gc, 9, 7, mark);
        drawPixel(gc, 9, 8, mark);
        drawPixel(gc, 8, 9, mark);

        drawPixel(gc, 8, 11, mark);

        // Fond
        for (int i = 5; i <= 10; i++) {
            for (int j = 5; j <= 12; j++) {
                if (gc.getFillRule() == null || !mark.equals(Color.web("#424242"))) {
                    // Ne pas écraser les pixels du point d'interrogation
                }
            }
        }
    }
}
