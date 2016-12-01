package hanjo.simukgak;
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
/**
 * Created by jkj89 on 2016-11-30.
 */

public class selorder_ListViewAdapter extends BaseAdapter implements View.OnClickListener {
    public interface ListBtnClickListener {
        void onListBtnClick(int position, View v) ;
    }

    int resourceId;
    private ListBtnClickListener listBtnClickListener;
    private int listSortStatus = 0;

    // ListViewAdapter의 생성자
    public selorder_ListViewAdapter(Context context,int resource,  ListBtnClickListener clickListener) {
        //super(context, resource);
        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
    }

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<listViewselorder> listViewItemList = new ArrayList<listViewselorder>() ;

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listViewselorder" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.selwait*/, parent, false);
        }

        TextView placeTextView = (TextView) convertView.findViewById(R.id.place) ;
        TextView itemTextView = (TextView) convertView.findViewById(R.id.item) ;
        TextView numberTextView = (TextView) convertView.findViewById(R.id.number) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        listViewselorder listsel = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getIcon());
        placeTextView.setText(listsel.getPlace());
        itemTextView.setText(listsel.getName());
        numberTextView.setText(listsel.getPhone());
        if(listsel.getState()==1||listsel.getState()==2)
        {
            TextView stateTextView=(TextView) convertView.findViewById(R.id.statetext);
            if(listsel.getState()==1)
                stateTextView.setText("배송 대기");
            else
                stateTextView.setText("배송중");
        }
        if(listsel.getState()==0) {
            Button okButton = (Button) convertView.findViewById(R.id.OkButton);
            okButton.setTag(position);
            okButton.setOnClickListener(this);
            Button cancleButton = (Button) convertView.findViewById(R.id.NOButton);
            cancleButton.setTag(position);
            cancleButton.setOnClickListener(this);
        }
        if(listsel.getState()==1||listsel.getState()==2) {
            Button goButton = (Button) convertView.findViewById(R.id.startButton);
            goButton.setTag(position);
            goButton.setOnClickListener(this);
            Button departButton = (Button) convertView.findViewById(R.id.departButtonButton);
            departButton.setTag(position);
            departButton.setOnClickListener(this);
        }
        return convertView;
    }

    public void onClick(View v) {
        if(this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag(), v);
        }
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String price, String name, String date,String phone, int state) {
        listViewselorder item = new listViewselorder();

        //item.setIcon(icon);
        item.setPrice(Integer.parseInt(price));
        item.setName(name);
        item.setDate(date);
        item.setPhone(phone);
        item.setState(state);

        listViewItemList.add(item);
    }
    public void stateup(int position)
    {
       listViewItemList.get(position).setState(listViewItemList.get(position).getState()+1);
    }
    public int getstate(int position)
    {
        return listViewItemList.get(position).getState();
    }

    //아이템 제거
    public void deleteItem(int position)
    {
        listViewItemList.remove(position);
    }

    //TODO: 분류 시 우선순위 방식
    public void sortItemByDate()
    {
        Comparator <listViewselorder> noAsc = new Comparator<listViewselorder>() {
            @Override
            public int compare(listViewselorder item1, listViewselorder item2) {
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

    public void sortItemByName()
    {
        Comparator<listViewselorder> noAsc = new Comparator<listViewselorder>() {
            @Override
            public int compare(listViewselorder item1, listViewselorder item2) {
                int ret = 0;
                if(listSortStatus == 2 || listSortStatus <= 0) {
                    if (item1.getName().compareTo(item2.getName()) < 0)
                        ret = -1;
                    else if (item1.getName().compareTo(item2.getName()) > 0)
                        ret = 0;
                    else
                        ret = 1;
                }
                else if(listSortStatus == 1 || listSortStatus <= 0)
                {
                    if (item1.getName().compareTo(item2.getName()) > 0)
                        ret = -1;
                    else if (item1.getName().compareTo(item2.getName()) < 0)
                        ret = 0;
                    else
                        ret = 1;
                }

                return ret ;
            }
        } ;

        Collections.sort(listViewItemList, noAsc) ;
        if(listSortStatus == 2 || listSortStatus <= 0) listSortStatus = 1 ;
        else if(listSortStatus == 1 || listSortStatus <= 0) listSortStatus = 2 ;
        notifyDataSetChanged() ;
    }

    public String getTotalPrice(int position) {
        int total = 0;
        int i;
        for(i=0; i<getCount(); i++)
        {
            if(listViewItemList.get(position).getName().compareTo(listViewItemList.get(i).getName()) == 0)
            {
                total = total + listViewItemList.get(i).getPrice();
            }
        }

        return Integer.toString(total);
    }

    //FIXME: MainActivity에서 ListViewAdapter class로부터 직접 불러오기 실패
    public String getName(int position) { return listViewItemList.get(position).getName(); }
}
