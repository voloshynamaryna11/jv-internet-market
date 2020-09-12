package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.service.ShoppingCartService;
import internet.market.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.market");
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService = (ShoppingCartService) injector
            .getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idUnit = req.getParameter("id");
        Long id = Long.valueOf(idUnit);
        shoppingCartService.delete(shoppingCartService.getByUserId(id));
        userService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/user/all");
    }
}
