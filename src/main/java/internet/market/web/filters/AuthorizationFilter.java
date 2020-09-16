package internet.market.web.filters;

import internet.market.lib.Injector;
import internet.market.model.Role;
import internet.market.model.User;
import internet.market.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {
    private static final Injector injector = Injector.getInstance("internet.market");
    private static final String USER_ID = "user_Id";
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/user/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/complete", Set.of(Role.RoleName.USER));
        protectedUrls.put("/admin/products", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/orders", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/user/orders", Set.of(Role.RoleName.USER));
        protectedUrls.put("/cart/product/add", Set.of(Role.RoleName.USER));
        protectedUrls.put("/cart/product/all", Set.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String url = req.getServletPath();
        if (protectedUrls.get(url) == null) {
            filterChain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }
        if (isAuthorized(userService.get(userId), protectedUrls.get(url))) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, Set<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
