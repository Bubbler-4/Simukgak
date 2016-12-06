package hanjo.simukgak;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageMenu extends AppCompatActivity implements ManageMenuAdapter.ListBtnClickListener {

    static final int REQUEST_CODE = 1;

    final ManageMenuAdapter adapter = new ManageMenuAdapter(this, R.layout.manage_menu_listview_item, this);

    private FileManager fileManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);
        final ListView listview;

        listview = (ListView) findViewById(R.id.list1);
        listview.setAdapter(adapter);

        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.bg2);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        });

        ArrayList<String> fileValues;
        String[] values;

        fileManager = new FileManager(getApplicationContext(), "menu_info.txt");
        fileValues = fileManager.readFile(); //product1, price1, amount1


        for(int i = 0; i < fileValues.size(); i++)
        {
            values = fileValues.get(i).split(",");

            adapter.addItem(values[0], Integer.parseInt(values[1])); //
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fileManager.resetData();
        String data;
        for(int i = 0; i<adapter.getCount(); i++)
        {
            data = adapter.getItem(i).getProduct() + "," + adapter.getItem(i).getPrice();
            fileManager.writeFile(data);
        }
    }

    public void onClickAdd(View v) {
        Intent intent = new Intent(getApplicationContext(), ManageMenuModifyItem.class);
        intent.putExtra("Status", 0);
        intent.putExtra("Index", -1);
        intent.putExtra("Product", "");
        intent.putExtra("Price", 0);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onListBtnClick(int position, View v) {
        switch(v.getId()) {
            case R.id.deleteButton:
                itemDelete(position);
                break;
            case R.id.editButton:
                itemEdit(position);
                break;
            default:
                break;
        }
    }

    public void itemDelete(final int position) {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ManageMenu.this);
        alert_confirm.setMessage("제품을 삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.deleteItem(position);
                        adapter.notifyDataSetChanged();
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

    public void itemEdit(final int position) {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ManageMenu.this);
        alert_confirm.setMessage("제품을 수정하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), ManageMenuModifyItem.class);
                        intent.putExtra("Status", 1);
                        intent.putExtra("Index", position);
                        intent.putExtra("Product", adapter.getItem(position).getProduct());
                        intent.putExtra("Price", adapter.getItem(position).getPrice());
                        startActivityForResult(intent, REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //MainActivity에서 부여한 번호표를 비교
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) { //세컨드 액티비티에서 이 값을 반환하는 코드가 동작 됐을때
                //Log.d("ss", intent.getExtras().getString("Product"));
                if(intent.getExtras().getInt("Status") == 0)
                    adapter.addItem(intent.getExtras().getString("Product"), intent.getExtras().getInt("Price"));
                else if(intent.getExtras().getInt("Status") == 1)
                    adapter.editItem(intent.getExtras().getInt("Index"), intent.getExtras().getString("Product"), intent.getExtras().getInt("Price"));

                adapter.notifyDataSetChanged();
            }
        }
    }
}
