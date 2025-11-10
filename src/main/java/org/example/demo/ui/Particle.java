package org.example.demo.ui;

import javafx.scene.paint.Color;

/**
 * Represents a single particle for visual effects
 */
public class Particle {
    private double x, y;
    private double velocityX, velocityY;
    private double size;
    private Color color;
    private double life; // 0.0 to 1.0
    private double decay; // How fast it fades
    private double rotation;
    private double rotationSpeed;

    public Particle(double x, double y, double velocityX, double velocityY,
                    double size, Color color, double life, double decay) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.size = size;
        this.color = color;
        this.life = life;
        this.decay = decay;
        this.rotation = Math.random() * 360;
        this.rotationSpeed = (Math.random() - 0.5) * 10;
    }

    public void update(double deltaTime) {
        x += velocityX * deltaTime * 60;
        y += velocityY * deltaTime * 60;
        velocityY += 0.3 * deltaTime * 60; // Gravity
        life -= decay * deltaTime;
        rotation += rotationSpeed * deltaTime * 60;
    }

    public boolean isDead() {
        return life <= 0;
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getSize() { return size; }
    public Color getColor() { return color; }
    public double getLife() { return life; }
    public double getRotation() { return rotation; }
}
