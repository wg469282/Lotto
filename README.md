# 🎯 System Loterii "Totolotek" - Symulator w Javie

Jest to zaawansowany symulator popularnej gry liczbowej "Totolotek", zaimplementowany w języku Java z wykorzystaniem paradygmatu programowania obiektowego. Projekt symuluje pełen cykl życia loterii – od zakupu kuponów w kolekturach przez graczy, przez przeprowadzanie losowań, aż po obliczanie pul nagród i wypłatę wygranych.

## 🚀 Główne Funkcjonalności

- **Centrala Loterii**: Zarządza losowaniami, finansami, pulami nagród i siecią kolektur.
- **Kolektury**: Punkty sprzedaży kuponów, które pośredniczą w transakcjach między graczami a centralą.
- **Kupony**: Unikalne, identyfikowalne dokumenty uprawniające do udziału w losowaniach i odbioru nagród.
- **Gracze**: Cztery różne typy graczy z unikalnymi strategiami gry:
  - **Minimalista**: Gra ostrożnie, kupując jeden zakład na jedno losowanie.
  - **Losowy**: Dynamicznie kupuje losową liczbę kuponów z losową liczbą zakładów.
  - **Stałoliczbowy**: Zawsze gra tym samym, ulubionym zestawem 6 liczb.
  - **Stałoblankietowy**: Używa stałego, predefiniowanego blankietu do regularnych zakupów.
- **Losowania**: Cykliczne losowanie 6 z 49 liczb.
- **System Nagród**: Dynamiczne obliczanie pul nagród I, II, III i IV stopnia, z uwzględnieniem kumulacji.
- **Budżet Państwa**: Modeluje przepływy finansowe związane z podatkami od gier i subwencjami.

## 🛠️ Technologie

- **Język**: Java 11+
- **Paradygmat**: Programowanie Obiektowe (OOP)
- **Testowanie**: JUnit 5
- **Build & Dependencies**: Maven (zalecane)
- **Struktura**: Projekt podzielony na pakiety logiczne (`model`, `player`, `system`, etc.)

## ⚙️ Uruchomienie Projektu

### Wymagania
- JDK 11 lub nowszy
- Apache Maven (do zarządzania zależnościami i budowania projektu)

### Kroki
1.  **Sklonuj repozytorium:**
    ```
    git clone <URL-do-repozytorium>
    cd <nazwa-repozytorium>
    ```
2.  **Zbuduj projekt za pomocą Maven:**
    ```
    mvn clean install
    ```
3.  **Uruchom symulację:**
    Główna klasa `Main` uruchamia pełną demonstrację systemu. Można ją uruchomić bezpośrednio z IDE lub z linii komend:
    ```
    mvn exec:java -Dexec.mainClass="twoj.pakiet.Main"
    ```

## 🧪 Testy Jednostkowe

Projekt zawiera obszerny zestaw testów jednostkowych napisanych przy użyciu **JUnit 5**, które weryfikują poprawność działania kluczowych komponentów systemu.

### Zakres Testów
Testy pokrywają między innymi:
- **Logikę Biznesową Kuponów**: Poprawność generowania identyfikatorów, obliczania cen, weryfikacji losowań.
- **Obliczenia Finansowe**: Prawidłowość obliczania pul nagród, minimalnych gwarantowanych kwot i kumulacji.
- **Sprzedaż i Podatki**: Weryfikacja transakcji, pobierania podatków i aktualizacji budżetów.
- **Poprawność Danych**: Sprawdzanie walidacji danych wejściowych w klasach `Zaklad`, `Blankiet` i u graczy.
- **Wykrywanie Błędów**: Testy sprawdzające odporność systemu na niepoprawne dane (np. duplikaty liczb, wartości spoza zakresu).

### Uruchamianie Testów
Dołączony skrypt `test.sh` automatyzuje proces uruchamiania wszystkich testów jednostkowych za pomocą Maven.

Aby uruchomić testy, wykonaj w głównym katalogu projektu:
