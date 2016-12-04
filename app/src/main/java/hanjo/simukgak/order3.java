package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static hanjo.simukgak.R.id.listview;

public class order3 extends AppCompatActivity /*implements CustomExpandableListViewAdapter.ListBtnClickListener*/{
    static int REQUEST_ACT =1234;
    public CustomExpandableListViewAdapter adapter;
    public ExpandableListView expandableListView;
    public HashMap<String, ArrayList<ListViewItem>> category_itemList;
    public ArrayList<String> category;
    public ArrayList<order_ListViewAdapter> child_adapterList;
    public TextView StoreName;
    public TextView state_And_time;
    public TextView Memo;
    public TextView call_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order3);

        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.bg2);

        StoreName =(TextView)findViewById(R.id.StoreName);
        state_And_time =(TextView)findViewById(R.id.state_And_time);
        Memo =(TextView)findViewById(R.id.memo);
        call_number=(TextView)findViewById(R.id.call_number);

        child_adapterList = new ArrayList<order_ListViewAdapter>();
        category = new ArrayList<String>();
        order_ListViewAdapter temp0 =new order_ListViewAdapter();
        temp0.setStoreName("   "+"돈까스류");
        temp0.addItem("마미돈까스",6000);
        temp0.addItem("치킨까스",6500);
        temp0.addItem("생선까스",6000);
        temp0.addItem("새우까스",6500);
        temp0.addItem("떡갈비",7000);
        category.add(temp0.getStoreName());
        child_adapterList.add(temp0);

        order_ListViewAdapter temp1 = new order_ListViewAdapter();
        temp1.setStoreName("   "+"도시락류");
        temp1.addItem("치킨마요 도시락",6000);
        temp1.addItem("제육 도시락",6000);
        temp1.addItem("탕수육 정식",7500);
        category.add(temp1.getStoreName());
        child_adapterList.add(temp1);

        order_ListViewAdapter temp2 = new order_ListViewAdapter();
        temp2.setStoreName("   "+"식사류");
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

        StoreName.setText(adapter.getStoreName());
        state_And_time.setText("열림"+" 영업 시간"+"9시~23시");
        call_number.setText("010-8874-0587");
        Memo.setText("*분식하나도 주문되며, 오전에도 주문가능!");
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
        startActivityForResult(intent,REQUEST_ACT);
    }
   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
   {
       super.onActivityResult(requestCode, resultCode, data);
       if(requestCode==REQUEST_ACT)
       {
           if(resultCode==RESULT_OK)
           {
               setResult(RESULT_OK);
               finish();
           }
       }
   }


}
