package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class order_last extends AppCompatActivity implements order_ListViewAdapter2.ListBtnClickListener{

    private TextView total_price_View ;
    private int total_price_int;
    private ArrayList<ListViewItem> itemList;
    private order_ListViewAdapter2 adapter;
    private FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_last);

        Intent intent = getIntent();
        itemList = (ArrayList<ListViewItem>) intent.getSerializableExtra("order");

        adapter = new order_ListViewAdapter2(this,R.layout.order_item2,this);
        adapter.setStoreName(intent.getStringExtra("StoreName"));

        adapter.setList(itemList);

        ListView list = (ListView) findViewById(R.id.k);
        list.setAdapter(adapter);

        fileManager = new FileManager(getApplicationContext(), "orderlist_info.txt");

         final TextView total_price_View =(TextView) findViewById(R.id.total_price);
         int total_price_int = 0;
        for (int i = 0; i < itemList.size(); i++) {
            total_price_int += (itemList.get(i).getcount() * itemList.get(i).getPrice());
        }

        total_price_View.setText("총 가격: " + total_price_int);
       /* LinearLayout mlayout =(LinearLayout)findViewById(R.id.activity_order_last);

        mlayout.setOnTouchListener(new LinearLayout.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent m){
                adapter.setTotal_price();
                total_price_View.invalidate();
                total_price_View.setText("총 가격: "+adapter.getTotal_price());
                return true;
            }
        });*/
    }
@Override
public void onListBtnClick(int position, View v)
{
    switch(v.getId()) {
        case R.id.btn_minus:
            adapter.count_minus(position);
            adapter.notifyDataSetInvalidated();
            break;
        case R.id.btn_plus:
            adapter.count_plus(position);
            adapter.notifyDataSetInvalidated();
            break;
        default:
            break;
    }
    TextView t =(TextView)findViewById(R.id.total_price);
    t.setText("총 가격:"+ adapter.getTotal_price());

}
public void send_order(View v)
{
    // 현재시간을 msec 으로 구한다.
    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd");
    // nowDate 변수에 값을 저장한다.
    String formatDate = sdfNow.format(date);


    String data;
    data = adapter.getStoreName() + "," + formatDate + ",0";
    for(int j = 0; j < adapter.getCount(); j++) {
        data = data + "," + (adapter.getItem(j).getTitle());
        data = data + "," + adapter.getItem(j).getPrice();
        data = data + "," + adapter.getItem(j).getcount();
    }
    fileManager.writeFile(data);

}
}
