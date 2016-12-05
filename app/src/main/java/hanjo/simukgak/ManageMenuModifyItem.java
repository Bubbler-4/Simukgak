package hanjo.simukgak;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

public class ManageMenuModifyItem extends AppCompatActivity {

    private int REQUEST_CODE = 1;
    private EditText productEditText;
    private EditText priceEditText;
    private int status;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_menu_modify_item);

        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.bg2);

        productEditText = (EditText) findViewById(R.id.productEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);

        Intent intent = getIntent();

        status = intent.getExtras().getInt("Status");
        index = intent.getExtras().getInt("Index");
        productEditText.setText(intent.getExtras().getString("Product"));
        priceEditText.setText(String.format(Locale.KOREA, "%d", intent.getExtras().getInt("Price")));
    }

    public void onClickConfirm(View v) {
        confirmItems();
    }

    public void confirmItems() {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ManageMenuModifyItem.this);
        alert_confirm.setMessage("작성을 완료하셨습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra("Status", status);
                        intent.putExtra("Index", index);
                        intent.putExtra("Product", productEditText.getText().toString());
                        intent.putExtra("Price", Integer.parseInt(priceEditText.getText().toString()));
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
}
