package org.example.demo.game;

import org.example.demo.entities.Player;

import java.io.*;

/**
 * Gère la sauvegarde et le chargement des données de jeu.
 */
public class GameData implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SAVE_FILE = "savegame.dat";

    private Player player;
    private int currentMonsterIndex; // 1-6 (1-5 normaux, 6 = boss)
    private int currentCycle;        // Quel cycle de boss (1, 2, 3...)
    private int totalUpgradesPurchased;
    private int totalEggsPurchased;

    public GameData() {
        this.player = new Player();
        this.currentMonsterIndex = 1;
        this.currentCycle = 1;
        this.totalUpgradesPurchased = 0;
        this.totalEggsPurchased = 0;
    }

    /**
     * Sauvegarde les données dans un fichier
     */
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(this);
            System.out.println("Partie sauvegardée !");
        } catch (IOException e) {
            System.err.println("Erreur de sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Charge les données depuis le fichier
     */
    public static GameData load() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            System.out.println("Aucune sauvegarde trouvée, nouvelle partie.");
            return new GameData();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(SAVE_FILE))) {
            GameData data = (GameData) ois.readObject();
            System.out.println("Partie chargée !");
            return data;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur de chargement : " + e.getMessage());
            return new GameData();
        }
    }

    // Getters et Setters
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }

    public int getCurrentMonsterIndex() { return currentMonsterIndex; }
    public void setCurrentMonsterIndex(int index) { this.currentMonsterIndex = index; }

    public int getCurrentCycle() { return currentCycle; }
    public void setCurrentCycle(int cycle) { this.currentCycle = cycle; }

    public int getTotalUpgradesPurchased() { return totalUpgradesPurchased; }
    public void setTotalUpgradesPurchased(int count) { this.totalUpgradesPurchased = count; }

    public int getTotalEggsPurchased() { return totalEggsPurchased; }
    public void setTotalEggsPurchased(int count) { this.totalEggsPurchased = count; }
}
