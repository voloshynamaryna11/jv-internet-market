package internet.market.service.impl;

import internet.market.dao.OrderDao;
import internet.market.lib.Inject;
import internet.market.lib.Service;
import internet.market.model.Order;
import internet.market.model.ShoppingCart;
import internet.market.service.OrderService;
import internet.market.service.ShoppingCartService;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;

    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order(shoppingCart.getUserId());
        order.setProducts(List.copyOf(shoppingCart.getProducts()));
        shoppingCartService.clear(shoppingCart);
        return orderDao.create(order);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDao.getUserOrders(userId);
    }

    @Override
    public Order create(Order item) {
        return orderDao.create(item);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order update(Order item) {
        return orderDao.update(item);
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
