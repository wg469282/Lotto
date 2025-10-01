import java.util.*;

public class BudzetPanstwa {
    private long budzet;// Zakladamy jako wlasciciele loterii mamy bardzo bliskie powiazanai z
                        // wladza(zapewne totalitarna)
    private long subwencje;
    private long podatki;

    public BudzetPanstwa() {
        this.budzet = 80920000000000L;// PKB Polski 2023, z załozenia wynika ze mamy dostep do calosci PKB
        this.subwencje = 0;
        this.podatki = 0;
    }

    public void dodajPieniadze(long l) {
        if (l < 0) {
            throw new IllegalArgumentException("Nie wolno wyciagac pieniedzy z kieszeni wszystkich Polek i Polakow");
        }
        this.podatki += l;
        this.budzet += l;
    }

    public void udzielSubwencji(long s) {
        if (budzet < s) {
            System.err.println("Jak do tego doszlo??");
            return;
        }
        subwencje += s;
        budzet -= s;

    }

    public void pobierzPodatek(long ile) {
        this.podatki += ile;
        this.budzet += ile;
    }

    public long getSubwencje() {
        return this.subwencje;
    }

    public long getPodatki() {
        return this.podatki;
    }

    public long getBudzet() {
        return this.budzet;
    }
    @Override
    public String toString() {
        String s = "";
        s += "Budzet Panstwa : "+this.budzet/100 +","+ this.budzet%100;
        s += "\nSubwencje : "+this.subwencje/100 +","+ this.subwencje%100;
        s += "\nPodatki : "+this.podatki/100 + ","+this.podatki%100;
        return s;
    }
}


