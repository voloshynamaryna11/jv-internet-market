package internet.market.dao;

import internet.market.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getByUserId(Long userId);

    boolean delete(ShoppingCart shoppingCart);
}
