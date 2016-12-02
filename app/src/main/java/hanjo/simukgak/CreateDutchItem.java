package hanjo.simukgak;

import java.util.ArrayList;

/**
 * Created by Kwon Ohhyun on 2016-11-29.
 */

public class CreateDutchItem {
    private ArrayList<String> productList;
    private int[] priceList;
    private int price;
    private int productIndex;
    private String Name;

    public void setProductList(ArrayList<String> strList) {productList = strList;}
    public void setProductIndex(int n) {productIndex = n;}
    public void setPriceList(int[] ints) {priceList = ints;}
    public void setPrice(int _price) {price = _price;}
    public void setName(String name) {Name = name;}
    public void setProduct(String product) {
        productIndex = 0;
        for(int i = 0; i<productList.size(); i++)
        {
            if(product.equals(productList.get(i))) {
                productIndex = i;
                price = priceList[i];
                break;
            }
        }
    }

    public ArrayList<String> getProductList() {return productList;}
    public int getProductIndex() {return productIndex;}
    public String getName() {return Name;}
    public String getProduct() {return productList.get(productIndex);}
    public int getPrice() {return price;}


}
