package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class order2 extends AppCompatActivity {
static int REQUEST_ACT =1111;

        static final String[] LIST_MENU ={"마미 음식 백화점","새천년 식육식당"};
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_order2);

            LinearLayout layout =(LinearLayout)findViewById(R.id.back);
            layout.setBackgroundResource(R.drawable.bg2);

            Intent parentIntent = getIntent();
            String[] restaurantList = parentIntent.getStringArrayExtra("restaurantList");

            ArrayAdapter adapter = new ArrayAdapter(
                    this, R.layout.support_simple_spinner_dropdown_item, restaurantList);

            ListView listview =(ListView)findViewById(R.id.koreaList);
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id){
                    Intent intent = new Intent(order2.this,order3.class);
                    //가게명 전송
                    intent.putExtra("StoreName",LIST_MENU[position]);

                    startActivityForResult(intent,REQUEST_ACT);

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
}
