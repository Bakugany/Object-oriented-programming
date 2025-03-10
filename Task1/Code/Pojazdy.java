package Ruch_Miejski;

public abstract
class Pojazdy {
    private int numer;
    private Linie linia;
    private int obecnyPrzystanek;
    private int pętla;
    private boolean kierunek;
    private int iluPasażerów;
    private Pasazerowie[] pasażerowie;
    private int godzina;
    private int minuta;

    // Funkcja pozwala wysadzić danego pasażera.
    protected abstract Pasazerowie wysiada(int i);

    // Funkcja pozwala wsiąść danemu pasażerowi.
    protected abstract void wsiada(Pasazerowie pasażer);


    // Funkcja pozwala przemieścić się pojazdowi do kolejnego przystanku.
    public void jedź(){
        // Najpierw zajmijmy się przypadkiem, gdy jesteśmy na pętli.
        if(obecnyPrzystanek == 0 && !kierunek ||
        kierunek && obecnyPrzystanek == linia.długośćTrasy() - 1){
            // Zmieniamy kierunek jazdy i doliczamy czas potrzbny do dojechania do kolejnego przystanku.
            this.kierunek = !this.kierunek;
            minuta += linia.getCzasyDojazdów()[linia.długośćTrasy() - 1];

            if(minuta >= 60){
                minuta -= 60;
                godzina++;
            }
            return;
        }
        // Teraz gdy nie jesteśmy na pętli.
        if(kierunek){
            minuta += linia.getCzasyDojazdów()[obecnyPrzystanek];
            obecnyPrzystanek++;
            if(minuta >= 60){
                minuta -= 60;
                godzina++;
            }
        }
        else {
            obecnyPrzystanek--;
            minuta += linia.getCzasyDojazdów()[obecnyPrzystanek];

            if(minuta >= 60){
                minuta -= 60;
                godzina++;
            }
        }

        return;
    }

    // Teraz tworzymy konstruktor, gettery i settery.

    public
    Pojazdy(int numer, Linie linia) {
        this.numer = numer;
        this.linia = linia;
    }

    public
    int getNumer() {
        return numer;
    }

    public
    Linie getLinia() {
        return linia;
    }

    public
    int getObecnyPrzystanek() {
        return obecnyPrzystanek;
    }

    public
    int getPętla() {
        return pętla;
    }

    public
    boolean isKierunek() {
        return kierunek;
    }

    public
    int getIluPasażerów() {
        return iluPasażerów;
    }

    public
    Pasazerowie[] getPasażerowie() {
        return pasażerowie;
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
    void setObecnyPrzystanek(int obecnyPrzystanek) {
        this.obecnyPrzystanek = obecnyPrzystanek;
    }

    public
    void setPętla(int pętla) {
        this.pętla = pętla;
    }

    public
    void setKierunek(boolean kierunek) {
        this.kierunek = kierunek;
    }

    public
    void setIluPasażerów(int iluPasażerów) {
        this.iluPasażerów = iluPasażerów;
    }

    public
    void setPasażerowie(int pojemność) {
        this.pasażerowie = new Pasazerowie[pojemność];
    }

    public
    void setDanyPasażer(Pasazerowie pasażer, int i){
        pasażerowie[i] = pasażer;
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
