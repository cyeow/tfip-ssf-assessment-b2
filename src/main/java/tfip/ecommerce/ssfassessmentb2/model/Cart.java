package tfip.ecommerce.ssfassessmentb2.model;

import java.util.HashSet;
import java.util.Set;

public class Cart {
    private Set<Item> items;

    public Cart() {
        this.items = new HashSet<>();
    }

    public Cart(Set<Item> items) {
        this.items = items;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart [items=" + items + "]";
    }

}
