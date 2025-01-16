package repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.PurchaseOrder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderRepository {
    private static final String ORDERS_FILE = "C:\\Users\\acer\\eclipse-workspace\\Stock-Control-System\\src\\resources\\orders.json";
    private final ObjectMapper objectMapper;

    public PurchaseOrderRepository() {
        this.objectMapper = new ObjectMapper();
    }

    // Save a new purchase order
    public void save(PurchaseOrder order) {
        List<PurchaseOrder> orders = loadAllOrders();
        orders.add(order);
        saveAllOrders(orders);
    }

    // Load all purchase orders
    public List<PurchaseOrder> loadAllOrders() {
        try {
            File file = new File(ORDERS_FILE);
            if (!file.exists()) {
                file.createNewFile();
                objectMapper.writeValue(file, new ArrayList<>());
            }
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, PurchaseOrder.class));
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Save all orders to file
    private void saveAllOrders(List<PurchaseOrder> orders) {
        try {
            File file = new File(ORDERS_FILE);
            objectMapper.writeValue(file, orders);
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }
}
