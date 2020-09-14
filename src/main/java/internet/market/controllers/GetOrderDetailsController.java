package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.model.Product;
import internet.market.service.OrderService;
import internet.market.service.ProductService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetOrderDetailsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.market");
    private OrderService orderService = (OrderService) injector
            .getInstance(OrderService.class);
    private ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("id");
        Long id = Long.valueOf(orderId);
        List<Product> products = orderService.get(id).getProducts();
        double sum = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
        req.setAttribute("products", products);
        req.setAttribute("sum", sum);
        req.getRequestDispatcher("/WEB-INF/views/order/orderDetails.jsp").forward(req, resp);
    }
}
