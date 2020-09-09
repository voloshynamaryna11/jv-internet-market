package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.model.User;
import internet.market.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectUserDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.market");
    private UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user1 = new User("Monica Geller",
                "turkey222", "678gkjx");
        User user2 = new User("Ross Geller", "newyork200", "47871982735");
        userService.create(user1);
        userService.create(user2);
        req.getRequestDispatcher("/WEB-INF/views/injectUserData.jsp").forward(req, resp);
    }
}
