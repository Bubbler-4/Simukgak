package hanjo.simukgak;

import java.util.ArrayList;

/**
 * Created by Kwon Ohhyun on 2016-11-30.
 */

public class OrderlistItem {
    private String companyStr ;
    private ArrayList<String> productList;
    private int[] priceArr;
    private int[] amountArr;
    private int dateYear ;
    private int dateMonth ;
    private int dateDay ;
    private boolean dutch = false;

    public void setCompany(String company) {
        companyStr = company ;
    }
    public void setProduct(ArrayList<String> product) { productList = product;}
    public void setPrice(int[] price) {
        priceArr = new int[price.length];
        for(int i = 0; i<price.length; i++)
            priceArr[i] = price[i] ;
    }
    public void setAmount(int[] amount)
    {
        amountArr = new int[amount.length];
        for(int i = 0; i<amount.length; i++)
            amountArr[i] = amount[i] ;
    }
    public void setDate(String date) { //형식 0000.00.00
        String[] str = date.split("\\.");
        dateYear = Integer.parseInt(str[0]) ;
        dateMonth = Integer.parseInt(str[1]) ;
        dateDay = Integer.parseInt(str[2]) ;
    }
    public void setDutch(boolean tf) {dutch = tf;}

    public String getCompany() { return companyStr ; }
    public ArrayList<String> getProductList() { return productList;}
    public int getPrice(int n) {
        return priceArr[n];
    }
    public int[] getPriceArr() {return priceArr;}
    public int getTotPrice() {
        int total = 0;
        for(int i = 0; i<priceArr.length; i++)
        {
            total = total + priceArr[i];
        }
        return total;
    }

    public int getAmount(int n) {
        return amountArr[n];
    }
    public int[] getAmountArr() {return amountArr;}
    public int getTotAmount() {
        int total = 0;
        for(int i = 0; i<amountArr.length; i++)
        {
            total = total + amountArr[i];
        }
        return total;
    }

    public String getDate() {
        String dateStr;
        dateStr = Integer.toString(dateYear) + "." + Integer.toString(dateMonth) + "." + Integer.toString(dateDay) ;
        return dateStr ;
    }
    public int getDateYear() { return dateYear ; }
    public int getDateMonth() { return dateMonth ; }
    public int getDateDay() { return dateDay ; }
    public boolean getDutch() {return dutch;}
}
