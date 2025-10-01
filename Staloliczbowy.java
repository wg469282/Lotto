import java.util.*;

public class Staloliczbowy extends Gracz {
    private int[] mojeLiczby;
    private Kolektura[] mojeKolektury;
    private int kotraKolektura;

    public Staloliczbowy(long p, String i, String n, int ps, int[] favLicz, List<Kolektura> k, Kolektura[] favKol) {
        super(p, i, n, ps, k);

        // Walidacja ulubionych liczb
        if (favLicz.length != 6) {
            throw new IllegalArgumentException("Gracz stałoliczbowy musi mieć dokładnie 6 ulubionych liczb");
        }

        // Sprawdź czy liczby są w zakresie 1-49 i są unikalne
        Set<Integer> sprawdzUnikalne = new HashSet<>();
        for (int liczba : favLicz) {
            if (liczba < 1 || liczba > 49) {
                throw new IllegalArgumentException("Liczby muszą być w zakresie 1-49");
            }
            if (!sprawdzUnikalne.add(liczba)) {
                throw new IllegalArgumentException("Liczby muszą być rozne");
            }
        }

        this.mojeLiczby = Arrays.copyOf(favLicz, 6);
        Arrays.sort(this.mojeLiczby); // Posortuj liczby
        this.mojeKolektury = favKol;
        this.kotraKolektura = 0;
    }

    /**
     * Implementacja abstrakcyjnej metody z klasy Gracz
     * Wypełnia blankiet swoimi ulubionymi liczbami
     */
    void wypelnijBlankiet() {
        // Sprawdź czy gracz ma jakiekolwiek kupony
        if (!this.kupony.isEmpty()) {
            Kupon ostatniKupon = this.kupony.get(this.kupony.size() - 1);
            int[] losowania = ostatniKupon.getLosowania();
            int ostatnieLosowanie = losowania[losowania.length - 1];

            // Jeśli poprzedni kupon jeszcze aktywny - nie może kupić nowego
            if (ostatnieLosowanie >= this.kolektury.get(0).getCentrala().nastLosowanie()) {
                return; // Poprzedni kupon jeszcze aktywny
            }
        }

        // Gracz może kupić nowy kupon - wypełnij blankiet swoimi ulubionymi liczbami
        Set<Integer> ulubioneLiczby = new HashSet<>();
        for (int liczba : this.mojeLiczby) {
            ulubioneLiczby.add(liczba);
        }
        List<Set<Integer>> typy = new ArrayList<>();
        typy.add(ulubioneLiczby);

        try {
            Blankiet b = new Blankiet(this.mojeKolektury[this.kotraKolektura], 10, typy);
            b.wydajBlankiet(this);
            this.kotraKolektura = (this.kotraKolektura + 1) % mojeKolektury.length;
        } catch (IllegalArgumentException e) {
            // Jeśli gracz nie ma pieniędzy, zwyczajnie nie kupi kuponu
        }
    }

    @Override
    public void kupKupon() {
        wypelnijBlankiet();
    }

}
