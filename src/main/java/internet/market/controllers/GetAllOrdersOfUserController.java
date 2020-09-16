package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.model.Order;
import internet.market.service.OrderService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllOrdersOfUserController extends HttpServlet {
    private static final String USER_ID = "user_Id";
    private static final Injector injector = Injector.getInstance("internet.market");
    private OrderService orderService = (OrderService) injector
            .getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> userOrders = orderService.getUserOrders((Long) req.getSession()
                .getAttribute(USER_ID));
        req.setAttribute("orders", userOrders);
        req.getRequestDispatcher("/WEB-INF/views/order/ordersOfUser.jsp").forward(req, resp);
    }
}
