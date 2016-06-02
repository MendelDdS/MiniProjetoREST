package ufcg.embedded.miniprojeto.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by treinamento-09 on 30/05/16.
 */
public class Order {

    private String createdAt;
    private String updatedAt;
    private String state;
    private String customerUrl;
    private Action actions;
    private String itemsUrl;
    private List<Item> items = new ArrayList<Item>();

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomerUrl() {
        return customerUrl;
    }

    public void setCustomerUrl(String customerUrl) {
        this.customerUrl = customerUrl;
    }

    public Action getActions() {
        return actions;
    }

    public void setActions(Action actions) {
        this.actions = actions;
    }

    public String getItemsUrl() {
        return itemsUrl;
    }

    public void setItemsUrl(String itemsUrl) {
        this.itemsUrl = itemsUrl;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
