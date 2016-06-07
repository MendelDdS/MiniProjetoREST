package ufcg.embedded.miniprojeto.models;

import java.util.List;

/**
 * Created by treinamento-09 on 06/06/16.
 */
public class CustomerItem extends Customer {
    private String customer_url;
    private List<Order> orders;

    public String getCustomer_url() {
        return customer_url;
    }

    public void setCustomer_url(String customer_url) {
        this.customer_url = customer_url;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
