package hanjo.simukgak;

import java.io.Serializable;

/**
 * Created by jkj89 on 2016-11-30.
 */

public class listViewselorder implements Serializable{
    //private Drawable iconDrawable ;
    private int priceStr ;
    private String nameStr;
    private int dateYear ;
    private int dateMonth ;
    private int dateDay ;
    private int state;
    private String place;
    private String phone;

    /*public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }*/
    public void setPrice(int price) {
        priceStr = price ;
    }
    public void setName(String name) {
        nameStr = name ;
    }
    public void setDate(String date) { //형식 0000.00.00
        dateYear = Integer.parseInt(date.substring(0, 4)) ;
        dateMonth = Integer.parseInt(date.substring(5, 7)) ;
        dateDay = Integer.parseInt(date.substring(8, 10)) ;
    }
    public void setState(int state1){state=state1;}
    public void setPlace(String place1){place=place1;}
    public void setPhone(String phone1){phone=phone1;}

    /*public Drawable getIcon() {
        return this.iconDrawable ;
    }*/
    public int getPrice() {
        return priceStr ;
    }
    public String getName() {
        return nameStr ;
    }
    public String getDate() {
        String dateStr;
        dateStr = Integer.toString(dateYear) + "." + Integer.toString(dateMonth) + "." + Integer.toString(dateDay) ;
        return dateStr ;
    }
    public int getDateYear() { return dateYear ; }
    public int getDateMonth() { return dateMonth ; }
    public int getDateDay() { return dateDay ; }
    public int getState(){return state;}
    public String getPlace(){return place;}
    public String getPhone(){return phone;}
}
