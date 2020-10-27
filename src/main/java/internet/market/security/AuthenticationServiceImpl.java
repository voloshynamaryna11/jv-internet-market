package internet.market.security;

import internet.market.exceptions.AuthenticationException;
import internet.market.lib.Inject;
import internet.market.lib.Service;
import internet.market.model.User;
import internet.market.service.UserService;
import internet.market.util.HashUtil;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> user = userService.findByLogin(login);
        if (user.isPresent() && user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            return user.get();
        }
        throw new AuthenticationException("Wrong password or login");
    }
}
