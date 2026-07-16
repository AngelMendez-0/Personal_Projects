package Entities;

import Utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Utilz.Constants.PlayerConstants.*;

public class Player extends Entity {

    // Player Sprites
    int spriteWidth = 32 ;
    int spriteHeight = 32 ;
    private BufferedImage[][] playerAnimations;

    // Animation variables
    private int aniTick, aniIndex, aniSpeed = 20;
    private boolean facingLeft = false;  // remembered facing only changes when you move left/right
    private int playerAction = IDLE;
    private boolean flipX = false;            // true when facing left
    private boolean animationLocked = false;  // true while a one-shot (jump/fall) plays out


    // Movemment variables
    private boolean up, down, left, right, attack, death;
    private float speed = 6f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();

    } // end of Player

    private void loadAnimations() {

        // Loads up the player Sprite Image and sets it to *img*
        InputStream is = getClass().getResourceAsStream(LoadSave.PlayerSprites);
        try{
            BufferedImage img = ImageIO.read(is);

            // loads each of the frames into the PlayerAnimations image array
            playerAnimations = new BufferedImage[9][8]; // 9 animation rows, 8 frames per row
            for (int i = 0; i < 8; i++) {               // i = column = frame index (0-7)
                for (int j = 0; j < 9; j++) {           // j = row = animation index (0-8)
                    playerAnimations[j][i] = img.getSubimage(i * spriteWidth, j * spriteHeight, spriteWidth, spriteHeight);
                } // end of for
            } // end of for

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            } // end of imbedded try catch
        } // end of try catch finally

    } // end of loadAnimations

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getFrameCount(playerAction)) {  // stop at the current row's real length
                aniIndex = 0;
                if (animationLocked) {          // a one-shot just finished
                    animationLocked = false;
                    playerAction = IDLE;
                } // end of if
            } // end of if
        } // end of if
    } // end of updateAnimationTick

    public void Update() {
        updatePosition();
        setAnimation();
        updateAnimationTick();

    } // end of Update

    private void setAnimation() {
        if (animationLocked) return;

        int previousAction = playerAction;
        boolean previousFlip = flipX;

        if (right) {
            playerAction = RUN;
            facingLeft = false;
        } else if (left) {
            playerAction = RUN;
            facingLeft = true;
        } else if (up) {
            playerAction = JUMP;
            animationLocked = true;
        } else if (down) {
            playerAction = FALL;
            animationLocked = true;
        } else if (attack){
            playerAction = ATTACK;
        } else if (death){
            playerAction = DEATH;
        } else {
            playerAction = IDLE;
        } // end of if else

        flipX = facingLeft;              // apply remembered facing to whatever we're doing

        if (playerAction != previousAction || flipX != previousFlip) {
            aniTick = 0;
            aniIndex = 0;
            if(finishAni(playerAction)){
                animationLocked = true;
            } // en
        } // end of if
    } // end of setAnimation

    private boolean finishAni(int action) {
        return action == ATTACK || action == JUMP || action == FALL || action == DEATH;
    } // end of finishAni

    public void updatePosition() {
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;
    } // end of updatePosition

    public void Render(Graphics g) {
        BufferedImage frame = playerAnimations[playerAction][aniIndex];

        if (flipX) {
            g.drawImage(frame, (int) x + spriteWidth, (int) y, -64, 64, null);
        } else {
            g.drawImage(frame, (int) x, (int) y, 64, 64, null);
        } // end of if else
    } // end of render

    // Action Setters
    public void setUp(boolean up) { this.up = up; }
    public void setDown(boolean down) { this.down = down; }
    public void setLeft(boolean left) { this.left = left; }
    public void setRight(boolean right) { this.right = right; }
    public void setAttack(boolean attack) { this.attack = attack; }
    public void setDeath(boolean death) { this.death = this.death; }
    private int getFrameCount(int playerAction) { return playerAction; }
} // end of player