package internet.market.service.impl;

import internet.market.dao.UserDao;
import internet.market.lib.Inject;
import internet.market.lib.Service;
import internet.market.model.User;
import internet.market.service.UserService;
import internet.market.util.HashUtil;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        byte [] salt = HashUtil.getSalt();
        String saltedPassword = HashUtil.hashPassword(user.getPassword(), salt);
        user.setPassword(saltedPassword);
        user.setSalt(salt);
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
        return userDao.delete(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
