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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DutchPay extends AppCompatActivity implements DutchListViewAdapter.ListBtnClickListener {

    final public static int REQUEST_CODE = 1;

    private FileManager fileManager;

    final DutchListViewAdapter adapter = new DutchListViewAdapter(this, R.layout.dutch_listview_item, this);

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

        ArrayList<String> fileValues;
        String[] values;

        fileManager = new FileManager(getApplicationContext(), "dutch_info.txt");
        fileValues = fileManager.readFile();
        for(int i = 0; i < fileValues.size(); i++)
        {
            values = fileValues.get(i).split(",");
            adapter.addItem(values[0], values[1], values[2], values[3]);
        }

        TextView totalTText = (TextView)findViewById(R.id.totalText);
        Button sortByDate = (Button)findViewById(R.id.sortByDateButton) ;
        Button sortByName = (Button)findViewById(R.id.sortByNameButton) ;

        totalTText.setText("받아야 할 돈: " + adapter.getTotalPrice() + "원");

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
    protected void onDestroy(){
        super.onDestroy();
        fileManager.resetData();
        String data;
        for(int i = 0; i<adapter.getCount(); i++)
        {
            data = adapter.getItem(i).getTitle() + "," + Integer.toString(adapter.getItem(i).getPrice()) + "," + adapter.getItem(i).getName() + "," + adapter.getItem(i).getDate();
            fileManager.writeFile(data);
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
                        TextView totalTText = (TextView)findViewById(R.id.totalText);
                        totalTText.setText("받아야 할 돈: " + adapter.getTotalPrice() + "원");
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
    //TODO: 한꺼번에 삭제 기능 (알림이 올 경우)

    public void noticeDutch(final int position)
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(DutchPay.this);
        alert_confirm.setMessage(adapter.getItem(position).getName() + "에게 " + adapter.getTotalPrice(position) + "원을 요청하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), adapter.getItem(position).getName() + ": " + adapter.getTotalPrice(position), Toast.LENGTH_SHORT).show();
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