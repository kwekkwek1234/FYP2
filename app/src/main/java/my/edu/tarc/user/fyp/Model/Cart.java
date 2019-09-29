package my.edu.tarc.user.fyp.Model;

public class Cart {
    private String productId;
    private String productName;
    private int productAmount;
    private double itemsPrice;


    public Cart() {
    }

    public Cart(String productId, String productName, int productAmount, double itemsPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productAmount = productAmount;
        this.itemsPrice = itemsPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public double getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(double itemsPrice) {
        this.itemsPrice = itemsPrice;
    }
}
