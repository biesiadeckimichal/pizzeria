package pl.sdacademy.italianrestaurant.food;

import java.util.*;

public class Pizza implements Food, Bakeable {

    // 4 - final, w momencie dodania finala, podkresla sie na czerwono
    // bo musimy zainicjalizowac
    private final Dough dough;
    private final Size size;
    private final String sauce;
    private final Set<String> toppings;

    // 3 - private
    // kolejny etap tu daje sie parametr przez alt enter builder
    // po utworzeniu tego:
//    public Pizza build() {
//        return new Pizza(this);
//    }
    // tak zmienia sie glowny builder:
//    private Pizza(Builder builder) {
//        this.dough = builder.dough;
//        this.size = builder.size;
//        this.sauce = builder.sauce;
//        this.toppings = builder.toppings
//    }

    private Pizza(Builder builder) {
        this.dough = builder.dough;
        this.size = builder.size;
        this.sauce = builder.sauce;
        this.toppings = builder.toppings;
    }

    public Dough getDough() {
        return dough;
    }

    public Size getSize() {
        return size;
    }

    public String getSauce() {
        return sauce;
    }

    // dzieki ponizszemu zapisowi jestesmy w stanie zablowkowac
    // przed zmiana dodatkow
    // dziala to tak ze tworzym w pamieci tak jakby kopie
    // miski w pamieci i korzystamy z nowej miski
    // stara miska zostaje w pamieci ale z niej juz nie korzystamy
    // tak to wygladalo na poczatku:
    //    public Set<String> getToppings() {
    //        return toppings;
    //    }
    // a tak wyglada teraz:
    public Set<String> getToppings() {
        return new HashSet<>(toppings);
    }

    // zawsze jednak odwolujemy sie w pamieci do pierwszej miski

    // 5 usuwamy wszystkie sety, bo nie mozemy zmieniac dodatkow po utworzeniu pizzy
    // usuwamy ta metode poniewaz jedyna opcja dodawania
    // dodatkow powinna byc w builderze
    //    public void addToppings(String topping) {
    //        toppings.add("toppings");
    //    }

    public void bake(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "dough=" + dough +
                ", size=" + size +
                ", sauce='" + sauce + '\'' +
                ", toppings=" + toppings +
                '}';
    }

    // 2 jedyna zadanie to zwracanie nowej instancji buildera
    // koncowy etap to dodanie argumentow do konstruktora
    // jak jest na czerwono to przez alt enter dodajemy do buildera
    // argumenty
    public static Builder builder(Dough dough, Size size) {
        // te dwie linijki powoduja ze nie mozna wpisac null w
        // trakcie tworzenia pizzy
        // wyrzuca wyjatek, to jest szybszy sposob napisania
        // if dough == null then throw...
        Objects.requireNonNull(dough);
        Objects.requireNonNull(size );
        return new Builder(dough, size);
    }

    // 1
    public static class Builder {
        private Dough dough;
        private Size size;
        private String sauce;
        private Set<String> toppings;

        private Builder(Dough dough, Size size) {
            this.dough = dough;
            this.size = size;
            this.toppings = new HashSet<>();
        }

        // usuwamy metody tworzenia ciasta i sosu
//        public Builder dough(Dough dough) {
//            this.dough = dough;
//            return this; // wywolywanie metod po kropce
//        }
//
//        public Builder size(Size size) {
//            this.size = size;
//            return this; // wywolywanie metod po kropce
//        }

        public Builder sauce(String sauce) {
            this.sauce = sauce;
            return this; // wywolywanie metod po kropce
        }

        public Builder toppings(Set<String> toppings) {

            // this.toppings = toppings; dodanie w ten sposob
            // spowoduje ze jesli dodamy dodatkowy ser
            // a pozniej oliwe, to doda tylko oliwe
            // jesli chcemy zeby dodalo obydwa to robimy jak nizej
            // przez all oraz dodac w lini 75
            // private Builder() {
            // this.toppings = new HashSet<>();
            // }
            // musimy dodac w lini 75 new HashSet zebysmy mieli
            // tak jakby "miske" do ktorej wrzucamy dodatki i tam
            // przechowujemy
            this.toppings.addAll(toppings);
            return this;
        }

        // ponizszy builder pozwala na dodawanie dodatkow
        // pojedynczo do miski
        // ta wyzsza powoduje dodanie calej listy dodatkow na raz do miski
        public Builder topping(String topping) {
            this.toppings.add(topping);
            return this;
        }

        // jak wywala na czerwono to dadajmey 1st cos tam
        // dodaje parametr Builder do konstruktora na samej gorze
        public Pizza build() {
            return new Pizza(this);
        }
    }

//    private final Dough dough;
//    private final Size size;
//    private final String sauce;
//    private final Set<String> toppings;

}
