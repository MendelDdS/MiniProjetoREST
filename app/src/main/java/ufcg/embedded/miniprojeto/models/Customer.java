package ufcg.embedded.miniprojeto.models;

import java.io.Serializable;

public class Customer implements Serializable {
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

    @Override
    public String toString() {
        return firstname.toString() + " " + lastname.toString() + " " + orders_url + " " + customer_url;
    }
}
