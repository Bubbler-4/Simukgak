package hanjo.simukgak;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by jkj89 on 2016-12-01.
 */

public class selorderwait extends AppCompatActivity implements selorder_ListViewAdapter.ListBtnClickListener {

    static final int REQUEST_CODE = 1;
    final selorder_ListViewAdapter adapter = new selorder_ListViewAdapter(this, R.layout.selwait, this);

    //TODO: 로컬 데이터 저장
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selorder);
        final ListView listview ;

        listview = (ListView) findViewById(R.id.orderlistview);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        });

        //default 아이템 추가. price, name, date,phone
        adapter.addItem("5000", "고추장 불고기", "2016.10.03","010-1111-2222",0) ;
        adapter.addItem("5000", "참치마요", "2016.10.10","010-3333-4444",0) ;
        adapter.addItem("5000", "참치마요", "2016.10.09","010-1111-3333",0) ;
        adapter.addItem("7000", "불고기", "2016.10.01","010-5555-4444",0) ;
        adapter.sortItemByDate();

        Button sort = (Button)findViewById(R.id.namesort) ;
        Button wait = (Button)findViewById(R.id.waiting) ;
        Button GD = (Button)findViewById(R.id.GetandDel) ;
        Button complet = (Button)findViewById(R.id.complet) ;

        wait.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(selorderwait.this, selorderwait.class));
            }
        }) ;


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
                startActivity(new Intent(selorderwait.this, selorderdel.class));
            }
        });
        complet.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selorderwait.this, selordercomp.class));

            }
        });

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
                adapter.addItem(productprice, productname, dateStr, phone,0) ; //아이템 추가
                adapter.notifyDataSetChanged(); //데이터 수정 알림
                Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();

            }
        }
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
            default:
                break;
        }
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
                        //TODO: 다음단계의 리스트로 정보 전송
                        Toast.makeText(getApplicationContext(), "주문을 수락하였습니다", Toast.LENGTH_SHORT).show();
                        //TODO: 네트워크로 메시지 전송
                        adapter.deleteItem(position);
                        adapter.notifyDataSetChanged();
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
}
