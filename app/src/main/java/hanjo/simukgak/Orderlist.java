package hanjo.simukgak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jkj89 on 2016-11-29.
 */

public class Orderlist extends AppCompatActivity implements OrderlistListViewAdapter.ListBtnClickListener {

    static final int REQUEST_CODE = 11;

    final OrderlistListViewAdapter adapter = new OrderlistListViewAdapter(this, R.layout.orderlist_listview_item, this);

    private FileManager fileManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        final ListView listview;

        listview = (ListView) findViewById(R.id.listorder);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        });

        ArrayList<String> fileValues;
        String[] values;

        fileManager = new FileManager(getApplicationContext(), "orderlist_info.txt");
        //default 아이템 추가. Product, Price, Name, Date
        fileManager.writeFile("맘스터치,5000,Alice,2016.10.13");
        fileManager.writeFile("버거킹,5000,James,2016.10.20");
        fileManager.writeFile("새천년,7000,Bob,2016.09.20");

        fileValues = fileManager.readFile();
        for(int i = 0; i < fileValues.size(); i++)
        {
            values = fileValues.get(i).split(",");
            adapter.addItem(values[0], values[1], values[2], values[3]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fileManager.resetData();
        String data;
        for(int i = 0; i<adapter.getCount(); i++)
        {
            data = adapter.getItem(i).getTitle() + "," + Integer.toString(adapter.getItem(i).getPrice()) + "," + adapter.getItem(i).getName() + "," + adapter.getItem(i).getDate();
            fileManager.writeFile(data);
        }
    }

    public void onClickSort(View view)
    {
        adapter.sortItemByDate();
    }

    @Override
    public void onListBtnClick(int position, View v) {}
}
