package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.model.Product;
import internet.market.model.Role;
import internet.market.model.ShoppingCart;
import internet.market.model.User;
import internet.market.service.ProductService;
import internet.market.service.ShoppingCartService;
import internet.market.service.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.market");
    private ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService = (ShoppingCartService) injector
            .getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Product classicGuitar = new Product("Classic guitar Yamaha С40", 3500);
        Product acousticGuitar = new Product("Acoustic guitar Cort AD810 BKS", 3000);
        productService.create(classicGuitar);
        productService.create(acousticGuitar);
        User user1 = new User("Monica Geller", "turkey222", "678gkjx");
        user1.setRoles(Set.of(Role.of("USER")));
        userService.create(user1);
        shoppingCartService.create(new ShoppingCart(user1.getId()));
        User user3 = new User("Admin", "admin", "1");
        user3.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(user3);
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
