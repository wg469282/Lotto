import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Main {
    // Stałe konfiguracyjne dla demonstracji
    private static final int LICZBA_KOLEKTUR = 10;
    private static final int GRACZE_KAZDEGO_TYPU = 20; // Zmniejszone dla demonstracji
    private static final int LICZBA_LOSOWAN = 5; // Zmniejszone dla demonstracji

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRACJA SYSTEMU TOTOLOTEK ===\n");

        // 1. Inicjalizacja systemu
        Centrala centrala = new Centrala(LICZBA_KOLEKTUR, 10_000_000L);
        List<Gracz> gracze = createAllPlayers(centrala);

        printSystemStatus("STAN POCZĄTKOWY", centrala, gracze.size());

        // 2. Symulacja losowań
        for (int losowanie = 1; losowanie <= LICZBA_LOSOWAN; losowanie++) {
            System.out.println("\n--- LOSOWANIE " + losowanie + " ---");

            // Kupowanie kuponów przez graczy
            int kupioneKupony = buyTickets(gracze);
            System.out.println("Kupiono kuponów: " + kupioneKupony);

            // Przeprowadzenie losowania
            centrala.przeprowadzNLosowania(1);
            centrala.pokazLosowanie(losowanie - 1);

            // Wypłata wygranych
            int wyplaconeWygrane = processWinnings(gracze);
            System.out.println("Wypłacono wygranych: " + wyplaconeWygrane);

            // Status po losowaniu
            long pula = centrala.getPule().get(losowanie - 1);
            System.out.println("Pula tego losowania: " + formatMoney(pula));
        }

        // 3. Podsumowanie końcowe
        printFinalSummary(centrala, gracze);
    }

    private static List<Gracz> createAllPlayers(Centrala centrala) {
        List<Gracz> gracze = new ArrayList<>();
        List<Kolektura> kolektury = centrala.getKolektury();

        // Tworzenie graczy Minimalistów
        for (int i = 0; i < GRACZE_KAZDEGO_TYPU; i++) {
            Kolektura ulubiona = kolektury.get(i % LICZBA_KOLEKTUR);
            gracze.add(new Minimalista("Min" + i, "Nazwisko" + i, 10000000 + i,
                    ulubiona, kolektury));
        }

        // Tworzenie graczy Losowych
        for (int i = 0; i < GRACZE_KAZDEGO_TYPU; i++) {
            gracze.add(new Losowy("Los" + i, "Nazwisko" + i, 20000000 + i,
                    kolektury, kolektury));
        }

        // Tworzenie graczy Stałoliczbowych
        for (int i = 0; i < GRACZE_KAZDEGO_TYPU; i++) {
            int[] ulubioneLiczby = generateFavoriteNumbers(i);
            Kolektura[] ulubione = { kolektury.get(i % LICZBA_KOLEKTUR),
                    kolektury.get((i + 1) % LICZBA_KOLEKTUR) };
            gracze.add(new Staloliczbowy(50_000_000L, "Stal" + i, "Nazwisko" + i,
                    30000000 + i, ulubioneLiczby, kolektury, ulubione));
        }

        // Tworzenie graczy Stałoblankietowych
        for (int i = 0; i < GRACZE_KAZDEGO_TYPU; i++) {
            List<Set<Integer>> typy = generateBlankietTypes(i);
            Kolektura[] ulubione = { kolektury.get(i % LICZBA_KOLEKTUR),
                    kolektury.get((i + 1) % LICZBA_KOLEKTUR),
                    kolektury.get((i + 2) % LICZBA_KOLEKTUR) };

            Blankiet blankiet = new Blankiet(kolektury.get(i % LICZBA_KOLEKTUR), 3, typy);
            gracze.add(new StaloBlankietowy(100_000_000L, "Blank" + i, "Nazwisko" + i,
                    40000000 + i, kolektury, blankiet, 2, ulubione));
        }

        System.out.println("Utworzono " + gracze.size() + " graczy:");
        System.out.println("- Minimaliści: " + GRACZE_KAZDEGO_TYPU);
        System.out.println("- Losowi: " + GRACZE_KAZDEGO_TYPU);
        System.out.println("- Stałoliczbowi: " + GRACZE_KAZDEGO_TYPU);
        System.out.println("- Stałoblankietowi: " + GRACZE_KAZDEGO_TYPU);

        return gracze;
    }

    private static int[] generateFavoriteNumbers(int seed) {
        Random rand = new Random(seed);
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < 6) {
            numbers.add(rand.nextInt(49) + 1);
        }
        return numbers.stream().mapToInt(Integer::intValue).sorted().toArray();
    }

    private static List<Set<Integer>> generateBlankietTypes(int seed) {
        List<Set<Integer>> types = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            Set<Integer> type = new HashSet<>();
            Random rand = new Random(seed * 10 + j);
            while (type.size() < 6) {
                type.add(rand.nextInt(49) + 1);
            }
            types.add(type);
        }
        return types;
    }

    private static int buyTickets(List<Gracz> gracze) {
        int kupione = 0;
        for (Gracz gracz : gracze) {
            int kuponowPrzed = gracz.getKupony().size();
            try {
                gracz.kupKupon();
                int kuponowPo = gracz.getKupony().size();
                if (kuponowPo > kuponowPrzed) {
                    kupione++;
                }
            } catch (Exception e) {
                // Gracz nie mógł kupić kuponu (brak środków itp.)
            }
        }
        return kupione;
    }

    private static int processWinnings(List<Gracz> gracze) {
        int wyplacone = 0;
        for (Gracz gracz : gracze) {
            List<Kupon> doWyplaty = new ArrayList<>();

            for (Kupon kupon : gracz.getKupony()) {
                if (kupon.koniecLos() && kupon.getWygrana() > 0) {
                    doWyplaty.add(kupon);
                }
            }

            for (Kupon kupon : doWyplaty) {
                try {
                    gracz.wyplacKupon(kupon);
                    wyplacone++;
                } catch (Exception e) {
                    // Błąd wypłaty
                }
            }
        }
        return wyplacone;
    }

    private static void printSystemStatus(String etap, Centrala centrala, int liczbaGraczy) {
        System.out.println("\n=== " + etap + " ===");
        System.out.println("Budżet centrali: " + formatMoney(centrala.getBudzet()));
        System.out.println("Liczba graczy: " + liczbaGraczy);
        System.out.println("Liczba kolektur: " + centrala.getKolektury().size());
        System.out.println("Kupony w systemie: " + centrala.ileKuponow());
    }

    private static void printFinalSummary(Centrala centrala, List<Gracz> gracze) {
        System.out.println("\n=== PODSUMOWANIE KOŃCOWE ===");
        System.out.println("Przeprowadzono losowań: " + centrala.getLosowania().size());
        System.out.println("Wystawiono kuponów: " + centrala.ileKuponow());
        System.out.println("Stan końcowy centrali: " + formatMoney(centrala.getBudzet()));
        System.out.println("Wpływy do budżetu państwa: " + centrala.getPanstwo().toString());

        // Statystyki graczy
        long sumaGraczy = gracze.stream().mapToLong(g -> g.ilePieniedzy()).sum();
        int aktywnychKuponow = gracze.stream().mapToInt(g -> g.getKupony().size()).sum();

        System.out.println("\nSTATYSTYKI GRACZY:");
        System.out.println("Łączne środki graczy: " + formatMoney(sumaGraczy));
        System.out.println("Aktywnych kuponów: " + aktywnychKuponow);

        System.out.println("\n=== KONIEC DEMONSTRACJI ===");
    }

    private static String formatMoney(long grosze) {
        long zlote = grosze / 100;
        long reszta = grosze % 100;
        return zlote + " zł " + String.format("%02d", reszta) + " gr";
    }
}
