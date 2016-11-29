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

public class CreateDutch extends AppCompatActivity implements CreateDutchListViewAdapter.ListBtnClickListener{

    final public static int REQUEST_CODE = 1;

    private FileManager fileManager;

    final CreateDutchListViewAdapter adapter = new CreateDutchListViewAdapter(this, R.layout.create_dutch_listview_item, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dutch);
        final ListView listview;

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        });


        fileManager = new FileManager(getApplicationContext(), "dutch_info.txt");
        //default 아이템 추가. Product, Price, Name, Date
        final String product = getIntent().getExtras().getString("product");
        final int price = Integer.parseInt(getIntent().getExtras().getString("price"));
        final String date = getIntent().getExtras().getString("date");

        adapter.addItem("나", price);


        TextView productText = (TextView) findViewById(R.id.productText);
        TextView dateText = (TextView) findViewById(R.id.dateText);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button confirmButton = (Button) findViewById(R.id.confirmButton);

        productText.setText(product);
        dateText.setText(date);

        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int remains = price;
                adapter.renewItem();
                adapter.addItem("Name", 0);
                for(int i=1; i<adapter.getCount(); i++)
                {
                    adapter.editItemPrice(i, price/adapter.getCount());
                    remains = remains - price/adapter.getCount();
                }
                adapter.editItemPrice(0, remains);
                adapter.notifyDataSetChanged();
            }
        });

        confirmButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String data;
                adapter.renewItem();
                for(int i = 1; i<adapter.getCount(); i++)
                {
                    data = product + "," + Integer.toString(adapter.getItem(i).getPrice()) + "," +  adapter.getItem(i).getName() + "," + date;
                    fileManager.writeFile(data);
                }
                finish();
            }
        });


    }

    @Override
    public void onListBtnClick(int position, View v) {
        switch(v.getId()) {
            case R.id.deleteButton:
                if(position != 0)
                    itemDelete(position);
                break;
            default:
                break;
        }
    }

    public void itemDelete(final int position)
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(CreateDutch.this);
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

}
