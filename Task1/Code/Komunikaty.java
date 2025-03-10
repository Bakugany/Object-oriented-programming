package Ruch_Miejski;

// Funkcja wypisuje komunikaty, które wyświetlnane są przez symulacje.
public
class Komunikaty {

    public void pasażerPrzyszedł(int obecnyDzień, Zdarzenia sytuacja){
        System.out.println(obecnyDzień + " dnia symulacji o godzinie " +
                sytuacja.getGodzina()+ ":" + sytuacja.getMinuta() +
                " pasażer o identyfikatorze: " + sytuacja.getPasażer().getId() +
                " przyszedł na przystanek "+ sytuacja.getPasażer().getDom().getNazwa() +
                " i oczekuje na  tramwaj");
    }

    public void pasażerWraca(int obecnyDzień, Zdarzenia sytuacja){
        System.out.println(obecnyDzień + " dnia symulacji o godzinie " +
                sytuacja.getGodzina()+ ":" + sytuacja.getMinuta() +
                " pasażer o identyfikatorze: " + sytuacja.getPasażer().getId() +
                " przyszedł na przystanek "+ sytuacja.getPasażer().getDom().getNazwa()+
                " i nie zmieścił się na przystanku, więc nie podróżuje dzisiaj");
    }

    public void tramwajPrzyjeżdza(int obecnyDzień, Zdarzenia sytuacja){
        System.out.println(obecnyDzień + " dnia symulacji o godzinie " +
                sytuacja.getGodzina()+ ":" + sytuacja.getMinuta() +
                " Tramwaj: " + sytuacja.getTramwaj().getNumer() +
                " linii "+ sytuacja.getTramwaj().getLinia().getNumerLinii()+
                " przyjechał na przystanek " +
                sytuacja.getTramwaj().getLinia().getTrasa()[sytuacja.getTramwaj().getObecnyPrzystanek()].getNazwa());
    }

    public void pasażerWysiada(int obecnyDzień, Zdarzenia sytuacja, Pasazerowie pasażer){
        System.out.println(obecnyDzień+ " dnia symulacji o godzinie " + sytuacja.getGodzina() +
                ":" + sytuacja.getMinuta() + " pasażer o identyfikatorze " + pasażer.getId()
                + " wysiadł z tramwaju linii " + sytuacja.getTramwaj().getLinia().getNumerLinii() + " o numerze bocznym " +
               sytuacja.getTramwaj().getNumer() + " na przystanku " + pasażer.getCel().getNazwa());
    }

    public void całkowiteStatystki(int ilośćPrzejazdów, int całkowityCzas, int dzielnikŚredniCzasOczekiwania){
        Czas przelicznik = new Czas();
        System.out.println("Łączna liczba przejazdów pasażerów to " + ilośćPrzejazdów);
        System.out.println("Średni czas oczekiwania pasażera na tramwaj to " +
                (przelicznik.godziny(całkowityCzas, dzielnikŚredniCzasOczekiwania) + " godzin oraz " +
                        (przelicznik.minuty(całkowityCzas, dzielnikŚredniCzasOczekiwania) + " minut")));
        System.out.println("Statyski dla każdego dnia prezentują się następująco:");
    }

    public void statystkiDzienne(int dzień, int liczbaPrzejazdów, int czasCzekania){
        System.out.println(dzień +" dnia symulacji łączna liczba przejazdów wyniosła " + liczbaPrzejazdów);
        System.out.println(dzień +" dnia symulacji łączny czas oczekiwania wyniosł " + czasCzekania);
    }

    public void pasażerWsiada(int obecnyDzień, Tramwaje tramwaj, Pasazerowie wsiadający){
        System.out.println(obecnyDzień + " dnia symulacji o godzinie " + tramwaj.getGodzina() +
                ":" + tramwaj.getMinuta() + " pasażer o identyfikatorze " + wsiadający.getId()
                + " wsiadł do tramwaju linii " + tramwaj.getLinia().getNumerLinii() + " o numerze bocznym " +
                tramwaj.getNumer() + " na przystanku " +
                tramwaj.getLinia().getTrasa()[tramwaj.getObecnyPrzystanek()].getNazwa()
                 + " z zamiarem dojechania na przystanek " + wsiadający.getCel().getNazwa());
    }
}
