package internet.market.security;

import internet.market.exceptions.AuthenticationException;
import internet.market.lib.Inject;
import internet.market.lib.Service;
import internet.market.model.User;
import internet.market.service.UserService;
import internet.market.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = userService.findByLogin(login)
                .orElseThrow(() ->
                        new AuthenticationException("Wrong password or login"));
        if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
            return user;
        }
        throw new AuthenticationException("Wrong password or login");
    }
}
