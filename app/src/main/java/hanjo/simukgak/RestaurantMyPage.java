package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class RestaurantMyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_my_page);
        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.bg2);
    }

    public void onClickManageMenu(View v) {

    }

    public void onClickManageSell(View v) {
        startActivity(new Intent(getApplicationContext(), ManageSell.class));
    }

    public void onClickRank(View v) {
        startActivity(new Intent(getApplicationContext(), ManageSellRank.class));
    }
}
