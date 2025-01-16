package models;

import java.util.List;

public class UserStocks {
    private String username;
    private List<Stock> stocks;

    public UserStocks() {
    }

    public UserStocks(String username, List<Stock> stocks) {
        this.username = username;
        this.stocks = stocks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "UserStocks{" +
                "username='" + username + '\'' +
                ", stocks=" + stocks +
                '}';
    }
}
