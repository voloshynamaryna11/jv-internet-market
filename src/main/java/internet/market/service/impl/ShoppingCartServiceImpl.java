package internet.market.service.impl;

import internet.market.dao.ShoppingCartDao;
import internet.market.lib.Inject;
import internet.market.lib.Service;
import internet.market.model.Product;
import internet.market.model.ShoppingCart;
import internet.market.service.ShoppingCartService;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart get(Long id) {
        return shoppingCartDao.get(id).get();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return shoppingCartDao.getAll();
    }

    @Override
    public ShoppingCart update(ShoppingCart item) {
        return shoppingCartDao.update(item);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        List<Product> list = shoppingCartDao.getByUserId(shoppingCart.getUserId())
                .get().getProducts();
        list.add(product);
        shoppingCart.setProducts(list);
        return shoppingCartDao.update(shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        List<Product> list = shoppingCartDao.getByUserId(shoppingCart.getUserId())
                .get().getProducts();
        list.remove(product);
        shoppingCart.setProducts(list);
        shoppingCartDao.update(shoppingCart);
        return true;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        List<Product> list = shoppingCartDao.getByUserId(shoppingCart.getUserId())
                .get().getProducts();
        list.clear();
        shoppingCart.setProducts(list);
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getByUserId(userId).get();
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return shoppingCartDao.delete(shoppingCart);
    }

    @Override
    public boolean delete(Long id) {
        return shoppingCartDao.delete(id);
    }
}
