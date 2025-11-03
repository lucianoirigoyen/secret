package org.example.demo.ui;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import org.example.demo.game.GameManager;
import org.example.demo.game.GameState;
import org.example.demo.entities.Familiar;
import org.example.demo.entities.Monster;
import org.example.demo.entities.Player;
import org.example.demo.systems.CombatSystem;
import org.example.demo.systems.UpgradeSystem;

/**
 * Panel principal du jeu avec JavaFX
 */
public class GamePanel extends StackPane {
    private final GameManager gameManager;
    private BorderPane mainLayout;

    // UI Components - Combat
    private Label monsterNameLabel;
    private Label monsterHpLabel;
    private ProgressBar monsterHpBar;
    private Rectangle monsterRect;
    private VBox damageNumbersContainer;

    // UI Components - Player Stats
    private Label playerHpLabel;
    private Label goldLabel;
    private Label clickDamageLabel;
    private Label critRateLabel;
    private Label bossesKilledLabel;

    // UI Components - Shop
    private Button shopButton;
    private Button buyUpgradeButton;
    private Button buyEggButton;
    private Label upgradeCostLabel;
    private Label eggCostLabel;
    private VBox shopPanel;
    private Label upgradeResultLabel;
    private Label familiarResultLabel;

    // UI Components - Death
    private StackPane deathOverlay;

    // Game Loop
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;

    public GamePanel() {
        gameManager = new GameManager();
        initializeUI();
        startGameLoop();
    }

    private void initializeUI() {
        mainLayout = new BorderPane();
        mainLayout.setTop(createPlayerStatsPanel());
        mainLayout.setCenter(createCombatPanel());
        mainLayout.setBottom(createActionPanel());

        shopPanel = createShopPanel();
        shopPanel.setVisible(false);
        mainLayout.setRight(shopPanel);

        deathOverlay = createDeathOverlay();
        deathOverlay.setVisible(false);

        getChildren().addAll(mainLayout, deathOverlay);
        updateUI();
    }

    private HBox createPlayerStatsPanel() {
        HBox statsBox = new HBox(20);
        statsBox.setPadding(new Insets(15));
        statsBox.setStyle("-fx-background-color: #2c3e50;");

        playerHpLabel = createStatLabel("❤ HP: 100/100");
        goldLabel = createStatLabel("💰 Gold: 0");
        clickDamageLabel = createStatLabel("⚔ Dégâts: 10");
        critRateLabel = createStatLabel("✨ Crit: 5%");
        bossesKilledLabel = createStatLabel("👑 Boss: 0");

        statsBox.getChildren().addAll(playerHpLabel, goldLabel, clickDamageLabel, critRateLabel, bossesKilledLabel);
        return statsBox;
    }

    private StackPane createCombatPanel() {
        StackPane combatPane = new StackPane();
        combatPane.setStyle("-fx-background-color: #34495e;");

        VBox combatContent = new VBox(20);
        combatContent.setAlignment(Pos.CENTER);

        monsterNameLabel = new Label("Monstre Niveau 1");
        monsterNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        monsterNameLabel.setTextFill(Color.WHITE);

        monsterHpLabel = new Label("50 / 50 HP");
        monsterHpLabel.setFont(Font.font("Arial", 18));
        monsterHpLabel.setTextFill(Color.LIGHTGRAY);

        monsterHpBar = new ProgressBar(1.0);
        monsterHpBar.setPrefWidth(300);
        monsterHpBar.setStyle("-fx-accent: #e74c3c;");

        monsterRect = new Rectangle(150, 150);
        monsterRect.setFill(Color.DARKRED);
        monsterRect.setStroke(Color.RED);
        monsterRect.setStrokeWidth(3);
        monsterRect.setArcWidth(20);
        monsterRect.setArcHeight(20);
        monsterRect.setOnMouseClicked(e -> handleMonsterClick());
        monsterRect.setStyle("-fx-cursor: hand;");

        damageNumbersContainer = new VBox();
        damageNumbersContainer.setAlignment(Pos.CENTER);
        damageNumbersContainer.setPickOnBounds(false);

        combatContent.getChildren().addAll(monsterNameLabel, monsterHpLabel, monsterHpBar, monsterRect);
        combatPane.getChildren().addAll(combatContent, damageNumbersContainer);
        return combatPane;
    }

    private HBox createActionPanel() {
        HBox actionBox = new HBox(15);
        actionBox.setPadding(new Insets(15));
        actionBox.setAlignment(Pos.CENTER);
        actionBox.setStyle("-fx-background-color: #2c3e50;");

        shopButton = new Button("🛒 Ouvrir le Shop");
        shopButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        shopButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10 20;");
        shopButton.setOnAction(e -> toggleShop());

        actionBox.getChildren().add(shopButton);
        return actionBox;
    }

    private VBox createShopPanel() {
        VBox shop = new VBox(15);
        shop.setPadding(new Insets(20));
        shop.setPrefWidth(280);
        shop.setStyle("-fx-background-color: #1abc9c;");

        Label shopTitle = new Label("🎁 SHOP");
        shopTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        shopTitle.setTextFill(Color.WHITE);

        Label upgradeTitle = new Label("AMÉLIORATIONS");
        upgradeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        upgradeTitle.setTextFill(Color.YELLOW);

        upgradeCostLabel = new Label("Coût: 50 💰");
        upgradeCostLabel.setFont(Font.font("Arial", 16));
        upgradeCostLabel.setTextFill(Color.WHITE);

        buyUpgradeButton = new Button("Acheter Amélioration");
        buyUpgradeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        buyUpgradeButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-padding: 10;");
        buyUpgradeButton.setOnAction(e -> handleUpgradePurchase());

        upgradeResultLabel = new Label("");
        upgradeResultLabel.setWrapText(true);
        upgradeResultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        upgradeResultLabel.setTextFill(Color.YELLOW);

        Label upgradesInfo = new Label("Améliorations possibles:\n• +10 HP Max (30%)\n• +2 Dégâts (30%)\n• +5% Crit (25%)\n• +20% Crit Mult (15%)");
        upgradesInfo.setFont(Font.font("Arial", 12));
        upgradesInfo.setTextFill(Color.WHITE);

        Label familiarTitle = new Label("\n🥚 FAMILIERS");
        familiarTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        familiarTitle.setTextFill(Color.YELLOW);

        eggCostLabel = new Label("Coût: 200 💰");
        eggCostLabel.setFont(Font.font("Arial", 16));
        eggCostLabel.setTextFill(Color.WHITE);

        buyEggButton = new Button("Acheter Œuf de Familier");
        buyEggButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        buyEggButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10;");
        buyEggButton.setOnAction(e -> handleEggPurchase());

        familiarResultLabel = new Label("");
        familiarResultLabel.setWrapText(true);
        familiarResultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        familiarResultLabel.setTextFill(Color.ORANGE);

        shop.getChildren().addAll(shopTitle, upgradeTitle, upgradeCostLabel, buyUpgradeButton, upgradeResultLabel, upgradesInfo, familiarTitle, eggCostLabel, buyEggButton, familiarResultLabel);
        return shop;
    }

    private StackPane createDeathOverlay() {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85);");
        overlay.setPickOnBounds(true);

        VBox deathPanel = new VBox(25);
        deathPanel.setAlignment(Pos.CENTER);
        deathPanel.setMaxSize(500, 400);
        deathPanel.setPadding(new Insets(40));
        deathPanel.setStyle("-fx-background-color: #2c3e50; -fx-border-color: #e74c3c; -fx-border-width: 4; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label deathTitle = new Label("💀 VOUS ÊTES MORT 💀");
        deathTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        deathTitle.setTextFill(Color.web("#e74c3c"));

        Label deathMessage = new Label("Vos améliorations et votre or sont conservés !\nRevenez 5 monstres en arrière pour farmer.");
        deathMessage.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        deathMessage.setTextFill(Color.WHITE);
        deathMessage.setWrapText(true);
        deathMessage.setStyle("-fx-text-alignment: center;");

        Button retryButton = new Button("🔄 RETRY");
        retryButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        retryButton.setPrefSize(200, 50);
        retryButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 5;");
        retryButton.setOnMouseEntered(e -> retryButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-background-radius: 5;"));
        retryButton.setOnMouseExited(e -> retryButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 5;"));
        retryButton.setOnAction(e -> handleRespawn());

        deathPanel.getChildren().addAll(deathTitle, deathMessage, retryButton);
        overlay.getChildren().add(deathPanel);
        return overlay;
    }

    private void handleMonsterClick() {
        if (gameManager.getCurrentState() != GameState.COMBAT) return;

        CombatSystem.AttackResult result = gameManager.handlePlayerClick();
        if (result != null) {
            if (result.dodged) {
                showFeedbackText("ESQUIVÉ !", Color.YELLOW);
                return;
            }
            if (result.blocked) {
                showFeedbackText("BOUCLIER !", Color.CYAN);
                return;
            }

            animateHit();
            showDamageNumber(result.damage, result.isCrit);
            updateUI();
        }
    }

    private void animateHit() {
        ScaleTransition scale = new ScaleTransition(Duration.millis(100), monsterRect);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(0.9);
        scale.setToY(0.9);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

    private void showDamageNumber(int damage, boolean isCrit) {
        Label damageLabel = new Label("-" + damage);
        damageLabel.setFont(Font.font("Arial", FontWeight.BOLD, isCrit ? 32 : 24));
        damageLabel.setTextFill(isCrit ? Color.ORANGE : Color.WHITE);
        damageNumbersContainer.getChildren().add(damageLabel);

        TranslateTransition move = new TranslateTransition(Duration.millis(1000), damageLabel);
        move.setByY(-100);
        FadeTransition fade = new FadeTransition(Duration.millis(1000), damageLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        ParallelTransition parallel = new ParallelTransition(move, fade);
        parallel.setOnFinished(e -> damageNumbersContainer.getChildren().remove(damageLabel));
        parallel.play();
    }

    private void showFeedbackText(String text, Color color) {
        Label feedbackLabel = new Label(text);
        feedbackLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        feedbackLabel.setTextFill(color);
        damageNumbersContainer.getChildren().add(feedbackLabel);

        ScaleTransition scale = new ScaleTransition(Duration.millis(300), feedbackLabel);
        scale.setFromX(0.5);
        scale.setFromY(0.5);
        scale.setToX(1.5);
        scale.setToY(1.5);

        FadeTransition fade = new FadeTransition(Duration.millis(800), feedbackLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setDelay(Duration.millis(300));

        ParallelTransition parallel = new ParallelTransition(scale, fade);
        parallel.setOnFinished(e -> damageNumbersContainer.getChildren().remove(feedbackLabel));
        parallel.play();
    }

    private void toggleShop() {
        shopPanel.setVisible(!shopPanel.isVisible());
        shopButton.setText(shopPanel.isVisible() ? "❌ Fermer Shop" : "🛒 Ouvrir Shop");
    }

    private void handleUpgradePurchase() {
        UpgradeSystem.UpgradeType upgrade = gameManager.getShopSystem().purchaseUpgrade();
        if (upgrade != null) {
            RotateTransition rotate = new RotateTransition(Duration.millis(500), buyUpgradeButton);
            rotate.setByAngle(360);
            rotate.play();

            upgradeResultLabel.setText("✨ " + upgrade.getDisplayName() + " !\n" + upgrade.getDescription());
            gameManager.saveGame();
            updateUI();

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> upgradeResultLabel.setText(""));
            pause.play();
        } else {
            upgradeResultLabel.setText("❌ Pas assez d'or !");
        }
    }

    private void handleEggPurchase() {
        Familiar familiar = gameManager.getShopSystem().purchaseEgg();
        if (familiar != null) {
            RotateTransition rotate = new RotateTransition(Duration.millis(200), buyEggButton);
            rotate.setByAngle(15);
            rotate.setAutoReverse(true);
            rotate.setCycleCount(6);
            rotate.play();

            String rarityColor = familiar.getRarity().getColorHex();
            familiarResultLabel.setStyle("-fx-text-fill: " + rarityColor + ";");
            familiarResultLabel.setText("🥚 " + familiar.getName() + " !\n" + familiar.getRarity().getDisplayName() + "\n" + familiar.getShortDescription());

            gameManager.saveGame();
            updateUI();

            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.setOnFinished(e -> familiarResultLabel.setText(""));
            pause.play();
        } else {
            familiarResultLabel.setText("❌ Pas assez d'or !");
        }
    }

    private void handleRespawn() {
        gameManager.respawnPlayer();
        deathOverlay.setVisible(false);
        updateUI();
    }

    private void updateUI() {
        Player player = gameManager.getPlayer();
        Monster monster = gameManager.getCurrentMonster();

        playerHpLabel.setText(String.format("❤ HP: %d/%d", player.getCurrentHp(), player.getTotalMaxHp()));
        goldLabel.setText("💰 Gold: " + player.getGold());
        clickDamageLabel.setText("⚔ Dégâts: " + player.getTotalClickDamage());
        critRateLabel.setText(String.format("✨ Crit: %.1f%%", player.getTotalCritRate()));
        bossesKilledLabel.setText("👑 Boss: " + player.getTotalBossesKilled());

        String monsterType = monster.isBoss() ? "👑 BOSS" : "👹 Monstre";
        monsterNameLabel.setText(monsterType + " Niveau " + monster.getLevel());
        monsterHpLabel.setText(monster.getCurrentHp() + " / " + monster.getMaxHp() + " HP");
        monsterHpBar.setProgress(monster.getCurrentHp() / (double) monster.getMaxHp());
        monsterRect.setFill(monster.isBoss() ? Color.DARKVIOLET : Color.DARKRED);
        monsterRect.setStroke(monster.isBoss() ? Color.VIOLET : Color.RED);

        upgradeCostLabel.setText("Coût: " + gameManager.getShopSystem().getCurrentUpgradeCost() + " 💰");
        buyUpgradeButton.setDisable(!gameManager.getShopSystem().canAffordUpgrade());
        eggCostLabel.setText("Coût: " + gameManager.getShopSystem().getCurrentEggCost() + " 💰");
        buyEggButton.setDisable(!gameManager.getShopSystem().canAffordEgg());

        if (gameManager.getCurrentState() == GameState.DEATH && !deathOverlay.isVisible()) {
            deathOverlay.setVisible(true);
        }
    }

    private Label createStatLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                gameManager.update(deltaTime);
                updateUI();
            }
        };
        gameLoop.start();
    }

    public void stop() {
        if (gameLoop != null) gameLoop.stop();
        gameManager.saveGame();
    }
}

