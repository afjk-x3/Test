package gameTestBUTO;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
    private int x, y, width, height, speed;
    private int health;
    private boolean isJumping, isAttacking, isBlocking;
    private boolean isKnockedBack;
    private int knockbackDirection;
    private int knockbackTime;
    private int velocityY;
    private Platform platform;
    private boolean isPlayerOne;
    private boolean isInAttackRange;
    private int swordLength = 50;  // Length of the sword
    private int swordWidth = 10;   // Width of the sword
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private double velocityX = 0;  // Smooth horizontal movement
    private boolean isDead = false;
    private String lastDirection = "right";  // Track the last direction ("left" or "right")
    private int respawnX;
    private int respawnY;

    public Player(int x, int y, Platform platform, boolean isPlayerOne) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 100;
        this.speed = 5;
        this.health = 100;
        this.platform = platform;
        this.isPlayerOne = isPlayerOne;
        this.respawnX = x; // Store the initial respawn position
        this.respawnY = y;
        this.isInAttackRange = false;
    }

    public void update() {
        if (isDead) {
            return;  // Don't update the player if they are dead
        }

        if (isKnockedBack) {
            x += knockbackDirection * 10;
            knockbackTime--;
            if (knockbackTime <= 0) {
                isKnockedBack = false;
            }
        } else {
            // Handle gravity and jumping
            if (isJumping) {
                velocityY = -15;
                isJumping = false;
            }
            y += velocityY;
            velocityY += 1;
            if (y >= platform.getY()) {
                y = platform.getY();
                velocityY = 0;
            }

            // Smooth horizontal movement
            if (isMovingLeft) {
                velocityX = Math.max(velocityX - 0.5, -speed); // Accelerate to the left
                lastDirection = "left";  // Update the last direction
            } else if (isMovingRight) {
                velocityX = Math.min(velocityX + 0.5, speed); // Accelerate to the right
                lastDirection = "right";  // Update the last direction
            } else {
                // Gradually slow down when no key is pressed
                velocityX = Math.signum(velocityX) * Math.max(0, Math.abs(velocityX) - 0.5);
            }
            x += velocityX; // Apply the smooth movement
        }
    }

    public void attack(Player opponent) {
        if (isDead) return;  // Don't allow attacking if dead
        isAttacking = true;

        // Check if the attack hits the opponent and if they are not blocking
        if (isSwordCollidingWith(opponent) && !opponent.isBlocking) {
            opponent.takeDamage(10);
            opponent.knockbackDirection = (x < opponent.x) ? 1 : -1;
            opponent.isKnockedBack = true;
            opponent.knockbackTime = 10;
        }
    }

    public void block() {
        if (isDead) return;  // Don't allow blocking if dead
        isBlocking = true;
    }

    public void moveLeft() {
        if (!isDead && !isKnockedBack) {
            isMovingLeft = true;
            isMovingRight = false;
        }
    }

    public void moveRight() {
        if (!isDead && !isKnockedBack) {
            isMovingRight = true;
            isMovingLeft = false;
        }
    }

    public void jump() {
        if (y == platform.getY() && !isDead) {
            isJumping = true;
        }
    }

    public void stop() {
        if (isDead) return;  // Don't stop if dead
        isAttacking = false;
        isBlocking = false;
        isMovingLeft = false;
        isMovingRight = false;
    }

    private boolean isSwordCollidingWith(Player opponent) {
        // Check if the sword intersects the opponent's body
        int swordX = lastDirection.equals("right") ? x + width : x - swordLength;
        int swordY = y + height / 3;

        // The sword is in front of the player, checking if it collides with the opponent's body
        return swordX < opponent.x + opponent.width && 
               swordX + swordLength > opponent.x &&
               swordY < opponent.y + opponent.height && 
               swordY + swordWidth > opponent.y;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            die();
        }
    }

    private void die() {
        isDead = true;
    }

    public void respawn() {
        // Reset player to respawn position
        x = respawnX;
        y = respawnY;
        health = 100;
        isDead = false;
        isJumping = false;
        isAttacking = false;
        isBlocking = false;
        velocityY = 0;
        velocityX = 0;
        lastDirection = "right";  // Reset the last direction
    }

    public void render(Graphics g) {
        if (isDead) {
            return;  // Don't render the player if dead
        }

        // Draw the player as a rectangle
        g.setColor(Color.BLUE);
        g.fillRect(x, y -100, width, height);

        // Render Health Bar
        g.setColor(Color.BLACK);
        g.fillRect(x, y - 120, width, 10);
        g.setColor(Color.RED);
        g.fillRect(x, y - 120, (int) (width * (health / 100.0)), 10);

        // If the player is attacking, draw the sword
        if (isAttacking) {
            if (lastDirection.equals("right")) {
                // Player faces right and attacks (sword to the right)
                g.setColor(Color.GRAY);
                g.fillRect(x + width, y - 80, swordLength, swordWidth);
            } else if (lastDirection.equals("left")) {
                // Player faces left and attacks (sword to the left)
                g.setColor(Color.GRAY);
                g.fillRect(x - swordLength, y - 80, swordLength, swordWidth);
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getHealth() {
        return health;
    }
}
