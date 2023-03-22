package tfip.ecommerce.ssfassessmentb2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import tfip.ecommerce.ssfassessmentb2.model.Cart;
import tfip.ecommerce.ssfassessmentb2.model.Item;
import tfip.ecommerce.ssfassessmentb2.service.ShoppingService;

@Controller
public class ShoppingController {
    
    @Autowired
    ShoppingService svc;

    @GetMapping("/")
    public String goToLanding(HttpSession session, Model model) {
        model.addAttribute("item", new Item());
        
        Cart cart;
        if(session.getAttribute("cart") == null) {
            // create new cart if new session
            cart = new Cart();
            session.setAttribute("cart", cart);
        } else {
            // retrieve old cart if existing session
            cart = (Cart) session.getAttribute("cart");
        }

        model.addAttribute("cart", cart);
        return "view1";
    }

    @PostMapping("/item")
    public String addToCart(HttpSession session, Model model, Item item) {
        // add validation

        svc.addToCart((Cart) session.getAttribute("cart"), item);
        // does this modify the cart in place? to confirm.
        model.addAttribute("cart", session.getAttribute("cart"));
        model.addAttribute("item", new Item());
        return "view1";
    }

    // @GetMapping("/shippingaddress")
}
