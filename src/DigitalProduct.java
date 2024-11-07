public class DigitalProduct extends Product {
    private double fileSize;

    public DigitalProduct(int productId, String name, double price, int quantity, double fileSize) {
        super(productId, name, price, quantity);
        this.fileSize = fileSize;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", File Size: " + fileSize + " MB";
    }
}