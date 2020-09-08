package internet.market.service;

import internet.market.model.User;
import java.util.List;

public interface UserService {
    User create(User user);

    User get(Long id);

    List<User> getAll();

    User update(User user);

    boolean delete(Long id);
}
