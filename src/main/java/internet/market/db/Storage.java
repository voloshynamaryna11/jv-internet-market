package internet.market.db;

import internet.market.model.Product;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Product> products = new ArrayList<>();
    private static Long productId = 0L;

    public static void addProduct(Product product) {
        product.setId(++productId);
        products.add(product);
    }
}