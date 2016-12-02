package hanjo.simukgak;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by jkj89 on 2016-12-01.
 */

public class selorderdel extends AppCompatActivity implements selorder_ListViewAdapter.ListBtnClickListener {

    static final int REQUEST_CODE = 1;
    final selorder_ListViewAdapter adapter = new selorder_ListViewAdapter(this, R.layout.seldel, this);
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

        //default 아이템 추가. price, name, date,phone
        adapter.addItem("5000", "1고추장 불고기", "2016.10.03.14:30", "010-1111-2222", 1);
        adapter.addItem("5000", "1참치마요", "2016.10.10.11:30", "010-3333-4444", 1);
        adapter.addItem("5000", "1참치마요", "2016.10.09.18:32", "010-1111-3333", 1);
        adapter.addItem("7000", "1불고기", "2016.10.01.19:22", "010-5555-4444", 1);
        adapter.sortItemByDate();

        Button sort = (Button) findViewById(R.id.namesort);
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

        sort.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.sortItemByName();
            }
        });

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

        //MainActivity에서 부여한 번호표를 비교
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) { //세컨드 액티비티에서 이 값을 반환하는 코드가 동작 됐을때
                String productname = intent.getExtras().getString("name"); //인자로 구분된 값을 불러오는 행위를 하고
                String productprice = intent.getExtras().getString("price");
                String phone = intent.getExtras().getString("phone");
                String dateStr = intent.getExtras().getString("date");
                adapter.addItem(productprice, productname, dateStr, phone, 1); //아이템 추가
                adapter.notifyDataSetChanged(); //데이터 수정 알림
                Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();

            }
        }
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
            default:
                break;
        }
    }

    public void start(final int position) {
        if (adapter.getstate(position)==1) {
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
                            adapter.deleteItem(position);
                            adapter.notifyDataSetChanged();
                            //TODO: 다음단계의 리스트로 정보 전송
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
            Toast.makeText(getApplicationContext(), "도착하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
