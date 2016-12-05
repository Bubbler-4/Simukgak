package hanjo.simukgak;

/**
 * Created by Kwon Ohhyun on 2016-12-04.
 */

public class ManageMenuItem {
    private String product;
    private int price = 0;


    public void setProduct(String string) {product = string;}
    public void setPrice(int n) {price = n;}


    public String getProduct() {return product;}
    public int getPrice() {return price;}
}
