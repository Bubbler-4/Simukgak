package hanjo.simukgak;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class order_ListViewAdapter2 extends BaseAdapter implements Serializable,View.OnClickListener {

    //버튼 클릭 이벤트를 위한 Listener 인터페이스 정의
    public interface ListBtnClickListener{
        void onListBtnClick(int position, View v);
    }
    int resourceId;
    private ListBtnClickListener listBtnClickListener;
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
    private int total_price;
    TextView count;

    public order_ListViewAdapter2(Context context, int resource,  ListBtnClickListener clickListener) {
   // super(context, resource,list);

        this.resourceId=resource;
        this.listBtnClickListener =clickListener;
    }

    public void setTotal_price()
    {   total_price=0;
        for(int i=0;i<listViewItemList.size();i++) {
            total_price +=(listViewItemList.get(i).getPrice() * listViewItemList.get(i).getcount());

        }
    }

    public int getTotal_price(){return total_price;}

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.order_item2, parent, false);

        }


        TextView titleTextView = (TextView) convertView.findViewById(R.id.order_productText2);
        TextView descTextView = (TextView) convertView.findViewById(R.id.order_priceText2);
        count = (TextView) convertView.findViewById(R.id.count_text);

        Button minus = (Button) convertView.findViewById(R.id.btn_minus);
        minus.setTag(position);
        minus.setOnClickListener(this);

        Button plus = (Button) convertView.findViewById(R.id.btn_plus);
        plus.setTag(position);
        plus.setOnClickListener(this);

         ListViewItem listViewItem = listViewItemList.get(position);

        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText("" + listViewItem.getPrice());
        count.setText("" + listViewItem.getcount());

        /*View.OnClickListener listenerplus = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewItemList.get(position).setcount(listViewItemList.get(position).getcount() + 1);
                count.setText(""+listViewItemList.get(position).getcount());
                setTotal_price();
            }
        };
        plus.setOnClickListener(listenerplus);
        View.OnClickListener listenerminus = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listViewItemList.get(position).getcount() == 0) {

                } else {
                    listViewItemList.get(position).setcount(listViewItemList.get(position).getcount() - 1);
                    count.setText(""+listViewItemList.get(position).getcount());
                    setTotal_price();

                }
            }
        };
        minus.setOnClickListener(listenerminus);
        */
        return convertView;
    }

    public void onClick(View v) {
        if(this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag(),v);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ListViewItem getItem(int position) {
        return listViewItemList.get(position);
    }

    public ArrayList<ListViewItem> getList() {
        return listViewItemList;
    }

    public void setList(ArrayList<ListViewItem> list) {
        listViewItemList = list;
    }

    public void addItem(ListViewItem i) {
        listViewItemList.add(i);
    }

    public void addItem(String title, int desc) {
        ListViewItem item = new ListViewItem();
        item.setTitle(title);
        item.setPrice(desc);
        item.setcount(1);
        listViewItemList.add(item);
    }
    public void count_plus(int position)
    {
        listViewItemList.get(position).setcount(listViewItemList.get(position).getcount() + 1);
        count.setText(""+listViewItemList.get(position).getcount());
        setTotal_price();
    }
    public void count_minus(int position)
    {
        if (listViewItemList.get(position).getcount() == 0) {

        } else {
            listViewItemList.get(position).setcount(listViewItemList.get(position).getcount() - 1);
            count.setText(""+listViewItemList.get(position).getcount());
            setTotal_price();

        }
    }

}
