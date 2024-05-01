package pl.kurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

        /*Pracujesz w banku i masz stworzyc trzy restowe metody:
        1) Metoda ktora pozwoli wykonac przelew miedzy dwoma uzytkownikami
        2) Metoda ktora pozwoli wyszukac historie transakcji uwzgledniajc nastepujace parametry: kwota <od, do>, data <od, do>, uzytkownik - parametry moga byc obecne w roznych permutacjach
        3) Metode ktora pozwoli pobrac userow [paginacja], kazdy user oprocz swoich danych musi miec info o ilosci wykonanych przelewow. - done

                w metodzie nr 1 nalezy zapewnic spojnosc wykonywania operacji i zabezpieczyc rozne aspekty wynikajace ze wspolbieznosci.
        w metodzie nr 2 wynik ma byc zwracany w formie paginacji.

                Calosc powinna byc pokryta testami integracyjnymi.
                Dane inicjalne powinny byc w pliku SQL albo changesetach flyway/liquibase.*/
    }
}
