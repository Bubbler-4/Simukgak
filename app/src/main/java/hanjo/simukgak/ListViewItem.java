package hanjo.simukgak;

import java.io.Serializable;

/**
 * Created by Kwon Ohhyun on 2016-11-27.
 */

public class ListViewItem implements Serializable{
    //private Drawable iconDrawable ;
    private String titleStr ;
    private int priceStr ;
    private String nameStr;
    private int dateYear ;
    private int dateMonth ;
    private int dateDay ;
    private int count;

    /*public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }*/
    public void setcount(int countset){count =countset;}
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setPrice(int price) {
        priceStr = price ;
    }
    public void setName(String name) {
        nameStr = name ;
    }
    public void setDate(String date) { //형식 0000.00.00
        String[] str = date.split("\\.");
        dateYear = Integer.parseInt(str[0]) ;
        dateMonth = Integer.parseInt(str[1]) ;
        dateDay = Integer.parseInt(str[2]) ;
    }

    /*public Drawable getIcon() {
        return this.iconDrawable ;
    }*/
    public int getcount(){return count;}
    public String getTitle() { return titleStr ; }
    public int getPrice() {
        return priceStr ;
    }
    public String getName() {
        return nameStr ;
    }
    public String getDate() {
        String dateStr;
        dateStr = Integer.toString(dateYear) + "." + Integer.toString(dateMonth) + "." + Integer.toString(dateDay) ;
        return dateStr;
    }
    public int getDateYear() { return dateYear ; }
    public int getDateMonth() { return dateMonth ; }
    public int getDateDay() { return dateDay ; }
}
