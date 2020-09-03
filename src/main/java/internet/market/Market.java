package internet.market;

import internet.market.lib.Injector;
import internet.market.model.Product;
import internet.market.service.ProductService;

public class Market {
    private static Injector injector = Injector.getInstance("internet.market");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product classicGuitar = new Product("Classic guitar Yamaha С40", 3500);
        Product acousticGuitar = new Product("Acoustic guitar Cort AD810 BKS", 3000);
        Product electricGuitar = new Product("Electric guitar Jackson JS32T King", 10000);
        productService.create(classicGuitar);
        productService.create(acousticGuitar);
        productService.create(electricGuitar);
        Product sopranoUkulele = new Product("Soprano ukulele Parksons UK21C", 1500);
        sopranoUkulele.setId(classicGuitar.getId());
        System.out.println(productService.get(1L).getName());
        productService.update(sopranoUkulele);
        System.out.println(productService.get(1L).getName());
        productService.delete(classicGuitar.getId());
        System.out.println(productService.get(classicGuitar.getId()));
    }
}
