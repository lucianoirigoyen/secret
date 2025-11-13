package org.example.demo.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages and renders particle effects
 */
public class ParticleSystem {
    private List<Particle> particles;

    public ParticleSystem() {
        particles = new ArrayList<>();
    }

    /**
     * Create a burst of particles at a location
     */
    public void createBurst(double x, double y, Color color, int count, double spread) {
        for (int i = 0; i < count; i++) {
            double angle = Math.random() * Math.PI * 2;
            double speed = (Math.random() * spread) + 1;
            double velocityX = Math.cos(angle) * speed;
            double velocityY = Math.sin(angle) * speed;
            double size = (Math.random() * 6) + 3;
            double life = 0.5 + Math.random() * 0.5;

            particles.add(new Particle(x, y, velocityX, velocityY, size, color, life, 2.0));
        }
    }

    /**
     * Create gold coins flying effect
     */
    public void createGoldCoins(double x, double y, int count) {
        Color gold = Color.GOLD;
        for (int i = 0; i < count; i++) {
            double angle = -Math.PI / 2 + (Math.random() - 0.5) * Math.PI / 3;
            double speed = 5 + Math.random() * 3;
            double velocityX = Math.cos(angle) * speed;
            double velocityY = Math.sin(angle) * speed;
            double size = 8 + Math.random() * 4;

            particles.add(new Particle(x, y, velocityX, velocityY, size, gold, 1.0, 1.5));
        }
    }

    /**
     * Create sparkles for critical hits
     */
    public void createSparkles(double x, double y) {
        for (int i = 0; i < 30; i++) {
            double angle = Math.random() * Math.PI * 2;
            double speed = 2 + Math.random() * 4;
            double velocityX = Math.cos(angle) * speed;
            double velocityY = Math.sin(angle) * speed - 2; // Upward bias
            double size = 3 + Math.random() * 5;
            Color color = Color.hsb(30 + Math.random() * 60, 0.8 + Math.random() * 0.2, 1.0);

            particles.add(new Particle(x, y, velocityX, velocityY, size, color, 0.8, 2.5));
        }
    }

    /**
     * Create explosion effect
     */
    public void createExplosion(double x, double y, Color color) {
        for (int i = 0; i < 50; i++) {
            double angle = Math.random() * Math.PI * 2;
            double speed = 3 + Math.random() * 8;
            double velocityX = Math.cos(angle) * speed;
            double velocityY = Math.sin(angle) * speed;
            double size = 4 + Math.random() * 8;

            particles.add(new Particle(x, y, velocityX, velocityY, size, color, 1.0, 1.8));
        }
    }

    /**
     * Update all particles
     */
    public void update(double deltaTime) {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update(deltaTime);
            if (particle.isDead()) {
                iterator.remove();
            }
        }
    }

    /**
     * Render all particles
     */
    public void render(GraphicsContext gc) {
        gc.setGlobalBlendMode(BlendMode.ADD);

        for (Particle particle : particles) {
            gc.save();

            // Apply rotation
            Rotate rotation = new Rotate(particle.getRotation(), particle.getX(), particle.getY());
            gc.setTransform(rotation.getMxx(), rotation.getMyx(), rotation.getMxy(),
                    rotation.getMyy(), rotation.getTx(), rotation.getTy());

            // Set color with alpha based on life
            Color color = particle.getColor();
            double alpha = Math.min(1.0, particle.getLife());
            gc.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));

            // Draw particle
            double size = particle.getSize();
            gc.fillOval(particle.getX() - size / 2, particle.getY() - size / 2, size, size);

            gc.restore();
        }

        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
    }

    public void clear() {
        particles.clear();
    }
}
