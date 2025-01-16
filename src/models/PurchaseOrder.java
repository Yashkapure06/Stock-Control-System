package models;

import java.util.Date;
import java.util.Objects;

public class PurchaseOrder {
    private String itemId;
    private int quantity;
    private Date orderDate;

    // Constructor
    public PurchaseOrder(String itemId, int quantity, Date orderDate) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrder that = (PurchaseOrder) o;
        return quantity == that.quantity &&
               Objects.equals(itemId, that.itemId) &&
               Objects.equals(orderDate, that.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, quantity, orderDate);
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
               "itemId='" + itemId + '\'' +
               ", quantity=" + quantity +
               ", orderDate=" + orderDate +
               '}';
    }
}