
public class Product extends Element {
    private int stock;

    public Product(String name, int stock) {
        super(name);
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String getType() {
        return "Product";
    }

    @Override
    public String toString() {
        return "Product: " + getName() + ", Stock: " + stock;
    }
}
