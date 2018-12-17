package pl.sdacademy.italianrestaurant;

import pl.sdacademy.italianrestaurant.staff.Chef;
import pl.sdacademy.italianrestaurant.staff.Waiter;

public class Main {
    public static void main(String[] args) {
        Waiter waiter = new Waiter();
        Chef chef = new Chef();

        Thread waiterThread = new Thread(waiter);
        Thread chefThread = new Thread(chef);

        waiterThread.start();
        chefThread.start();
    }
}
