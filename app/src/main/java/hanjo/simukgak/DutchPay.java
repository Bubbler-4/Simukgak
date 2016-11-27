package hanjo.simukgak;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;



public class DutchPay extends AppCompatActivity implements DutchListViewAdapter.ListBtnClickListener {

    static final int REQUEST_CODE = 1;
    final DutchListViewAdapter adapter = new DutchListViewAdapter(this, R.layout.listview_item, this);

    //TODO: 로컬 데이터 저장
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutch_pay);
        final ListView listview ;

        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        });

        //default 아이템 추가. Product, Price, Name, Date
        adapter.addItem("맘스터치", "5000", "Alice", "2016.10.03") ;
        adapter.addItem("버거킹", "5000", "James", "2016.10.20") ;
        adapter.addItem("새천년", "7000", "Bob", "2016.10.09") ;
        adapter.addItem("굽네치킨", "18000", "Tom", "2016.10.30") ;
        adapter.addItem("참서리", "3000", "Bob", "2016.10.10") ;
        adapter.addItem("야식장", "4500", "Alice", "2016.10.21") ;
        adapter.addItem("윈드벨", "8000", "Bob", "2016.09.21") ;
        adapter.addItem("스낵바", "2700", "Jack", "2015.07.21") ;
        adapter.addItem("아아아", "3000", "김민수", "2013.12.30") ;
        adapter.addItem("아아앙ㅇ아아", "8000", "최영희", "2013.01.01") ;
        adapter.addItem("가아아아아", "8000", "김민수", "2013.05.10") ;

        Button addButton = (Button)findViewById(R.id.addSequence) ;
        Button sortByDate = (Button)findViewById(R.id.sortByDateButton) ;
        Button sortByName = (Button)findViewById(R.id.sortByNameButton) ;

        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                //startActivityForResult(new Intent(getApplicationContext(), AddItem.class), 1);
                //TODO: 네트워크/액티비티로 값을 받아 추가
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

        sortByDate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.sortItemByDate();
            }
        });

        sortByName.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.sortItemByName();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //MainActivity에서 부여한 번호표를 비교
        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) { //세컨드 액티비티에서 이 값을 반환하는 코드가 동작 됐을때
                String productStr = intent.getExtras().getString("product"); //인자로 구분된 값을 불러오는 행위를 하고
                String priceStr = intent.getExtras().getString("price");
                String nameStr = intent.getExtras().getString("name");
                String dateStr = intent.getExtras().getString("date");
                adapter.addItem(productStr, priceStr, nameStr, dateStr) ; //아이템 추가
                adapter.notifyDataSetChanged(); //데이터 수정 알림
                Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onListBtnClick(int position, View v) {
        switch(v.getId()) {
            case R.id.deleteButton:
                itemDelete(position);
                break;
            case R.id.askButton:
                noticeDutch(position);
                break;
            default:
                break;
        }
    }

    public void itemDelete(final int position)
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(DutchPay.this);
        alert_confirm.setMessage("내역을 삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.deleteItem(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
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
    //TODO: 한꺼번에 삭제 기능

    public void noticeDutch(final int position)
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(DutchPay.this);
        alert_confirm.setMessage(adapter.getName(position) + "에게 " + adapter.getTotalPrice(position) + "원을 요청하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), adapter.getName(position) + ": " + adapter.getTotalPrice(position), Toast.LENGTH_SHORT).show();
                        //TODO: 네트워크로 메시지 전송
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