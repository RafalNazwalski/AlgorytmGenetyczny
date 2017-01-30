import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static int liczbaRodzicow;

    public static void main(String [] args) {

        Scanner odczyt = new Scanner(System.in);
        Random rand = new Random();
        DecimalFormat df = new DecimalFormat("#.0");

        //deklaracja zmiennych wprowadzanych przez usera
        int liczebnosc_rodzicow, ilosc_pokolen, wspolczynnik_a, wspolczynnik_b, wspolczynnik_c, wspolczynnik_d;
        int najlepszyX = 0;

        float Pk, Pm;
        float najlepszyZawodnik = 0;

        //liczniki
        int i, j, k, m;

        //
        float number_tmp_2 = 0;

        int suma = 0, point = 0, number_tmp = 0, number_tmp_3 = 0, najmocniejszy_osobnik = 0;

        //wprowadzanie parametrow przez uzytkownika

        System.out.println("Sztuczna inteligencja");
        System.out.println("Projekt wykonal: Rafal Nazwalski\n");


        System.out.println("Wprowadz liczebnosc grupy rodzicielskiej: ");
        liczebnosc_rodzicow = odczyt.nextInt();
        System.out.println("Wprowadz ilosc pokolen: ");
        ilosc_pokolen = odczyt.nextInt();

        //deklaracje tablic do operacji binarnych
        int tablicaBinarna1[] = new int[liczebnosc_rodzicow];
        int tablicaBinarna2[] = new int[liczebnosc_rodzicow];
        liczbaRodzicow = liczebnosc_rodzicow;

        int GRUPA_RODZICIELSKA[] = new int[liczebnosc_rodzicow];
        int WYBRANI[] = new int[liczebnosc_rodzicow];
        int POTOMKOWIE[] = new int[liczebnosc_rodzicow];

        float RODZICE_FUNKCJA[] = new float[liczebnosc_rodzicow];
        float POTOMKOWIE_FUNKCJA[] = new float[liczebnosc_rodzicow];
        float TAB_PROCENTY[] = new float[liczebnosc_rodzicow];

        //zerowanie danych w tablic do operacji binarnych
        for (i = 0; i < liczebnosc_rodzicow; i++) {
            tablicaBinarna1[i] = 0;
            tablicaBinarna2[i] = 0;
        }

        //losowanie grupy rodzicielskiej
        for (i = 0; i < liczebnosc_rodzicow; i++) {
            GRUPA_RODZICIELSKA[i] = losowanie(1, 31);
        }

        for (j = 0; j < liczebnosc_rodzicow; j++) {
            System.out.print(GRUPA_RODZICIELSKA[j] + "  ");
        }

        //wprowadzanie parametrow przez uzytkownika
        System.out.print("\nwprowadz wspolczynnik krzyzowania (z zakresu 0-1 uzywajac przecinka): ");
        Pk = odczyt.nextFloat();
        System.out.print("\nWprowadz wspolczynnik mutowania (z zakresu 0-1 uzywajac przecinka): ");
        Pm = odczyt.nextFloat();
        System.out.println("\nPodaj wspolczynniki funkcji przystosowania: \n");
        System.out.print("Wspolczynnik x^3 = ");
        wspolczynnik_a = odczyt.nextInt();
        System.out.print("Wspolczynnik x^2 = ");
        wspolczynnik_b = odczyt.nextInt();
        System.out.print("Wspolczynnik x^1 = ");
        wspolczynnik_c = odczyt.nextInt();
        System.out.print("Wspolczynnik x^0 = ");
        wspolczynnik_d = odczyt.nextInt();

        //glowna petla wykonujaca sie w zaleznosci od ilosci pokolen
        for (m = 0; m < ilosc_pokolen; m++) {

            //obliczamy funkcje przystowania dla kazdego rodzica
            for (j = 0; j < liczebnosc_rodzicow; j++) {
                RODZICE_FUNKCJA[j] = ( wspolczynnik_a * GRUPA_RODZICIELSKA[j] * GRUPA_RODZICIELSKA[j] * GRUPA_RODZICIELSKA[j] + wspolczynnik_b * GRUPA_RODZICIELSKA[j] * GRUPA_RODZICIELSKA[j] + wspolczynnik_c * GRUPA_RODZICIELSKA[j] + wspolczynnik_d); // do zmiany jesli dodamy wiecej parametrow
                suma += RODZICE_FUNKCJA[j];
            }

            //wyswietlamy wartosci kazdego rodzica po funkcji przystosowania
            for (j = 0; j < liczebnosc_rodzicow; j++) {
                System.out.println("Funkcja rodzica (" + j + 1 + ") = " + RODZICE_FUNKCJA[j]);
            }
            System.out.println("\nSuma Funkcja (wszystkich fenotypow) = " + suma);

            //obliczamy wartosci procentowe kola ruletki dla kazdego rodzica i zapisujemy w tablicy TAB_PROCENTY
            for (i = 0; i < liczebnosc_rodzicow; i++) {
                TAB_PROCENTY[i] = (RODZICE_FUNKCJA[i] * 100 / suma);
            }


            System.out.println("\nProcentowe przedstawienie kola ruletki:\n");

            //wyswitlamy wartosci procentowe kazdego rodzica
            for (j = 0; j < liczebnosc_rodzicow; j++) {
                System.out.println("Procent rodzica (" + j + 1 + ") = " + TAB_PROCENTY[j]);
            }

            //petla ktora zwieksza wartosci procentowe o poprzednikow. Czyli jak pierwszy mial 10%, drugi 15% a trzeci 13%
            //to po tej petli pierwzy bedzie mial 10%, drugi 25%, a trzeci 38%
            for (k = 1; k < liczebnosc_rodzicow; k++) {
                TAB_PROCENTY[k] += TAB_PROCENTY[k - 1];
            }


            //losowanie liczb na podstawie ktorych wybierzemy z grupy rodzicielskiej osobnikow do krzyzowki i mutacji
            //chwilowo te dane zapiszemy do tablicy potomkowie, a nastepnie wyzerujemy
            for (i = 0; i < liczebnosc_rodzicow; i++) {
                POTOMKOWIE[i] = losowanie(1, 100);
            }


            for (i = 0; i < liczebnosc_rodzicow; i++) {
                for (j = 0; j < liczebnosc_rodzicow; j++) {
                    if (POTOMKOWIE[i] < TAB_PROCENTY[j]) {
                        WYBRANI[i] = GRUPA_RODZICIELSKA[j];
                        break;
                    }
                }
            }


            //zerowanie danych w tablicy potomkow
            for (j = 0; j < liczebnosc_rodzicow; j++) {
                POTOMKOWIE[j] = 0;
            }


            System.out.println("\nKrzyzowanie:\n");

            //Petla krzyzowania. Krzyzowan jest o polowe mniej niz osobników bo biora udzial 2 osobniki wiec warunkiem wykonania petli jest liczebnosc_rodzicow/2
            //do operacji bitowych wykorzystujemy dwie dablice piecio elementowe (maxymalna wartosc potomka 31 co oznacza 11111 czyli 5 liczb
            for (i = 0; i < liczebnosc_rodzicow / 2; i++) {
                //dwóch potomków z dziesietnych zamieniamy na binarna reprezentacje i zapisujemy do tablic tymczasowych
                j = 0;
                number_tmp = WYBRANI[i * 2];
                while (number_tmp != 0) {
                    tablicaBinarna1[j++] = number_tmp % 2;
                    number_tmp /= 2;
                }

                j = 0;
                number_tmp = WYBRANI[i * 2 + 1];
                while (number_tmp != 0) {
                    tablicaBinarna2[j++] = number_tmp % 2;
                    number_tmp /= 2;
                }

                //losujemy czy nastapi krzyzowanie i na ktorych bitach zostanie dokonane
                number_tmp = losowanie(0, liczebnosc_rodzicow-1);
                System.out.println("Ik = " + number_tmp);


                number_tmp_2 = (rand.nextFloat() * (32766 -1) + 1) / 32677;
                System.out.println("Pk = " + number_tmp_2 + "\n");

                //petla dokonujaca krzyzowania. Odpowiednie bity sa wybierane od miejsca zerowego to warunku petli ktory uzalezniony jest od wczesniejszego losowania
                for (k = 0; k < liczebnosc_rodzicow - number_tmp; k++) {
                    if ((tablicaBinarna1[k] != tablicaBinarna2[k]) && (number_tmp_2 <= Pk)) {
                        number_tmp_3 = tablicaBinarna1[k];
                        tablicaBinarna1[k] = tablicaBinarna2[k];
                        tablicaBinarna2[k] = number_tmp_3;
                    }

                }

                //wykorzystujac schemat Hornera liczby w postaci binarnej zamieniamy na dziesietne i zapisujemy do tablicy potomkowie
                for (k = liczebnosc_rodzicow-1; k >= 0; k--) {
                    POTOMKOWIE[i * 2] = POTOMKOWIE[i * 2] * 2 + tablicaBinarna1[k];
                    POTOMKOWIE[i * 2 + 1] = POTOMKOWIE[i * 2 + 1] * 2 + tablicaBinarna2[k];
                }

                //zerujemy tablice tymczasowe
                for (k = 0; k < liczebnosc_rodzicow; k++) {
                    tablicaBinarna1[k] = 0;
                    tablicaBinarna2[k] = 0;
                }
            }


            System.out.println("Mutacja:\n");

            //Mutacja ma miejsce dla kazdego osobno wiec licznikiem petli jest liczebnosc_rodzicow
            for (i = 0 ; i < liczebnosc_rodzicow; i++) {
                j = 0;
                //losowanie czy nastapi mutacja i na ktorym bicie
                number_tmp = losowanie(0, liczebnosc_rodzicow-1);
                System.out.println("Ik = " + number_tmp);

                number_tmp_2 = (rand.nextFloat() * (32766 -1) + 1) / 32677;
                System.out.println("Pm = " + number_tmp_2);

                //sprawdzenie czy w ogole dojdzie do mutacji
                if (number_tmp_2 <= Pm) {
                    //potomka z dziesietnych zamieniamy na binarna reprezentacje i zapisujemy do tablicy tymczasowej
                    while (POTOMKOWIE[i] != 0) {
                        tablicaBinarna1[j++] = POTOMKOWIE[i] % 2;
                        POTOMKOWIE[i] /= 2;
                    }

                    //mutacja
                    if (tablicaBinarna1[number_tmp] == 1) {
                        tablicaBinarna1[number_tmp] = 0;
                    } else {
                        tablicaBinarna1[number_tmp] = 1;
                    }

                    //wykorzystujac schemat Hornera liczby w postaci binarnej zamieniamy na dziesietne i zapisujemy do tablicy potomkowie
                    for (k = liczebnosc_rodzicow-1; k >= 0; k--) {
                        POTOMKOWIE[i] = POTOMKOWIE[i] * 2 + tablicaBinarna1[k];
                    }

                    //zerujemy tablice tymczasowe
                    for (k = 0; k < liczebnosc_rodzicow; k++) {
                        tablicaBinarna1[k] = 0;
                    }
                }
            }


            //wyswietlenie wynikow
            System.out.println("\n\nRodzice:");

            for (j = 0; j < liczebnosc_rodzicow; j++) {
                System.out.print(WYBRANI[j] + " = ");
                dec_na_bin(WYBRANI[j]);
            }


            System.out.println("\n\nPotomkowie po wykonaniu algorytmow genetycznych:\n");
            for (j = 0; j < liczebnosc_rodzicow; j++) {
                System.out.print("\n" + POTOMKOWIE[j] + " = ");
                dec_na_bin(POTOMKOWIE[j]);
            }

            suma = 0;

            for (k = 0; k < liczebnosc_rodzicow; k++) {
                POTOMKOWIE_FUNKCJA[k] = ( (wspolczynnik_a * GRUPA_RODZICIELSKA[k] * GRUPA_RODZICIELSKA[k] * GRUPA_RODZICIELSKA[k]) + (wspolczynnik_b * GRUPA_RODZICIELSKA[k] * GRUPA_RODZICIELSKA[k]) + (wspolczynnik_c * GRUPA_RODZICIELSKA[k]) + wspolczynnik_d);
                suma += POTOMKOWIE_FUNKCJA[k];
            }


            for (j = 0; j < liczebnosc_rodzicow; j++) {
                System.out.println("Funkcja potomek (" + j + 1 + ") = " + POTOMKOWIE_FUNKCJA[j]);
            }
            System.out.println("\nSuma funkcji = " + suma);


            for (i = 0; i < liczebnosc_rodzicow; i++) {
                TAB_PROCENTY[i] = (POTOMKOWIE_FUNKCJA[i] * 100 / suma);
            }


            System.out.println("\n\nProcentowe przedstawienie kola ruletki:\n");

            //wyswietlamy wartosci procentowe kazdego potomka
            for (j = 0; j < liczebnosc_rodzicow; j++) {
                System.out.println("Procent potomek (" + j + 1 + ") = " + TAB_PROCENTY[j]);
            }


            System.out.println("\n\n Najmocniejszy osobnik populacji\n");

            //pentla wybierajaca pierwszego najlepiej przystosowanego osobnika
            for (k = 0; k < liczebnosc_rodzicow; k++) {
                if (TAB_PROCENTY[k] > number_tmp_3) {
                    najmocniejszy_osobnik = k;
                    number_tmp_3 = (int) TAB_PROCENTY[k];
                }
            }

            System.out.println("Zestawienie w pokoleniu:" + m + 1);
            System.out.println("Najmocniejszy osobnik: " + najmocniejszy_osobnik + 1);
            System.out.println("Wartosc funkcji przystosowania: " + POTOMKOWIE_FUNKCJA[najmocniejszy_osobnik]);
            System.out.println("Wartosc procentowa: " + TAB_PROCENTY[najmocniejszy_osobnik]);
            if (najlepszyZawodnik < POTOMKOWIE_FUNKCJA[najmocniejszy_osobnik]) {
                najlepszyZawodnik = POTOMKOWIE_FUNKCJA[najmocniejszy_osobnik];
                najlepszyX = GRUPA_RODZICIELSKA[najmocniejszy_osobnik];
            }


            //podstawienie potomków pod grupe rodzicielska
            for (k = 0; k < liczebnosc_rodzicow; k++) {
                GRUPA_RODZICIELSKA[k] = POTOMKOWIE[k];
            }


            //zerowanie tablic przed kolejnym pokoleniem
            for (k = 0; k < liczebnosc_rodzicow; k++) {
                POTOMKOWIE[k] = 0;
                RODZICE_FUNKCJA[k] = 0;
                POTOMKOWIE_FUNKCJA[k] = 0;
                TAB_PROCENTY[k] = 0;
                WYBRANI[k] = 0;
            }

            //zerowanie zmiennych przed kolejnym pokoleniem
            suma = 0;
            najmocniejszy_osobnik = 0;
            number_tmp = 0;
            number_tmp_2 = 0;
            number_tmp_3 = 0;

        }
        System.out.println();
        System.out.println();
        System.out.println("Najlepszy z najlepszych po " + ilosc_pokolen +  " iteracjach to: " + df.format(najlepszyZawodnik));
        System.out.println("Najlepszy X: " + najlepszyX);
    }

    // funkcja losujaca lczby z zakresu podanego jako parametry
    public static int losowanie(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static void dec_na_bin(int liczba){
            int j, i=0;
            int tab[] = new int[liczbaRodzicow];
            while(liczba != 0) {
                tab[i++]=liczba%2;
                liczba/=2;
            }

            for(j=i-1; j>=0; j--)
                System.out.print(tab[j] + " ");
        System.out.println("");
    }
}
