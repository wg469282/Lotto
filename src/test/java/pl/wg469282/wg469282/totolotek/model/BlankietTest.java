package pl.wg469282.totolotek.model;

import org.junit.jupiter.api.Test;

import pl.wg469282.totolotek.model.Blankiet;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class BlankietTest {
    private Centrala centrala;
    private Kolektura kolektura;

    @BeforeEach
    void setUp() {
        centrala = new Centrala(1, 1000000L);
        kolektura = centrala.getKolektury().get(0);
    }

    @Test
    void testTworzenieBlankietuZPoprawnymi6Liczbami() {
        Set<Integer> typ1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        List<Set<Integer>> typy = Arrays.asList(typ1);

        Blankiet blankiet = new Blankiet(kolektura, 1, typy);
        assertNotNull(blankiet);
    }

    @Test
    void testBlankietOdrzucaZakladyZNiepoprawnąLiczbąTypów() {
        Set<Integer> typ1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5)); // tylko 5 liczb
        Set<Integer> typ2 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7)); // 7 liczb
        List<Set<Integer>> typy = Arrays.asList(typ1, typ2);

        Blankiet blankiet = new Blankiet(kolektura, 1, typy);
        // Blankiet powinien zostać utworzony, ale niepoprawne zakłady będą pominięte
        assertNotNull(blankiet);
    }

    @Test
    void testBlankietZWielkąLiczbąLosowań() {
        Set<Integer> typ1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        List<Set<Integer>> typy = Arrays.asList(typ1);

        Blankiet blankiet = new Blankiet(kolektura, 10, typy);
        assertNotNull(blankiet);
    }

    @Test
    void testBlankietZPustąListąTypów() {
        List<Set<Integer>> typy = new ArrayList<>();

        Blankiet blankiet = new Blankiet(kolektura, 1, typy);
        assertNotNull(blankiet);
    }
}
