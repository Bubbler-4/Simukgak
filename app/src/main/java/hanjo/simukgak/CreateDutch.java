package hanjo.simukgak;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateDutch extends AppCompatActivity implements CreateDutchListViewAdapter.ListBtnClickListener{

    private  int index;
    private FileManager fileManager;

    final CreateDutchListViewAdapter adapter = new CreateDutchListViewAdapter(this, R.layout.create_dutch_listview_item, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dutch);
        final ListView listview;

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setItemsCanFocus(true);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Log.d("CreateDutch", "listview.onItemClick");
            }
        });


        fileManager = new FileManager(getApplicationContext(), "dutch_info.txt");
        //아이템 추가.
        final String company = getIntent().getExtras().getString("company");
        final String date = getIntent().getExtras().getString("date");
        final int[] price = getIntent().getExtras().getIntArray("price");
        final int[] amount = getIntent().getExtras().getIntArray("amount");
        final ArrayList<String> product = getIntent().getExtras().getStringArrayList("product");
        index = getIntent().getExtras().getInt("index");

        int count = 0;
        if(product!=null && amount!=null && price!=null) {
            for (int i = 0; i < product.size(); i++) {
                for (int j = 0; j < amount[i]; j++) {
                    adapter.addItem("", price, product);
                    adapter.getItem(count).setProductIndex(i);
                    adapter.getItem(count).setPrice(price[i]);
                    count++;
                }
            }
            adapter.getItem(0).setName("나");
        }

        TextView productText = (TextView) findViewById(R.id.productText);
        TextView dateText = (TextView) findViewById(R.id.dateText);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button confirmButton = (Button) findViewById(R.id.confirmButton);

        productText.setText(company);
        dateText.setText(date);

        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                adapter.renewItem();
                adapter.addItem("", price, product);
                adapter.notifyDataSetChanged();
            }
        });

        confirmButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                adapter.renewItem();
                if(adapter.AllDataSelected(product, price, amount) == null) {
                    confirmItem(company, date);
                }
                else {
                    String[] errorMsg = adapter.AllDataSelected(product, price, amount).split(",");
                    switch (errorMsg[0])
                    {
                        case "0":
                            Toast.makeText(getApplicationContext(), "모든 아이템이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case "1":
                            Toast.makeText(getApplicationContext(), errorMsg[1] + "가  " + errorMsg[2] + "원 부족합니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case "2":
                            Toast.makeText(getApplicationContext(), errorMsg[1] + "가  " + errorMsg[2] + "원 초과되었습니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case "3":
                            Toast.makeText(getApplicationContext(), "이름이 모두 작성되지 않았습니다.", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onListBtnClick(int position, View v) {
        switch(v.getId()) {
            case R.id.deleteButton:
                if(!adapter.getItem(position).getName().equals("나"))
                    itemDelete(position);
                break;
            default:
                break;
        }
    }


    public void confirmItem(final String company, final String date)
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(CreateDutch.this);
        alert_confirm.setMessage("더치페이를 하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String data;
                        for (int i = 0; i < adapter.getCount(); i++) {
                            if(!adapter.getItem(i).getName().equals("나")) {
                                data = company + "-" + adapter.getItem(i).getProduct() + "," + Integer.toString(adapter.getItem(i).getPrice()) + "," + adapter.getItem(i).getName() + "," + date;
                                fileManager.writeFile(data);
                            }
                        }
                        Intent intent = new Intent();
                        intent.putExtra("index",  index);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'No'1
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
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
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

}
