package internet.market.security;

import internet.market.exceptions.AuthenticationException;
import internet.market.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
