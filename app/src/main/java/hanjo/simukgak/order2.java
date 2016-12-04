package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

public class order2 extends AppCompatActivity implements Observer {
static int REQUEST_ACT =1111;
    private String[] restaurantList;
    private String restaurant;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_order2);

            LinearLayout layout =(LinearLayout)findViewById(R.id.back);
            layout.setBackgroundResource(R.drawable.bg2);

            Intent parentIntent = getIntent();
            restaurantList = parentIntent.getStringArrayExtra("restaurantList");

            ArrayAdapter adapter = new ArrayAdapter(
                    this, R.layout.support_simple_spinner_dropdown_item, restaurantList);

            ListView listview =(ListView)findViewById(R.id.koreaList);
            listview.setAdapter(adapter);

            SocketWrapper.object().deleteObservers();
            SocketWrapper.object().addObserver(this);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id){
                    restaurant = restaurantList[position];
                    SocketWrapper.object().requestMenuList(restaurant);
                }
            });
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_ACT)
        {
            if(resultCode==RESULT_OK)
            {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        SocketWrapper sw = (SocketWrapper) o;
        String menuList = sw.getMenuList();

        Intent intent = new Intent(order2.this,order3.class);
        intent.putExtra("StoreName",restaurant);
        intent.putExtra("menuList", menuList);
        startActivityForResult(intent, REQUEST_ACT);
    }
}
