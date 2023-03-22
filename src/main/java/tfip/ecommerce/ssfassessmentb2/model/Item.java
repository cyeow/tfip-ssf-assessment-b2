package tfip.ecommerce.ssfassessmentb2.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Item {

    @NotNull(message="You must specify an item")
    private String item;

    @NotNull(message="You must add at least 1 item")
    @Min(value=1, message="You must add at least 1 item")
    private Integer quantity;

    public Item() {
    }

    public Item(String item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item [item=" + item + ", quantity=" + quantity + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((item == null) ? 0 : item.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        if (item == null) {
            if (other.item != null)
                return false;
        } else if (!item.equals(other.item))
            return false;
        return true;
    }

}
