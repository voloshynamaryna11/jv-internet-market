package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.model.User;
import internet.market.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.market");
    private UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<User> all = userService.getAll();
        req.setAttribute("users", all);
        req.getRequestDispatcher("/WEB-INF/views/users/allUsers.jsp").forward(req, resp);
    }
}
