package Utilz;

import java.awt.geom.Rectangle2D;
import static Utilz.Constants.GameConstants.TILE_SIZE;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        int leftTile   = (int) (x / TILE_SIZE);
        int rightTile  = (int) ((x + width) / TILE_SIZE);
        int topTile    = (int) (y / TILE_SIZE);
        int bottomTile = (int) ((y + height) / TILE_SIZE);

        for (int ty = topTile; ty <= bottomTile; ty++)
            for (int tx = leftTile; tx <= rightTile; tx++)
                if (isSolidTile(tx, ty, lvlData))
                    return false;
        return true;
    } // end of CanMoveHere

    // A tile is solid if it's off the map, or its id isn't 0
    private static boolean isSolidTile(int tileX, int tileY, int[][] lvlData) {
        int cols = lvlData[0].length;
        int rows = lvlData.length;
        if (tileX < 0 || tileX >= cols) return true;
        if (tileY < 0 || tileY >= rows) return true;
        return lvlData[tileY][tileX] != 0;
    } // end of isSolidTile

    // Is there solid ground directly under the hitbox's feet
    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        int footY = (int) (hitbox.y + hitbox.height + 1) / TILE_SIZE;   // 1px below feet
        int leftX  = (int) (hitbox.x) / TILE_SIZE;
        int rightX = (int) (hitbox.x + hitbox.width) / TILE_SIZE;
        return isSolidTile(leftX, footY, lvlData) || isSolidTile(rightX, footY, lvlData);
    } // end of IsEntityOnFloor

    // When a sideways move is blocked, return the x that puts the hitbox flush to the wall
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        if (xSpeed > 0) {   // moving right -> stop just left of the wall
            int tileCol = (int) ((hitbox.x + hitbox.width + xSpeed) / TILE_SIZE);
            return tileCol * TILE_SIZE - hitbox.width - 1;
        } else {            // moving left -> stop just right of the wall
            int tileCol = (int) ((hitbox.x + xSpeed) / TILE_SIZE);
            return (tileCol + 1) * TILE_SIZE;
        } // end of if else
    } // end of GetEntityXPosNextToWall

    // When a vertical move is blocked, return the y that lands on the floor
    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        if (airSpeed > 0) { // falling -> land on top of the floor tile
            int tileRow = (int) ((hitbox.y + hitbox.height + airSpeed) / TILE_SIZE);
            return tileRow * TILE_SIZE - hitbox.height - 1;
        } else {            // rising -> bump head, sit just under the roof tile
            int tileRow = (int) ((hitbox.y + airSpeed) / TILE_SIZE);
            return (tileRow + 1) * TILE_SIZE;
        } // end of if else
    } // end of GetEntityYPosUnderRoofOrAboveFloor

} // end of HelpMethods