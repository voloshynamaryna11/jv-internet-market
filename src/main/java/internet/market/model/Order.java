package internet.market.model;

import java.util.List;

public class Order {
    Long id;
    List<Product> products;
    Long userId;

    public Order(List<Product> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
