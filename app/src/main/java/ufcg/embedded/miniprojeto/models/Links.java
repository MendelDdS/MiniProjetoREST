package ufcg.embedded.miniprojeto.models;

/**
 * Created by treinamento-09 on 01/06/16.
 */
public class Links {
    private String orders_url;
    private String products_url;
    private String customers_url;
    private String categories_url;
    private String vendors_url;

    public String getOrders_url() {
        return orders_url;
    }

    public void setOrders_url(String orders_url) {
        this.orders_url = orders_url;
    }

    public String getProducts_url() {
        return products_url;
    }

    public void setProducts_url(String products_url) {
        this.products_url = products_url;
    }

    public String getCustomers_url() {
        return customers_url;
    }

    public void setCustomers_url(String customers_url) {
        this.customers_url = customers_url;
    }

    public String getCategories_url() {
        return categories_url;
    }

    public void setCategories_url(String categories_url) {
        this.categories_url = categories_url;
    }

    public String getVendors_url() {
        return vendors_url;
    }

    public void setVendors_url(String vendors_url) {
        this.vendors_url = vendors_url;
    }
}