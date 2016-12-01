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
import java.util.ArrayList;

public class order_last extends AppCompatActivity implements order_ListViewAdapter2.ListBtnClickListener{

    private TextView total_price_View ;
    private int total_price_int;
    private ArrayList<ListViewItem> itemList;
    private order_ListViewAdapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_last);


        Intent intent = getIntent();
        itemList = (ArrayList<ListViewItem>) intent.getSerializableExtra("order");

        Log.d("order_last", String.valueOf(itemList.size()));

        adapter = new order_ListViewAdapter2(this,R.layout.order_item2,this);
        adapter.setList(itemList);

        ListView list = (ListView) findViewById(R.id.k);
        list.setAdapter(adapter);

         final TextView total_price_View =(TextView) findViewById(R.id.total_price);
         int total_price_int = 0;
        for (int i = 0; i < itemList.size(); i++) {
            total_price_int += (itemList.get(i).getcount() * itemList.get(i).getPrice());
        }

        total_price_View.setText("총 가격: " + total_price_int);
        LinearLayout mlayout =(LinearLayout)findViewById(R.id.activity_order_last);

        mlayout.setOnTouchListener(new LinearLayout.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent m){
                adapter.setTotal_price();
                total_price_View.invalidate();
                total_price_View.setText("총 가격: "+adapter.getTotal_price());
                return true;
            }
        });
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
public void send_order() {

}
}
