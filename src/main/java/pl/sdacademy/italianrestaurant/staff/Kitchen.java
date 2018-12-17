package pl.sdacademy.italianrestaurant.staff;

import pl.sdacademy.italianrestaurant.food.Food;
import pl.sdacademy.italianrestaurant.supply.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Kitchen {
    // tworzymy tu singletona ale ni cholery nie wiem czeu
    private static final Kitchen INSTANCE = new Kitchen();
    private List<OrderObserver> orderObservers;
    private List<FoodObserver> foodObservers;
    // kolejka dwukierunkowa, podwojnie zabezpieczona
    // do dopytania czemu akurat ta bo jest kilka innych do wyboru
    private Queue<Order> ordersToBeProcessed;
    private Queue<Food> preparedFoods;

    public Kitchen() {
        orderObservers = new ArrayList<>();
        foodObservers = new ArrayList<>();
        ordersToBeProcessed = new ConcurrentLinkedDeque<>();
        preparedFoods = new ConcurrentLinkedDeque<>();
    }

    // tu tez tworzymy singletona
    public static Kitchen getInstance() {
        return INSTANCE;
    }

    public void registerObserver(OrderObserver observer) {
        orderObservers.add(observer);
    }

    public void registerObserver(FoodObserver observer) {
        foodObservers.add(observer);
    }

    // w momencie gdy kucharz nie chce bys w grupie obserwatorow
    public void unregisterObserver(OrderObserver observer) {
        orderObservers.remove(observer);
    }

    public void unregisterObserver(FoodObserver observer) {
        foodObservers.remove(observer);
    }

    // przejscie po wszystkich obeserwerach i poinformowanie ich
    // o zmianie stanu kuchni
    public void addOrder(Order order) {
        ordersToBeProcessed.add(order);
//        for (OrderObserver observer : orderObservers) {
//            observer.update();
//        }
        orderObservers.forEach(OrderObserver::update);
    }

    // kucharz wywoluje add food infrmujac
    // kuchnie, wszystkich obserwerow ze food jest gotowy
    public void addFood(Food food) {
        preparedFoods.add(food);
        foodObservers.forEach(FoodObserver::update);
    }

    // aby uniknac nulla czego staramy sie unikac
    public Optional<Order> takeOrder() {
        // poll wyciagnij element z kolejki i usun kolejke
        return Optional.ofNullable(ordersToBeProcessed.poll());
    }

    // tu mamy optional
    // bo jesli przyjdzie dwoch kucharzy po zamowienie
    // to jeden wezmie zamowienie, a drugi dostanie pustego optionala
    public Optional<Food> takeFood() {
        return Optional.ofNullable(preparedFoods.poll());
    }
}
