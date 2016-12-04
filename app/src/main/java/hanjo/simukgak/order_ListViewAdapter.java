package hanjo.simukgak;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;

public class order_ListViewAdapter extends BaseAdapter implements Serializable,View.OnClickListener {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
    private CheckBox selected;
    int resourceId;
    private ListBtnClickListener listBtnClickListener;
    private String StoreName;
    private String FoodType;

    public interface ListBtnClickListener{
        void onListBtnClick(int position, View v);
    }
    public order_ListViewAdapter()
    {

    }

    public order_ListViewAdapter(Context context, int resource,  ListBtnClickListener clickListener) {
        // super(context, resource,list);

        this.resourceId=resource;
        this.listBtnClickListener =clickListener;
    }
    @Override
    public int getCount(){
        return listViewItemList.size();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.order_item, parent, false);

        }
        final View temp = convertView;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.order_productText);
        TextView descTextView = (TextView) convertView.findViewById(R.id.order_priceText);
        selected = (CheckBox) convertView.findViewById(R.id.checkBox1);

        final ListViewItem listViewItem = listViewItemList.get(position);
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(""+listViewItem.getPrice());

       // selected.setChecked(false);
        selected.setTag(position);
        selected.setOnClickListener(this);

        //selected.setChecked(((ListView)parent).isItemChecked(position));


       /* View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        selected.setOnClickListener(listener);*/

        return convertView;
    }
    public void onClick(View v) {
        if(this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag(),v);
        }
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public ListViewItem getItem(int position){
        return listViewItemList.get(position);
    }
    public ArrayList<ListViewItem> getList() { return listViewItemList; }
    public void setList(ArrayList<ListViewItem> list) { listViewItemList = list; }
    public void addItem(ListViewItem i){
        listViewItemList.add(i);
    }
    public void addItem( String title, int desc) {
        ListViewItem item = new ListViewItem();
        item.setTitle(title);
        item.setPrice(desc);
        item.setcount(1);
        item.setChecked(false);
        listViewItemList.add(item);
    }

    public void setStoreName(String name){ StoreName= name;}
    public String getStoreName(){return StoreName;}

}