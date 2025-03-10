package Ruch_Miejski;

public
interface Kolejka_Zdarzen {
    public abstract void wstaw(Zdarzenia zdarzenie);
    public abstract Zdarzenia pobierzPierwszy();
    public abstract boolean czyPusta();
}
