package hanjo.simukgak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageSellRank extends AppCompatActivity implements ManageSellRankListViewAdapter.ListBtnClickListener {

    static final int REQUEST_CODE = 1;

    final ManageSellRankListViewAdapter adapter = new ManageSellRankListViewAdapter(this, R.layout.manage_sell_rank_listview_item, this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sell_rank);
        final ListView listview;

        listview = (ListView) findViewById(R.id.list1);
        listview.setAdapter(adapter);

        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.bg2);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        });

        ArrayList<String> fileValues;
        String[] values;

        FileManager fileManager;

        fileManager = new FileManager(getApplicationContext(), "selcomp.txt");
        fileValues = fileManager.readFile(); //date, location, phoneNum, product1, price1, amount1, product2, price2, amount2,...

        for(int i = 0; i < fileValues.size(); i++)
        {
            values = fileValues.get(i).split(",");
            for(int j = 4; j < values.length; j = j + 3) {
                if(adapter.checkProduct(values[j]) == -1)
                {
                    adapter.addItem(values[j], Integer.parseInt(values[j+1]), Integer.parseInt(values[j+2]));
                }
                else
                {
                    adapter.getItem(adapter.checkProduct(values[j])).increaseNum(Integer.parseInt(values[j+2]));
                }

            }
        }
        adapter.sortItemByPrice();

    }

    @Override
    public void onListBtnClick(int position, View v) {
        switch(v.getId()) {
            default:
                break;
        }
    }
}
