package hanjo.simukgak;

import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddressChange extends AppCompatActivity {
    private EditText etxValue;
    private TextView txvValue;
    private SharedPreferences preferences;
    private static java.lang.String address="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_change);

        etxValue=(EditText)findViewById(R.id.amin_etxValue);
        txvValue=(TextView)findViewById(R.id.amin_txvValue);
        preferences=getSharedPreferences("shared", MODE_PRIVATE);
        txvValue.setText(address);
        Button Change=(Button)findViewById(R.id.ChangeButton);
            Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("key", etxValue.getText().toString());
                editor.commit();
                address=etxValue.getText().toString();
                txvValue.setText(address);

            }
        });
    }
}
