package Levels;

// Level layouts as a grid of tile ids. Each String is one ROW,
// each character is one TILE:
//     '0' = empty / sky   -> draws nothing
//     '1' = grass-topped ground
//     '2' = dirt
// These ids line up with the levelSprite[] array in LevelHandler.
//
// Grid is 25 wide x 18 tall = the whole play area (25*32 x 18*32 = 800 x 576).
// Put a '1' or '2' wherever you want a tile; edit freely.
public class LevelData {

    private static final String[] LEVEL_ONE = {
            "0000000000000000000000000", // air
            "0000000000000000000000000",
            "0000000000000000000000000",
            "0000000000000000000000000",
            "0000000000000000000000000",
            "0000000000000000000000000",
            "0000000000000000000000000",
            "0000000000000000000000000",
            "0000000000000001111100000",
            "0000000000000000000000000",
            "0000000000000000000000000",
            "0000000000000000000000000",
            "0000011111000000000000000",
            "0000000000000000000000000",
            "1020030040050090060070080",
            "1111111111111111111111111", // grass
            "2222222222222222222222222",
            "2222222222222222222222222", // dirt
    };

    private static final String[][] LEVELS = { LEVEL_ONE };

    public static int[][] getLevel(int index) {
        return toIntArray(LEVELS[index]);
    } // end of getLevel

    public static int getLevelCount() { return LEVELS.length; }

    private static int[][] toIntArray(String[] rows) {
        int height = rows.length;
        int width  = rows[0].length();
        int[][] data = new int[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                data[y][x] = rows[y].charAt(x) - '0';   // digit char -> int
        return data;
    } // end of toIntArray

} // end of LevelData