package pl.sdacademy.italianrestaurant.food;

import pl.sdacademy.italianrestaurant.supply.OrderElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// stosujemy tu klase optional, ktore jest nowoscia w j8,
// pozwala nam to na ominiecie sytuacji wywalenia bledu
// jesli bedzie podane null
// polega to na tym ze jesli w srodku bedzie null, to optional
// tworzy kolejne "puste" pudelko i przekazuje je dalej
public class FoodFactory {
    public Optional<Food> prepareFood(OrderElement orderElement) {
        // I sposob
        String orderedDishType = orderElement.getElementType();
        switch (orderedDishType) {
            // ctr + alt + m, powoduje stworzenie metody z danego
            // bloku kodu
            case "pizza":
                Pizza pizza = createPizza(orderElement);
                // poniewaz korzystamy z optionala opakowujemy go
                // w pudelko optional
                return Optional.of(createPizza(orderElement));
            case "pasta":
        }
        return Optional.empty();

    }

    private Pizza createPizza(OrderElement orderElement) {
        String dough = orderElement.getSpecifics().get("dough").iterator().next();
        String sauce = orderElement.getSpecifics().get("sauce").iterator().next();
        Set<String> toppings = orderElement.getSpecifics().get("topping");
        return Pizza.builder(Dough.valueOf(dough.toUpperCase()), Size.MEDIUM)
                .sauce(sauce)
                .toppings(toppings)
                .build();
    }
    // tu tworzymy tak jakby przepisy dla kazdego z dan
        // ta metoda ma nam zwracac danie ktore wpiszemy
        // a wiec zwraca nam dane danie w kazdej petli
        // lub nulla jesli nic nie wpiszemy na samym koncu
}

