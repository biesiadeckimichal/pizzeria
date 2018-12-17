package pl.sdacademy.italianrestaurant.staff;

import pl.sdacademy.italianrestaurant.food.Food;
import pl.sdacademy.italianrestaurant.supply.Order;
import pl.sdacademy.italianrestaurant.supply.OrderElement;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class Waiter implements Runnable, FoodObserver {
    private Kitchen kitchen;
    private boolean thereIsNewDish;

    public Waiter() {
        // pobieramy kuchnie stworzona przez singletona
        // tak aby wszyscy mieli jedna
        kitchen = Kitchen.getInstance();
    }

    @Override
    public void run() {
        kitchen.registerObserver(this);
        while (!Thread.currentThread().isInterrupted()) {
            if (customerDemandsAttention()) {
                handleCustomer();
            }

            if (thereIsNewDish) {
                // to jest poto zeby poinformowac ze odbieram to nowe danie
                thereIsNewDish = false;
                kitchen.takeFood().ifPresent((food) ->
                        System.out.println("there is your dish: " + food.toString())
                );

            }
        }
        kitchen.unregisterObserver(this);
    }

    private boolean customerDemandsAttention() {
        // metoda do sprawdzania ile znakow zostalo wpisanyych
        // uzywamy zeby zaznaczyc czy klient sie odezwal
        // abysmy go obsluzyli
        // wpisywanie w konsoli jakichkolwiek znakow aby dac znac
        // kelnerowi ze chcemy zlozyc zamowienie
        // musimy usunac te pierwsze smieci ktore klient wpisuje
        // zeby zwrocic uwage
        // czyscimy wiec to co napisal
        // zeby moc zlozyc zammowienie
        try {
            int availableCharacters = System.in.available();
            if (availableCharacters > 0) {
                // odczytaj ilosc znakow ktore wpisal klient
                // kazdy znak to mniej wiecej jeden byte
                // to jakos tak dziala ze program odcina to co
                // klient nastukal na poczatku
                // od tego co jest wyborem dania
                System.in.read(new byte[availableCharacters]);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        thereIsNewDish = true;
    }

    public void handleCustomer() {
        int userSelection = 0;
        while (userSelection != 2) {
            scrollTheScreen();
            System.out.println("Hi, welcome in Prego Italian Restaurant! Would you like...");
            System.out.println("1. Take order");
            System.out.println("2. Pay the bill");
            userSelection = getUserSelection();
            switch (userSelection) {
                case 1:
                    takeOrder();
                    break;
                case 2:
                    payTheBill();
                    break;
            }
        }
        System.out.println("See you!");
    }

    private void takeOrder() {
        scrollTheScreen();
        Order order = new Order();
        System.out.println("I can recommend you delicious pizzas!");
        System.out.println("1. Neapolitan with mozzarella, grana padano and tomato sauce");
        System.out.println("2. New York with mozzarella, prosciutto cotto, mushrooms and tomato sauce");
        System.out.println("3. Sicilian with mozzarella, salami milano, olives and tomato sauce");
        int userSelection = getUserSelection();
        List<Food> dishes;
        switch (userSelection) {
            case 1:
                System.out.println("Neapolitan! So traditional, so delicious! I'll bring it soon.");
                OrderElement firstPizza = new OrderElement("pizza");
                firstPizza.addSpecifics("dough", "neapolitan");
                firstPizza.addSpecifics("sauce", "tomato");
                firstPizza.addSpecifics("topping", "mozzarella");
                firstPizza.addSpecifics("topping", "grana padano");
                order.addElement(firstPizza);
                break;
            case 2:
                System.out.println("Meh, another yankee pizza. Fine.");
                OrderElement secondPizza = new OrderElement("pizza");
                secondPizza.addSpecifics("dough", "new_york");
                secondPizza.addSpecifics("sauce", "tomato");
                secondPizza.addSpecifics("topping", "mozzarella");
                secondPizza.addSpecifics("topping", "prosciutto cotto");
                secondPizza.addSpecifics("topping", "mushrooms");
                order.addElement(secondPizza);
                break;
            case 3:
                System.out.println("Nonna used to cut it into squares. I'll bring it soon.");
                OrderElement thirdPizza = new OrderElement("pizza");
                thirdPizza.addSpecifics("dough", "sicilian");
                thirdPizza.addSpecifics("sauce", "tomato");
                thirdPizza.addSpecifics("topping", "mozzarella");
                thirdPizza.addSpecifics("topping", "salami milano");
                thirdPizza.addSpecifics("topping", "olives");
                order.addElement(thirdPizza);
                break;
            default:
                System.out.println("Mamma mia! That's not even a pizza! Vaffanculo!");
        }
        kitchen.addOrder(order);
    }

    private void payTheBill() {
        scrollTheScreen();
        System.out.println("That will be XXX total.");
    }

    private int getUserSelection() {
        InputStreamReader reader = new InputStreamReader(System.in);
        int readSelection = '0';
        try {
            readSelection = reader.read();
        } catch (IOException e) {
            System.out.println("Could not read input");
        }
        return readSelection - '0';
    }

    private void scrollTheScreen() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }


}
