package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ManageDiet extends AppCompatActivity {

    private TextView num1, num2, num3, num4, num5, pro1, pro2, pro3, pro4, pro5;
    private Button btnShow;
    private FileManager fileManager;
    private ArrayList<String> fileValues;
    private ArrayList<String> restaurantName;
    private int[] orderCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_diet);
        pro1 = (TextView)findViewById(R.id.pro1);
        num1 = (TextView)findViewById(R.id.num1);
        pro2 = (TextView)findViewById(R.id.pro2);
        num2 = (TextView)findViewById(R.id.num2);
        pro3 = (TextView)findViewById(R.id.pro3);
        num3 = (TextView)findViewById(R.id.num3);
        pro4 = (TextView)findViewById(R.id.pro4);
        num4 = (TextView)findViewById(R.id.num4);
        pro5 = (TextView)findViewById(R.id.pro5);
        num5 = (TextView)findViewById(R.id.num5);

        //여기서 제품 받아오면 될것 같군요
        btnShow = (Button)findViewById(R.id.show);

        btnShow.setOnClickListener(btnShowOnClickListener);
    }

    View.OnClickListener btnShowOnClickListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    fileManager = new FileManager(getApplicationContext(), "orderlist_info.txt");
                    fileValues=fileManager.readFile();
                    restaurantName = new ArrayList<>();
                    String[] values;
                    orderCount= new int[fileValues.size()];
                    for(int i=0; i<fileValues.size(); i++)
                    {
                        orderCount[i]=0;
                    }
                    int index;
                    for(int i=0; i<fileValues.size(); i++) {
                        values = fileValues.get(i).split(",");
                        index=checkRepeat(values[0], restaurantName);
                        if(index==-1) {
                            //TODO: 여기서 date 비교해노읍시다..
                            restaurantName.add(values[0]);
                        }
                        else
                        {
                            orderCount[index]++;
                        }
                    }
                }
            };

    private int checkRepeat(String name, ArrayList<String> restaurantName) {

        for (int i = 0; i < restaurantName.size(); i++) {
            if (name.equals(restaurantName.get(i)))
                return i;
        }
        return -1;
    }
}
