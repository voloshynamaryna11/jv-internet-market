package internet.market.dao.jdbc;

import internet.market.dao.ProductDao;
import internet.market.exceptions.DataProcessingException;
import internet.market.model.Product;
import internet.market.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoJdbcImpl implements ProductDao {

    @Override
    public Product create(Product item) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?) ";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                item.setId(resultSet.getLong(1));
            }
            return item;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add product with id = " + item.getId(), e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE deleted=false AND product_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getProductInformationFromDataBase(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find product with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products WHERE deleted=false";
        List<Product> productList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(getProductInformationFromDataBase(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all products", e);
        }
        return productList;
    }

    @Override
    public Product update(Product item) {
        String query = "UPDATE products SET name=?, price=? WHERE product_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setLong(3, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update product with id = " + item.getId(), e);
        }
        return item;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE products SET deleted=true WHERE product_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete product with id = " + id, e);
        }
    }

    private static Product getProductInformationFromDataBase(ResultSet resultSet)
            throws SQLException {
        Long productId = resultSet.getLong("product_id");
        String productName = resultSet.getString("name");
        double productPrice = resultSet.getDouble("price");
        Product product = new Product(productName, productPrice);
        product.setId(productId);
        return product;
    }
}
