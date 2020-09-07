package internet.market.dao.impl;

import internet.market.dao.ShoppingCartDao;
import internet.market.db.Storage;
import internet.market.lib.Dao;
import internet.market.model.ShoppingCart;
import java.util.Optional;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getUserId().equals(userId))
                .findAny();
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return Storage.shoppingCarts
                .removeIf(shoppingCartUnit -> shoppingCartUnit.equals(shoppingCart));
    }
}
