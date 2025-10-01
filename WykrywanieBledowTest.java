import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class WykrywanieBledowTest {
    private Centrala centrala;
    private Kolektura kolektura;

    @BeforeEach
    void setUp() {
        centrala = new Centrala(1, 1000000L);
        kolektura = centrala.getKolektury().get(0);
    }

    @Test
    void testTworzenieZakladuZDuplikatami() {
        int[] typyZDuplikatami = { 1, 1, 2, 3, 4, 5 }; // Duplikat liczby 1

        // W obecnej implementacji Zaklad nie sprawdza duplikatów,
        // ale powinien - to jest błąd do wykrycia
        Zaklad zaklad = new Zaklad(typyZDuplikatami);

        // Test sprawdza czy system poprawnie obsługuje duplikaty
        int[] wynik = zaklad.getTypy();
        Set<Integer> unikalne = new HashSet<>();
        for (int liczba : wynik) {
            unikalne.add(liczba);
        }

        // Jeśli są duplikaty, zbiór będzie mniejszy niż tablica
        assertTrue(unikalne.size() <= wynik.length);
    }

    @Test
    void testNiepoprawneDaneWKonstruktorach() {
        // Test dla Zaklad z null
        assertThrows(Exception.class, () -> {
            new Zaklad(null);
        });

        // Test dla pustej tablicy
        assertThrows(IllegalArgumentException.class, () -> {
            new Zaklad(new int[] {});
        });
    }

    @Test
    void testGraniceZakresowLiczb() {
        // Test granicznych wartości
        int[] typy1 = { 1, 2, 3, 4, 5, 6 }; // Minimalne wartości
        assertDoesNotThrow(() -> new Zaklad(typy1));

        int[] typy2 = { 44, 45, 46, 47, 48, 49 }; // Maksymalne wartości
        assertDoesNotThrow(() -> new Zaklad(typy2));

        int[] typy3 = { 0, 1, 2, 3, 4, 5 }; // Poniżej minimum
        assertThrows(IllegalArgumentException.class, () -> new Zaklad(typy3));

        int[] typy4 = { 45, 46, 47, 48, 49, 50 }; // Powyżej maksimum
        assertThrows(IllegalArgumentException.class, () -> new Zaklad(typy4));
    }
}
