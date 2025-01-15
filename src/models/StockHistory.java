package models;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class StockHistory {

    private String itemId;
    private List<HistoryRecord> historyRecords;

    // Constructor
    public StockHistory(String itemId) {
        this.itemId = itemId;
        this.historyRecords = new ArrayList<>();
    }

    // Add a history record
    public void addHistoryRecord(int quantityAdjusted, String adjustmentType, String description) {
        HistoryRecord record = new HistoryRecord(new Date(), quantityAdjusted, adjustmentType, description);
        historyRecords.add(record);
    }

    // Get history records
    public List<HistoryRecord> getHistoryRecords() {
        return historyRecords;
    }

    @Override
    public String toString() {
        if (historyRecords.isEmpty()) {
            return "No History";
        }
        StringBuilder sb = new StringBuilder("Stock History:\n");
        for (HistoryRecord record : historyRecords) {
            sb.append(record.toString()).append("\n");
        }
        return sb.toString();
    }


    // Inner class to represent a history record
    public class HistoryRecord {
        private Date date;
        private int quantityAdjusted;
        private String adjustmentType;
        private String description;

        // Constructor
        public HistoryRecord(Date date, int quantityAdjusted, String adjustmentType, String description) {
            this.date = date;
            this.quantityAdjusted = quantityAdjusted;
            this.adjustmentType = adjustmentType;
            this.description = description;
        }

        // Getters
        public Date getDate() {
            return date;
        }

        public int getQuantityAdjusted() {
            return quantityAdjusted;
        }

        public String getAdjustmentType() {
            return adjustmentType;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "Date: " + date +
                   ", Quantity Adjusted: " + quantityAdjusted +
                   ", Adjustment Type: " + adjustmentType +
                   ", Description: " + description;
        }
        
}
}
