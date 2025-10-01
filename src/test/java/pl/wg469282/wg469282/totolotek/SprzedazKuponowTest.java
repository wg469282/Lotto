package pl.wg469282.totolotek;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class SprzedazKuponowTest {
    private Centrala centrala;
    private Kolektura kolektura;
    private Gracz gracz;

    @BeforeEach
    void setUp() {
        centrala = new Centrala(1, 1000000L);
        kolektura = centrala.getKolektury().get(0);
        gracz = new Minimalista("Jan", "Kowalski", 12345678, kolektura, centrala.getKolektury());
    }

    @Test
    void testSprzedazKuponuBezWystarczającychŚrodków() {
        // Upewnij się, że gracz ma dokładnie 200 groszy (mniej niż koszt kuponu)
        long aktualneSrodki = gracz.ilePieniedzy();
        if (aktualneSrodki >= 200L) {
            gracz.zaplac(aktualneSrodki - 200L); // Zostaw tylko 200 groszy
        } else {
            gracz.odbierzPieniadze(200L - aktualneSrodki); // Dodaj do 200 groszy
        }

        // Sprawdź, że gracz ma mniej niż koszt kuponu (300 groszy)
        assertTrue(gracz.ilePieniedzy() < 300L);

        // Test powinien rzucić wyjątek z powodu niewystarczających środków
        assertThrows(IllegalArgumentException.class, () -> {
            kolektura.wydajKuponChybilTrafil(1, 1, gracz);
        });

        // Sprawdź, że środki gracza nie zmieniły się (transakcja nie doszła do skutku)
        assertEquals(200L, gracz.ilePieniedzy());
    }

    @Test
    void testOdprowadzaniePodatku() {
        gracz.odbierzPieniadze(1000L);
        long początkowePodatki = centrala.getPanstwo().getPodatki();

        kolektura.wydajKuponChybilTrafil(1, 1, gracz);

        // Podatek to 20% z ceny brutto (0,60 zł z 3,00 zł)
        long oczekiwanyPodatek = 300L * 20 / 100; // 60 groszy
        assertEquals(początkowePodatki + oczekiwanyPodatek, centrala.getPanstwo().getPodatki());
    }

    @Test
    void testDodawanieKuponuDoSystemu() {
        gracz.odbierzPieniadze(1000L);
        int początkowaLiczbaKuponow = centrala.ileKuponow();

        kolektura.wydajKuponChybilTrafil(1, 1, gracz);

        assertEquals(początkowaLiczbaKuponow + 1, centrala.ileKuponow());
        assertEquals(1, gracz.getKupony().size());
    }
}
