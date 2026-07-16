package Utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LoadSave {

    // Player Assets
    public static final String PlayerSprites = "/AnimationSheet_Character.png";

    // Background Assets
    public static final String Menu_Background = "/ParallaxBackground.png";
    public static final String Game_Background = "/ParallaxBackground_BackgroundColor.png";
    public static final String Fog1 =  "/ParallaxBackground_Fog1.png";
    public static final String Fog2 =  "/ParallaxBackground_Fog2.png";
    public static final String Moon =  "/ParallaxBackground_Moon.png";
    public static final String Stars =  "/ParallaxBackground_Stars.png";

    // TileSet Assets
    public static final String Forest =  "/Tileset_Forest.png";
    public static final String House = "/Tileset_House.png";

    // Getting and returning given Sprite
    public static BufferedImage getSprite(String fileName){
        BufferedImage img = null;

        try {
            img = ImageIO.read(LoadSave.class.getResourceAsStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    } // end of getSprite

} // end of LoadSave

