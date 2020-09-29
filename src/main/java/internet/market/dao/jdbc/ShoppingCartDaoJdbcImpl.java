package internet.market.dao.jdbc;

import internet.market.dao.ShoppingCartDao;
import internet.market.exceptions.DataProcessingException;
import internet.market.lib.Dao;
import internet.market.model.Product;
import internet.market.model.ShoppingCart;
import internet.market.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String query = "SELECT * FROM shopping_carts WHERE user_id = ? AND deleted = false";
        ShoppingCart shoppingCart = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                shoppingCart = getShoppingCartInformationFromDataBase(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart of user with id = "
                    + userId, e);
        }
        if (shoppingCart != null) {
            shoppingCart.setProducts(getProductsFromCart(shoppingCart.getId()));
        }
        return Optional.ofNullable(shoppingCart);
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE shopping_carts SET deleted = true WHERE shopping_cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete shoppingCart with id = "
                    + id, e);
        }
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return delete(shoppingCart.getId());
    }

    @Override
    public ShoppingCart create(ShoppingCart item) {
        String query = "INSERT INTO shopping_carts (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, item.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                item.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add shopping cart", e);
        }
        addProductsToShoppingCart(item.getProducts(), item.getId());
        return item;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE "
                + "deleted = false AND shopping_cart_id = ?";
        ShoppingCart shoppingCart = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                shoppingCart = getShoppingCartInformationFromDataBase(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shoppingCart with id = " + id, e);
        }
        if (shoppingCart != null) {
            shoppingCart.setProducts(getProductsFromCart(id));
        }
        return Optional.ofNullable(shoppingCart);
    }

    @Override
    public List<ShoppingCart> getAll() {
        List<ShoppingCart> allCarts = new ArrayList<>();
        String query = "SELECT * FROM shopping_carts WHERE deleted = false";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allCarts.add(getShoppingCartInformationFromDataBase(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all shoppingCarts", e);
        }
        for (ShoppingCart cart : allCarts) {
            cart.setProducts(getProductsFromCart(cart.getId()));
        }
        return allCarts;
    }

    @Override
    public ShoppingCart update(ShoppingCart item) {
        String query = "UPDATE shopping_carts SET user_id = ? "
                + "WHERE shopping_cart_id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, item.getUserId());
            statement.setLong(2, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update shoppingCart with id = "
                    + item.getId(), e);
        }
        deleteProductsFromCart(item.getId());
        addProductsToShoppingCart(item.getProducts(), item.getId());
        return item;
    }

    private ShoppingCart getShoppingCartInformationFromDataBase(ResultSet resultSet)
            throws SQLException {
        Long cartId = resultSet.getLong("shopping_cart_id");
        Long userId = resultSet.getLong("user_id");
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        shoppingCart.setId(cartId);
        return shoppingCart;
    }

    private List<Product> getProductsFromCart(Long id) {
        String query = "SELECT product_id, name, price FROM products JOIN shopping_cart_products"
                + "ON products.product_id = shopping_cart_products.product_id"
                + " WHERE products.deleted = false AND shopping_cart_id = ?";
        List<Product> productList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String productName = resultSet.getString("name");
                double productPrice = resultSet.getDouble("price");
                Product product = new Product(productName, productPrice);
                product.setId(productId);
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get products from "
                    + "shoppingCart with id = " + id, e);
        }
        return productList;
    }

    private void addProductsToShoppingCart(List<Product> products,
                                           Long cartId) {
        String query = "INSERT INTO shopping_cart_products"
                + " (shopping_cart_id, product_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : products) {
                statement.setLong(1, cartId);
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add products to shoppingCart with id = "
                    + cartId, e);
        }
    }

    private void deleteProductsFromCart(Long cartId) {
        String query = "DELETE FROM shopping_cart_products WHERE shopping_cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products from shoppingCart with id = "
                    + cartId, e);
        }
    }
}
