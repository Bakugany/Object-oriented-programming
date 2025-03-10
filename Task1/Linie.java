package Ruch_Miejski;

public abstract
class Linie {
    private int numerLinii;
    private Przystanki[] trasa;
    private int[] czasyDojazdów;
    private int liczbaPojazdów;
    private Pojazdy[] pojazdy;


    // Funkcja pozwala pojazdom rozpocząć kursowanie i dodać to jako zdarzenie.
    public abstract void rozpocznijPrzejazd(Kolejka_Zdarzen linia_czasu);

    // Funkcja pozwalająca wstawić przystanek w podanym miejscu trasy.
    public void dodajPrzystanek(Przystanki przystanek, int i){
        trasa[i] = przystanek;
    }

    // Funkcja pozwalająca wstawić czas przejazdu na danym odcinku trasy.
    public void dodajCzas(int czasyDojazdu, int i){
        czasyDojazdów[i] = czasyDojazdu;
    }

    // Funkcja zwraca czas potrzebny na przebycie całej trasy
    public int czasTrasy(){
        int czasPrzebyciaTrasy = 0;

        for(int i = 0; i < czasyDojazdów.length; i++){
            czasPrzebyciaTrasy += 2 * czasyDojazdów[i];
        }

        return czasPrzebyciaTrasy;
    }

    // Funkcja zwraca długość trasy tej linii
    public int długośćTrasy(){
        return trasa.length;
    }

    // Konstruktor dla tej klasy
    public
    Linie(int długośćTrasy, int liczbaPojazdów, int numerLinii) {
        this.numerLinii = numerLinii;
        this.trasa = new Przystanki[długośćTrasy];
        this.czasyDojazdów = new int[długośćTrasy];
        this.liczbaPojazdów =  liczbaPojazdów;
        this.pojazdy = new Pojazdy[liczbaPojazdów];
    }

    // Teraz jeszcze robimy gettery do tych atrybutów


    public
    int getLiczbaPojazdów() {
        return liczbaPojazdów;
    }

    public
    int[] getCzasyDojazdów() {
        return czasyDojazdów;
    }

    public
    Przystanki[] getTrasa() {
        return trasa;
    }

    public
    int getNumerLinii() {
        return numerLinii;
    }
}
