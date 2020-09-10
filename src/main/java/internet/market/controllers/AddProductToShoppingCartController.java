package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.model.ShoppingCart;
import internet.market.service.ProductService;
import internet.market.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductToShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;

    private static final Injector injector = Injector.getInstance("internet.market");
    private ShoppingCartService shoppingCartService = (ShoppingCartService) injector
            .getInstance(ShoppingCartService.class);
    private ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productId = req.getParameter("id");
        Long id = Long.valueOf(productId);
        ShoppingCart shoppingCart = shoppingCartService.create(new ShoppingCart(USER_ID));
        shoppingCartService.addProduct(shoppingCart, productService.get(id));
        resp.sendRedirect(req.getContextPath() + "/product/all");
    }
}
