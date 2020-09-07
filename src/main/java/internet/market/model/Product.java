package internet.market.model;

public class Product {
    private Long id;
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", price=" + price
                + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        if (id != null) {
            result = prime * result + id.hashCode();
        }
        if (name != null) {
            result = prime * result + name.hashCode();
        }

        return result * prime + (int) price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj.getClass().equals(Product.class)) {
            Product product = (Product) obj;
            return id != null ? id.equals(product.id)
                    : product.id == null
                    && name != null ? name.equalsIgnoreCase(product.name)
                    : product.name == null
                    && price == product.price;
        }
        return false;
    }
}
