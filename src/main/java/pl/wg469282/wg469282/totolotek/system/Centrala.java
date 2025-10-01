import java.util.*;

/**
 * Klasa reprezentująca centralę Totolotka
 * Odpowiedzialna za przeprowadzanie losowań, zarządzanie pulami nagród,
 * obsługę kolektur i kuponów oraz współpracę z budżetem państwa
 */
public class Centrala {
    // Lista przechowująca całkowite pule środków dla każdego losowania
    private List<Long> pule;
    // Lista przechowująca pule nagród I stopnia (6 trafień) dla każdego losowania
    private List<Long> pule1Stopnia;

    private List<Losowanie> losowania;
    // Lista wszystkich kolektur w systemie
    private List<Kolektura> kolektury;
    // Budżet centrali w groszach
    private long budzet;
    // Referencja do budżetu państwa dla podatków i subwencji
    private BudzetPanstwa bp;
    // Numer następnego losowania do przeprowadzenia
    private int nastLosowanie;
    // Licznik wszystkich wystawionych kuponów
    private List<Kupon> kupony;
    // Łączny zysk centrali w groszach
    private long zysk;
    private int ileKuponow;
    private int poprzedniWygrani1;

    /**
     * Konstruktor centrali Totolotka
     * 
     * @param ileKolektur liczba kolektur do utworzenia
     * @param budzet      początkowy budżet centrali w groszach
     */
    public Centrala( int ileKolektur , long budzet) {
        // Inicjalizacja wszystkich list
        this.pule = new ArrayList<>();
        this.pule1Stopnia = new ArrayList<>();
        this.pule1Stopnia.add(0L);
        this.kupony = new ArrayList<>();
        this.losowania = new ArrayList<>();
        this.kolektury = new ArrayList<>();
        this.budzet = budzet;
        this.zysk = 0;
        this.ileKuponow = 0;
        // Pierwsze losowanie ma numer 1
        this.nastLosowanie = 1;
        // Początkowo brak kuponów

        // Utworzenie określonej liczby kolektur
        for (int j = 0; j < ileKolektur; j++) {
            this.kolektury.add(new Kolektura(this, j));
        }
        for(int i  = 0 ; i < 1000 ; i ++ ){
            this.pule.add(0L);
        }
        // Utworzenie instancji budżetu państwa
        this.bp = new BudzetPanstwa();
    }

    /**
     * Dodaje środki do budżetu centrali
     * 
     * @param p kwota do dodania w groszach (musi być nieujemna)
     * @throws IllegalArgumentException gdy próbuje się odjąć środki
     */
    public void dodajBudzet(long p) {
        if (p < 0) {
            throw new IllegalArgumentException("!!!Proba zabronionego wyciagniecia pieniedzy !!!");
        }
        this.budzet += p;
    }

    /**
     * @return numer następnego losowania
     */
    public int getLosowanie() {
        return this.nastLosowanie;
    }


    /**
     * @return łączną liczbę wystawionych kuponów
     */


    /**
     * @return numer następnego losowania (alias dla getLosowanie)
     */
    public int nastLosowanie() {
        return this.nastLosowanie;
    }

    /**
     * Sprawdza czy dana wartość występuje w tablicy
     * 
     * @param array   tablica do przeszukania
     * @param szukana wartość do znalezienia
     * @return true jeśli wartość została znaleziona
     */
    private Boolean wTablicy(int[] array, int szukana) {

        for (int i = 0; i < array.length; i++) {
            if (array[i] == szukana) {
                return true;
            }
        }
        return false;
    }

    /**
     * Przeprowadza pojedyncze losowanie
     * Generuje wyniki, sprawdza zwycięzców, oblicza pule nagród,
     * stosuje kumulację i przypisuje wygrane do kuponów
     */
    private void przeprowadzLosowanie() {
        // Listy przechowujące zwycięzców każdego stopnia
        List<Kupon> wygrani1 = new ArrayList<>(); // 6 trafień
        List<Kupon> wygrani2 = new ArrayList<>(); // 5 trafień
        List<Kupon> wygrani3 = new ArrayList<>(); // 4 trafienia
        List<Kupon> wygrani4 = new ArrayList<>(); // 3 trafienia

        // Utworzenie nowego losowania z wylosowanymi liczbami
        Losowanie losowanie = new Losowanie(this.nastLosowanie);
        long pula = this.pule.get(this.nastLosowanie); // Początkowa pula (będzie obliczona później
        this.losowania.add(losowanie);
        this.pule1Stopnia.add(0L);

        // Sprawdzenie wszystkich kuponów pod kątem wygranych
        for (int i = 0; i < this.kupony.size(); i++) {
            Kupon kupon = this.kupony.get(i);
            // Sprawdź czy kupon uczestniczy w tym losowaniu
            if (wTablicy(kupon.getLosowania(), this.nastLosowanie )) {
                // Sprawdź wszystkie zakłady w kuponie
                for (int j = 0; j < kupon.getZaklady().size(); j++) {
                    Zaklad zaklad = kupon.getZaklady().get(j);
                    int poprawne = porownajWyniki(losowanie.getWyniki(), zaklad.getTypy());
                    // Przypisz kupon do odpowiedniej kategorii zwycięzców
                    if (poprawne == 3) {
                        wygrani4.add(kupon);
                    } else if (poprawne == 4) {
                        wygrani3.add(kupon);
                    } else if (poprawne == 5) {
                        wygrani2.add(kupon);
                    } else if (poprawne == 6) {
                        wygrani1.add(kupon);
                    }
                    losowanie.dodajZaklad();
                }
            }
        }

        // Oblicz pule nagród
        this.pule1Stopnia.set(this.nastLosowanie , (this.pule.get(this.nastLosowanie ) * 44) / 100);
        long pule2Stopnia = (this.pule.get(this.nastLosowanie )* 8) / 100;


        // Oblicz pulę IV stopnia (24,00 zł za każdą nagrodę)
        long pula4 = wygrani4.size() * 2400;

        // Oblicz pulę III stopnia jako resztę
        long pule3Stopnia = (this.pule.get(this.nastLosowanie) * (100 - 44 - 8)) / 100 - pula4;


        // Sprawdź minimalną pulę I stopnia (2 mln zł = 200_000_000 groszy)
        if (this.pule1Stopnia.get(nastLosowanie -1  ) < 200_000_000L ) {
            this.pule1Stopnia.set(nastLosowanie-1, 200_000_000L);
        }
        if(poprzedniWygrani1 == 0) {

            {
                this.pule1Stopnia.set(nastLosowanie, this.pule1Stopnia.get(nastLosowanie) + this.pule1Stopnia.get(nastLosowanie - 1));
                this.pule.set(nastLosowanie, this.pule.get(nastLosowanie) + this.pule.get(nastLosowanie - 1));
            }
        }



        // Sprawdź czy budżet jest ujemny i pobierz subwencję
        if (this.budzet < 0) {
            this.wystapOSubwencje();
            this.budzet = 0; // Wyzeruj budżet po subwencji
        }
        // Sprawdz kumulacje

        // Oblicz indywidualne nagrody dla zwycięzców
        long nagroda1 = 0;
        long nagroda2 = 0;
        long nagroda3 = 0;
        long nagroda4 = 2400; // Stała nagroda IV stopnia (24,00 zł)

        // Podziel pule między zwycięzców (z zabezpieczeniem przed dzieleniem przez
        // zero)
        if (!wygrani1.isEmpty()) {
            nagroda1 = this.pule1Stopnia.get(this.nastLosowanie - 1) / wygrani1.size();
        }
        if (!wygrani2.isEmpty()) {
            nagroda2 = pule2Stopnia / wygrani2.size();
        }
        if (!wygrani3.isEmpty()) {
            nagroda3 = pule3Stopnia / wygrani3.size();
            // Sprawdź minimalną nagrodę III stopnia (36,00 zł = 3600 groszy)
            if (nagroda3 < 3600)
                nagroda3 = 3600;
        }

        // Zapisz nagrody w obiekcie losowania
        long[] nagrody = { nagroda1, nagroda2, nagroda3, nagroda4 };
        this.losowania.get(this.nastLosowanie - 1).setNagrody(nagrody);

        // Utwórz listę par (zwycięzcy, nagroda) dla łatwiejszego przetwarzania
        List<Map.Entry<List<Kupon>, Long>> wygrane = Arrays.asList(
                Map.entry(wygrani1, nagroda1),
                Map.entry(wygrani2, nagroda2),
                Map.entry(wygrani3, nagroda3),
                Map.entry(wygrani4, nagroda4));

        // Przypisz wygrane do odpowiednich kuponów
        for (var para : wygrane) {
            for (Kupon kupon : para.getKey()) {
                kupon.dodajWygrana(para.getValue());
            }
        }
        poprzedniWygrani1 = wygrani1.size();
        this.nastLosowanie++;

    }

    /**
     * Porównuje wyniki losowania z typowaniem gracza
     * 
     * @param wyniki wylosowane liczby (posortowane)
     * @param typy   liczby wytypowane przez gracza
     * @return liczba trafień (BŁĘDNIE OBLICZONA)
     */
    private int porownajWyniki(int[] wyniki, int[] typy) {
        int ile = 0;
        if (wyniki.length == typy.length && typy.length == 6) {
            for (int i = 0; i < 6; i++) {
                if (wyniki[i] == typy[i])
                    ile++;

            }
            return ile;
        } else {
            throw new IllegalArgumentException("Porownywane typowania  musza miec dlugosc 6");
        }
    }

    /**
     * Przeprowadza n kolejnych losowań
     * 
     * @param n liczba losowań do przeprowadzenia
     */
    public void przeprowadzNLosowania(int n) {
        for (int i = 0; i < n; i++) {
            przeprowadzLosowanie();
        }
    }

    public int ileKuponow(){return this.ileKuponow;}
    /**
     * Dodaje środki do puli określonego losowania
     * 
     * @param ktorej indeks losowania
     * @param ile    kwota do dodania w groszach
     */
    public void dodajDoPuli(int ktorej, long ile) {
        this.pule.set(ktorej, this.pule.get(ktorej) + ile);
    }

    /**
     * Usuwa kupon z systemu (np. po realizacji wygranej)
     * 
     * @param kpn kupon do usunięcia
     */
    public void usunKupon(Kupon kpn) {
        this.kupony.remove(kpn);
    }

    /**
     * Dodaje kupon do systemu
     * 
     * @param k kupon do dodania
     */
    public void dodajKupon(Kupon k) {
        this.ileKuponow++;
        this.kupony.add(k);
    }

    /**
     * Dodaje kwotę do zysku centrali
     * 
     * @param ile kwota zysku w groszach
     */
    public void dodajZysk(long ile) {
        this.zysk += ile;
    }

    /**
     * @return referencję do budżetu państwa
     */
    public BudzetPanstwa getPanstwo() {
        return this.bp;
    }
    public List<Kupon> getKupony() {return this.kupony;}
    /**
     * Występuje o subwencję z budżetu państwa
     * Pobiera kwotę równą ujemnemu budżetowi centrali
     */
    public void wystapOSubwencje() {
        this.bp.udzielSubwencji(-this.budzet);
    }
    public List<Long> getPule(){return this.pule;}
    public List<Long> getPule1Stopnia(){return this.pule1Stopnia;}
    /**
     * Wyświetla informacje o określonym losowaniu
     * 
     * @param i indeks losowania do wyświetlenia
     */
    public void pokazLosowanie(int i) {
        System.out.print(this.losowania.get(i).toString());
    }
    public long getBudzet(){return this.budzet;}
    public List<Kolektura> getKolektury() {return this.kolektury;}
    public List<Losowanie> getLosowania(){return this.losowania;}
    public void wyplacWygrana(long kwota){
        this.budzet -= kwota;
    }

}
