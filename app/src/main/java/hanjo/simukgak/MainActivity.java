package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSelectCustomer(View view) {
        Intent onSelectCustomerIntent = new Intent(this, CustomerActivity.class);

        onSelectCustomerIntent.putExtra("key", "value");

        final int result = 1;
        startActivity(onSelectCustomerIntent);
        //startActivityForResult(onSelectCustomerIntent, result);
    }

    public void onSelectRestaurant(View view) {
    }
}
