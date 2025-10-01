import java.util.*;

public class StaloBlankietowy extends Gracz {
    private Blankiet staloBlankiet;
    private int coIleLosowania;
    private int licznikLosowania;
    private Kolektura[] mojeKolektury;
    private int aktualnaKolekturaIndex;

    public StaloBlankietowy(long p, String i, String n, int ps, List<Kolektura> k,
            Blankiet bl, int coIle, Kolektura[] favKol) {
        super(p, i, n, ps, k);

        // Walidacja parametrów
        if (bl == null) {
            throw new IllegalArgumentException("Stały blankiet nie może być null");
        }
        if (coIle <= 0) {
            throw new IllegalArgumentException("Częstotliwość kupowania musi być większa od 0");
        }
        if (favKol == null || favKol.length == 0) {
            throw new IllegalArgumentException("Gracz musi mieć przynajmniej jedną ulubioną kolekturę");
        }

        this.staloBlankiet = bl;
        this.coIleLosowania = coIle;
        this.licznikLosowania = 0;
        this.mojeKolektury = favKol;
        this.aktualnaKolekturaIndex = 0;
    }

    /**
     * Implementacja abstrakcyjnej metody z klasy Gracz
     * Gracz stałoblankietowy ma swój blankiet i nie wypełnia nowego
     */

    void wypelnijBlankiet() {
        // Gracz stałoblankietowy ma już swój stały blankiet
        throw new UnsupportedOperationException(
                "Gracz stałoblankietowy nie wypełnia nowego blankietu - ma swój stały blankiet");
    }

    /**
     * Kupuje kupon zgodnie ze specyfikacją Stałoblankietowego:
     * - ma swój stały blankiet
     * - kupuje kupon co pewną stałą liczbę losowań
     * - korzysta z ulubionych kolektur na zmianę
     */
    @Override
    void kupKupon() {
        // Sprawdź czy nadszedł czas na kupno kuponu (co określoną liczbę losowań)
        if (this.licznikLosowania % this.coIleLosowania != 0) {
            return; // Nie czas na kupowanie kuponu
        }

        try {
            // Wybierz kolekturę z rotacją
            Kolektura wybranaKolektura = this.mojeKolektury[this.aktualnaKolekturaIndex];
            this.aktualnaKolekturaIndex = (this.aktualnaKolekturaIndex + 1) % this.mojeKolektury.length;

            // Użyj stałego blankietu do wydania kuponu
            this.staloBlankiet.wydajBlankiet(this);
            this.licznikLosowania++;
        } catch (IllegalArgumentException e) {
            // Jeśli nie ma środków lub inny błąd, po prostu nie kupuje kuponu
        }
    }

    /**
     * Ustawia nowy stały blankiet
     * Bo gracz staloblankietowy podobnie jak my wszyscy moze sie zmienic, ale tylko
     * troche
     */
    public void setStaloBlankiet(Blankiet nowyBlankiet) {
        if (nowyBlankiet == null) {
            throw new IllegalArgumentException("Blankiet nie może być null");
        }
        this.staloBlankiet = nowyBlankiet;
    }

    /**
     * Ustawia nową częstotliwość kupowania kuponów
     */
    public void setCoIleLosowania(int nowaWartosc) {
        if (nowaWartosc <= 0) {
            throw new IllegalArgumentException("Częstotliwość musi być większa od 0");
        }
        this.coIleLosowania = nowaWartosc;
    }

}
