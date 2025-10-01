import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class BudzetPanstwaTest {
    private BudzetPanstwa budzetPanstwa;

    @BeforeEach
    void setUp() {
        budzetPanstwa = new BudzetPanstwa();
    }

    @Test
    void testDodawaniePodatkow() {
        long początkowePodatki = budzetPanstwa.getPodatki();
        long początkowyBudzet = budzetPanstwa.getBudzet();

        budzetPanstwa.dodajPieniadze(1000L);

        assertEquals(początkowePodatki + 1000L, budzetPanstwa.getPodatki());
        assertEquals(początkowyBudzet + 1000L, budzetPanstwa.getBudzet());
    }

    @Test
    void testUdzielanieSubwencji() {
        long początkoweSubwencje = budzetPanstwa.getSubwencje();
        long początkowyBudzet = budzetPanstwa.getBudzet();

        budzetPanstwa.udzielSubwencji(5000000L);

        assertEquals(początkoweSubwencje + 5000000L, budzetPanstwa.getSubwencje());
        assertEquals(początkowyBudzet - 5000000L, budzetPanstwa.getBudzet());
    }

    @Test
    void testDodawanieUjemnychPodatkow() {
        assertThrows(IllegalArgumentException.class, () -> {
            budzetPanstwa.dodajPieniadze(-1000L);
        });
    }

    @Test
    void testPobieraniePodatku() {
        long początkowePodatki = budzetPanstwa.getPodatki();
        long początkowyBudzet = budzetPanstwa.getBudzet();

        budzetPanstwa.pobierzPodatek(2000L);

        assertEquals(początkowePodatki + 2000L, budzetPanstwa.getPodatki());
        assertEquals(początkowyBudzet + 2000L, budzetPanstwa.getBudzet());
    }
}
