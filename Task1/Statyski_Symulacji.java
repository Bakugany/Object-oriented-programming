package Ruch_Miejski;

// Klasa zbiera statystki symulacji.
public
class Statyski_Symulacji {
    private int dzielnikŚredniCzasOczekiwania = 0;

    private int[] liczbaPrzejazdów;

    private int[] czasCzekania;

    public
    Statyski_Symulacji(int dzielnikŚredniCzasOczekiwania) {
        this.dzielnikŚredniCzasOczekiwania = dzielnikŚredniCzasOczekiwania;
    }

    public void ustawLiczbęDni(int liczbaDni){
        liczbaPrzejazdów = new int[liczbaDni];
        czasCzekania = new int[liczbaDni];
    }

    // Funkcja wypisuje statystki naszej symulacji
    public void wypisz_statystyki_symulacji(){
        int ilośćPrzejazdów = 0;

        Komunikaty wypiszStatystki = new Komunikaty();
        for(int i =0; i < liczbaPrzejazdów.length; i++){
            ilośćPrzejazdów += liczbaPrzejazdów[i];
        }

        int całkowityCzas = 0;

        for (int i = 0; i < liczbaPrzejazdów.length; i++) {
            całkowityCzas += czasCzekania[i];
        }

        wypiszStatystki.całkowiteStatystki(ilośćPrzejazdów,całkowityCzas,dzielnikŚredniCzasOczekiwania);

        for(int i =0; i < liczbaPrzejazdów.length; i++){
            wypiszStatystki.statystkiDzienne(i, liczbaPrzejazdów[i], czasCzekania[i]);
        }
    }

    //
    public void dodaj(int godzinaPasażera, int minutaPasażera, int aktualnyDzień, int godzinaTramwaju, int minutaTramwaju){
       dzielnikŚredniCzasOczekiwania++;
        liczbaPrzejazdów[aktualnyDzień]++;
        Czas przelicznik = new Czas();
        czasCzekania[aktualnyDzień] += przelicznik.dodaj(godzinaTramwaju,godzinaPasażera,minutaTramwaju, minutaPasażera);
    }

    // Zakładam, że pasażerowie, którym udało się zostać na przystanku wychodząc z domu czekali na tramwaje do
    // północy i doliczam czas, który jeszcze spędzieli od ostatniego przejazdu do statystyk.
    public void podliczOczekujących(Pasazerowie[] pasażerowie, int aktualny_dzień_symulacji){
        for(int i = 0; i < pasażerowie.length; i++){
            if(pasażerowie[i].isCzyPodróżuje()){
                dzielnikŚredniCzasOczekiwania ++;
                Czas przelicznik = new Czas();
                czasCzekania[aktualny_dzień_symulacji] += przelicznik.czasDoPółnocy(pasażerowie[i].getGodzina(),
                        pasażerowie[i].getMinuta());
            }
        }
    }

}
