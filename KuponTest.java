import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class KuponTest {
    private Centrala centrala;
    private Kolektura kolektura;
    private List<Zaklad> zaklady;

    @BeforeEach
    void setUp() {
        centrala = new Centrala(1, 1000000L);
        kolektura = centrala.getKolektury().get(0);

        zaklady = new ArrayList<>();
        zaklady.add(new Zaklad(new int[]{1, 2, 3, 4, 5, 6}));
        zaklady.add(new Zaklad(new int[]{7, 8, 9, 10, 11, 12}));
    }

    @Test
    void testTworzenieKuponu() {
        Kupon kupon = new Kupon(zaklady, 1, kolektura);

        assertNotNull(kupon);
        assertEquals(600L, kupon.getCena()); // 2 zakłady * 1 losowanie * 300 groszy
        assertEquals(2, kupon.getZaklady().size());
        assertEquals(1, kupon.getLosowania().length);
    }

    @Test
    void testObliczanieCenyKuponu() {
        // Test dla różnych kombinacji zakładów i losowań
        Kupon kupon1 = new Kupon(zaklady, 1, kolektura); // 2 zakłady, 1 losowanie
        assertEquals(600L, kupon1.getCena());

        Kupon kupon2 = new Kupon(zaklady, 5, kolektura); // 2 zakłady, 5 losowań
        assertEquals(3000L, kupon2.getCena());

        List<Zaklad> pojedynczyZaklad = Arrays.asList(new Zaklad(new int[]{1, 2, 3, 4, 5, 6}));
        Kupon kupon3 = new Kupon(pojedynczyZaklad, 1, kolektura); // 1 zakład, 1 losowanie
        assertEquals(300L, kupon3.getCena());
    }

    @Test
    void testGenerowanieIdentyfikatoraKuponu() {
        Kupon kupon1 = new Kupon(zaklady, 1, kolektura);
        Kupon kupon2 = new Kupon(zaklady, 1, kolektura);

        assertNotNull(kupon1.toString());
        assertNotNull(kupon2.toString());
        // Identyfikatory powinny być różne
        assertNotEquals(kupon1.toString(), kupon2.toString());
    }

    @Test
    void testDodawanieWygranej() {
        Kupon kupon = new Kupon(zaklady, 1, kolektura);

        assertEquals(0L, kupon.getWygrana());

        kupon.dodajWygrana(2400L);
        assertEquals(2400L, kupon.getWygrana());

        kupon.dodajWygrana(1200L);
        assertEquals(3600L, kupon.getWygrana());
    }

    @Test
    void testKoniecLosowania() {
        Kupon kupon = new Kupon(zaklady, 2, kolektura);

        // Na początku losowania nie są zakończone
        assertFalse(kupon.koniecLos());

        // Po przeprowadzeniu losowań
        centrala.przeprowadzNLosowania(3);
        assertTrue(kupon.koniecLos());
    }
}
