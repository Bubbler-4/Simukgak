package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class order_last extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_last);

        Intent intent = getIntent();
        ArrayList<ListViewItem> itemList = (ArrayList<ListViewItem>) intent.getSerializableExtra("order");

        Log.d("order_last", String.valueOf(itemList.size()));

        order_ListViewAdapter2 adapter = new order_ListViewAdapter2();
        adapter.setList(itemList);

        ListView list = (ListView) findViewById(R.id.k);
        list.setAdapter(adapter);

        int total_price_int =5000;
        TextView total_price_View =(TextView) findViewById(R.id.total_price);
        total_price_View.setText("총 가격: "+total_price_int);

    }
}
