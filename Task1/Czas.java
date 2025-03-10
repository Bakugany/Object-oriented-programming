package Ruch_Miejski;

// Klasa jest odpowiedzialna za przeliczanie czasów w programie.
public
class Czas {

    public int godziny(int czas, int dzielnik){
        if(dzielnik != 0){
            return (czas/ dzielnik / 60);
        }
        System.out.println("Błąd, żaden pasażer nigdy nie przejechał tramwajem.");
        return 0;
    }

    public int minuty(int czas, int dzielnik){
        if(dzielnik != 0){
            return (czas / dzielnik) % 60;
        }
        System.out.println("Błąd, żaden pasażer nigdy nie przejechał tramwajem.");
        return 0;
    }

    public int czasDoPółnocy(int godzina, int minuta){
        return (24-godzina)*60 - minuta;
    }

    public int dodaj(int godzinaTramwaju, int godzinaPasażera, int minutaTramwaju, int minutaPasażera){
        return (godzinaTramwaju -godzinaPasażera)* 60 + minutaTramwaju - minutaPasażera;
    }
}
