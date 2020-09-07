package internet.market.service.impl;

import internet.market.dao.UserDao;
import internet.market.lib.Inject;
import internet.market.lib.Service;
import internet.market.model.User;
import internet.market.service.ShoppingCartService;
import internet.market.service.UserService;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        shoppingCartService.delete(shoppingCartService.getByUserId(id));
        return userDao.delete(id);
    }
}
