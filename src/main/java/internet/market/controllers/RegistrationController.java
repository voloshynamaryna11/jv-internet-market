package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.model.User;
import internet.market.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.market");
    private UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("password-repeat");
        if (password.equals(passwordRepeat)) {
            User user = new User(name, login, password);
            userService.create(user);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "You entered different passwords");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
