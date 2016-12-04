package hanjo.simukgak;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jkj89 on 2016-12-01.
 */

public class selordercomp extends AppCompatActivity implements selorder_ListViewAdapter.ListBtnClickListener {

    static final int REQUEST_CODE = 1;
    final selorder_ListViewAdapter adapter = new selorder_ListViewAdapter(this, R.layout.selcomplet, this);
    FileManager fileManager;

    //TODO: 로컬 데이터 저장
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selorder);
        final ListView listview ;

        listview = (ListView) findViewById(R.id.orderlistview);
        listview.setAdapter(adapter);

        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.bg2);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        });
        fileManager = new FileManager(getApplicationContext(), "selcomp.txt");
        ArrayList<String> fileValues;
        ArrayList<String> productList;
        String[] values;
        fileValues = fileManager.readFile();
        for(int i = 0; i < fileValues.size(); i++)
        {
            productList = new ArrayList<>();
            Log.d("selordercomp", fileValues.get(i));
            values = fileValues.get(i).split(",");
            int[] priceList = new int[(values.length-4)/3];
            int[] amountList = new int[(values.length-4)/3];
            for(int j = 4; j < values.length; j = j + 3) {
                Log.d("selordercomp", values[j] + "," + values[j+1] + "," + values[j+2]);
                productList.add(values[j]);
                priceList[j/3 - 1] = Integer.parseInt(values[j+1]);
                amountList[j/3 - 1] = Integer.parseInt(values[j+2]);
            }
            adapter.addItem(values[0], values[1], values[2], values[3], productList, priceList, amountList); //
        }

        //default 아이템 추가. price, name, date,phone
        /*adapter.addItem("5000", "2고추장 불고기", "2016.10.03.10:20","010-1111-2222","공학관","3") ;
        adapter.addItem("5000", "2참치마요", "2016.10.10.01:30","010-3333-4444","공학관","3") ;
        adapter.addItem("5000", "2참치마요", "2016.10.09.11:09","010-1111-3333","공학관","3") ;
        adapter.addItem("7000", "2불고기", "2016.10.01.23:59","010-5555-4444","공학관","3") ;*/
        adapter.sortItemByDate();

        Button wait = (Button)findViewById(R.id.waiting) ;
        Button GD = (Button)findViewById(R.id.GetandDel) ;
        Button complet = (Button)findViewById(R.id.complet) ;

        wait.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(selordercomp.this, selorderwait.class));
            }
        }) ;

        GD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(selordercomp.this, selorderdel.class));
            }
        });
        complet.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(selordercomp.this, selordercomp.class));
            }
        });

    }
    public void onListBtnClick(int position, View v){
        switch (v.getId()) {
            case R.id.item:
                item(position);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

    }
    public void item(final int position)
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(selordercomp.this);
        alert_confirm.setMessage(adapter.getlist(position));
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }
}
