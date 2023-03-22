package tfip.ecommerce.ssfassessmentb2.model;

public class Invoice {
    private String invoiceId;
    private ShippingAddress shippingAddress;
    private Cart cart;
    private Float total;
    public Invoice() {
    }
    public Invoice(String id, ShippingAddress shippingAddress, Cart cart, Float totalPrice) {
        this.invoiceId = id;
        this.shippingAddress = shippingAddress;
        this.cart = cart;
        this.total = totalPrice;
    }
    public String getInvoiceId() {
        return invoiceId;
    }
    public void setInvoiceId(String id) {
        this.invoiceId = id;
    }
    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Float getTotal() {
        return total;
    }
    public void setTotal(Float totalPrice) {
        this.total = totalPrice;
    }
    @Override
    public String toString() {
        return "Invoice [id=" + invoiceId + ", shippingAddress=" + shippingAddress + ", cart=" + cart + ", totalPrice="
                + total + "]";
    }
    
}
