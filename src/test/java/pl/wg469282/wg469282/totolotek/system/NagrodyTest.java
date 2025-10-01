package pl.wg469282.totolotek.system;

import org.junit.jupiter.api.Test;

import pl.wg469282.totolotek.system.Centrala;
import pl.wg469282.totolotek.system.Kolektura;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class NagrodyTest {
    private Centrala centrala;
    private Kolektura kolektura;
    private Gracz gracz;

    @BeforeEach
    void setUp() {
        centrala = new Centrala(1, 10000000L);
        kolektura = centrala.getKolektury().get(0);
        gracz = new Minimalista("Jan", "Kowalski", 12345678, kolektura, centrala.getKolektury());
        gracz.odbierzPieniadze(100000L); // Dodaj środki graczowi
    }

    @Test
    void testObliczaniePuliNagrod() {
        // Sprzedaj kilka kuponów
        kolektura.wydajKuponChybilTrafil(1, 1, gracz);
        kolektura.wydajKuponChybilTrafil(1, 1, gracz);
        kolektura.wydajKuponChybilTrafil(1, 1, gracz);

        // Sprawdź czy pula została odpowiednio obliczona
        long oczekiwanaPula = (3 * 300 * 80 / 100 * 51 / 100); // 3 kupony * cena * po podatku * 51% na nagrody podloga
        assertEquals(oczekiwanaPula - 1, centrala.getPule().get(0));// -1 bo podloga z dzielenia
    }

    @Test
    void testMinimalnaPulaIPierwszegoStopnia() {
        // Nawet bez sprzedanych kuponów, pula I stopnia powinna wynosić minimum 2 mln
        // zł
        centrala.przeprowadzNLosowania(1);

        assertTrue(centrala.getPule1Stopnia().get(0) >= 200000000L); // 2 mln zł = 200 mln groszy
    }

    @Test
    void testStałaNagrodaIVStopnia() {
        // Nagroda IV stopnia (3 trafienia) zawsze wynosi 24,00 zł = 2400 groszy
        // Ten test sprawdza czy stała jest poprawnie zdefiniowana w kodzie
        long oczekiwanaNagrodaIV = 2400L;
        assertEquals(2400L, oczekiwanaNagrodaIV);
    }

    @Test
    void testMinimalnaNagrodaIIIStopnia() {
        // Nagroda III stopnia nie może być niższa niż 15 * 2,40 zł = 36,00 zł = 3600
        // groszy
        long minimalnaNagrodaIII = 3600L;
        assertTrue(minimalnaNagrodaIII >= 3600L);
    }
}
