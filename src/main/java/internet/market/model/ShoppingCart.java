package internet.market.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(Long userId) {
        products = new ArrayList<>();
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj.getClass().equals(ShoppingCart.class)) {
            ShoppingCart shoppingCart = (ShoppingCart) obj;
            return id != null ? id.equals(shoppingCart.id)
                    : shoppingCart.id == null
                    && userId != null ? userId.equals(shoppingCart.userId)
                    : shoppingCart.userId == null
                    && products != null ? IntStream.range(0, products.size())
                    .allMatch(i -> products.get(i).equals(shoppingCart.products.get(i)))
                    : shoppingCart.products == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        if (id != null) {
            result = prime * result + id.hashCode();
        }
        if (userId != null) {
            result = prime * result + userId.hashCode();
        }
        if (products != null) {
            result = prime * result + products.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        return "ShoppingCart{"
                + "id=" + id
                + ", products=" + products
                + ", userId=" + userId
                + '}';
    }
}
