package Ruch_Miejski;

import static java.util.Arrays.copyOf;
import static java.util.Arrays.sort;


// Implementujemy kolejkę zdarzeń jako rozszerzającą się tablicę.
public
class Kolejka implements Kolejka_Zdarzen{
    private int zapełnienie = 0;
    private Zdarzenia[] zdarzenia = new Zdarzenia[2];

    // Jeżeli nie mamy miejsca, to tablicę będziemy podwajać.
    private void zapewnijMiejsce(){
        zdarzenia = copyOf(zdarzenia, 2 * zdarzenia.length);
    }

    // Dodajemy do kolejki kolejne zdarzenie.
    @Override
    public
    void wstaw(Zdarzenia zdarzenie) {
        // W razie potrzeby rozszerzamy tablicę.
        if(zapełnienie == zdarzenia.length){
            zapewnijMiejsce();
        }

        // Wstawiamy nowe zdarzenie i sortujemy kolejkę.
        zdarzenia[zapełnienie] = zdarzenie;
        zapełnienie++;
        sort(zdarzenia, 0, zapełnienie);
    }

    // Pobieramy z kolejki pierwsze zdarzenie.
    @Override
    public
    Zdarzenia pobierzPierwszy() {
        // Sprawdzamy, czy kolejka nie jest pusta z asercją.
        assert zapełnienie != 0;

        // Pobieramy pierwsze zdarzenie
        Zdarzenia pierwszy = zdarzenia[0];

        // Przesuwamy naszą kolejkę
        for(int i = 1; i < zapełnienie; i++){
            zdarzenia[i-1] = zdarzenia[i];
        }
        zapełnienie--;

        return pierwszy;
    }

    // Sprawdzmy, czy kolejka jest pusta.
    @Override
    public
    boolean czyPusta() {
        return (zapełnienie == 0);
    }

    // Tworzymy konstruktor dla kolejki.
    public Kolejka(){
        this.zapełnienie = 0;
        this.zdarzenia = new Zdarzenia[1];
    }
}
