import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Minimalista extends Gracz {
    private Kolektura k;

    public Minimalista(String i, String n, int ps, Kolektura kl, List<Kolektura> k) {
        super(ThreadLocalRandom.current().nextLong(1, 1_000_000), i, n, ps, k);
        this.k = kl;
        this.kupony = new ArrayList<>(); // Inicjalizacja listy kuponów
    }

    void wypelnijBlankiet() {
        // Minimalista nie wypełnia blankietów - używa tylko chybił-trafił
        // Ta metoda może pozostać pusta lub rzucać wyjątek
        throw new UnsupportedOperationException("Minimalista nie używa blankietów");
    }

    /**
     * Kupuje kupon zgodnie ze specyfikacją Minimalisty:
     * - jeden zakład na chybił-trafił
     * - tylko na jedno najbliższe losowanie
     * - zawsze w swojej ulubionej kolekturze
     */
    @Override
    public void kupKupon() {
        try {
            this.k.wydajKuponChybilTrafil(1, 1, this);

        } catch (IllegalArgumentException e) {
            // Jeśli nie ma środków, po prostu nie kupuje kuponu
            // Zgodnie ze specyfikacją gracz nie może kupić kuponu bez wystarczających
            // środków
        }
    }

    /**
     * @return ulubioną kolekturę Minimalisty
     */
    public Kolektura getUlubionaKolektura() {
        return this.k;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Minimalista: ").append(this.imie)
                .append(", PESEL: ").append(this.PESEL)
                .append(", Środki: ").append(this.pieniadze).append(" groszy")
                .append(", Ulubiona kolektura: ").append(this.k.getNumer());

        if (this.kupony.isEmpty()) {
            sb.append(", Brak kuponów");
        } else {
            sb.append(", Kupony: ");
            for (int i = 0; i < this.kupony.size(); i++) {
                sb.append(this.kupony.get(i).toString());
                if (i < this.kupony.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }

}
