package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ManageSell extends AppCompatActivity implements ManageSellListViewAdapter.ListBtnClickListener {

    static final int REQUEST_CODE = 1;

    final ManageSellListViewAdapter adapter = new ManageSellListViewAdapter(this, R.layout.manage_sell_listview_item, this);

    private FileManager fileManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sell);
        final ListView listview;

        listview = (ListView) findViewById(R.id.listorder);
        listview.setAdapter(adapter);

        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.bg2);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        });

        ArrayList<String> fileValues;
        ArrayList<String> productList;
        String[] values;

        fileManager = new FileManager(getApplicationContext(), "selcomp.txt");
        fileValues = fileManager.readFile(); //date, location, phoneNum, product1, price1, amount1, product2, price2, amount2,...

        for(int i = 0; i < fileValues.size(); i++)
        {
            productList = new ArrayList<>();
            values = fileValues.get(i).split(",");
            int[] priceList = new int[(values.length-4)/3];
            int[] amountList = new int[(values.length-4)/3];
            for(int j = 4; j < values.length; j = j + 3) {
                productList.add(values[j]);
                priceList[(j-4)/3] = Integer.parseInt(values[j+1]);
                amountList[(j-4)/3] = Integer.parseInt(values[j+2]);
            }
            adapter.addItem(productList, priceList, amountList, values[3], values[1], values[0]); //
        }
        int n = checkDue();
        if(n > 0)
            Toast.makeText(getApplicationContext(), String.format(Locale.KOREA, "오래된 %d개의 항목이 삭제되었습니다.", n), Toast.LENGTH_SHORT).show();
        adapter.sortItemByDate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fileManager.resetData();
        String data;
        for(int i = 0; i<adapter.getCount(); i++)
        {
            data = adapter.getItem(i).getPhoneNum() + "," + adapter.getItem(i).getLocation() + "," + adapter.getItem(i).getPrintDate() + "," + "3";
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

            }
        }
    }

    private int checkDue() {
        int count = 0;
        for(int i = 0; i<adapter.getCount(); i++) {
            if (adapter.checkDate(adapter.getItem(i).getDate())) {
                adapter.deleteItem(i);
                count++;
            }
        }
        return count;
    }
}
