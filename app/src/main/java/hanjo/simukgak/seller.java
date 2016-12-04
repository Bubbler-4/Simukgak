package hanjo.simukgak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class seller extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seller);
        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.background3);

        Intent parentIntent = getIntent();
        String value = parentIntent.getExtras().getString("key");
    }

    public void onReturn(View view) {
        Intent returnIntent = new Intent();

        returnIntent.putExtra("key", "value");
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void onClickMyPage(View view) {
        startActivity(new Intent(seller.this, RestaurantMyPage.class));
    }

    public void onClickBoard(View view) {
    }

    public void onClickOrder(View view) {
        startActivity(new Intent(seller.this, selorderwait.class));
    }

}
