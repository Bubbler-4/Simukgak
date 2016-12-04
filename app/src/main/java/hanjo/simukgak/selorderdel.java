package hanjo.simukgak;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by jkj89 on 2016-12-01.
 */

public class selorderdel extends AppCompatActivity implements selorder_ListViewAdapter.ListBtnClickListener {

    static final int REQUEST_CODE = 1;
    final selorder_ListViewAdapter adapter = new selorder_ListViewAdapter(this, R.layout.seldel, this);
    FileManager fileManager;
    FileManager writeManager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //TODO: 로컬 데이터 저장
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selorder);
        final ListView listview;

        listview = (ListView) findViewById(R.id.orderlistview);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        });
        fileManager = new FileManager(getApplicationContext(), "seldel.txt");
        ArrayList<String> fileValues;
        ArrayList<String> productList;
        String[] values;
        fileValues = fileManager.readFile();
        for(int i = 0; i < fileValues.size(); i++)
        {
            productList = new ArrayList<>();
            values = fileValues.get(i).split(",");
            int[] priceList = new int[(values.length-4)/3];
            int[] amountList = new int[(values.length-4)/3];
            for(int j = 4; j < values.length; j = j + 3) {
                productList.add(values[j]);
                priceList[(j-4)/3 ] = Integer.parseInt(values[j+1]);
                amountList[(j-4)/3 ] = Integer.parseInt(values[j+2]);
            }
            adapter.addItem(values[0], values[1], values[2], values[3], productList, priceList, amountList); //
        }

        //default 아이템 추가. price, name, date,phone
        /*
        adapter.addItem("5000", "1고추장 불고기", "2016.10.03.14:30", "010-1111-2222","공학관","1");
        adapter.addItem("5000", "1참치마요", "2016.10.10.11:30", "010-3333-4444" ,"공학관","1");
        adapter.addItem("5000", "1참치마요", "2016.10.09.18:32", "010-1111-3333","공학관","1");
        adapter.addItem("7000", "1불고기", "2016.10.01.19:22", "010-5555-4444","공학관","1");*/
        adapter.sortItemByDate();

        Button wait = (Button) findViewById(R.id.waiting);
        Button GD = (Button) findViewById(R.id.GetandDel);
        Button complet = (Button) findViewById(R.id.complet);

        wait.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(selorderdel.this, selorderwait.class));
            }
        });


        /*
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;
                //Drawable iconDrawable = item.getIcon() ;
                Toast.makeText(getApplicationContext(), titleStr, Toast.LENGTH_SHORT).show();
            }
        }) ;*/

        GD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selorderdel.this, selorderdel.class));

            }
        });
        complet.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selorderdel.this, selordercomp.class));

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


    }

    @Override
    public void onListBtnClick(int position, View v) {
        switch (v.getId()) {
            case R.id.startButton:
                start(position);
                break;
            case R.id.departButtonButton:
                depart(position);
                break;
            case R.id.item:
                item(position);
            default:
                break;
        }
    }
    public void item(final int position)
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(selorderdel.this);
        alert_confirm.setMessage(adapter.getlist(position));
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }
    public void start(final int position) {
        Log.d("state", String.format(Locale.KOREA, "%d", adapter.getstate(position)));
        if (adapter.getstate(position)==1) {
            Log.d("sel", "!");
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(selorderdel.this);
            alert_confirm.setMessage("출발하십니까?").setCancelable(false).setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.stateup(position);
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
        else{
            Toast.makeText(getApplicationContext(), "이미 출발했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void depart(final int position) {
        if (adapter.getstate(position)==2) {
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(selorderdel.this);
            alert_confirm.setMessage("도착하셨습니까?").setCancelable(false).setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.stateup(position);
                            writeManager=new FileManager(getApplicationContext(),"selcomp.txt");
                            writeManager.writeFile(adapter.getPhone(position) + "," + adapter.getPlace(position) + "," +
                                    "3"+adapter.getDate(position) + "," +adapter.getall(position));
                            adapter.deleteItem(position);
                            adapter.notifyDataSetChanged();
                            fileManager.resetData();
                            int i;
                            for(i=0;i<adapter.getCount();i++) {
                                fileManager.writeFile(adapter.getPhone(i) + "," + adapter.getPlace(i) + "," +
                                        "2"+adapter.getDate(i) + "," +adapter.getall(i));
                            }

                            //TODO: 구매자에게 메시지 전송
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
        else
        {
            Toast.makeText(getApplicationContext(), "출발하지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
