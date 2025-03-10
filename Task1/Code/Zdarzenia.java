package Ruch_Miejski;

public
class Zdarzenia implements Comparable<Zdarzenia> {
    private int godzina;
    private int minuta;
    private Tramwaje tramwaj;
    private Pasazerowie pasażer;
    private boolean czy_tramwaj;

    // Sortujemy zdarzenie za względu na czas wystąpienia.
    @Override
    public int compareTo(Zdarzenia inne_Zdarzenia){
        if(this.godzina > inne_Zdarzenia.godzina)
            return 1;
        if(this.godzina < inne_Zdarzenia.godzina)
            return -1;
        if(this.minuta > inne_Zdarzenia.minuta)
            return 1;
        if (this.minuta < inne_Zdarzenia.minuta)
            return -1;
        return 0;
    }

    // Tworzymy konstruktory oraz gettery.
    public
    Zdarzenia(int godzina, int minuta, Tramwaje obiekt, boolean czy_tramwaj) {
        this.godzina = godzina;
        this.minuta = minuta;
        this.tramwaj = obiekt;
        this.czy_tramwaj = czy_tramwaj;
    }

    public
    Zdarzenia(int godzina, int minuta, Pasazerowie obiekt, boolean czy_tramwaj) {
        this.godzina = godzina;
        this.minuta = minuta;
        this.pasażer = obiekt;
        this.czy_tramwaj = czy_tramwaj;
    }

    public
    int getGodzina() {
        return godzina;
    }

    public
    int getMinuta() {
        return minuta;
    }

    public
    Tramwaje getTramwaj() {
        return tramwaj;
    }

    public
    Pasazerowie getPasażer() {
        return pasażer;
    }

    public
    boolean isCzy_tramwaj() {
        return czy_tramwaj;
    }

}
