package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.model.Product;
import internet.market.service.ProductService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageProductsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.market");
    private ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> allProducts = productService.getAll();
        req.setAttribute("products", allProducts);
        req.getRequestDispatcher("/WEB-INF/views/admin/allProducts.jsp").forward(req, resp);
    }
}
