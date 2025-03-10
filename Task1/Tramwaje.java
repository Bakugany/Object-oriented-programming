package Ruch_Miejski;

public
class Tramwaje extends Pojazdy{
    private int pojemność;
    private Linie_Tramwajowe linia;

    // Implementujemy metody abstrakcyjne pozwalająca wsiąść i wysiąść.

    @Override
    protected
    Pasazerowie wysiada(int i) {
        Pasazerowie wsiadający = getPasażerowie()[i];
        setDanyPasażer(null, i);
        setIluPasażerów(getIluPasażerów() - 1);
        return wsiadający;
    }

    @Override
    protected
    void wsiada(Pasazerowie pasażer) {
        for(int i = 0; i < pojemność; i++){
            if(getPasażerowie()[i] == null){
                getPasażerowie()[i] = pasażer;
                setIluPasażerów(getIluPasażerów() + 1);
                return;
            }
        }
    }


    public void wypuść(int obecnyDzień, Zdarzenia sytuacja){
        // Przechodzimy po tablicy pasażerów i sprawdzamy, czy to ich przystanek i czy jest miejsce by ich wypuścić
        for(int i = 0; i < getPasażerowie().length; i++){
            if(getPasażerowie()[i] != null &&
                    getPasażerowie()[i].getCel() == getLinia().getTrasa()[getObecnyPrzystanek()] &&
                    getPasażerowie()[i].getCel().getIluPasażerów() <getPasażerowie()[i].getCel().getPojemność()) {

                // Jeżeli było miejsce to nadajemy komunikat
                Komunikaty informacjeStacyjne = new Komunikaty();
                informacjeStacyjne.pasażerWysiada(obecnyDzień, sytuacja, getPasażerowie()[i]);

                // Wsadzamy pasażera na przystanek.
                Pasazerowie wysiadający = getPasażerowie()[i];
                getPasażerowie()[i].getCel().dodaj(wysiadający);

                // Wysadzamy go z tramwaju.
                wysiada(i);

                // Ustawiamy mu czas kiedy opuścił tramwaj.
                wysiadający.setGodzina(getGodzina());
                wysiadający.setMinuta(getMinuta());

            }
        }
    }

    public void wpuść(Statyski_Symulacji statystki, int obecnyDzień ) {
        // Dopóki mamy miejsce w tramwaju i pasażerów na przystanku to wsiadają oni do tramwaju.
        while (getIluPasażerów() < getPojemność() && getLinia().getTrasa()[getObecnyPrzystanek()].getIluPasażerów() > 0) {

            Pasazerowie wsiadający = getLinia().getTrasa()[getObecnyPrzystanek()].zabierz();

            // Dodajemy do statystyk naszej symulacji przejazd tego pasażera i ile oczekiwał.
            statystki.dodaj(wsiadający.getGodzina(), wsiadający.getMinuta(), obecnyDzień, this.getGodzina(), this.getMinuta());

            wsiada(wsiadający);

            // Losujemy przystanki na jakie pasażer może chcieć jechać.
            Losowanie gdzie_jedzie = new Losowanie();
            int wynik_losowania;
            if (isKierunek()) {
                wynik_losowania = gdzie_jedzie.losuj(getObecnyPrzystanek() + 1, getLinia().długośćTrasy());
            }
            else {
                wynik_losowania = gdzie_jedzie.losuj(0, getObecnyPrzystanek());
            }

            // Ustawiamy pasażerowi wylosowany cel podróży.
            Przystanki celPodróży = getLinia().getTrasa()[wynik_losowania];
            wsiadający.setCel(celPodróży);

            // Nadajemy komunikat o tym, że pasażer wsiadł.
            Komunikaty informacjeStacyjne = new Komunikaty();
            informacjeStacyjne.pasażerWsiada(obecnyDzień, this, wsiadający);

        }
    }



    // Teraz jeszcze piszemy konstruktor oraz gettery

    public
    Tramwaje(int numer, Linie_Tramwajowe linia, int pojemność) {
        super(numer, linia);
        this.linia = linia;
        this.pojemność = pojemność;
        this.setPasażerowie(pojemność);
    }

    public
    int getPojemność() {
        return pojemność;
    }

    @Override
    public
    Linie_Tramwajowe getLinia() {
        return linia;
    }
}
