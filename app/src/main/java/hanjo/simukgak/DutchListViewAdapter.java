package hanjo.simukgak;

/**
 * Created by Kwon Ohhyun on 2016-11-27.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class DutchListViewAdapter extends BaseAdapter implements View.OnClickListener {

    public interface ListBtnClickListener {
        void onListBtnClick(int position, View v);
    }

    private int resourceId;
    private ListBtnClickListener listBtnClickListener;
    private int listSortStatus = 0;

    // ListViewAdapter의 생성자
    public DutchListViewAdapter(Context context, int resource, ListBtnClickListener clickListener) {
        //super(context, resource);

        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
    }

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    // Adapter에 사용되는 데이터의 개수를 리턴.
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.listview_item*/, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.productText);
        TextView priceTextView = (TextView) convertView.findViewById(R.id.priceText);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameText);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateText);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        priceTextView.setText(String.format(Locale.KOREA, "%d원", listViewItem.getPrice()));
        nameTextView.setText(listViewItem.getName());
        dateTextView.setText(listViewItem.getDate());

        //delete button
        Button deleteButton = (Button) convertView.findViewById(R.id.deleteButton);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(this);

        Button askButton = (Button) convertView.findViewById(R.id.askButton);
        askButton.setTag(position);
        askButton.setOnClickListener(this);

        return convertView;
    }

    public void onClick(View v) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int) v.getTag(), v);
        }
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴.
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public ListViewItem getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String title, String price, String name, String date) {
        ListViewItem item = new ListViewItem();

        //item.setIcon(icon);
        item.setTitle(title);
        item.setPrice(Integer.parseInt(price));
        item.setName(name);
        item.setDate(date);

        listViewItemList.add(item);
    }

    //아이템 제거
    public void deleteItem(int position) {
        listViewItemList.remove(position);
    }

    public int deleteCommonItem(int position) {
        String name = listViewItemList.get(position).getName();
        int count = 0;
        for (int i = 0; i < getCount(); i++) {
            if (name.compareTo(listViewItemList.get(i).getName()) == 0) {
                listViewItemList.remove(i);
                count ++;
                i--;
            }
        }
        return count;
    }

    public void sortItemByDate() {
        Comparator<ListViewItem> noAsc = new Comparator<ListViewItem>() {
            @Override
            public int compare(ListViewItem item1, ListViewItem item2) {
                int ret = 0;
                if (listSortStatus == -2 || listSortStatus >= 0) {
                    if (item1.getDateYear() < item2.getDateYear())
                        ret = -1;
                    else if (item1.getDateYear() > item2.getDateYear())
                        ret = 0;
                    else {
                        if (item1.getDateMonth() < item2.getDateMonth())
                            ret = -1;
                        else if (item1.getDateMonth() > item2.getDateMonth())
                            ret = 0;
                        else {
                            if (item1.getDateDay() < item2.getDateDay())
                                ret = -1;
                            else if (item1.getDateDay() > item2.getDateDay())
                                ret = 0;
                            else
                                ret = -1;
                        }
                    }
                } else if (listSortStatus == -1 || listSortStatus >= 0) {
                    if (item1.getDateYear() > item2.getDateYear())
                        ret = -1;
                    else if (item1.getDateYear() < item2.getDateYear())
                        ret = 0;
                    else {
                        if (item1.getDateMonth() > item2.getDateMonth())
                            ret = -1;
                        else if (item1.getDateMonth() < item2.getDateMonth())
                            ret = 0;
                        else {
                            if (item1.getDateDay() > item2.getDateDay())
                                ret = -1;
                            else if (item1.getDateDay() < item2.getDateDay())
                                ret = 0;
                            else
                                ret = -1;
                        }
                    }
                }

                return ret;
            }
        };

        Collections.sort(listViewItemList, noAsc);
        if (listSortStatus == -2 || listSortStatus >= 0) listSortStatus = -1;
        else if (listSortStatus == -1 || listSortStatus >= 0) listSortStatus = -2;
        notifyDataSetChanged();
    }

    public void sortItemByName() {
        Comparator<ListViewItem> noAsc = new Comparator<ListViewItem>() {
            @Override
            public int compare(ListViewItem item1, ListViewItem item2) {
                int ret = 0;
                if (listSortStatus == 2 || listSortStatus <= 0) {
                    if (item1.getName().compareTo(item2.getName()) < 0)
                        ret = -1;
                    else if (item1.getName().compareTo(item2.getName()) > 0)
                        ret = 0;
                    else
                        ret = 1;
                } else if (listSortStatus == 1 || listSortStatus <= 0) {
                    if (item1.getName().compareTo(item2.getName()) > 0)
                        ret = -1;
                    else if (item1.getName().compareTo(item2.getName()) < 0)
                        ret = 0;
                    else
                        ret = 1;
                }

                return ret;
            }
        };

        Collections.sort(listViewItemList, noAsc);
        if (listSortStatus == 2 || listSortStatus <= 0) listSortStatus = 1;
        else if (listSortStatus == 1 || listSortStatus <= 0) listSortStatus = 2;
        notifyDataSetChanged();
    }

    public String getTotalPrice(int position) {
        int total = 0;
        for (int i = 0; i < getCount(); i++) {
            if (listViewItemList.get(position).getName().compareTo(listViewItemList.get(i).getName()) == 0) {
                total = total + listViewItemList.get(i).getPrice();
            }
        }

        return Integer.toString(total);
    }

    public String getTotalPrice()
    {
        int total = 0;
        for (int i = 0; i < getCount(); i++) {
            total = total + listViewItemList.get(i).getPrice();
        }
        return Integer.toString(total);
    }
}
