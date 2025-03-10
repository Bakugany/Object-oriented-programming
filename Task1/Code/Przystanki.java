package Ruch_Miejski;

public
class Przystanki implements Comparable<Przystanki> {
    private String nazwa;
    private int pojemność;
    private int iluPasażerów;
    private Pasazerowie[] pasażerowie;

    // Implementujemy porównanie przystanków leksykograficznie po nazwie.
    @Override
    public int compareTo(Przystanki inny_przystanek){
        return (this.nazwa.compareTo(inny_przystanek.nazwa));
    }

    // Funkcja pozwala dodać pasażera na przystanek.
    public void dodaj(Pasazerowie pasażer){
        pasażerowie[iluPasażerów] = pasażer;
        iluPasażerów ++;
    }

    // Funkcja pozwala zabrać pasażera z przystanku
    public Pasazerowie zabierz(){
        Pasazerowie wsiadający = pasażerowie[iluPasażerów - 1];
        iluPasażerów --;
        return wsiadający;
    }

    // Teraz jeszcze tworzymy gettery i settery


    public
    String getNazwa() {
        return nazwa;
    }

    public
    int getPojemność() {
        return pojemność;
    }

    public
    int getIluPasażerów() {
        return iluPasażerów;
    }

    public
    void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public
    void setPojemność(int pojemność) {
        this.pojemność = pojemność;
        this.pasażerowie = new Pasazerowie[pojemność];
    }

    public
    void setIluPasażerów() {
        this.iluPasażerów = 0;
    }
}
