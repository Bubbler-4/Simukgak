package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static hanjo.simukgak.R.id.listview;

public class order3 extends AppCompatActivity /*implements CustomExpandableListViewAdapter.ListBtnClickListener*/{
public CustomExpandableListViewAdapter adapter;
    public ExpandableListView expandableListView;
    public HashMap<String, ArrayList<ListViewItem>> category_itemList;
    public ArrayList<String> category;
    public ArrayList<order_ListViewAdapter> child_adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order3);

        child_adapterList = new ArrayList<order_ListViewAdapter>();
        category = new ArrayList<String>();
        order_ListViewAdapter temp0 =new order_ListViewAdapter();
        temp0.setStoreName("돈까스류");
        temp0.addItem("마미돈까스",6000);
        temp0.addItem("치킨까스",6500);
        temp0.addItem("생선까스",6000);
        temp0.addItem("새우까스",6500);
        temp0.addItem("떡갈비",7000);
        category.add(temp0.getStoreName());
        child_adapterList.add(temp0);

        order_ListViewAdapter temp1 = new order_ListViewAdapter();
        temp1.setStoreName("도시락류");
        temp1.addItem("치킨마요 도시락",6000);
        temp1.addItem("제육 도시락",6000);
        temp1.addItem("탕수육 정식",7500);
        category.add(temp1.getStoreName());
        child_adapterList.add(temp1);

        order_ListViewAdapter temp2 = new order_ListViewAdapter();
        temp2.setStoreName("식사류");
        temp2.addItem("김치국",6500);
        temp2.addItem("육개장",6000);
        temp2.addItem("제육덮밥",5000);
        category.add(temp2.getStoreName());
        child_adapterList.add(temp2);

category_itemList = new HashMap<String,ArrayList<ListViewItem>>();
        for(int i=0;i<3;i++) {
            category_itemList.put(category.get(i),child_adapterList.get(i).getList());
        }

        expandableListView = (ExpandableListView) findViewById(R.id.expandablelist);

        adapter = new CustomExpandableListViewAdapter();
        Intent intent = getIntent();
        adapter.setStoreName(intent.getStringExtra("StoreName"));
        adapter.setmContext(this);
        adapter.setmChildHashMap(category_itemList);
        adapter.setmParentList(category);

        expandableListView.setAdapter(adapter);

    }
    public void send_item(View v)
    {
        order_ListViewAdapter temp = new order_ListViewAdapter();

        for(int i=0;i<adapter.getGroupCount();i++)
         {
            for(int j=0;j<adapter.getChildrenCount(i);j++)
            {
                if(adapter.getChild(i,j).getchecked()==true)
                {
                    temp.addItem(adapter.getChild(i,j));
                }
             }
    }
        Intent intent = new Intent(order3.this, order_last.class);
        intent.putExtra("order", temp.getList());
        intent.putExtra("StoreName",adapter.getStoreName());
        startActivity(intent);
    }
   /* @Override
    public void onListBtnClick(int position, View v) {
        //Toast.makeText(this,""+adapter.get,Toast.LENGTH_LONG).show();
        Toast.makeText(this,""+position,Toast.LENGTH_LONG).show();
    }*/

}
