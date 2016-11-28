package hanjo.simukgak;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class order1 extends AppCompatActivity implements Observer {

    static final String[] LIST_MENU ={"한식","중식","일식"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order1);

        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,LIST_MENU);

        ListView listview =(ListView)findViewById(R.id.store);
        listview.setAdapter(adapter);

        SocketWrapper.object().addObserver(this);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                SocketWrapper.object().requestRestaurantList(LIST_MENU[position]);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        SocketWrapper sw = (SocketWrapper) o;
        String[] restaurantList = sw.getRestaurantList();

        Intent intent = new Intent(order1.this,order2.class);
        intent.putExtra("restaurantList", restaurantList);
        startActivity(intent);
    }
}