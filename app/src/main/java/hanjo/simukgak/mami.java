package hanjo.simukgak;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class mami extends AppCompatActivity implements order_ListViewAdapter.ListBtnClickListener{
    private ListView listview;
    private order_ListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mami);
        final Button orderButton;
        final Button backButton;
        final CheckBox cb;
        final TextView T;

        orderButton = (Button) findViewById(R.id.order);
        backButton = (Button) findViewById(R.id.back);
        cb = (CheckBox) findViewById(R.id.checkBox1);
        T =(TextView) findViewById(R.id.test);

        Intent intent = getIntent();


        adapter = new order_ListViewAdapter(this,R.layout.order_item,this);

        adapter.setStoreName(intent.getStringExtra("StoreName"));

        listview = (ListView) findViewById(R.id.list12);
        listview.setAdapter(adapter);

        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        adapter.addItem("김치국", 6000);
        adapter.addItem("육개장", 6000);
        adapter.addItem("갈비탕", 6500);
        adapter.addItem("제육덮밥", 5000);
        adapter.addItem("닭갈비 덮밥", 5000);
        adapter.addItem("참치마요 덮밥", 6000);



    }
    public void orderk(View v)
    {
        order_ListViewAdapter temp= new order_ListViewAdapter();

        SparseBooleanArray checkedItem = listview.getCheckedItemPositions();

        for(int i=0;i<adapter.getList().size();i++)
        {
            if(checkedItem.get(i))
            {
                temp.addItem(adapter.getItem(i));
            }
        }
        Intent intent = new Intent(mami.this, order_last.class);
        intent.putExtra("order", temp.getList());
        intent.putExtra("StoreName",adapter.getStoreName());
        startActivity(intent);
    }

    public void check_on(View view) {
    }
    @Override
    public void onListBtnClick(int position, View v) {
        listview.setItemChecked(position,!(listview.isItemChecked(position)));

    }
}

