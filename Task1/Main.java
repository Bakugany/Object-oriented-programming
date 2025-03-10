package Ruch_Miejski;
public
class Main {
    public static
    void main(String[] args) {
        Symulacja symulacja = new Symulacja();
        symulacja.wczytajDane();
        symulacja.przygotuj();
        for(int i = 0; i < symulacja.getLiczbaDni(); i++){
            symulacja.przeprowadźSymulacje();
        }
        symulacja.podsumuj();
    }
}