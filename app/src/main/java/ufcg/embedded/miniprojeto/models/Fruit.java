package ufcg.embedded.miniprojeto.models;

/**
 * Created by treinamento-09 on 30/05/16.
 */
public class Fruit {
    private String id;
    private String name;
    private String price;
    private String photo_url;
    private String category_url;
    private String vendor_url;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
