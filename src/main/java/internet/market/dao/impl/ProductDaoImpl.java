package internet.market.dao.impl;

import internet.market.dao.ProductDao;
import internet.market.db.Storage;
import internet.market.lib.Dao;
import internet.market.model.Product;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product product) {
        Storage.products.stream()
                .filter(productUnit -> productUnit.getId().equals(product.getId()))
                .forEach(productUnit -> Storage.products
                        .set(Storage.products.indexOf(productUnit), product));
        return product;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.products.removeIf(product -> product.getId().equals(id));
    }
}
