package internet.market.dao.jdbc;

import internet.market.dao.UserDao;
import internet.market.exceptions.DataProcessingException;
import internet.market.lib.Dao;
import internet.market.model.Role;
import internet.market.model.User;
import internet.market.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE user_login = ? AND deleted = false";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getUserInformationFromDataBase(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user with login = "
                    + login, e);
        }
        if (user != null) {
            user.setRoles(getRolesOfUser(user.getId()));
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User create(User item) {
        String query = "INSERT INTO users(user_name, user_login, user_password, salt) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            statement.setString(2, item.getLogin());
            statement.setString(3, item.getPassword());
            statement.setBytes(4, item.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                item.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add user", e);
        }
        setRoles(item);
        return item;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ? AND deleted = false";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getUserInformationFromDataBase(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user with id = "
                    + id, e);
        }
        if (user != null) {
            user.setRoles(getRolesOfUser(id));
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users WHERE deleted = false";
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUserInformationFromDataBase(resultSet);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users", e);
        }

        for (User user : userList) {
            user.setRoles(getRolesOfUser(user.getId()));
        }
        return userList;
    }

    @Override
    public User update(User item) {
        String query = "UPDATE users SET user_name = ?, user_login = ?, "
                + "user_password = ?, salt = ? "
                + "WHERE user_id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, item.getName());
            statement.setString(2, item.getLogin());
            statement.setString(3, item.getPassword());
            statement.setBytes(4, item.getSalt());
            statement.setLong(5, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user with id = "
                    + item.getId(), e);
        }
        deleteRoles(item.getId());
        setRoles(item);
        return item;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE users SET deleted = true WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user with id = "
                    + id, e);
        }
    }

    private User getUserInformationFromDataBase(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String userName = resultSet.getString("user_name");
        String login = resultSet.getString("user_login");
        String password = resultSet.getString("user_password");
        byte [] bytes = resultSet.getBytes("salt");
        User user = new User(userName, login, password);
        user.setId(userId);
        user.setSalt(bytes);
        return user;
    }

    private Set<Role> getRolesOfUser(Long id) {
        String query = "SELECT * FROM roles INNER JOIN users_roles "
                + "ON roles.role_id = users_roles.role_id "
                + "WHERE users_roles.user_id = ?";
        Set<Role> userRoles = new HashSet<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long roleId = resultSet.getLong("role_id");
                String roleName = resultSet.getString("role_name");
                Role role = Role.of(roleName);
                role.setId(roleId);
                userRoles.add(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get roles of user with id = "
                    + id, e);
        }
        return userRoles;
    }

    private void setRoles(User user) {
        String query = "INSERT INTO users_roles (user_id, role_id)"
                + " VALUES (?, (SELECT role_id FROM roles WHERE role_name = ?))";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Role role : user.getRoles()) {
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, String.valueOf(role.getRoleName()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set user roles for user with id = "
                    + user.getId(), e);
        }
    }

    private void deleteRoles(Long userId) {
        String query = "DELETE FROM users_roles WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete roles of user with id = "
                    + userId, e);
        }
    }
}
