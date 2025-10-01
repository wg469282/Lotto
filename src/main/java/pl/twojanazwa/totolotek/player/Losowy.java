import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Losowy extends Gracz {

    public Losowy(String i, String n, int ps, List<Kolektura> dostepneKolektury, List<Kolektura> k) {
        // Losowa kwota mniej niż milion zł (100_000_000 groszy)
        super(ThreadLocalRandom.current().nextLong(1, 100_000_000), i, n, ps, k);
        this.kolektury = dostepneKolektury;
    }

    /**
     * Implementacja abstrakcyjnej metody z klasy Gracz
     * Gracz losowy nie używa blankietów - kupuje tylko kupony chybił-trafił
     */

    void wypelnijBlankiet() {
        throw new UnsupportedOperationException("Gracz losowy nie używa blankietów");
    }

    /**
     * Kupuje losową liczbę kuponów chybił-trafił (od 1 do 100)
     * w losowo wybranej kolekturze
     */
    @Override
    void kupKupon() {
        if (this.kolektury.isEmpty()) {
            throw new UnsupportedOperationException("Brak dostepnych kolektur");
        }

        // Losowa liczba kuponów do kupienia (1-100)
        int liczbaKuponow = ThreadLocalRandom.current().nextInt(1, 101);

        for (int i = 0; i < liczbaKuponow; i++) {
            // Sprawdź czy gracz ma jeszcze środki na minimalny kupon (3 zł = 300 groszy)
            if (this.pieniadze < 300L) {
                break; // Brak środków na kolejne kupony
            }

            try {
                // Losowo wybierz kolekturę
                Kolektura wybranaKolektura = kolektury.get(
                        ThreadLocalRandom.current().nextInt(kolektury.size()));

                // Losowa liczba zakładów (1-8, zgodnie z ograniczeniami kuponu)
                int liczbaZakladow = ThreadLocalRandom.current().nextInt(1, 9);

                // Losowa liczba losowań (1-10, zgodnie z ograniczeniami kuponu)
                int liczbaLosowan = ThreadLocalRandom.current().nextInt(1, 11);

                /*
                 * Zarowno gracz jak i kupon tj kolektura sprawdzaja czygracza stac na dany
                 * kupon co lepiej odwzorowywujefunkcjonowanie swiata rzeczywistego
                 */
                long cenaKuponu = liczbaZakladow * liczbaLosowan * 300L;
                if (cenaKuponu <= this.pieniadze) {
                    wybranaKolektura.wydajKuponChybilTrafil(liczbaZakladow, liczbaLosowan, this);
                } else {
                    break; // Brak środków na ten kupon
                }

            } catch (IllegalArgumentException e) {
                // Jeśli nie ma środków lub inny błąd, przerwij kupowanie
                break;
            }
        }
    }

    /**
     * Ustawia nową listę dostępnych kolektur na wypadek gdyby losowy losowo chcial zmiany
     */
    public void setKolektury(List<Kolektura> kolektury) {
        this.kolektury = kolektury;
    }
}
