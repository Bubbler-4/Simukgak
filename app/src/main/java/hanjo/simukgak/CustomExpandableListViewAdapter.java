package hanjo.simukgak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rkaehddbs on 2016-12-02.
 */

public class CustomExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<String> mParentList;
    private ArrayList<ListViewItem> mChildList = new ArrayList<>();
    private HashMap<String, ArrayList<ListViewItem>> mChildHashMap;
    private String StoreName;
    //int resourceId;
    //private ListBtnClickListener listBtnClickListener;

    public CustomExpandableListViewAdapter(){}
    public CustomExpandableListViewAdapter(Context context, ArrayList<String> parentList, HashMap<String,ArrayList<ListViewItem>> childHashMap)
    {
        this.mContext =context;
        this.mParentList=parentList;
        this.mChildHashMap=childHashMap;
    }
    public interface ListBtnClickListener{
        void onListBtnClick(int position, View v);
    }
    /*public CustomExpandableListViewAdapter(Context context, int resource,  ListBtnClickListener clickListener) {
        // super(context, resource,list);

        this.resourceId=resource;
        this.listBtnClickListener =clickListener;
    }*/
    public void setmContext(Context context){mContext=context;}
    public void setmParentList(ArrayList<String> parentList){mParentList=parentList;}
    public void setmChildHashMap(HashMap<String,ArrayList<ListViewItem>> childHashMap){ mChildHashMap=childHashMap;}
    @Override
    public String getGroup(int groupPosition)
    {
        return mParentList.get(groupPosition);
    }
    @Override
    public long getGroupId(int groupPosition) { // ParentList의 position을 받아 long값으로 반환
        return groupPosition;
    }

    @Override
    public int getGroupCount(){
        return mParentList.size();
    }
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) { // ParentList의 View
        if(convertView == null){
            LayoutInflater groupInfla = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // ParentList의 layout 연결. root로 argument 중 parent를 받으며 root로 고정하지는 않음
            convertView = groupInfla.inflate(R.layout.order_category, parent, false);
        }

        // ParentList의 Layout 연결 후, 해당 layout 내 TextView를 연결
        TextView parentText = (TextView)convertView.findViewById(R.id.category);
        parentText.setText(""+getGroup(groupPosition));
        return convertView;
    }

    //여기서 부터 ChildListView

    @Override
    public ListViewItem getChild(int groupPosition, int childPosition) {
        return this.mChildHashMap.get(this.mParentList.get(groupPosition)).get(childPosition);

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mChildHashMap.get(this.mParentList.get(groupPosition)).size();

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) { // ChildList의 ID로 long 형 값을 반환
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        // ChildList의 View. 위 ParentList의 View를 얻을 때와 비슷하게 Layout 연결 후, layout 내 TextView, ImageView를 연결

        ListViewItem childData = (ListViewItem)getChild(groupPosition, childPosition);

        if(convertView == null){
            LayoutInflater childInfla = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = childInfla.inflate(R.layout.order_item, null);


        }
        TextView titleTextView = (TextView) convertView.findViewById(R.id.order_productText);
        TextView descTextView = (TextView) convertView.findViewById(R.id.order_priceText);
        CheckBox selected = (CheckBox) convertView.findViewById(R.id.checkBox1);

        /*selected.setTag(groupPosition*10+childPosition);
        selected.setOnClickListener(this);*/


       View.OnClickListener listener= new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                getChild(groupPosition,childPosition).setChecked(!getChild(groupPosition,childPosition).getchecked());
            }
        };

        selected.setOnClickListener(listener);

        titleTextView.setText(childData.getTitle());
        descTextView.setText(""+childData.getPrice());

        return convertView;

    }

    @Override
    public boolean hasStableIds() { return true; } // stable ID인지 boolean 값으로 반환

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; } // 선택여부를 boolean 값으로 반환

    /*public void onClick(View v) {
        if(this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag(),v);
        }
    }*/
    public void setStoreName(String name){ StoreName= name;}
    public String getStoreName(){return StoreName;}
}
