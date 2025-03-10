package Ruch_Miejski;

public
class Linie_Tramwajowe extends Linie{
    private Tramwaje[] tramwaje;



    @Override
    public
    void rozpocznijPrzejazd(Kolejka_Zdarzen linia_czasu) {
        int częstotliwość = czasTrasy() / getLiczbaPojazdów();

        // Pierwszą (większą połowę) tramwajów wypuszczamy z pierwszego końca trasy.
        for(int i = 0; i <= (getLiczbaPojazdów() - 1) / 2; i++){
            tramwaje[i].setGodzina(6);
            tramwaje[i].setMinuta(i * częstotliwość);
            tramwaje[i].setIluPasażerów(0);
            tramwaje[i].setPętla(0);
            tramwaje[i].setObecnyPrzystanek(0);
            tramwaje[i].setKierunek(true);

            Zdarzenia tramwajWyrusza = new Zdarzenia(6, i * częstotliwość, tramwaje[i], true);
            linia_czasu.wstaw(tramwajWyrusza);
        }

        // I teraz dla drugiej połowy tramwajów robimy to samo.
        int początek = (getLiczbaPojazdów() + 1) / 2;
        for(int i = początek; i < getLiczbaPojazdów() ; i++){
            tramwaje[i].setGodzina(6);
            tramwaje[i].setMinuta((i-początek) * częstotliwość);
            tramwaje[i].setIluPasażerów(0);
            tramwaje[i].setPętla(długośćTrasy() - 1);
            tramwaje[i].setObecnyPrzystanek(długośćTrasy() - 1);
            tramwaje[i].setKierunek(false);

            Zdarzenia tramwajWyrusza = new Zdarzenia(6, (i- początek)* częstotliwość, tramwaje[i], true);
            linia_czasu.wstaw(tramwajWyrusza);
        }
    }

    // Tworzymy konstruktor dla tej klasy.
    public
    Linie_Tramwajowe(int długośćTrasy, int liczbaPojazdów, int numerLinii) {
        super(długośćTrasy, liczbaPojazdów, numerLinii);
        this.tramwaje = new Tramwaje[liczbaPojazdów];
    }

    // Funkcja pozwala ustawić dany tramwaj.
    public
    void ustawTramwaj(Tramwaje tramwaj, int i){
        this.tramwaje[i] = tramwaj;
    }

}
