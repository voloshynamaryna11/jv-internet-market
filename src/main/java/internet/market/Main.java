package internet.market;

import internet.market.dao.ProductDao;
import internet.market.dao.jdbc.ProductDaoJdbcImpl;
import internet.market.model.Product;

public class Main {

    public static void main(String[] args) {
        ProductDao productDao = new ProductDaoJdbcImpl();
        Product product1 = new Product("mediator", 100);
        productDao.create(product1);
        System.out.println(productDao.get(product1.getId()));
        Product product2 = new Product("aF", 234);
        productDao.create(product2);
        product2.setPrice(12220);
        System.out.println(productDao.update(product2));
        productDao.delete(product1.getId());
        System.out.println(productDao.getAll());
    }
}
