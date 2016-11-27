package hanjo.simukgak;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class DutchAddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dutch_add_item);

        Button addButton = (Button) findViewById(R.id.addConfirm);

        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                callFinish();
            }
        });



        //DatePickerDialog dialog = new DatePickerDialog(this, listener, 2013, 10, 22);
    }

    //TODO: 날짜 수정
    /*
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            Toast.makeText(getApplicationContext(), i + "년" + i1 + "월" + i2 + "일", Toast.LENGTH_SHORT).show();
        }
    };*/


    private void callFinish() {

        final EditText product = (EditText) findViewById(R.id.productEdit);
        final EditText price = (EditText) findViewById(R.id.priceEdit);
        final EditText name = (EditText) findViewById(R.id.nameEdit);
        final EditText date = (EditText) findViewById(R.id.dateEdit);

        Intent intent = new Intent();

        intent.putExtra("product", product.getText().toString());
        intent.putExtra("price", price.getText().toString());
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("date", date.getText().toString());
        this.setResult(RESULT_OK, intent);

        finish();
    }


}
