package hanjo.simukgak;

/**
 * Created by Kwon Ohhyun on 2016-11-27.
 */

public class ListViewItem {
    //private Drawable iconDrawable ;
    private String titleStr ;
    private int priceStr ;
    private String nameStr;
    private int dateYear ;
    private int dateMonth ;
    private int dateDay ;

    /*public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }*/
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setPrice(String price) {
        priceStr = Integer.parseInt(price) ;
    }
    public void setName(String name) {
        nameStr = name ;
    }
    public void setDate(String date) {
        dateYear = Integer.parseInt(date.substring(0, 4)) ;
        dateMonth = Integer.parseInt(date.substring(5, 7)) ;
        dateDay = Integer.parseInt(date.substring(8, 10)) ;
    }

    /*public Drawable getIcon() {
        return this.iconDrawable ;
    }*/
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
        return dateStr ;
    }
    public int getDateYear() { return dateYear ; }
    public int getDateMonth() { return dateMonth ; }
    public int getDateDay() { return dateDay ; }
}