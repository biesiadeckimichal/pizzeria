package pl.sdacademy.italianrestaurant.staff;

import pl.sdacademy.italianrestaurant.food.*;
import pl.sdacademy.italianrestaurant.supply.Order;
import pl.sdacademy.italianrestaurant.supply.OrderElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Chef implements Runnable, OrderObserver {
    private FoodFactory foodFactory;
    private Kitchen kitchen;
    private boolean thereIsNewOrder;


    public Chef() {
        foodFactory = new FoodFactory();
        // tworzymy kuchnie, jedna dla wszystkich
        kitchen = Kitchen.getInstance();
    }

    // metoda run jest pobrana z interface Runnable
    // obslugujemy tu watek
    @Override
    public void run() {
        // rejestrujemy sie ze jestesmy dostepni do odebrania zamowienia
        kitchen.registerObserver(this);

        // ponizsza linia sprawdza czy watek biegnie
        while (!Thread.currentThread().isInterrupted()) {
            if (thereIsNewOrder) {
                thereIsNewOrder = false;
                Optional<Order> order = kitchen.takeOrder();
                if (order.isPresent()) {
                    List<Food> preparedFoods = prepareOrderedFood(order.get());
                    for (Food preparedFood : preparedFoods) {
                        kitchen.addFood(preparedFood);
                    }
                }
            }
            // sprawdz, czy zostales poinformowany o nowym zamowieniu
            // jesli tak, to pobierz zamowienie
            // jesli udalo sie pobrac zamowienie, to je przetworz
            // przekaz gotowe danie do kuchni
        }
        // kucharz wyrejestrowuje sie, nie jest dostepny
        kitchen.unregisterObserver(this);
    }

    //                .collect(Collectors.toList());
    //                .map(Optional::get)
    //                .filter(Optional::isPresent)
    //                .map(foodFactory::prepareFood)
    //        return order.getElements().stream()
//    public List<Food> prepareOrderedFood(Order order) {

//    }

    // to dziala na tej zasadzie ze kelner przynosi liste dan

    public List<Food> prepareOrderedFood(Order order) {
        // tworzymy wiec liste dan ktore zostana przygotowane
        // przygotowujemy je, i jak sa gotowe dodajemy do
        // listy gotowych dan
        List<Food> preparedFood = new ArrayList<>();
        // tu przechodzimy przez kazde danie z listy i wywolujemy builder
        // ktory to danie przygotuje
        for (OrderElement element : order.getElements()) {
            Optional<Food> food = foodFactory.prepareFood(element);
            // korzystamy tu z optionala
            // zwroc mi optional z food, i jesli jest cos w srodku tego food
            // to wywolaj mi metode add na obiekcie preparedFood
            // zamiast tego mozna napisac
            // if (food.isPresent()) {
            //  Food zawartoscPudelka = food.get();
            //  preparedFood.add(zawartoscPudelka)
            // }
            food.ifPresent(preparedFood::add);
            // w mapie mamy oczywiscie klucz - wartosc
            // rodzaj maki jest tylko jeden, sos tez
            // ponizszy iterator powoduje pobranie maki i sosu
            // pobieramy z setu tylko jeden element, jesli jest ich wiecej
            // pobieramy tylko jeden
            // jak juz zrobilismy metode tworzenia pizzy w foodFactory
            // mozemy usunac to ponizej
            String dough = element.getSpecifics().get("dough").iterator().next();
            String sauce = element.getSpecifics().get("sauce").iterator().next();
            // roznica miedzy pojedynczymi elementami jak maka lub sos a dodatkami
            // jest taka ze maka i sos maja tylko jeden rodzaj/element
            // i tylko jeden element jest dodany, pierwszy element
            // natomiast przy dodatkach zwracane jest wiele elementow
            Set<String> toppings = element.getSpecifics().get("topping");
            // ponizej zmienilismy sposob tworzenia pizzy
            // na taki wymagajacy ciasta i rozmiaru
            Pizza pizza = Pizza.builder(Dough.valueOf(dough.toUpperCase()), Size.MEDIUM)
                    .sauce(sauce)
                    .toppings(toppings)
                    .build();
            // dodajemy gotowa pizze do listy
            preparedFood.add(pizza);
        }
        // tu zwracam liste gotowych dan
        return preparedFood;
    }

    @Override
    public void update() {
        thereIsNewOrder = true;

    }
}
