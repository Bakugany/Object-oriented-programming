package Ruch_Miejski;
import java.util.Random;

public
class Losowanie {
    public static int losuj(int dolnaGranica, int górnaGranica){
        Random rand = new Random();
        return rand.nextInt(dolnaGranica , górnaGranica );
    }
}
