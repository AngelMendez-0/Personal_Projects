package Entities;

import Utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Utilz.Constants.PlayerConstants.*;
import static Utilz.HelpMethods.*;

public class Player extends Entity {

    // Player Sprites
    int spriteWidth = 32 ;
    int spriteHeight = 32 ;
    private BufferedImage[][] playerAnimations;

    // Animation variables
    private int aniTick, aniIndex, aniSpeed = 20;
    private boolean facingLeft = false;
    private int playerAction = IDLE;
    private boolean flipX = false;
    private boolean animationLocked = false;

    // Movement input
    private boolean up, down, left, right, attack, death;

    // --- Physics / collision ---
    private Rectangle2D.Float hitbox;
    private int[][] lvlData;

    // Where the hitbox sits
    private float xDrawOffset = 16;
    private float yDrawOffset = 16;

    private float walkSpeed = 2.5f;
    private float airSpeed = 0f;
    private float gravity = 0.2f;
    private float jumpSpeed = -8f;
    private float fallSpeedAfterCollision = 0.5f;
    private boolean inAir = false;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
        initHitbox(x, y, 32, 48);// hitbox size in pixels
    } // end of Player

    private void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    } // end of initHitbox

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    } // end of loadLvlData

    public Rectangle2D.Float getHitbox() { return hitbox; }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream(LoadSave.PlayerSprites);
        try {
            BufferedImage img = ImageIO.read(is);
            playerAnimations = new BufferedImage[9][8];
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 9; j++)
                    playerAnimations[j][i] = img.getSubimage(i * spriteWidth, j * spriteHeight, spriteWidth, spriteHeight);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { is.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    } // end of loadAnimations

    public void Update() {
        updatePos();
        setAnimation();
        updateAnimationTick();
    } // end of Update

    private void updatePos() {
        if (lvlData == null) return;

        if (up) jump();

        // horizontal movement for this frame
        float xSpeed = 0;
        if (left)  xSpeed -= walkSpeed;
        if (right) xSpeed += walkSpeed;

        // if we walked off a ledge, start falling
        if (!inAir && !IsEntityOnFloor(hitbox, lvlData))
            inAir = true;

        if (inAir) {
            // try to move vertically by airSpeed
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                // blocked snap to floor or ceiling
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) resetInAir(); // landed
                else airSpeed = fallSpeedAfterCollision; // hit head
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }

        x = hitbox.x - xDrawOffset;
        y = hitbox.y - yDrawOffset;
    } // end of updatePos

    private void updateXPos(float xSpeed) {
        if (xSpeed == 0) return;
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
            hitbox.x += xSpeed;
        else
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
    } // end of updateXPos

    private void jump() {
        if (inAir) return;
        inAir = true;
        airSpeed = jumpSpeed;
    } // end of jump

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    } // end of resetInAir

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;

            if (aniIndex >= getFrameCount(playerAction)) {
                aniIndex = 0;
                if (animationLocked) {
                    animationLocked = false;
                    playerAction = IDLE;
                } // end of if
            } // end of if
        } // end of if
    } // end of updateAnimationTick

    private void setAnimation() {
        if (animationLocked) return;

        int previousAction = playerAction;
        boolean previousFlip = flipX;

        if (right) { playerAction = RUN; facingLeft = false; }
        else if (left) { playerAction = RUN; facingLeft = true; }
        else if (up) { playerAction = JUMP; animationLocked = true; }
        else if (down) { playerAction = FALL; animationLocked = true; }
        else if (attack) { playerAction = ATTACK; }
        else if (death) { playerAction = DEATH; }
        else { playerAction = IDLE; }

        flipX = facingLeft;

        if (playerAction != previousAction || flipX != previousFlip) {
            aniTick = 0;
            aniIndex = 0;
            if (finishAni(playerAction)) animationLocked = true;
        }
    } // end of setAnimation

    private boolean finishAni(int action) {
        return action == ATTACK || action == JUMP || action == FALL || action == DEATH;
    } // end of finishAni

    public void Render(Graphics g) {
        BufferedImage frame = playerAnimations[playerAction][aniIndex];

        int drawX = (int) (hitbox.x - xDrawOffset);
        int drawY = (int) (hitbox.y - yDrawOffset);

        if (flipX)
            g.drawImage(frame, drawX + 64, drawY, -64, 64, null);
        else
            g.drawImage(frame, drawX, drawY, 64, 64, null);

        drawHitbox(g); // remove when hitbox isn't wanted
    } // end of render

    // draw the collision box so you can size it correctly.
    private void drawHitbox(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    } // end of drawHitbox

    // Action Setters
    public void setUp(boolean up) { this.up = up; }
    public void setDown(boolean down) { this.down = down; }
    public void setLeft(boolean left) { this.left = left; }
    public void setRight(boolean right) { this.right = right; }
    public void setAttack(boolean attack) { this.attack = attack; }
    public void setDeath(boolean death) { this.death = death; }
    private int getFrameCount(int playerAction) { return playerAction; }
} // end of player