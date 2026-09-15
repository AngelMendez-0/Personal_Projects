package Levels;

import Main.Game;
import Utilz.Constants;
import Utilz.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class LevelHandler {

    private Game game;
    private BufferedImage background;
    private BufferedImage forest_sheet, fog1, fog2;
    private Level levelOne;

    // --- Tile variants for the grid ---
    private BufferedImage[] levelSprite;

    private BufferedImage[] grassVariants;
    private int[] grassWeights;
    private int[][] grassChoice;

    private BufferedImage[] dirtVariants;
    private int[] dirtWeights;
    private int[][] dirtChoice;

    private final Random rng = new Random();

    // --- Decoration objects (sliced from the sheet, ready to place) ---
    private BufferedImage shrine, fountain;
    private BufferedImage bigBrownMushroom, brownMushroomA, brownMushroomB;
    private BufferedImage redToadstool, redToadstoolSmall;
    private BufferedImage treeStump, log, sprout;
    private BufferedImage tallTree, pineTrees, pineTree;
    private BufferedImage groundStrip, cliff;

    public LevelHandler(Game game) {
        this.game = game;
        background   = LoadSave.getSprite(LoadSave.Game_Background);
        forest_sheet = LoadSave.getSprite(LoadSave.Forest);
        fog1 = LoadSave.getSprite(LoadSave.Fog1);
        fog2 = LoadSave.getSprite(LoadSave.Fog2);
        buildTiles();
        loadSprites();
        levelOne = new Level(LevelData.getLevel(0));
        rollTiles();
    } // end of LevelHandler


    private void buildTiles() {
        levelSprite = new BufferedImage[] {
                null,
                sub(160, 144, 16, 16),
                sub(176, 160, 16, 16),
        };

        grassVariants = new BufferedImage[] {
                sub(144, 144, 16, 16), sub(160, 144, 16, 16), sub(176, 144, 16, 16),
                sub(192, 144, 16, 16), sub(208, 144, 16, 16), sub(224, 144, 16, 16),
        };
        grassWeights = new int[] { 1, 1, 1, 1, 1, 1 };

        dirtVariants = new BufferedImage[] {
                sub(144, 160, 16, 16), sub(160, 160, 16, 16), sub(176, 160, 16, 16),
                sub(192, 160, 16, 16), sub(208, 160, 16, 16), sub(224, 160, 16, 16),
        };
        dirtWeights = new int[] { 4, 3, 5, 1, 2, 6 };
    } // end of buildTiles

    private void loadSprites() {
        shrine            = sub(  0,   0, 64,  64);
        fountain          = sub( 64,   0, 64, 112);

        bigBrownMushroom  = sub(208,  32, 48,  48);
        brownMushroomA    = sub(144,  48, 32,  32);
        brownMushroomB    = sub(176,  48, 32,  32);

        redToadstool      = sub(160,  80, 32,  32);
        redToadstoolSmall = sub(128,  96, 16,  16);

        treeStump         = sub(  0, 112, 32,  32);
        log               = sub( 32, 128, 64,  16);
        sprout            = sub( 96, 128, 32,  16);

        tallTree          = sub(  0, 144, 32,  96);
        pineTrees         = sub( 32, 144, 64,  96);
        pineTree          = sub( 96, 144, 32,  96);

        groundStrip       = sub(128, 128, 128, 64);
        cliff             = sub(128, 192, 128, 48);
    } // end of loadSprites

    private void rollTiles() {
        int h = levelOne.getHeight();
        int w = levelOne.getWidth();
        grassChoice = new int[h][w];
        dirtChoice  = new int[h][w];

        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                int id = levelOne.getSpriteIndex(x, y);
                if (id == 1) grassChoice[y][x] = weightedPick(grassWeights);
                if (id == 2) dirtChoice[y][x]  = weightedPick(dirtWeights);
            }
    } // end of rollTiles

    private int weightedPick(int[] weights) {
        int total = 0;
        for (int wgt : weights) total += wgt;
        int r = rng.nextInt(total);
        for (int i = 0; i < weights.length; i++) {
            r -= weights[i];
            if (r < 0) return i;
        }
        return weights.length - 1;
    } // end of weightedPick

    private BufferedImage sub(int x, int y, int w, int h) {
        return forest_sheet.getSubimage(x, y, w, h);
    } // end of sub

    public void draw(Graphics g) {
        g.drawImage(background, 0, 0, Constants.GameConstants.GAME_WIDTH , Constants.GameConstants.GAME_HEIGHT,   null);
        g.drawImage(fog1, 0, 0, Constants.GameConstants.GAME_WIDTH , Constants.GameConstants.GAME_HEIGHT,   null);
        g.drawImage(fog2, 0, 0, Constants.GameConstants.GAME_WIDTH , Constants.GameConstants.GAME_HEIGHT,   null);

        int t = Constants.GameConstants.TILE_SIZE;   // 32

        for (int j = 0; j < levelOne.getHeight(); j++)          // rows (y)
            for (int i = 0; i < levelOne.getWidth(); i++) {     // cols (x)
                int index = levelOne.getSpriteIndex(i, j);
                if (index < 0 || index >= levelSprite.length) continue;
                BufferedImage sprite = levelSprite[index];
                if (sprite == null) continue;

                if (index == 1) sprite = grassVariants[grassChoice[j][i]];
                if (index == 2) sprite = dirtVariants[dirtChoice[j][i]];

                g.drawImage(sprite, i * t, j * t, t, t, null);
            } // end of for

    } // end of draw

    public void update() {

    } // end of update

    public Level getCurrentLevel() { return levelOne; }

} // end of LevelHandler