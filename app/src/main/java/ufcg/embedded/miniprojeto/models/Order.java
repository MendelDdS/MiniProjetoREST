package ufcg.embedded.miniprojeto.models;

/**
 * Created by treinamento-09 on 30/05/16.
 */
public class Order {

    private Double total;
    private String createdAt;
    private String updatedAt;
    private String state;
    private String customer_url;
    private Action actions;
    private String items_url;

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


    public Action getActions() {
        return actions;
    }

    public void setActions(Action actions) {
        this.actions = actions;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCustomer_url() {
        return customer_url;
    }

    public void setCustomer_url(String customer_url) {
        this.customer_url = customer_url;
    }

    public String getItems_url() {
        return items_url;
    }

    public void setItems_url(String items_url) {
        this.items_url = items_url;
    }
}
