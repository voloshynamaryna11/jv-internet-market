package internet.market.dao;

import internet.market.model.Order;
import internet.market.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order createOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(Long userId);

    Optional<Order> get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
