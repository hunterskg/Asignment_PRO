public class DigitalProduct extends Product{
    private double size;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public DigitalProduct(int productId, String name, double price, int quantity, double size) {
        super(productId, name, price, quantity);
        this.size = size;
    }
    @Override
    public String getInfo(){
        return super.getInfo() + ", Size: " + size;
    }
}
