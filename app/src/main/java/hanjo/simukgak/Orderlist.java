package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jkj89 on 2016-11-29.
 */

public class Orderlist extends AppCompatActivity implements OrderlistListViewAdapter.ListBtnClickListener {

    static final int REQUEST_CODE = 1;

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
        ArrayList<String> productList;
        String[] values;

        fileManager = new FileManager(getApplicationContext(), "orderlist_info.txt");
        //TODO: 주문 메뉴에서 아이템 추가

        //fileManager.writeFile("참서리,2016.08.03,0,고추장불고기,5000,1,닭갈비,5000,2,초벌구이소,13000,3");
        //fileManager.writeFile("새천년,2016.11.30,0,보쌈대,20000,1,돼지고추장,6000,2");
        //fileManager.writeFile("치킨,2016.12.01,0,양념치킨,16000,1");
        fileValues = fileManager.readFile(); //company, date, 0, product1, price1, amount1, product2, price2, amount2,...

        for(int i = 0; i < fileValues.size(); i++)
        {
            productList = new ArrayList();
            values = fileValues.get(i).split(",");
            int[] priceList = new int[(values.length-3)/3];
            int[] amountList = new int[(values.length-3)/3];
            for(int j = 3; j < values.length; j = j + 3) {
                productList.add(values[j]);
                priceList[j/3 - 1] = Integer.parseInt(values[j+1]);
                amountList[j/3 - 1] = Integer.parseInt(values[j+2]);
            }
            adapter.addItem(values[0], values[1], Integer.parseInt(values[2]), productList, priceList, amountList); //
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fileManager.resetData();
        String data;
        for(int i = 0; i<adapter.getCount(); i++)
        {
            data = adapter.getItem(i).getCompany() + "," + adapter.getItem(i).getDate();
            if(adapter.getItem(i).getDutch())
                data = data + "," + "1";
            else
                data = data + "," + "0";
            for(int j = 0; j < adapter.getItem(i).getProductList().size(); j++) {
                data = data + "," + (adapter.getItem(i).getProductList()).get(j);
                data = data + "," + Integer.toString(adapter.getItem(i).getPrice(j));
                data = data + "," + Integer.toString(adapter.getItem(i).getAmount(j));
            }
            fileManager.writeFile(data);
        }
    }

    public void onClickSort(View view)
    {
        adapter.sortItemByDate();
    }

    @Override
    public void onListBtnClick(int position, View v) {
        switch(v.getId()) {
            case R.id.dutchButton:
                if(!adapter.getItem(position).getDutch()) {
                    Intent intent = new Intent(getApplicationContext(), CreateDutch.class);
                    int n = position;
                    intent.putExtra("company", adapter.getItem(n).getCompany());
                    intent.putExtra("product", adapter.getItem(n).getProductList());
                    intent.putExtra("price", adapter.getItem(n).getPriceArr());
                    intent.putExtra("amount", adapter.getItem(n).getAmountArr());
                    intent.putExtra("date", adapter.getItem(n).getDate());
                    intent.putExtra("index", n);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "이미 더치페이를 했습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //MainActivity에서 부여한 번호표를 비교
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) { //세컨드 액티비티에서 이 값을 반환하는 코드가 동작 됐을때
                Toast.makeText(getApplicationContext(), "Dutch Pay Added", Toast.LENGTH_SHORT).show();
                int n = intent.getExtras().getInt("index");
                adapter.getItem(n).setDutch(true);
            }
        }
    }
}
