package internet.market.controllers;

import internet.market.lib.Injector;
import internet.market.model.Product;
import internet.market.service.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectProductDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.market");
    private ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Product classicGuitar = new Product("Classic guitar Yamaha С40", 3500);
        Product acousticGuitar = new Product("Acoustic guitar Cort AD810 BKS", 3000);
        productService.create(classicGuitar);
        productService.create(acousticGuitar);
        req.getRequestDispatcher("/WEB-INF/views/injectProductData.jsp").forward(req, resp);
    }
}
