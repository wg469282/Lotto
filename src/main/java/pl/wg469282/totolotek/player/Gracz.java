import java.util.ArrayList;
import java.util.List;

public abstract class Gracz {
    protected long pieniadze;
    protected List<Kupon> kupony;
    protected String imie;
    protected String nazwisko;
    protected int PESEL;
    protected List<Kolektura> kolektury;

    abstract void kupKupon();

    public Gracz(long p, String i, String n, int ps, List<Kolektura> k) {
        this.kolektury = k;
        this.nazwisko = n;
        this.kupony = new ArrayList<>();
        this.pieniadze = p;
        this.imie = i;
        this.PESEL = ps;
    }

    public void dodajKupon(Kupon k) {
        kupony.add(k);
    }

    public long ilePieniedzy() {
        return this.pieniadze;
    }

    public long zaplac(long ile) {
        this.pieniadze -= ile;
        return ile;
    }

    public void odbierzPieniadze(long ile) {
        if (ile < 0) {
            throw new IllegalArgumentException("Zaszla pomylka przy wyplacaniu wygranej ");
        }
        this.pieniadze += ile;

    }

    protected void wyplacKupon(Kupon k) {
        if (this.kupony.contains(k) == false) {
            throw new IllegalArgumentException("Musisz mieć kupon aby go wypłacić");
        }
        k.getKolektura().wyplacWygrana(this, k);
        k.getKolektura().getCentrala().usunKupon(k);
        k.getKolektura().usunKupon(k);
        this.kupony.remove(k);

    }

    /**
     * Sprawdza wszystkie kupony i realizuje te, które mają zakończone losowania
     * oraz przyniosły jakąś wygrą
     */
    protected void sprawdzKupony() {
        List<Kupon> kuponydoUsuniecia = new ArrayList<>();

        for (Kupon kupon : this.kupony) {
            // Sprawdź czy wszystkie losowania dla tego kuponu zostały przeprowadzone
            if (kupon.koniecLos() == true) {
                // Sprawdź czy kupon ma jakąś wygraną
                if (kupon.getWygrana() > 0) {
                    // Realizuj kupon (wypłać wygraną)
                    wyplacKupon(kupon);
                }
                kuponydoUsuniecia.add(kupon);
            }
        }

        // Usuń zrealizowane kupony
        for (Kupon kupon : kuponydoUsuniecia) {
            this.wyplacKupon(kupon);
        }
    }

    @Override
    public String toString() { // DODANE
        StringBuilder sb = new StringBuilder();
        sb.append("Gracz: ").append(this.nazwisko).append(" ").append(this.imie)
                .append(", PESEL: ").append(this.PESEL)
                .append(", Środki: ").append(this.pieniadze).append(" groszy");

        if (this.kupony.isEmpty()) {
            sb.append(", Brak kuponów");
        } else {
            sb.append(", Kupony: ");
            for (int i = 0; i < this.kupony.size(); i++) {
                sb.append(this.kupony.get(i).toString());
                if (i < this.kupony.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        return sb.toString();
    }
    public List<Kupon> getKupony() {return this.kupony;}

}