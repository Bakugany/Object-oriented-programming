package Ruch_Miejski;

// Klasa przeprowadza wyszukiwanie binarne szukając odpowiedniego napisu.
public
class Wyszukiwanie_Binarne {

    public Przystanki szukaj(String nazwaPrzystanku, Przystanki[] przystanki){
            int l = 0, p = przystanki.length;
            while(l < p){
                int s = (l + p) / 2;
                if((przystanki[s].getNazwa()).compareTo(nazwaPrzystanku) == 0){
                    return przystanki[s];
                }
                if((przystanki[s].getNazwa()).compareTo(nazwaPrzystanku) < 0){
                    l = s + 1;
                }
                else {
                    p = s;
                }
            }
            return przystanki[l];
    }
}
