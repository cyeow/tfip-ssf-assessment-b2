package tfip.ecommerce.ssfassessmentb2.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import jakarta.validation.Valid;
import tfip.ecommerce.ssfassessmentb2.model.Cart;
import tfip.ecommerce.ssfassessmentb2.model.Invoice;
import tfip.ecommerce.ssfassessmentb2.model.Item;
import tfip.ecommerce.ssfassessmentb2.model.ShippingAddress;
import tfip.ecommerce.ssfassessmentb2.util.Generate;

@Service
public class PurchaseOrderService {

    private static final String[] AVAILABLE_ITEMS = {"apple", "orange", "bread", "cheese", "chicken", "mineral_water", "instant_noodles"};

    @Autowired
    QuotationService qsvc;

    public void addToCart(Cart cart, Item item) {
        Optional<Item> originalItem = cart.getItems().stream().filter(i -> i.equals(item)).findFirst();
        if(!originalItem.isEmpty()) {
            // item exists
            originalItem.get().setQuantity(originalItem.get().getQuantity() + item.getQuantity());
        }

        // item does not exist
        cart.getItems().add(item);
    }

    public List<ObjectError> validateItem(Item item) {
        List<ObjectError> errors = new ArrayList<>();

        if(!Arrays.asList(AVAILABLE_ITEMS).contains(item.getItem())) {
            errors.add(new FieldError("item", "item", "We do not stock " + item.getItem()));
        }

        return null;
    }

    public Invoice checkoutCart(Cart cart, @Valid ShippingAddress shippingAddress) throws Exception {
        Float totalPrice = calculatePrice(cart);
        
        if(totalPrice == null) {
            return null;
        }

        return new Invoice(Generate.idString(8), shippingAddress, cart, totalPrice);
    }

    private Float calculatePrice(Cart cart) throws Exception {
        Map<String, Float> prices = getQuote(cart);

        Float totalPrice = 0f;
        for(Item item : cart.getItems()) {
            totalPrice += prices.get(item.getItem()) * item.getQuantity();
        }

        return totalPrice;
    }

    private Map<String, Float> getQuote(Cart cart) throws Exception {
        List<String> items = new ArrayList<>();
        cart.getItems().forEach(item -> items.add(item.getItem()));

        return qsvc.getQuotations(items).getQuotations();
    }
}
