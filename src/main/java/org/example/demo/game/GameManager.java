package org.example.demo.game;

import org.example.demo.entities.Boss;
import org.example.demo.entities.Monster;
import org.example.demo.entities.Player;
import org.example.demo.systems.CombatSystem;
import org.example.demo.systems.ShopSystem;

/**
 * Gestionnaire principal du jeu : coordonne tous les systèmes.
 */
public class GameManager {
    private static final int MONSTERS_PER_CYCLE = 5;

    private GameData gameData;
    private Player player;
    private Monster currentMonster;
    private GameState currentState;

    private CombatSystem combatSystem;
    private ShopSystem shopSystem;

    private double bossDpsTimer;

    public GameManager() {
        loadGame();
        initializeSystems();

        // Si le joueur était mort au chargement, on le réanime
        if (player.isDead()) {
            player.revive();
        }

        spawnCurrentMonster();
        currentState = GameState.COMBAT;
        bossDpsTimer = 0;
    }

    /**
     * Charge ou crée une nouvelle partie
     */
    private void loadGame() {
        gameData = GameData.load();
        player = gameData.getPlayer();
    }

    /**
     * Initialise les systèmes de jeu
     */
    private void initializeSystems() {
        combatSystem = new CombatSystem(player);
        shopSystem = new ShopSystem(player);
        shopSystem.setTotalUpgradesPurchased(gameData.getTotalUpgradesPurchased());
        shopSystem.setTotalEggsPurchased(gameData.getTotalEggsPurchased());
    }

    /**
     * Spawn le monstre correspondant à l'index actuel
     */
    private void spawnCurrentMonster() {
        int index = gameData.getCurrentMonsterIndex();
        int cycle = gameData.getCurrentCycle();

        if (index == MONSTERS_PER_CYCLE + 1) {
            // Boss
            currentMonster = new Boss(cycle);
        } else {
            // Monstre normal
            currentMonster = new Monster(cycle);
        }
    }

    /**
     * Gère un clic du joueur sur le monstre
     */
    public CombatSystem.AttackResult handlePlayerClick() {
        if (currentState != GameState.COMBAT) {
            return null;
        }

        CombatSystem.AttackResult result = combatSystem.attack(currentMonster);

        if (result.monsterDied) {
            onMonsterKilled();
        }

        return result;
    }

    /**
     * Appelé quand un monstre meurt
     */
    private void onMonsterKilled() {
        // Drop d'or
        int gold = currentMonster.getGoldDrop();
        player.addGold(gold);

        // Si c'était un boss
        if (currentMonster.isBoss()) {
            player.incrementBossKills();
            gameData.setCurrentCycle(gameData.getCurrentCycle() + 1);
            gameData.setCurrentMonsterIndex(1);
        } else {
            gameData.setCurrentMonsterIndex(gameData.getCurrentMonsterIndex() + 1);
        }

        // Sauvegarde et spawn du prochain
        saveGame();
        spawnCurrentMonster();
    }

    /**
     * Update de la game loop (appelé à chaque frame)
     */
    public void update(double deltaTime) {
        if (currentState == GameState.COMBAT) {
            // Mise à jour du bouclier du boss si c'en est un
            if (currentMonster instanceof Boss) {
                ((Boss) currentMonster).updateShield(deltaTime);
            }

            // Application des DPS du boss
            if (currentMonster.isBoss()) {
                bossDpsTimer += deltaTime;
                if (bossDpsTimer >= 1.0) { // Chaque seconde
                    combatSystem.applyBossDps(currentMonster, 1.0);
                    bossDpsTimer = 0;

                    // Vérifier la mort du joueur
                    if (player.isDead()) {
                        onPlayerDeath();
                    }
                }
            }
        }
    }

    /**
     * Appelé quand le joueur meurt
     */
    private void onPlayerDeath() {
        currentState = GameState.DEATH;
        // Le joueur garde son or et ses upgrades
        saveGame();
    }

    /**
     * Ressuscite le joueur et recule de 5 monstres (ou au début du cycle)
     */
    public void respawnPlayer() {
        player.revive();

        int currentIndex = gameData.getCurrentMonsterIndex();
        int newIndex;

        if (currentIndex == MONSTERS_PER_CYCLE + 1) {
            // Était sur le boss -> retour au monstre 1
            newIndex = 1;
        } else if (currentIndex > 5) {
            // Recule de 5 monstres, minimum 1
            newIndex = Math.max(1, currentIndex - 5);
        } else {
            // Déjà au début, on reste au monstre 1
            newIndex = 1;
        }

        gameData.setCurrentMonsterIndex(newIndex);
        spawnCurrentMonster();
        currentState = GameState.COMBAT;
        bossDpsTimer = 0;

        // Sauvegarder l'état "vivant"
        saveGame();
    }

    /**
     * Sauvegarde la partie
     */
    public void saveGame() {
        gameData.setTotalUpgradesPurchased(shopSystem.getTotalUpgradesPurchased());
        gameData.setTotalEggsPurchased(shopSystem.getTotalEggsPurchased());
        gameData.save();
    }

    // Getters
    public Player getPlayer() { return player; }
    public Monster getCurrentMonster() { return currentMonster; }
    public GameState getCurrentState() { return currentState; }
    public ShopSystem getShopSystem() { return shopSystem; }
    public CombatSystem getCombatSystem() { return combatSystem; }

    public void setCurrentState(GameState state) {
        this.currentState = state;
    }
}
