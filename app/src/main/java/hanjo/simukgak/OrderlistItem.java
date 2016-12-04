package hanjo.simukgak;

import java.util.ArrayList;

/**
 * Created by Kwon Ohhyun on 2016-11-30.
 */

public class OrderlistItem {
    private String companyStr ;
    private ArrayList<String> productList;
    private int[] priceList;
    private int[] amountList;

    private int dateYear ;
    private int dateMonth ;
    private int dateDay ;
    private int dateHour;
    private int dateMinute;

    private boolean dutch;

    public void setCompany(String company) {
        companyStr = company ;
    }
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
    public void setDutch(boolean tf) {dutch = tf;}

    public String getCompany() { return companyStr ; }
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
    public int[] getAmountArr() {return amountList;}
    public int getTotAmount() {
        int total = 0;
        for(int i : amountList)
            total = total + i;
        return total;
    }

    public String getPrintDate() {
        String dateStr;
        dateStr = Integer.toString(dateYear) + "." + Integer.toString(dateMonth) + "." + Integer.toString(dateDay) + " " + Integer.toString(dateHour) + ":" + Integer.toString(dateMinute) ;
        return dateStr ;
    }

    public String getDate() {
        String dateStr;
        dateStr = Integer.toString(dateYear) + "." + Integer.toString(dateMonth) + "." + Integer.toString(dateDay) + "." + Integer.toString(dateHour) + "." + Integer.toString(dateMinute) ;
        return dateStr ;
    }

    public int getDateYear() { return dateYear ; }
    public int getDateMonth() { return dateMonth ; }
    public int getDateDay() { return dateDay ; }
    public int getDateHour() {return dateHour;}
    public int getDateMinute() {return dateMinute;}

    public boolean getDutch() {return dutch;}
}
