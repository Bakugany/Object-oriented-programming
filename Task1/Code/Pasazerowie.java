package Ruch_Miejski;

public
class Pasazerowie {
    private int id;
    private Przystanki dom;
    private Przystanki cel;
    private boolean czyPodróżuje;
    private int godzina;
    private int minuta;

    // Funkcja losuje pasażerowi czas jego przyjścia na przystanek i tworzymy z tego zdarzenie.
    public Zdarzenia czasPrzyjścia(){
        Losowanie wylosowanaGodzina = new Losowanie();
       godzina = wylosowanaGodzina.losuj(6,12);

        Losowanie wylosowanaMinuta = new Losowanie();
        minuta  = wylosowanaMinuta.losuj(0, 60);

       return new Zdarzenia(godzina, minuta, this, false);
    }

    // Funkcja przyprowadza pasażera na startowy przystanek.
    // Jeśli na przystanku nie ma miejsca to zwraca false.
    public boolean rozpocznijPodróż(){
        if(dom.getIluPasażerów() < dom.getPojemność()){
            czyPodróżuje = true;
            dom.dodaj(this);
            return true;
        }
        czyPodróżuje = false;
        return false;
    }


    // Tworzymy konstruktor, gettery i settery.

    public
    Pasazerowie(int id) {
        this.id = id;
    }

    public
    int getId() {
        return id;
    }

    public
    Przystanki getDom() {
        return dom;
    }

    public
    Przystanki getCel() {
        return cel;
    }

    public
    boolean isCzyPodróżuje() {
        return czyPodróżuje;
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
    void setDom(Przystanki dom) {
        this.dom = dom;
    }

    public
    void setCel(Przystanki cel) {
        this.cel = cel;
    }

    public
    void setGodzina(int godzina) {
        this.godzina = godzina;
    }

    public
    void setMinuta(int minuta) {
        this.minuta = minuta;
    }
}
