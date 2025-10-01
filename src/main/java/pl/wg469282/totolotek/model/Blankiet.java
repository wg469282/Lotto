
import java.util.*;

public class Blankiet {
    private List<Zaklad> zaklady;
    private List<Set<Integer>> strzaly;
    private int ileLosowan;
    private Kolektura k;

    public Blankiet(Kolektura kl, int ileL, List<Set<Integer>> strz) {

        this.k = kl;
        this.zaklady = new ArrayList<>();
        this.strzaly = strz;
        this.ileLosowan = ileL;
        for (Set<Integer> zbior : strz) {
            int[] typy = zbior.stream().mapToInt(Integer::intValue).toArray();
            if (typy.length != 6) {
                continue;
            }
            Arrays.sort(typy);
            zaklady.add(new Zaklad(typy));
        }
    }

    public void wydajBlankiet(Gracz g) {
        if (g.ilePieniedzy() >= this.ileLosowan * 300 * this.strzaly.size())
            this.k.wydajKupon(this.zaklady, this.ileLosowan, g);

    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 8; i++) {
            s += zaklady.get(i).toString();
        }
        s += "Liczba losowań:";
        for (int j = 1; j <= 10; j++) {
            s += "  [ ";
            if (j != this.zaklady.size() - 1)
                s += j + "";
            else
                s += "--";
            s += " ]";
        }
        return s;
    }
}
