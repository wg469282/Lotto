package pl.wg469282.totolotek.model;

import org.junit.jupiter.api.Test;

import pl.wg469282.totolotek.model.Zaklad;

import static org.junit.jupiter.api.Assertions.*;

public class ZakladTest {

    @Test
    void testTworzeniePoprawnegaZakladu() {
        int[] typy = { 1, 15, 23, 31, 42, 49 };
        Zaklad zaklad = new Zaklad(typy);

        assertNotNull(zaklad);
        assertArrayEquals(new int[] { 1, 15, 23, 31, 42, 49 }, zaklad.getTypy());
    }

    @Test
    void testSortowanieTypów() {
        int[] typy = { 49, 1, 31, 15, 42, 23 }; // Nieposortowane
        Zaklad zaklad = new Zaklad(typy);

        assertArrayEquals(new int[] { 1, 15, 23, 31, 42, 49 }, zaklad.getTypy());
    }

    @Test
    void testZakladZNiepoprawnąLiczbąTypów() {
        int[] typy = { 1, 2, 3, 4, 5 }; // Tylko 5 liczb

        assertThrows(IllegalArgumentException.class, () -> {
            new Zaklad(typy);
        });
    }

    @Test
    void testZakladZLiczbąPozaZakresem() {
        int[] typy = { 1, 2, 3, 4, 5, 50 }; // 50 jest poza zakresem

        assertThrows(IllegalArgumentException.class, () -> {
            new Zaklad(typy);
        });

        int[] typy2 = { 0, 1, 2, 3, 4, 5 }; // 0 jest poza zakresem

        assertThrows(IllegalArgumentException.class, () -> {
            new Zaklad(typy2);
        });
    }

    @Test
    void testZakladZAnulowaniem() {
        int[] typy = { 1, 2, 3, 4, 5, 6 };
        Zaklad zaklad = new Zaklad(typy, true);

        assertNotNull(zaklad);
        assertTrue(zaklad.anuluj);
    }
}
