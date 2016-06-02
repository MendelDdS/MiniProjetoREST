package ufcg.embedded.miniprojeto.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by treinamento-09 on 30/05/16.
 */
public class Customer {
    private List<Order> orders;
    private String firstname;
    private String lastname;
    private String customer_url;

    public Customer() {
        orders = new ArrayList<Order>();
    }

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

    public String getCustomer_url() {
        return customer_url;
    }

    public void setCustomer_url(String customer_url) {
        this.customer_url = customer_url;
    }
}
