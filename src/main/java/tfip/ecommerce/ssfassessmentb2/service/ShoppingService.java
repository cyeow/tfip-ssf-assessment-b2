package tfip.ecommerce.ssfassessmentb2.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import tfip.ecommerce.ssfassessmentb2.model.Cart;
import tfip.ecommerce.ssfassessmentb2.model.Item;

@Service
public class ShoppingService {

    public void addToCart(Cart cart, Item item) {
        Optional<Item> originalItem = cart.getItems().stream().filter(i -> i.equals(item)).findFirst();
        if(!originalItem.isEmpty()) {
            // item exists
            originalItem.get().setQuantity(originalItem.get().getQuantity() + item.getQuantity());
        }

        // item does not exist
        cart.getItems().add(item);
    }
    
}
