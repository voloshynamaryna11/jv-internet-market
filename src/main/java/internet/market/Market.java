package internet.market;

import internet.market.lib.Injector;
import internet.market.model.Product;
import internet.market.model.ShoppingCart;
import internet.market.model.User;
import internet.market.service.OrderService;
import internet.market.service.ProductService;
import internet.market.service.ShoppingCartService;
import internet.market.service.UserService;

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
        System.out.println(productService.getAll());

        ShoppingCartService shoppingCartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        ShoppingCart shoppingCart1 = new ShoppingCart(1L);
        shoppingCartService.create(shoppingCart1);
        shoppingCartService.addProduct(shoppingCart1, acousticGuitar);
        ShoppingCart shoppingCart2 = new ShoppingCart(2L);
        shoppingCartService.create(shoppingCart2);
        shoppingCartService.addProduct(shoppingCart2, sopranoUkulele);
        ShoppingCart shoppingCart3 = new ShoppingCart(3L);
        shoppingCartService.create(shoppingCart3);
        shoppingCartService.addProduct(shoppingCart3, electricGuitar);
        shoppingCartService.addProduct(shoppingCart3, acousticGuitar);
        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user1 = new User("Monica Geller", "thanksgivingTurkey777", "qwerty123");
        User user2 = new User("Chandler Bing", "sarcasm001", "password");
        User user3 = new User("Ross Geller", "pterodactyl505", "1234509876");
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        System.out.println(userService.get(1L));
        userService.delete(user1.getId());
        System.out.println(userService.getAll());
        User user4 = new User("Phoebe Buffet", "smellyCat123", "abc123");
        user4.setId(2L);
        userService.update(user4);
        System.out.println(userService.getAll());
        System.out.println(shoppingCartService.getByUserId(2L));
        System.out.println(shoppingCartService.getByUserId(3L));
        shoppingCartService.deleteProduct(shoppingCart3, electricGuitar);
        System.out.println(shoppingCartService.getByUserId(3L));
        shoppingCartService.clear(shoppingCartService.getByUserId(3L));
        System.out.println(shoppingCartService.getByUserId(3L));
        shoppingCartService.addProduct(shoppingCart3, electricGuitar);
        shoppingCartService.addProduct(shoppingCart3, acousticGuitar);
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCartService.getByUserId(3L));
        orderService.completeOrder(shoppingCartService.getByUserId(2L));
        System.out.println(orderService.getAll());
        System.out.println(orderService.getUserOrders(user3.getId()));
        orderService.delete(1L);
        System.out.println(orderService.getAll());
    }
}
