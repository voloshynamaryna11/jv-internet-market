package internet.market.dao;

import internet.market.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order createOrder(Order order);

    List<Order> getUserOrders(Long userId);

    Optional<Order> get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}