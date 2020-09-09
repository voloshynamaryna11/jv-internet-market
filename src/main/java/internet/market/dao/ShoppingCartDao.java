package internet.market.dao;

import internet.market.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {
    Optional<ShoppingCart> getByUserId(Long userId);

    boolean deleteByObject(ShoppingCart shoppingCart);
}
