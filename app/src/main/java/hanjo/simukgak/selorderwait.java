package hanjo.simukgak;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jkj89 on 2016-12-01.
 */

public class selorderwait extends AppCompatActivity implements selorder_ListViewAdapter.ListBtnClickListener, Observer {

    static final int REQUEST_CODE = 1;
    final selorder_ListViewAdapter adapter = new selorder_ListViewAdapter(this, R.layout.selwait, this);
    FileManager fileManager;
    FileManager writeManager;
    static int l=1;

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


        fileManager = new FileManager(getApplicationContext(), "selwait.txt");
        ArrayList<String> fileValues;
        ArrayList<String> productList;
        String[] values;
        fileValues = fileManager.readFile();
        for(int i = 0; i < fileValues.size(); i++)
        {
            productList = new ArrayList<>();
            Log.d("selorderwait", fileValues.get(i));
            values = fileValues.get(i).split(",");
            int[] priceList = new int[(values.length-4)/3];
            int[] amountList = new int[(values.length-4)/3];
            for(int j = 4; j < values.length; j = j + 3) {
                Log.d("selorderwait", values[j] + "," + values[j+1] + "," + values[j+2]);
                productList.add(values[j]);
                priceList[(j-4)/3] = Integer.parseInt(values[j+1]);
                amountList[(j-4)/3] = Integer.parseInt(values[j+2]);
            }
            adapter.addItem(values[0], values[1], values[2], values[3], productList, priceList, amountList); //
        }

        adapter.sortItemByDate();

        Button wait = (Button)findViewById(R.id.waiting) ;
        Button GD = (Button)findViewById(R.id.GetandDel) ;
        Button complet = (Button)findViewById(R.id.complet) ;

        wait.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(selorderwait.this, selorderwait.class));
            }
        }) ;

        GD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(selorderwait.this, selorderdel.class));
            }
        });
        complet.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(selorderwait.this, selordercomp.class));

            }
        });

        SocketWrapper.object().deleteObservers();
        SocketWrapper.object().addObserver(this);
    }


    @Override
    public void onListBtnClick(int position, View v) {
        switch(v.getId()) {
            case R.id.NOButton:
                itemDelete(position);
                break;
            case R.id.OkButton:
                submit(position);
                break;
            case R.id.item_wait:
                item(position);
            default:
                break;
        }
    }
    public void item(final int position)
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(selorderwait.this);
        alert_confirm.setMessage(adapter.getlist(position));
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }
    public void itemDelete(final int position)
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(selorderwait.this);
        alert_confirm.setMessage("내역을 취소하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.deleteItem(position);
                        adapter.notifyDataSetChanged();
                        fileManager.resetData();
                        int i=0;
                        for(i=0;i<adapter.getCount();i++) {
                            fileManager.writeFile( adapter.getPhone(i) + "," + adapter.getPlace(i) + "," +
                                    "0"+adapter.getDate(i) + "," +adapter.getall(i));
                        }
                        Toast.makeText(getApplicationContext(), "취소되었습니다", Toast.LENGTH_SHORT).show();
                        //TODO:구매자에게 거절 전송
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'No'
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }
    public void submit(final int position)
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(selorderwait.this);
        alert_confirm.setMessage("주문을 수락하시겼습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.stateup(position);
                        writeManager=new FileManager(getApplicationContext(),"seldel.txt");
                        writeManager.writeFile(adapter.getPhone(position) + "," + adapter.getPlace(position) + "," +
                                "1,"+adapter.getDate(position) + "," +adapter.getall(position));
                        Toast.makeText(getApplicationContext(), "주문을 수락하였습니다", Toast.LENGTH_SHORT).show();
                        //TODO: 네트워크로 메시지 전송
                        adapter.deleteItem(position);
                        adapter.notifyDataSetChanged();
                        fileManager.resetData();
                        int i;
                        for(i=0;i<adapter.getCount();i++) {
                            fileManager.writeFile(adapter.getPhone(i) + "," + adapter.getPlace(i) + "," +
                                    "0,"+adapter.getDate(i) + "," +adapter.getall(position));
                        }
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        SocketWrapper sw = (SocketWrapper) o;
        String newOrder = sw.getNewOrder();

        fileManager = new FileManager(getApplicationContext(), "selwait.txt");
        ArrayList<String> productList;
        String[] values;
        productList = new ArrayList<>();
        values = newOrder.split(",");
        int[] priceList = new int[(values.length-4)/3];
        int[] amountList = new int[(values.length-4)/3];
        for(int j = 4; j < values.length; j = j + 3) {
            productList.add(values[j]);
            priceList[(j-4)/3] = Integer.parseInt(values[j+1]);
            amountList[(j-4)/3] = Integer.parseInt(values[j+2]);
        }

        final String value0 = values[0];
        final String value1 = values[1];
        final String value2 = values[2];
        final String value3 = values[3];
        final ArrayList<String> prodList = productList;
        final int[] priList = priceList;
        final int[] amtList = amountList;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addItem(value0, value1, value2, value3, prodList, priList, amtList);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
