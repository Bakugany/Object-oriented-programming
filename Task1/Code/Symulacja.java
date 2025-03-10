package Ruch_Miejski;

import java.util.Scanner;
import static java.util.Arrays.sort;

public
class Symulacja {
    private int obecnyDzień = 0;

    private int liczbaDni;

    private int pojemnośćPrzystanków;

    private int liczbaPrzystanków;

    private Przystanki[] przystanki;

    private int liczbaPasażerów;

    private Pasazerowie[] pasażerowie;

    private int pojemnośćTramwaju;

    private int liczbaLinii;

    private Linie_Tramwajowe[] linie;

     Statyski_Symulacji statystki = new Statyski_Symulacji(0);

    // Robimy getter do liczby dni symulacji
    public
    int getLiczbaDni() {
        return liczbaDni;
    }

    // Najpierw wczytajmy dane do symulacji.
    public
    void wczytajDane(){
        Scanner sc = new Scanner(System.in);

        liczbaDni = sc.nextInt();
        System.out.println(liczbaDni);

       statystki.ustawLiczbęDni(liczbaDni);

        pojemnośćPrzystanków = sc.nextInt();
        System.out.println(pojemnośćPrzystanków);

        liczbaPrzystanków = sc.nextInt();
        przystanki = new Przystanki[liczbaPrzystanków];
        System.out.println(liczbaPrzystanków);

        for(int i = 0; i< liczbaPrzystanków; i++){
            przystanki[i] = new Przystanki();
            przystanki[i].setNazwa(sc.next());
            przystanki[i].setPojemność(pojemnośćPrzystanków);
            System.out.println(przystanki[i].getNazwa());
        }
        sort(przystanki);

        liczbaPasażerów = sc.nextInt();
        pasażerowie = new Pasazerowie[liczbaPasażerów];
        System.out.println(liczbaPasażerów);

        for(int i = 0; i < liczbaPasażerów; i++){
            pasażerowie[i] = new Pasazerowie(i);
        }

        pojemnośćTramwaju = sc.nextInt();
        System.out.println(pojemnośćTramwaju);

        liczbaLinii = sc.nextInt();
        linie = new Linie_Tramwajowe[liczbaLinii];
        System.out.println(liczbaLinii);

        for(int i = 0; i < liczbaLinii; i++){
            int liczbaTramwajów = sc.nextInt();
            System.out.println(liczbaTramwajów);

            int długośćTrasy = sc.nextInt();
            System.out.println(długośćTrasy);

            linie[i] = new Linie_Tramwajowe(długośćTrasy, liczbaTramwajów, i);

            for(int j = 0; j < długośćTrasy; j++){
                String nazwa = sc.next();
                System.out.println(nazwa);

                Wyszukiwanie_Binarne dodajPrzystanek = new Wyszukiwanie_Binarne();
                Przystanki wstaw = dodajPrzystanek.szukaj(nazwa,przystanki);
                linie[i].dodajPrzystanek(wstaw, j);

                int czas = sc.nextInt();
                System.out.println(czas);

                linie[i].dodajCzas(czas, j);
            }
        }
    }


    // Funkcja pozwala pobrane dane załadować do tramwajów.
    private void załadujTramwaje(){
        for(int i = 0; i < liczbaLinii; i++){
            for(int j = 0; j < linie[i].getLiczbaPojazdów(); j++){
                linie[i].ustawTramwaj( new Tramwaje(j, linie[i], pojemnośćTramwaju), j);
            }
        }
    }

    // Funkcja korzystając z klasy losowanie przydziela pasażerom miejsce zamieszkania.
    private void zakwateruj(){
        Losowanie dom = new Losowanie();
        for(int i = 0; i < liczbaPasażerów; i++){
            int miejsce = dom.losuj(0, przystanki.length);
            pasażerowie[i].setDom(przystanki[miejsce]);
        }
    }

    // Dwie powyższe funkcje traktujemy jako przygotowanie symulacji.
    public void przygotuj(){
        załadujTramwaje();
        zakwateruj();
    }

    public void przeprowadźSymulacje(){
        Kolejka_Zdarzen liniaCzasu = new Kolejka();

        // Najpierw losujemy pasażerom czas przyjścia na przystanek.
        for(int i = 0; i < liczbaPasażerów; i++){
            liniaCzasu.wstaw(pasażerowie[i].czasPrzyjścia());
        }

        // Teraz wypuszczamy tramwaje z zajezdni.
        for(int i = 0; i < liczbaLinii; i++){
            linie[i].rozpocznijPrzejazd(liniaCzasu);
        }

        // Ustawiamy, że początkowo przystanki są puste.
        for(int i = 0; i < liczbaPrzystanków; i++){
            przystanki[i].setIluPasażerów();
        }
        // Teraz tak długo jak kolejka nie jest pusta pobieramy zdarzenia z niej. 
        while(! liniaCzasu.czyPusta()){
            Zdarzenia sytuacja = liniaCzasu.pobierzPierwszy();

            Komunikaty informacjeStacyjne = new Komunikaty();

            // Sprawdzamy teraz, czy sytuacja dotyczy pasażera, czy tramwaju.
            // Zajmijmy się najpierw pasażerem.
            if(!sytuacja.isCzy_tramwaj()){
                boolean czyPodróżuje = sytuacja.getPasażer().rozpocznijPodróż();

                // Na podstawie tego wypisujemy odpowiedni komunikat.
                if(czyPodróżuje == true){
                    informacjeStacyjne.pasażerPrzyszedł(obecnyDzień,sytuacja);
                    continue;
                }
                informacjeStacyjne.pasażerWraca(obecnyDzień,sytuacja);
                continue;
            }
            // Teraz zajmijmy się obsługą tramwaju.
            // Najpierw wypiszmy komunikat o jego przyjeździe.
            informacjeStacyjne.tramwajPrzyjeżdza(obecnyDzień, sytuacja);

            // Sprawdzamy najpierw, czy tramwaj jest na pętli.
            if(sytuacja.getTramwaj().isKierunek() &&
                    sytuacja.getTramwaj().getObecnyPrzystanek() == sytuacja.getTramwaj().getLinia().długośćTrasy() - 1
                || !sytuacja.getTramwaj().isKierunek() && sytuacja.getTramwaj().getObecnyPrzystanek() == 0) {

                // Jeżeli jest na pętli to tylko wypuszcza pasażerów.
                sytuacja.getTramwaj().wypuść(obecnyDzień, sytuacja);

                // Jeżeli jest na swojej macierzystej pętli i jest po 23 to kończy przejazd
                if(sytuacja.getGodzina() >= 23 && sytuacja.getTramwaj().getObecnyPrzystanek() ==
                        sytuacja.getTramwaj().getPętla()){
                    continue;
                }
                // Jeżeli jest na nie macierzystej pętli to kończy jeszcze przejazd.
                else {
                    // Tramwaj jedzie do kolejnego przystanku.
                    sytuacja.getTramwaj().jedź();

                    // Dodaje swój odjazd do linii czasu.
                    Zdarzenia kolejna_sytuacja = new Zdarzenia(sytuacja.getTramwaj().getGodzina(),
                            sytuacja.getTramwaj().getMinuta(),sytuacja.getTramwaj(), true);
                    liniaCzasu.wstaw(kolejna_sytuacja);
                    continue;
                }
            }

            // Jeżeli jest poza pętlą, to wypuszcza i wpuszcza pasażerów i jedzie dalej.
            sytuacja.getTramwaj().wypuść(obecnyDzień, sytuacja);
            sytuacja.getTramwaj().wpuść(statystki,obecnyDzień);
            sytuacja.getTramwaj().jedź();

            // Dodaje swój odjazd do linii czasu.
            Zdarzenia kolejna_sytuacja = new Zdarzenia(sytuacja.getTramwaj().getGodzina(),
                    sytuacja.getTramwaj().getMinuta(),sytuacja.getTramwaj(), true);
            liniaCzasu.wstaw(kolejna_sytuacja);

        }
        statystki.podliczOczekujących(pasażerowie, obecnyDzień);
        obecnyDzień++;
    }

    public void podsumuj(){
        statystki.wypisz_statystyki_symulacji();
    }
}
