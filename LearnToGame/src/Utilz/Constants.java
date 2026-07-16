package Utilz;

public class Constants {
    public static class PlayerConstants {

        // Animation Constants
        public static final int IDLE = 0;
        public static final int RUN  = 3;
        public static final int JUMP = 5;
        public static final int FALL = 2;
        public static final int ATTACK = 8;
        public static final int DEATH =  6;


        // how many real frames each sprite-sheet row actually has
        private int getFrameCount(int action) {
            switch (action) {
                case 0: return 2;
                case 1: return 2;
                case 2: return 4;
                case 3: return 8;
                case 4: return 6;
                case 5: return 8;
                case 6: return 4;
                case 7: return 8;
                case 8: return 8;
                default: return 1;
            } // end of switch
        } // end of getFrameCount


    } // end of  PlayerConstants
}
