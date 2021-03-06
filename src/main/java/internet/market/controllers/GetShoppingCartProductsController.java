package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.model.Product;
import internet.market.model.ShoppingCart;
import internet.market.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetShoppingCartProductsController extends HttpServlet {
    private static final String USER_ID = "user_Id";
    private static final Injector injector = Injector.getInstance("internet.market");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart shoppingCart = shoppingCartService
                .getByUserId((Long) req.getSession().getAttribute(USER_ID));
        List<Product> productList = shoppingCart.getProducts();
        req.setAttribute("products", productList);
        req.getRequestDispatcher("/WEB-INF/views/cart/allProductsInCart.jsp").forward(req, resp);
    }
}
