package ufcg.embedded.miniprojeto.models;

/**
 * Created by treinamento-09 on 30/05/16.
 */
public class Customer {
    private String firstname;
    private String lastname;
    private String orders_url;
    private String customer_url;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOrders_url() {
        return orders_url;
    }

    public void setOrders_url(String orders_url) {
        this.orders_url = orders_url;
    }

    public String getCustomer_url() {
        return customer_url;
    }

    public void setCustomer_url(String customer_url) {
        this.customer_url = customer_url;
    }
}
