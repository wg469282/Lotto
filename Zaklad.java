
import java.util.Arrays;

public class Zaklad {
    private int[] typy;
    boolean anuluj;

    public Zaklad(int[] t, boolean a) {
        this.typy = new int[6];
        if (t.length != 6) {
            anuluj = true;
            throw new IllegalArgumentException("Kazdy Zaklad obejmuje wytypowanie 6 liczb od 1 do 49");
        }
        for (int i = 0; i < 6; i++) {
            if (0 < t[i] && t[i] < 50) {
                this.typy[i] = t[i];
            } else
                throw new IllegalArgumentException("Kazdy Zaklad obejmuje wytypowanie 6 liczb od 1 do 49");
        }
        Arrays.sort(this.typy);
        this.anuluj = a;
    }

    public Zaklad(int[] t) {
        this(t, false);
    }

    public int[] getTypy() {
        return this.typy;
    }


    @Override
    public String toString() {
        String s = "";
        for (int p = 0; p < 10; p++) {
            for (int q = 0; q < 5; q++) {
                int l = p * 10 + q;
                int i = 0;
                s += "[ ";
                if (l < 10) {
                    s += " ";
                }
                if (l != this.typy[i]) {
                    s += l + " ";
                } else {
                    s += "-- ";
                    i++;
                }
                s += " ]  ";
            }
            s += '\n';

        }
        if (this.anuluj == false)
            s += "\n[    ]  anuluj\n";
        else
            s += "\n[ -- ] anuluj\n";
        return s;
    }
}
