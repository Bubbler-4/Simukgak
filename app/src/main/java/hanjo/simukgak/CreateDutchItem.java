package hanjo.simukgak;

import java.util.ArrayList;

/**
 * Created by Kwon Ohhyun on 2016-11-29.
 */

public class CreateDutchItem {
    private ArrayList<String> productList;
    private int productIndex;
    private int[] priceList;
    private int price;
    private String Name;

    public void setProductList(ArrayList<String> strList) {productList = strList;}
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
    public void setProductIndex(int n) {productIndex = n;}
    public void setPriceList(int[] ints) {priceList = ints;}
    public void setPrice(int _price) {price = _price;}
    public void setName(String name) {Name = name;}


    public ArrayList<String> getProductList() {return productList;}
    public String getProduct() {return productList.get(productIndex);}
    public int getProductIndex() {return productIndex;}
    public int getPrice() {return price;}
    public String getName() {return Name;}


}
