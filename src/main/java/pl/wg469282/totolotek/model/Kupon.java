import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Kupon {
    private String id;
    private List<Zaklad> zaklady;
    private int ileLosowan;
    private int[] numLosowan;
    private long cena;
    private Kolektura kolektura;
    private long wygrana;

    public Kupon(List<Zaklad> z, int ileL, Kolektura k) {
        this.wygrana = 0;
        cena = 300L * z.size() * ileL;
        int suma = 0;
        this.ileLosowan = ileL;
        this.numLosowan = new int[ileL];
        this.kolektura = k;
        this.zaklady =z;
        String s = "";
        int numerKuponu = this.kolektura.getCentrala().ileKuponow();
        int numerKolektury = this.kolektura.getNumer();
        s += numerKuponu + "-";
        s += numerKolektury + "-";
        while (numerKuponu > 0) {
            suma += numerKuponu % 10;
            numerKuponu /= 10;
        }
        while (numerKolektury > 0) {
            suma += numerKolektury % 10;
            numerKolektury /= 10;
        }
        for (int i = 0; i < 9; i++) {
            int rand = ThreadLocalRandom.current().nextInt(0, 10);
            suma += rand;
            s += rand;
        }
        s += suma%100;
        this.id = s;
        for (int j = 0; j < ileL; j++) {
            this.numLosowan[j] = j   + k.getCentrala().nastLosowanie()   ;
        }
    }

    public long getCena() {
        return this.cena;
    }

    public long getWygrana() {
        return this.wygrana;
    }

    public void dodajWygrana(long w) {
        this.wygrana += w;
    }

    public Kolektura getKolektura() {
        return this.kolektura;
    }

    public List<Zaklad> getZaklady() {
        return this.zaklady;
    }

    public int[] getLosowania() {
        return this.numLosowan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // 1. Identyfikator kuponu
        sb.append("KUPON NR ").append(this.id).append("\n");

        // 2. Ponumerowana lista kolejnych zakładów (w osobnych wierszach, wylosowane
        // liczby wyrównane do prawej)
        for (int i = 0; i < this.zaklady.size(); i++) {
            sb.append(i + 1).append(": ");
            int[] typy = this.zaklady.get(i).getTypy();
            for (int j = 0; j < typy.length; j++) {
                if (typy[j] < 10) {
                    sb.append(" "); // Dodatkowa spacja dla liczb jednocyfrowych (wyrównanie do prawej)
                }
                sb.append(typy[j]);
                if (j < typy.length - 1) {
                    sb.append(" ");
                }
            }
            sb.append(" \n"); // Końcowa spacja i nowa linia
        }

        // 3. Liczba losowań
        sb.append("LICZBA LOSOWAŃ: ").append(this.ileLosowan).append("\n");

        // 4. Lista numerów losowań (w jednym wierszu)
        sb.append("NUMERY LOSOWAŃ:\n");
        for (int i = 0; i < this.numLosowan.length; i++) {
            if (i == 0) {
                sb.append(" "); // Początkowa spacja
            }
            sb.append(this.numLosowan[i]);
            if (i < this.numLosowan.length - 1) {
                sb.append(" ");
            }
        }
        sb.append("\n");

        // 5. Cena brutto kuponu
        long zlote = this.cena / 100;
        long grosze = this.cena % 100;
        sb.append("CENA: ").append(zlote).append(" zł ");
        if (grosze < 10) {
            sb.append("0"); // Zero wiodące dla groszy jednocyfrowych
        }
        sb.append(grosze).append(" gr");

        return sb.toString();
    }

    public Boolean koniecLos() {
        int ostatnieLosowanieKuponu = this.numLosowan[ileLosowan - 1];
        int aktualneNastepneLosowanie = this.kolektura.getCentrala().nastLosowanie();

        return (this.numLosowan[ileLosowan - 1] < this.kolektura.getCentrala().nastLosowanie());   }

}
