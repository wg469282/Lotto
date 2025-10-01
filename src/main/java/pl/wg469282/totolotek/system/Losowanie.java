import java.util.*;

public class Losowanie {
    private int id;
    private long[] nagrody;
    private int[] wynik;
    private List<Kupon> zwyciezcy;
    private int ileZakladow;
    public Losowanie(int numerLosowania) {
        this.nagrody = new long[4];
        this.ileZakladow = 0;
        this.id = numerLosowania;
        this.zwyciezcy = new ArrayList<>();
        this.wynik = new int[6];
        Random random = new Random();
        Set<Integer> wylosowane = new HashSet<>();

        while (wylosowane.size() < 6) {
            int liczba = random.nextInt(49) + 1;
            wylosowane.add(liczba);
        }

        this.wynik = wylosowane.stream()
                .mapToInt(Integer::intValue)
                .sorted()
                .toArray();
    }

    public void dodajZwyciezce(Kupon kupon) {
        this.zwyciezcy.add(kupon);
    }

    public int[] getWyniki() {
        return this.wynik;
    }
    public int getNumer(){return this.id;}
    public void dodajZaklad(){
        this.ileZakladow++;
    }
    public int ileZakladow(){return this.ileZakladow;}
    public void setNagrody(long[] w) {
        this.nagrody = w;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Numer losowania
        sb.append("Losowanie nr ").append(this.id).append("\n");

        // Wyniki - liczby wyrównane do prawej z dodatkową spacją dla liczb
        // jednocyfrowych
        sb.append("Wyniki: ");
        for (int i = 0; i < this.wynik.length; i++) {
            if (this.wynik[i] < 10) {
                sb.append(" "); // Dodatkowa spacja dla liczb jednocyfrowych
            }
            sb.append(this.wynik[i]);
            if (i < this.wynik.length - 1) {
                sb.append(" ");
            }
        }
        sb.append(" "); // Końcowa spacja zgodnie ze specyfikacją

        return sb.toString();
    }

}
