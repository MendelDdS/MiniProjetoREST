package ufcg.embedded.miniprojeto.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by treinamento-09 on 30/05/16.
 */
public class Shop {
    private Links links;
    private List<Product> products;
    private List<Categorie> categories;
    private List<Order> orders;
    private List<Vendor>vendors;
    private List<CustomerItem> customers;

    public Shop() {
        products = new ArrayList<Product>();
        categories = new ArrayList<Categorie>();
        orders = new ArrayList<Order>();
        vendors = new ArrayList<Vendor>();
        customers = new ArrayList<CustomerItem>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    public List<CustomerItem> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerItem> customers) {
        this.customers = customers;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
