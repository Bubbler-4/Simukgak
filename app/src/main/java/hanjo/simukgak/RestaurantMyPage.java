package hanjo.simukgak;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RestaurantMyPage extends AppCompatActivity {
    private String job;
    private String UserID;
    private Bitmap profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_my_page);
        Intent intent = getIntent();
        String value = intent.getExtras().getString("key");
        job = intent.getStringExtra("Job");
        UserID = intent.getStringExtra("email_id");
        profile = (Bitmap)intent.getExtras().get("bm");
    }

    public void onClickManageSell(View v) {
        startActivity(new Intent(getApplicationContext(), ManageSell.class));
    }
}
