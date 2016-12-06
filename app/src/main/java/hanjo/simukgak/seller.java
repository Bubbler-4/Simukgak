package hanjo.simukgak;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class seller extends Activity {
    private String Id;
    private String job;
    private Bitmap profile;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seller);
        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.background3);

        Intent parentIntent = getIntent();
        job = parentIntent.getStringExtra("Job");
        Id = parentIntent.getStringExtra("email_id");
        profile = parentIntent.getParcelableExtra("bm");
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
        Intent intent = new Intent(seller.this, test.class);
        intent.putExtra("Job",job);
        intent.putExtra("email_id",Id);
        intent.putExtra("bm", (Bitmap)profile);
        startActivity(intent);
    }

    public void onClickOrder(View view) {
        startActivity(new Intent(seller.this, selorderwait.class));
    }

}
