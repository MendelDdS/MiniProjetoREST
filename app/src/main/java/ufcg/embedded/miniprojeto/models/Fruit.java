package ufcg.embedded.miniprojeto.models;

/**
 * Created by treinamento-09 on 30/05/16.
 */
public class Fruit {
    private String name;
    private double price;
    private String photo_url;
    private String category_url;
    private String vendor_url;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVendor_url() {
        return vendor_url;
    }

    public void setVendor_url(String vendor_url) {
        this.vendor_url = vendor_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
