package hanjo.simukgak;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by jkj89 on 2016-11-30.
 */

public class listViewselorder implements Serializable{
    //private Drawable iconDrawable ;
    private ArrayList<String> productList;
    private int[] priceList;
    private int[] amountList;
    private int dateYear ;
    private int dateMonth ;
    private int dateDay ;
    private int dateHour;
    private int dateMin;
    private int state;
    private String place;
    private String phone;
    private int count;

    /*public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }*/
    public void setProduct(ArrayList<String> product) { productList = product;}
    public void setPrice(int[] price) {
        priceList = new int[price.length];
        System.arraycopy(price, 0, priceList, 0, price.length);
    }
    public void setAmount(int[] amount) {
        amountList = new int[amount.length];
        System.arraycopy(amount, 0, amountList, 0, amount.length);
    }
    public void setDate(String date) { //형식 0000.00.00.00.00
        dateYear = Integer.parseInt(date.split("\\.")[0]) ;
        dateMonth = Integer.parseInt(date.split("\\.")[1]) ;
        dateDay = Integer.parseInt(date.split("\\.")[2]) ;
        dateHour=Integer.parseInt(date.split("\\.")[3]);
        dateMin=Integer.parseInt(date.split("\\.")[4]);
    }
    public void setState(int state1){state=state1;}
    public void setPlace(String place1){place=place1;}
    public void setPhone(String phone1){phone=phone1;}

    /*public Drawable getIcon() {
        return this.iconDrawable ;
    }*/
    public ArrayList<String> getProductList() { return productList;}

    public int getPrice(int n) {
        return priceList[n];
    }
    public int[] getPriceArr() {return priceList;}
    public int getTotPrice() {
        int total = 0;
        for(int i : priceList)
            total = total + i;
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
    public String getDate() {
        String dateStr;
        dateStr = Integer.toString(dateYear) + "." + Integer.toString(dateMonth) + "." + Integer.toString(dateDay)
                +"."+Integer.toString(dateHour)+"."+Integer.toString(dateMin) ;
        return dateStr ;
    }
    public int getDateYear() { return dateYear ; }
    public int getDateMonth() { return dateMonth ; }
    public int getDateDay() { return dateDay ; }
    public int getDateHour(){return dateHour;}
    public int getDateMin(){return dateMin;}
    public int getState(){return state;}
    public String getPlace(){return place;}
    public String getPhone(){return phone;}
}
