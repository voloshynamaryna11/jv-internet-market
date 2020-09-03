package internet.market.service;

import internet.market.dao.ProductDao;
import internet.market.lib.Inject;
import internet.market.lib.Service;
import internet.market.model.Product;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductDao productDao;

    @Override
    public Product create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Product get(Long id) {
        if (productDao.get(id).isPresent()) {
            return productDao.get(id).get();
        }
        throw new IllegalArgumentException("We don't have product with such id");
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public Product update(Product product) {
        if(product.getId() == null){
            throw new IllegalArgumentException("Please indicate id of product you want to update");
        }
        get(product.getId());
        productDao.update(product);
        return product;
    }

    @Override
    public boolean delete(Long id) {
        return productDao.delete(id);
    }
}
