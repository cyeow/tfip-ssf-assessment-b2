package tfip.ecommerce.ssfassessmentb2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import tfip.ecommerce.ssfassessmentb2.model.Cart;
import tfip.ecommerce.ssfassessmentb2.model.Invoice;
import tfip.ecommerce.ssfassessmentb2.model.Item;
import tfip.ecommerce.ssfassessmentb2.model.ShippingAddress;
import tfip.ecommerce.ssfassessmentb2.service.PurchaseOrderService;

@Controller
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService svc;

    @GetMapping("/")
    public String goToLanding(HttpSession session, Model model) {
        model.addAttribute("item", new Item());

        Cart cart;
        if (session.getAttribute("cart") == null) {
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
    public String addToCart(HttpSession session, Model model, @Valid Item item, BindingResult binding) {
        // validation
        List<ObjectError> errors = svc.validateItem(item);
        if (errors != null) {
            errors.forEach(e -> binding.addError(e));
        }
        if (binding.hasErrors()) {
            model.addAttribute("cart", session.getAttribute("cart"));
            return "view1";
        }

        svc.addToCart((Cart) session.getAttribute("cart"), item);
        model.addAttribute("cart", session.getAttribute("cart"));
        model.addAttribute("item", new Item());
        return "view1";
    }

    @GetMapping("/shippingaddress")
    public String goToShippingPage(HttpSession session, Model model) {
        if ((session.getAttribute("cart") == null) || ((Cart) session.getAttribute("cart")).isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("shippingAddress", new ShippingAddress());
        return "view2";
    }

    @PostMapping("/checkout")
    public String checkoutCart(HttpSession session, Model model, @Valid ShippingAddress shippingAddress, BindingResult binding) throws Exception {
        // validation
        if(binding.hasErrors()) {
            return "view2";
        }

        Invoice invoice = svc.checkoutCart((Cart) session.getAttribute("cart"), shippingAddress);
        model.addAttribute("invoice", invoice);
        
        // clear session
        session.removeAttribute("cart");
        return "view3";
    }
}
