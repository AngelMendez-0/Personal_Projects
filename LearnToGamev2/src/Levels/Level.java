package Levels;

public class Level {

    private int[][] levelData;

    public Level(int[][] levelData){
        this.levelData = levelData;
    } // end of level

    public int getSpriteIndex(int x, int y) { return levelData[y][x]; } // [row=y][col=x]

    public int[][] getLevelData() { return levelData; }

    // Size of the level in TILES, taken from the data array itself.
    // draw() MUST loop by these, not by the window constants.
    public int getWidth()  { return levelData[0].length; } // columns (x)
    public int getHeight() { return levelData.length;    } // rows    (y)

} // end of Level