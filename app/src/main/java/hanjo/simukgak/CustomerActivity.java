package hanjo.simukgak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class CustomerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_customer);

        Intent parentIntent = getIntent();
        String value = parentIntent.getExtras().getString("key");
    }

    public void onReturn(View view) {
        Intent returnIntent = new Intent();

        returnIntent.putExtra("key", "value");
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
