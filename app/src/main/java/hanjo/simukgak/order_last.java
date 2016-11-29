package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class order_last extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_last);

        Intent intent = getIntent();
        ArrayList<ListViewItem> itemList = (ArrayList<ListViewItem>) intent.getSerializableExtra("order");

        Log.d("order_last", String.valueOf(itemList.size()));

        order_ListViewAdapter adapter = new order_ListViewAdapter();
        adapter.setList(itemList);

        ListView list = (ListView) findViewById(R.id.k);
        list.setAdapter(adapter);

    }
}
