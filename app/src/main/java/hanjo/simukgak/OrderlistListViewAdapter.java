package hanjo.simukgak;


/**
 * Created by jkj89 on 2016-11-29.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class OrderlistListViewAdapter extends BaseAdapter implements View.OnClickListener{

    public interface ListBtnClickListener {
        void onListBtnClick(int position, View v) ;
    }

    private int resourceId;
    private int listSortStatus = 0;
    private ListBtnClickListener listBtnClickListener;
    private ArrayList<OrderlistItem> listViewItemList = new ArrayList<>() ; // Adapter에 추가된 데이터를 저장하기 위한 ArrayList

    // ListViewAdapter의 생성자
    public OrderlistListViewAdapter(Context context, int resource, ListBtnClickListener clickListener) {

        this.listBtnClickListener = clickListener;
        this.resourceId = resource;
    }

    public void onClick(View v) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int) v.getTag(), v);
        }
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.orderview*/, parent, false);
        }

        TextView companyTextView = (TextView) convertView.findViewById(R.id.CompanyText) ;
        TextView productTextView = (TextView) convertView.findViewById(R.id.productText) ;
        TextView priceTextView = (TextView) convertView.findViewById(R.id.priceText) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateText) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        OrderlistItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        companyTextView.setText(listViewItem.getCompany());
        String products = listViewItem.getProductList().get(0) + " 외 " + Integer.toString(listViewItem.getTotAmount()-1) + "개";
        productTextView.setText(products);
        priceTextView.setText(Integer.toString(listViewItem.getTotPrice()) + "원");
        dateTextView.setText(listViewItem.getDate());

        Button deleteButton = (Button) convertView.findViewById(R.id.dutchButton);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(this);

        return convertView;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public OrderlistItem getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String company, String date, int dutch, ArrayList<String> product, int[] price, int[] amount) {
        OrderlistItem item = new OrderlistItem();

        item.setCompany(company);
        item.setProduct(product);
        item.setPrice(price);
        item.setAmount(amount);
        item.setDate(date);
        if(dutch == 0)
            item.setDutch(false);
        else
            item.setDutch(true);

        listViewItemList.add(item);
    }

    public void deleteItem(int position) {listViewItemList.remove(position);}

    public void sortItemByDate()
    {
        Comparator<OrderlistItem> noAsc = new Comparator<OrderlistItem>() {
            @Override
            public int compare(OrderlistItem item1, OrderlistItem item2) {
                int ret = 0;
                if(listSortStatus == -2 || listSortStatus >= 0) {
                    if (item1.getDateYear() < item2.getDateYear())
                        ret = -1;
                    else if (item1.getDateYear() > item2.getDateYear())
                        ret = 0;
                    else
                    {
                        if (item1.getDateMonth() < item2.getDateMonth())
                            ret = -1;
                        else if (item1.getDateMonth() > item2.getDateMonth())
                            ret = 0;
                        else
                        {
                            if (item1.getDateDay() < item2.getDateDay())
                                ret = -1;
                            else if (item1.getDateDay() > item2.getDateDay())
                                ret = 0;
                            else
                                ret = -1;
                        }
                    }
                }
                else if(listSortStatus == -1 || listSortStatus >= 0)
                {
                    if (item1.getDateYear() > item2.getDateYear())
                        ret = -1;
                    else if (item1.getDateYear() < item2.getDateYear())
                        ret = 0;
                    else
                    {
                        if (item1.getDateMonth() > item2.getDateMonth())
                            ret = -1;
                        else if (item1.getDateMonth() < item2.getDateMonth())
                            ret = 0;
                        else
                        {
                            if (item1.getDateDay() > item2.getDateDay())
                                ret = -1;
                            else if (item1.getDateDay() < item2.getDateDay())
                                ret = 0;
                            else
                                ret = -1;
                        }
                    }
                }

                return ret ;
            }
        } ;

        Collections.sort(listViewItemList, noAsc) ;
        if(listSortStatus == -2 || listSortStatus >= 0) listSortStatus = -1 ;
        else if(listSortStatus == -1 || listSortStatus >= 0) listSortStatus = -2 ;
        notifyDataSetChanged() ;
    }

    /**
     *
     * @param savedDate 데이터가 저장된 시간
     * @return 3개월이 지났을 경우 true, 지나지 않았을 경우 false를 반환
     */

    public boolean checkDate(String savedDate) {
        // 현재시간을 msec 으로 구한다.
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd");
        // nowDate 변수에 값을 저장한다.
        String formatDate = sdfNow.format(date);

        String[] savedDates = savedDate.split("\\.");
        String[] currentDates = formatDate.split("\\.");

        if(Integer.parseInt(currentDates[0]) == Integer.parseInt(savedDates[0]))
            if(Integer.parseInt(currentDates[1]) - Integer.parseInt(savedDates[1]) >= 3)
                return true;
        else if(Integer.parseInt(currentDates[0]) - Integer.parseInt(savedDates[0]) == 1)
                if(Integer.parseInt(currentDates[1]) + 12 - Integer.parseInt(savedDates[1]) >= 3)
                    return true;
        else if(Integer.parseInt(currentDates[0]) - Integer.parseInt(savedDates[0]) > 1)
                    return true;

        return false;

    }

}
