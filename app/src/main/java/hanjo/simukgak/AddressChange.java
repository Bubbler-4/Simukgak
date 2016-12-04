package hanjo.simukgak;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddressChange extends AppCompatActivity {
    private EditText etxValue;
    private TextView txvValue;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_change);

        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.bg2);

        etxValue=(EditText)findViewById(R.id.amin_etxValue);
        txvValue=(TextView)findViewById(R.id.amin_txvValue);
        preferences=getSharedPreferences("shared", MODE_PRIVATE);
        Button Change=(Button)findViewById(R.id.ChangeButton);
        txvValue.setText(preferences.getString("key", null));
            Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("key", etxValue.getText().toString());
                editor.commit();
                txvValue.setText(preferences.getString("key", null));

            }
        });
    }
}
