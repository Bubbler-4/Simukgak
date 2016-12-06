package hanjo.simukgak;

import java.util.ArrayList;

/**
 * Created by Kwon Ohhyun on 2016-12-04.
 */

public class ManageSellItem {
    private ArrayList<String> productList;
    private int[] priceList;
    private int[] amountList;

    private int dateYear ;
    private int dateMonth ;
    private int dateDay;
    private int dateHour;
    private int dateMinute;

    private String phoneNum;
    private String location;


    public void setProduct(ArrayList<String> product) { productList = product;}
    public void setPrice(int[] price) {
        priceList = new int[price.length];
        System.arraycopy(price, 0, priceList, 0, price.length);
    }
    public void setAmount(int[] amount) {
        amountList = new int[amount.length];
        System.arraycopy(amount, 0, amountList, 0, amount.length);
    }
    public void setDate(String date) { //형식 0000.00.00
        String[] str = date.split("\\.");
        dateYear = Integer.parseInt(str[0]) ;
        dateMonth = Integer.parseInt(str[1]) ;
        dateDay = Integer.parseInt(str[2]) ;
        dateHour = Integer.parseInt(str[3]);
        dateMinute = Integer.parseInt(str[4]);
    }
    public void setPhoneNum(String str) {phoneNum = str;}
    public void setLocation(String str) {location = str;}

    public ArrayList<String> getProductList() { return productList;}

    public int getPrice(int n) {
        return priceList[n];
    }
    public int[] getPriceArr() {return priceList;}
    public int getTotPrice() {
        int total = 0;
        for(int i = 0; i < priceList.length; i++)
            total = total + priceList[i]*amountList[i];
        return total;
    }

    public int getAmount(int n) {
        return amountList[n];
    }
    public int getTotAmount() {
        int total = 0;
        for(int i : amountList)
            total = total + i;
        return total;
    }

    public String getDate() {
        String dateStr;
        dateStr = Integer.toString(dateYear) + "." + Integer.toString(dateMonth) + "." + Integer.toString(dateDay) + " " + Integer.toString(dateHour) + ":" + Integer.toString(dateMinute);
        return dateStr ;
    }
    public String getPrintDate() {
        String dateStr;
        dateStr = Integer.toString(dateYear) + "." + Integer.toString(dateMonth) + "." + Integer.toString(dateDay) + "." + Integer.toString(dateHour) + "." + Integer.toString(dateMinute) ;
        return dateStr ;
    }
    public int getDateYear() { return dateYear ; }
    public int getDateMonth() { return dateMonth ; }
    public int getDateDay() { return dateDay ; }
    public int getDateHour() {return dateHour;}
    public int getDateMinute() {return dateMinute;}
    public String getPhoneNum() {return  phoneNum;}
    public String getLocation() {return location;}
}
