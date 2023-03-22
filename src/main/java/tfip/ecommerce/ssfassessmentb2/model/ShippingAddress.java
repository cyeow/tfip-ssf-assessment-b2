package tfip.ecommerce.ssfassessmentb2.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ShippingAddress {

    @NotNull(message="Name is required.")
    @Size(min=2, message="Name has to have a minimum of 2 characters.")
    private String name;

    @NotBlank(message="Address is required.")
    private String address;

    public ShippingAddress() {
    }

    public ShippingAddress(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ShippingAddress [name=" + name + ", address=" + address + "]";
    }
}
