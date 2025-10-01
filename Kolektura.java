import java.util.*;

public class Kolektura {
    private List<Kupon> kupony;
    private Centrala centrala;
    private int numer;

    public Kolektura(Centrala c, int i) {
        this.centrala = c;
        this.numer = i;
        this.kupony = new ArrayList<>();
    }

    public void wyplacWygrana(Gracz g, Kupon k) {
        if(!this.kupony.contains(k)) {
            throw new IllegalArgumentException("Kupon mozna wyplacic jedynie w kolekturze w ktorej zostal zakupiony");
        }
        long wygrana = opodatkujWygrane(k.getWygrana());

        this.centrala.wyplacWygrana(wygrana);
    }

    private long opodatkujWygrane(long wygrana){

            long opodatkowane = ((wygrana)*90)/100;
            this.centrala.getPanstwo().dodajPieniadze(wygrana- opodatkowane);
            return opodatkowane;


    }
    public Centrala getCentrala() {
        return this.centrala;
    }

    public int getNumer() {
        return this.numer;
    }

    public void wydajKupon(List<Zaklad> zaklady, int ileLosowan, Gracz g) {
        long cena = zaklady.size() * ileLosowan * 300L; // 3 zł za zakład w groszach
        if (cena > g.ilePieniedzy()) {
            throw new IllegalArgumentException("Niedostateczne fundusze");
        }
        Kupon kupon = new Kupon(zaklady, ileLosowan, this);

        // To sa informacje dotyczace tego ile pieniedzy jest w puli na kazde z losowan
        // rzeczywiste pieniadze znajduja sie w polach budzet i pieniadze
        g.zaplac(cena);
        long opodatkowane = odprowadzPodatek(cena);
        long budzet = (opodatkowane *51)/100;
        long kwotaNaLosowanie = budzet/ileLosowan;
        this.centrala.dodajZysk(opodatkowane -budzet);
        this.centrala.dodajBudzet(budzet);
        for (int i = 0; i < kupon.getLosowania().length; i++) {
            centrala.dodajDoPuli( kupon.getLosowania()[i] -1, kwotaNaLosowanie);
        }
        g.dodajKupon(kupon);
        this.kupony.add(kupon);
        this.centrala.dodajKupon(kupon);

    }

    public void usunKupon(Kupon kpn) {
        this.kupony.remove(kpn);
    }

    public void wydajKuponChybilTrafil(int ileZakladow, int ileLosowan, Gracz gracz) {

        // Wygeneruj losowe zakłady
        List<Zaklad> zaklady = new ArrayList<>();
        for (int i = 0; i < ileZakladow; i++) {
            int[] losoweLiczby = generujLosoweLiczby();
            zaklady.add(new Zaklad(losoweLiczby));
        }

        // Wydaj kupon
        wydajKupon(zaklady, ileLosowan, gracz);
    }

    /**
     * Generuje losowy zestaw 6 liczb z zakresu 1-49
     * 
     * @return posortowana tablica 6 unikalnych liczb
     */
    private int[] generujLosoweLiczby() {
        Random random = new Random();
        Set<Integer> wylosowane = new HashSet<>();

        while (wylosowane.size() < 6) {
            int liczba = random.nextInt(49) + 1; // 1-49
            wylosowane.add(liczba);
        }

        return wylosowane.stream()
                .mapToInt(Integer::intValue)
                .sorted()
                .toArray();
    }
    private long odprowadzPodatek(long cena ){
        long podatek = (cena*20)/100;
        this.centrala.getPanstwo().dodajPieniadze(podatek);
        return cena-podatek;
    }
}
