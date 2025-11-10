package org.example.demo.ui;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
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
 * ENHANCED Game Panel with stunning visual effects
 */
public class GamePanelEnhanced extends StackPane {
    private final GameManager gameManager;
    private BorderPane mainLayout;

    // UI Components - Combat
    private Label monsterNameLabel;
    private Label monsterHpLabel;
    private ProgressBar monsterHpBar;
    private StackPane monsterContainer;
    private Group monsterGroup;
    private VBox damageNumbersContainer;
    private Label cycleProgressLabel;
    private Label comboLabel;

    // UI Components - Player Stats
    private Label playerHpLabel;
    private Label goldLabel;
    private Label clickDamageLabel;
    private Label critRateLabel;
    private Label bossesKilledLabel;
    private ProgressBar playerHpBar;

    // UI Components - Shop
    private Button shopButton;
    private VBox shopPanel;
    private VBox upgradeCards;
    private VBox familiarCards;

    // UI Components - Death
    private StackPane deathOverlay;

    // UI Components - Familiars Display
    private HBox familiarDisplayBox;

    // Visual Effects
    private ParticleSystem particleSystem;
    private Canvas particleCanvas;
    private Timeline monsterPulse;
    private double screenShakeX = 0;
    private double screenShakeY = 0;
    private int comboCounter = 0;
    private Timeline comboReset;

    // Game Loop
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;

    public GamePanelEnhanced() {
        gameManager = new GameManager();
        particleSystem = new ParticleSystem();
        initializeUI();
        startGameLoop();
    }

    private void initializeUI() {
        // Create animated background
        Region background = createAnimatedBackground();

        mainLayout = new BorderPane();
        mainLayout.setTop(createPlayerStatsPanel());
        mainLayout.setCenter(createCombatPanel());
        mainLayout.setBottom(createActionPanel());

        shopPanel = createEnhancedShopPanel();
        shopPanel.setVisible(false);
        mainLayout.setRight(shopPanel);

        deathOverlay = createDeathOverlay();
        deathOverlay.setVisible(false);

        // Particle canvas overlay
        particleCanvas = new Canvas(800, 600);
        particleCanvas.setPickOnBounds(false);
        particleCanvas.setMouseTransparent(true);

        getChildren().addAll(background, mainLayout, particleCanvas, deathOverlay);
        updateUI();
    }

    private Region createAnimatedBackground() {
        Region background = new Region();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #0f2027, #203a43, #2c5364);");

        // Animated gradient effect
        Stop[] stops = new Stop[]{
                new Stop(0, Color.web("#0f2027")),
                new Stop(0.5, Color.web("#203a43")),
                new Stop(1, Color.web("#2c5364"))
        };

        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        background.setBackground(new Background(new BackgroundFill(gradient, null, null)));

        return background;
    }

    private HBox createPlayerStatsPanel() {
        HBox statsBox = new HBox(15);
        statsBox.setPadding(new Insets(15));
        statsBox.setAlignment(Pos.CENTER_LEFT);

        // Apply glass morphism effect
        statsBox.setStyle("-fx-background-color: rgba(44, 62, 80, 0.7); " +
                "-fx-background-radius: 10; -fx-border-radius: 10; " +
                "-fx-border-color: rgba(255, 255, 255, 0.2); -fx-border-width: 1;");

        // Add glow effect
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#3498db", 0.6));
        glow.setRadius(15);
        statsBox.setEffect(glow);

        // Player HP with bar
        VBox hpBox = new VBox(3);
        playerHpLabel = createEnhancedStatLabel("❤ HP: 100/100", Color.web("#e74c3c"));
        playerHpBar = new ProgressBar(1.0);
        playerHpBar.setPrefWidth(150);
        playerHpBar.setStyle("-fx-accent: #e74c3c;");
        hpBox.getChildren().addAll(playerHpLabel, playerHpBar);

        goldLabel = createEnhancedStatLabel("💰 Gold: 0", Color.GOLD);
        clickDamageLabel = createEnhancedStatLabel("⚔ Dégâts: 10", Color.web("#e67e22"));
        critRateLabel = createEnhancedStatLabel("✨ Crit: 5%", Color.web("#9b59b6"));
        bossesKilledLabel = createEnhancedStatLabel("👑 Boss: 0", Color.web("#f39c12"));

        // Add pulsing effect to gold label
        addPulseAnimation(goldLabel);

        statsBox.getChildren().addAll(hpBox, goldLabel, clickDamageLabel, critRateLabel, bossesKilledLabel);
        return statsBox;
    }

    private StackPane createCombatPanel() {
        StackPane combatPane = new StackPane();

        // Radial gradient background
        Stop[] stops = new Stop[]{
                new Stop(0, Color.web("#34495e", 0.9)),
                new Stop(1, Color.web("#2c3e50", 0.95))
        };
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.7, true,
                CycleMethod.NO_CYCLE, stops);
        combatPane.setBackground(new Background(new BackgroundFill(radialGradient, null, null)));

        VBox combatContent = new VBox(15);
        combatContent.setAlignment(Pos.CENTER);

        // Cycle progress indicator
        cycleProgressLabel = new Label("Monstre 1/6 - Cycle 1");
        cycleProgressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        cycleProgressLabel.setTextFill(Color.web("#ecf0f1"));
        cycleProgressLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); " +
                "-fx-background-radius: 15; -fx-padding: 5 15;");

        // Combo counter
        comboLabel = new Label("");
        comboLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        comboLabel.setTextFill(Color.ORANGE);
        comboLabel.setVisible(false);

        // Monster name with glow
        monsterNameLabel = new Label("Monstre Niveau 1");
        monsterNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        monsterNameLabel.setTextFill(Color.WHITE);
        Glow nameGlow = new Glow(0.8);
        monsterNameLabel.setEffect(nameGlow);

        // HP Bar with segments
        VBox hpBox = new VBox(5);
        hpBox.setAlignment(Pos.CENTER);

        monsterHpLabel = new Label("50 / 50 HP");
        monsterHpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        monsterHpLabel.setTextFill(Color.LIGHTGRAY);

        monsterHpBar = new ProgressBar(1.0);
        monsterHpBar.setPrefWidth(400);
        monsterHpBar.setPrefHeight(25);
        monsterHpBar.setStyle("-fx-accent: linear-gradient(to right, #e74c3c, #c0392b);");
        hpBox.getChildren().addAll(monsterHpLabel, monsterHpBar);

        // Enhanced monster visual
        monsterContainer = createEnhancedMonster();

        damageNumbersContainer = new VBox();
        damageNumbersContainer.setAlignment(Pos.CENTER);
        damageNumbersContainer.setPickOnBounds(false);

        combatContent.getChildren().addAll(cycleProgressLabel, comboLabel, monsterNameLabel, hpBox, monsterContainer);
        combatPane.getChildren().addAll(combatContent, damageNumbersContainer);
        return combatPane;
    }

    private StackPane createEnhancedMonster() {
        StackPane container = new StackPane();
        container.setMaxSize(250, 250);

        monsterGroup = new Group();

        // Outer glow circle
        Circle outerGlow = new Circle(125);
        outerGlow.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.7, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#e74c3c", 0.3)),
                new Stop(1, Color.TRANSPARENT)));

        // Main monster body (layered shapes)
        Rectangle body = new Rectangle(150, 150);
        body.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#c0392b")),
                new Stop(1, Color.web("#e74c3c"))));
        body.setStroke(Color.web("#a93226"));
        body.setStrokeWidth(4);
        body.setArcWidth(30);
        body.setArcHeight(30);

        // Add depth with inner shadow
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.web("#000000", 0.5));
        innerShadow.setRadius(15);

        // Add outer glow
        DropShadow outerShadow = new DropShadow();
        outerShadow.setColor(Color.web("#e74c3c", 0.8));
        outerShadow.setRadius(25);
        outerShadow.setSpread(0.5);

        innerShadow.setInput(outerShadow);
        body.setEffect(innerShadow);

        // Eyes
        Circle leftEye = new Circle(15);
        leftEye.setFill(Color.web("#ecf0f1"));
        leftEye.setCenterX(-30);
        leftEye.setCenterY(-20);

        Circle rightEye = new Circle(15);
        rightEye.setFill(Color.web("#ecf0f1"));
        rightEye.setCenterX(30);
        rightEye.setCenterY(-20);

        Circle leftPupil = new Circle(8);
        leftPupil.setFill(Color.web("#2c3e50"));
        leftPupil.setCenterX(-30);
        leftPupil.setCenterY(-20);

        Circle rightPupil = new Circle(8);
        rightPupil.setFill(Color.web("#2c3e50"));
        rightPupil.setCenterX(30);
        rightPupil.setCenterY(-20);

        monsterGroup.getChildren().addAll(outerGlow, body, leftEye, rightEye, leftPupil, rightPupil);

        // Add pulsing animation
        monsterPulse = createMonsterPulseAnimation();

        // Click handler
        container.setOnMouseClicked(e -> handleMonsterClick());
        container.setStyle("-fx-cursor: hand;");

        // Add hover effect
        container.setOnMouseEntered(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(100), monsterGroup);
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.play();
        });

        container.setOnMouseExited(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(100), monsterGroup);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();
        });

        container.getChildren().add(monsterGroup);
        return container;
    }

    private Timeline createMonsterPulseAnimation() {
        Timeline pulse = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(monsterGroup.scaleXProperty(), 1.0),
                        new KeyValue(monsterGroup.scaleYProperty(), 1.0)),
                new KeyFrame(Duration.millis(1000), new KeyValue(monsterGroup.scaleXProperty(), 1.02),
                        new KeyValue(monsterGroup.scaleYProperty(), 1.02)),
                new KeyFrame(Duration.millis(2000), new KeyValue(monsterGroup.scaleXProperty(), 1.0),
                        new KeyValue(monsterGroup.scaleYProperty(), 1.0))
        );
        pulse.setCycleCount(Timeline.INDEFINITE);
        pulse.play();
        return pulse;
    }

    private HBox createActionPanel() {
        HBox actionBox = new HBox(15);
        actionBox.setPadding(new Insets(15));
        actionBox.setAlignment(Pos.CENTER);
        actionBox.setStyle("-fx-background-color: rgba(44, 62, 80, 0.7); " +
                "-fx-background-radius: 10;");

        shopButton = createStyledButton("🛒 Ouvrir le Shop", "#3498db", "#2980b9");
        shopButton.setOnAction(e -> toggleShop());

        // Familiar display
        familiarDisplayBox = new HBox(10);
        familiarDisplayBox.setAlignment(Pos.CENTER);
        updateFamiliarDisplay();

        actionBox.getChildren().addAll(shopButton, familiarDisplayBox);
        return actionBox;
    }

    private VBox createEnhancedShopPanel() {
        VBox shop = new VBox(15);
        shop.setPadding(new Insets(20));
        shop.setPrefWidth(320);

        // Glass morphism effect
        shop.setStyle("-fx-background-color: rgba(26, 188, 156, 0.85); " +
                "-fx-background-radius: 15; " +
                "-fx-border-color: rgba(255, 255, 255, 0.3); " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 15;");

        // Add blur effect
        BoxBlur blur = new BoxBlur(3, 3, 1);
        shop.setEffect(blur);

        Label shopTitle = new Label("🎁 SHOP MAGIQUE");
        shopTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        shopTitle.setTextFill(Color.WHITE);
        DropShadow titleShadow = new DropShadow();
        titleShadow.setColor(Color.BLACK);
        titleShadow.setRadius(10);
        shopTitle.setEffect(titleShadow);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setFitToWidth(true);

        VBox shopContent = new VBox(15);

        // Upgrade section
        Label upgradeTitle = new Label("⚡ AMÉLIORATIONS");
        upgradeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        upgradeTitle.setTextFill(Color.YELLOW);

        upgradeCards = new VBox(10);
        updateUpgradeCards();

        // Familiar section
        Label familiarTitle = new Label("🥚 FAMILIERS");
        familiarTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        familiarTitle.setTextFill(Color.YELLOW);

        familiarCards = new VBox(10);
        updateFamiliarCards();

        shopContent.getChildren().addAll(upgradeTitle, upgradeCards, familiarTitle, familiarCards);
        scrollPane.setContent(shopContent);

        shop.getChildren().addAll(shopTitle, scrollPane);
        return shop;
    }

    private void updateUpgradeCards() {
        upgradeCards.getChildren().clear();

        // Create upgrade card
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-color: rgba(241, 196, 15, 0.2); " +
                "-fx-background-radius: 10; " +
                "-fx-border-color: #f39c12; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 10;");

        Label cardTitle = new Label("Amélioration Aléatoire");
        cardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cardTitle.setTextFill(Color.WHITE);

        Label cardDesc = new Label("Possibilités:\n• +10 HP Max (30%)\n• +2 Dégâts (30%)\n• +5% Crit (25%)\n• +20% Crit Mult (15%)");
        cardDesc.setFont(Font.font("Arial", 11));
        cardDesc.setTextFill(Color.web("#ecf0f1"));
        cardDesc.setWrapText(true);

        Label costLabel = new Label("💰 Coût: " + gameManager.getShopSystem().getCurrentUpgradeCost());
        costLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        costLabel.setTextFill(Color.GOLD);

        Button buyButton = createStyledButton("Acheter", "#e67e22", "#d35400");
        buyButton.setMaxWidth(Double.MAX_VALUE);
        buyButton.setDisable(!gameManager.getShopSystem().canAffordUpgrade());
        buyButton.setOnAction(e -> handleUpgradePurchase(card));

        card.getChildren().addAll(cardTitle, cardDesc, costLabel, buyButton);
        upgradeCards.getChildren().add(card);
    }

    private void updateFamiliarCards() {
        familiarCards.getChildren().clear();

        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-color: rgba(231, 76, 60, 0.2); " +
                "-fx-background-radius: 10; " +
                "-fx-border-color: #e74c3c; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 10;");

        Label cardTitle = new Label("🥚 Œuf Mystérieux");
        cardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cardTitle.setTextFill(Color.WHITE);

        Label cardDesc = new Label("Contient un familier aléatoire!\nRarités: Commun, Rare, Épique, Légendaire");
        cardDesc.setFont(Font.font("Arial", 11));
        cardDesc.setTextFill(Color.web("#ecf0f1"));
        cardDesc.setWrapText(true);

        Label costLabel = new Label("💰 Coût: " + gameManager.getShopSystem().getCurrentEggCost());
        costLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        costLabel.setTextFill(Color.GOLD);

        Button buyButton = createStyledButton("Acheter", "#c0392b", "#a93226");
        buyButton.setMaxWidth(Double.MAX_VALUE);
        buyButton.setDisable(!gameManager.getShopSystem().canAffordEgg());
        buyButton.setOnAction(e -> handleEggPurchase(card));

        card.getChildren().addAll(cardTitle, cardDesc, costLabel, buyButton);
        familiarCards.getChildren().add(card);
    }

    private Button createStyledButton(String text, String color, String hoverColor) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-padding: 10 20; " +
                "-fx-border-color: rgba(255,255,255,0.3); -fx-border-radius: 8;", color));

        // Add drop shadow
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web(color, 0.6));
        shadow.setRadius(10);
        button.setEffect(shadow);

        // Hover effects
        button.setOnMouseEntered(e -> {
            button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; " +
                    "-fx-background-radius: 8; -fx-padding: 10 20; " +
                    "-fx-border-color: rgba(255,255,255,0.5); -fx-border-radius: 8;", hoverColor));
            ScaleTransition scale = new ScaleTransition(Duration.millis(100), button);
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.play();
        });

        button.setOnMouseExited(e -> {
            button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; " +
                    "-fx-background-radius: 8; -fx-padding: 10 20; " +
                    "-fx-border-color: rgba(255,255,255,0.3); -fx-border-radius: 8;", color));
            ScaleTransition scale = new ScaleTransition(Duration.millis(100), button);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();
        });

        return button;
    }

    private StackPane createDeathOverlay() {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9);");
        overlay.setPickOnBounds(true);

        VBox deathPanel = new VBox(25);
        deathPanel.setAlignment(Pos.CENTER);
        deathPanel.setMaxSize(550, 450);
        deathPanel.setPadding(new Insets(40));
        deathPanel.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e); " +
                "-fx-border-color: #e74c3c; -fx-border-width: 5; " +
                "-fx-border-radius: 20; -fx-background-radius: 20;");

        // Add glow effect
        DropShadow deathGlow = new DropShadow();
        deathGlow.setColor(Color.web("#e74c3c"));
        deathGlow.setRadius(30);
        deathGlow.setSpread(0.6);
        deathPanel.setEffect(deathGlow);

        Label deathTitle = new Label("💀 VOUS ÊTES MORT 💀");
        deathTitle.setFont(Font.font("Arial", FontWeight.BOLD, 42));
        deathTitle.setTextFill(Color.web("#e74c3c"));

        // Animated pulsing text
        FadeTransition fade = new FadeTransition(Duration.seconds(1), deathTitle);
        fade.setFromValue(1.0);
        fade.setToValue(0.5);
        fade.setCycleCount(Timeline.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();

        Label deathMessage = new Label("Vos améliorations et votre or sont conservés !\n\n" +
                "Vous revenez 5 monstres en arrière pour farmer.\n\n" +
                "Utilisez le shop pour devenir plus fort !");
        deathMessage.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        deathMessage.setTextFill(Color.WHITE);
        deathMessage.setWrapText(true);
        deathMessage.setStyle("-fx-text-alignment: center;");

        Button retryButton = createStyledButton("🔄 RÉAPPARAÎTRE", "#e74c3c", "#c0392b");
        retryButton.setPrefSize(250, 60);
        retryButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        retryButton.setOnAction(e -> handleRespawn());

        deathPanel.getChildren().addAll(deathTitle, deathMessage, retryButton);
        overlay.getChildren().add(deathPanel);
        return overlay;
    }

    private void updateFamiliarDisplay() {
        familiarDisplayBox.getChildren().clear();

        Player player = gameManager.getPlayer();
        Familiar fam1 = player.getEquippedFamiliar1();
        Familiar fam2 = player.getEquippedFamiliar2();

        if (fam1 != null) {
            familiarDisplayBox.getChildren().add(createFamiliarIcon(fam1));
        }
        if (fam2 != null) {
            familiarDisplayBox.getChildren().add(createFamiliarIcon(fam2));
        }
    }

    private StackPane createFamiliarIcon(Familiar familiar) {
        StackPane icon = new StackPane();
        icon.setPrefSize(50, 50);

        Circle circle = new Circle(25);
        circle.setFill(Color.web(familiar.getRarity().getColorHex()));
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(2);

        Label emoji = new Label("🐾");
        emoji.setFont(Font.font(24));

        // Glow effect based on rarity
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web(familiar.getRarity().getColorHex(), 0.8));
        glow.setRadius(15);
        icon.setEffect(glow);

        // Pulsing animation
        addPulseAnimation(icon);

        icon.getChildren().addAll(circle, emoji);

        // Tooltip on hover
        icon.setOnMouseEntered(e -> {
            // Could show familiar details
        });

        return icon;
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

            // Combo system
            comboCounter++;
            updateComboDisplay();

            // Visual effects
            animateHit(result.isCrit);
            showDamageNumber(result.damage, result.isCrit);

            // Particle effects
            double centerX = monsterContainer.getLayoutX() + monsterContainer.getWidth() / 2;
            double centerY = monsterContainer.getLayoutY() + monsterContainer.getHeight() / 2;

            if (result.isCrit) {
                particleSystem.createSparkles(400, 300);
                createScreenShake(8);
            } else {
                particleSystem.createBurst(400, 300, Color.ORANGERED, 15, 3);
                createScreenShake(3);
            }

            // Check if monster died
            if (result.monsterDied) {
                onMonsterDeath();
            }

            updateUI();
        }
    }

    private void updateComboDisplay() {
        if (comboCounter > 1) {
            comboLabel.setText(String.format("COMBO x%d!", comboCounter));
            comboLabel.setVisible(true);

            // Scale animation
            ScaleTransition scale = new ScaleTransition(Duration.millis(150), comboLabel);
            scale.setFromX(0.8);
            scale.setFromY(0.8);
            scale.setToX(1.2);
            scale.setToY(1.2);
            scale.setAutoReverse(true);
            scale.setCycleCount(2);
            scale.play();
        }

        // Reset combo after 2 seconds of inactivity
        if (comboReset != null) {
            comboReset.stop();
        }
        comboReset = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            comboCounter = 0;
            comboLabel.setVisible(false);
        }));
        comboReset.play();
    }

    private void onMonsterDeath() {
        // Explosion effect
        particleSystem.createExplosion(400, 300, Color.web("#e74c3c"));

        // Gold coin effect
        particleSystem.createGoldCoins(400, 300, 20);

        // Reset combo
        comboCounter = 0;
        comboLabel.setVisible(false);

        // Check if it was a boss
        if (gameManager.getCurrentMonster().isBoss()) {
            showFeedbackText("BOSS VAINCU !", Color.GOLD);
            createScreenShake(15);
        }
    }

    private void createScreenShake(double intensity) {
        Timeline shake = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(mainLayout.translateXProperty(), 0),
                        new KeyValue(mainLayout.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(50), new KeyValue(mainLayout.translateXProperty(), intensity),
                        new KeyValue(mainLayout.translateYProperty(), -intensity)),
                new KeyFrame(Duration.millis(100), new KeyValue(mainLayout.translateXProperty(), -intensity),
                        new KeyValue(mainLayout.translateYProperty(), intensity)),
                new KeyFrame(Duration.millis(150), new KeyValue(mainLayout.translateXProperty(), intensity / 2),
                        new KeyValue(mainLayout.translateYProperty(), -intensity / 2)),
                new KeyFrame(Duration.millis(200), new KeyValue(mainLayout.translateXProperty(), 0),
                        new KeyValue(mainLayout.translateYProperty(), 0))
        );
        shake.play();
    }

    private void animateHit(boolean isCrit) {
        double scale = isCrit ? 0.85 : 0.92;
        ScaleTransition scaleAnim = new ScaleTransition(Duration.millis(80), monsterGroup);
        scaleAnim.setFromX(1.0);
        scaleAnim.setFromY(1.0);
        scaleAnim.setToX(scale);
        scaleAnim.setToY(scale);
        scaleAnim.setAutoReverse(true);
        scaleAnim.setCycleCount(2);
        scaleAnim.play();

        // Flash effect
        FadeTransition flash = new FadeTransition(Duration.millis(100), monsterGroup);
        flash.setFromValue(1.0);
        flash.setToValue(0.7);
        flash.setAutoReverse(true);
        flash.setCycleCount(2);
        flash.play();
    }

    private void showDamageNumber(int damage, boolean isCrit) {
        Label damageLabel = new Label("-" + damage);
        damageLabel.setFont(Font.font("Impact", FontWeight.BOLD, isCrit ? 48 : 32));
        damageLabel.setTextFill(isCrit ? Color.ORANGE : Color.WHITE);

        // Add text stroke
        damageLabel.setStyle("-fx-effect: dropshadow(gaussian, black, 5, 0.8, 0, 0);");

        damageNumbersContainer.getChildren().add(damageLabel);

        TranslateTransition move = new TranslateTransition(Duration.millis(1200), damageLabel);
        move.setByY(-150);
        move.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.millis(1200), damageLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(200), damageLabel);
        scale.setFromX(0.5);
        scale.setFromY(0.5);
        scale.setToX(1.0);
        scale.setToY(1.0);

        ParallelTransition parallel = new ParallelTransition(move, fade, scale);
        parallel.setOnFinished(e -> damageNumbersContainer.getChildren().remove(damageLabel));
        parallel.play();
    }

    private void showFeedbackText(String text, Color color) {
        Label feedbackLabel = new Label(text);
        feedbackLabel.setFont(Font.font("Impact", FontWeight.BOLD, 36));
        feedbackLabel.setTextFill(color);
        feedbackLabel.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0.8, 0, 0);");
        damageNumbersContainer.getChildren().add(feedbackLabel);

        ScaleTransition scale = new ScaleTransition(Duration.millis(400), feedbackLabel);
        scale.setFromX(0.3);
        scale.setFromY(0.3);
        scale.setToX(1.5);
        scale.setToY(1.5);

        FadeTransition fade = new FadeTransition(Duration.millis(1000), feedbackLabel);
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

        if (shopPanel.isVisible()) {
            updateUpgradeCards();
            updateFamiliarCards();
        }
    }

    private void handleUpgradePurchase(VBox card) {
        UpgradeSystem.UpgradeType upgrade = gameManager.getShopSystem().purchaseUpgrade();
        if (upgrade != null) {
            // Success animation
            RotateTransition rotate = new RotateTransition(Duration.millis(500), card);
            rotate.setByAngle(360);
            rotate.play();

            // Show success feedback
            showFeedbackText("+" + upgrade.getDisplayName(), Color.LIME);

            // Particle burst at shop
            particleSystem.createBurst(600, 300, Color.GOLD, 30, 5);

            gameManager.saveGame();
            updateUI();
            updateUpgradeCards();
        } else {
            // Shake card to indicate failure
            TranslateTransition shake = new TranslateTransition(Duration.millis(100), card);
            shake.setFromX(0);
            shake.setByX(10);
            shake.setCycleCount(4);
            shake.setAutoReverse(true);
            shake.play();

            showFeedbackText("Pas assez d'or !", Color.RED);
        }
    }

    private void handleEggPurchase(VBox card) {
        Familiar familiar = gameManager.getShopSystem().purchaseEgg();
        if (familiar != null) {
            // Egg crack animation
            RotateTransition rotate = new RotateTransition(Duration.millis(100), card);
            rotate.setByAngle(10);
            rotate.setAutoReverse(true);
            rotate.setCycleCount(8);
            rotate.play();

            // Show familiar obtained
            showFeedbackText(familiar.getName() + "!", Color.web(familiar.getRarity().getColorHex()));

            // Particle explosion with rarity color
            particleSystem.createExplosion(600, 400, Color.web(familiar.getRarity().getColorHex()));

            gameManager.saveGame();
            updateUI();
            updateFamiliarCards();
            updateFamiliarDisplay();
        } else {
            TranslateTransition shake = new TranslateTransition(Duration.millis(100), card);
            shake.setFromX(0);
            shake.setByX(10);
            shake.setCycleCount(4);
            shake.setAutoReverse(true);
            shake.play();

            showFeedbackText("Pas assez d'or !", Color.RED);
        }
    }

    private void handleRespawn() {
        gameManager.respawnPlayer();
        deathOverlay.setVisible(false);

        // Respawn effect
        particleSystem.createBurst(400, 300, Color.CYAN, 50, 8);
        showFeedbackText("RESSUSCITÉ !", Color.CYAN);

        updateUI();
    }

    private void updateUI() {
        Player player = gameManager.getPlayer();
        Monster monster = gameManager.getCurrentMonster();

        // Update player stats
        playerHpLabel.setText(String.format("❤ HP: %d/%d", player.getCurrentHp(), player.getTotalMaxHp()));
        playerHpBar.setProgress((double) player.getCurrentHp() / player.getTotalMaxHp());

        goldLabel.setText("💰 Gold: " + player.getGold());
        clickDamageLabel.setText("⚔ Dégâts: " + player.getTotalClickDamage());
        critRateLabel.setText(String.format("✨ Crit: %.1f%%", player.getTotalCritRate()));
        bossesKilledLabel.setText("👑 Boss: " + player.getTotalBossesKilled());

        // Update monster
        String monsterType = monster.isBoss() ? "👑 BOSS" : "👹 Monstre";
        monsterNameLabel.setText(monsterType + " Niveau " + monster.getLevel());
        monsterHpLabel.setText(monster.getCurrentHp() + " / " + monster.getMaxHp() + " HP");
        monsterHpBar.setProgress(monster.getCurrentHp() / (double) monster.getMaxHp());

        // Update cycle progress
        int currentIndex = gameManager.getCurrentMonster().isBoss() ? 6 :
                          gameManager.getCurrentMonster().getLevel() % 6 == 0 ? 6 : gameManager.getCurrentMonster().getLevel() % 6;
        int cycle = (gameManager.getCurrentMonster().getLevel() - 1) / 6 + 1;
        cycleProgressLabel.setText(String.format("Monstre %d/6 - Cycle %d", currentIndex, cycle));

        // Update monster appearance for boss
        updateMonsterAppearance(monster.isBoss());

        // Update familiar display
        updateFamiliarDisplay();

        // Check death state
        if (gameManager.getCurrentState() == GameState.DEATH && !deathOverlay.isVisible()) {
            deathOverlay.setVisible(true);
        }
    }

    private void updateMonsterAppearance(boolean isBoss) {
        // Change color for boss
        Rectangle body = (Rectangle) monsterGroup.getChildren().get(1);

        if (isBoss) {
            body.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web("#8e44ad")),
                    new Stop(1, Color.web("#9b59b6"))));
            body.setStroke(Color.web("#6c3483"));

            // Boss glow
            DropShadow bossGlow = new DropShadow();
            bossGlow.setColor(Color.web("#9b59b6", 0.9));
            bossGlow.setRadius(35);
            bossGlow.setSpread(0.7);

            InnerShadow inner = new InnerShadow();
            inner.setColor(Color.web("#000000", 0.5));
            inner.setRadius(15);
            inner.setInput(bossGlow);

            body.setEffect(inner);
        } else {
            body.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web("#c0392b")),
                    new Stop(1, Color.web("#e74c3c"))));
            body.setStroke(Color.web("#a93226"));

            InnerShadow innerShadow = new InnerShadow();
            innerShadow.setColor(Color.web("#000000", 0.5));
            innerShadow.setRadius(15);

            DropShadow outerShadow = new DropShadow();
            outerShadow.setColor(Color.web("#e74c3c", 0.8));
            outerShadow.setRadius(25);
            outerShadow.setSpread(0.5);

            innerShadow.setInput(outerShadow);
            body.setEffect(innerShadow);
        }
    }

    private Label createEnhancedStatLabel(String text, Color color) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(color);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setRadius(5);
        label.setEffect(shadow);

        return label;
    }

    private void addPulseAnimation(javafx.scene.Node node) {
        ScaleTransition pulse = new ScaleTransition(Duration.seconds(1.5), node);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.08);
        pulse.setToY(1.08);
        pulse.setCycleCount(Timeline.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();
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

                // Update game logic
                gameManager.update(deltaTime);

                // Update particles
                particleSystem.update(deltaTime);

                // Render particles
                GraphicsContext gc = particleCanvas.getGraphicsContext2D();
                gc.clearRect(0, 0, particleCanvas.getWidth(), particleCanvas.getHeight());
                particleSystem.render(gc);

                // Update UI
                updateUI();
            }
        };
        gameLoop.start();
    }

    public void stop() {
        if (gameLoop != null) gameLoop.stop();
        if (monsterPulse != null) monsterPulse.stop();
        gameManager.saveGame();
    }
}
