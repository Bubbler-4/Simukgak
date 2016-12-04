package hanjo.simukgak;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class CustomerActivity extends Activity {
    private String job;
    private String UserID;
    private Bitmap profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_customer);

        Intent intent = getIntent();
        String value = intent.getExtras().getString("key");
        job = intent.getStringExtra("Job");
        UserID = intent.getStringExtra("email_id");
        profile = (Bitmap)intent.getExtras().get("bm");

        LinearLayout layout =(LinearLayout)findViewById(R.id.back_);
        layout.setBackgroundResource(R.drawable.background3);
    }

    public void onReturn(View view) {
        Intent returnIntent = new Intent();

        returnIntent.putExtra("key", "value");
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void onClickMyPage(View view) {
        startActivity(new Intent(getApplicationContext(), Mypage.class));
    }

    public void onClickBoard(View view) {
        Intent intent = new Intent(CustomerActivity.this, test.class);
        intent.putExtra("key", "value");
        intent.putExtra("Job",job);
        intent.putExtra("email_id",UserID);
        intent.putExtra("bm", profile);
        startActivity(intent);
    }

    public void onClickOrder(View view) {
        startActivity(new Intent(CustomerActivity.this, order1.class));
    }

    public void onClickDutch(View view) {
        startActivity(new Intent(getApplicationContext(), DutchPay.class));
    }
    public void onClickSettings(View view) {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }
}
