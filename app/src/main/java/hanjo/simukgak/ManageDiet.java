package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

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

        fileManager = new FileManager(getApplicationContext(), "orderlist_info.txt");
        fileValues=fileManager.readFile();
        restaurantName = new ArrayList<>();
        ArrayList<String> sortedList;
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
                orderCount[restaurantName.size()-1]++;
            }
            else
            {
                orderCount[index]++;
            }
        }
        sortProducts(restaurantName, orderCount);
        switch(restaurantName.size()) {
            case 5:
                pro5.setText(restaurantName.get(4));
                num5.setText(String.format(Locale.KOREA, "%d", orderCount[4]));
            case 4:
                pro4.setText(restaurantName.get(3));
                num4.setText(String.format(Locale.KOREA, "%d", orderCount[3]));
            case 3:
                pro3.setText(restaurantName.get(2));
                num3.setText(String.format(Locale.KOREA, "%d", orderCount[2]));
            case 2:
                pro2.setText(restaurantName.get(1));
                num2.setText(String.format(Locale.KOREA, "%d", orderCount[1]));
            case 1:
                pro1.setText(restaurantName.get(0));
                num1.setText(String.format(Locale.KOREA, "%d", orderCount[0]));
            case 0:
                break;
            default:
                pro1.setText(restaurantName.get(0));
                num1.setText(String.format(Locale.KOREA, "%d", orderCount[0]));
                pro2.setText(restaurantName.get(1));
                num2.setText(String.format(Locale.KOREA, "%d", orderCount[1]));
                pro3.setText(restaurantName.get(2));
                num3.setText(String.format(Locale.KOREA, "%d", orderCount[2]));
                pro4.setText(restaurantName.get(3));
                num4.setText(String.format(Locale.KOREA, "%d", orderCount[3]));
                pro5.setText(restaurantName.get(4));
                num5.setText(String.format(Locale.KOREA, "%d", orderCount[4]));
        }
        //여기서 제품 받아오면 될것 같군요
        btnShow = (Button)findViewById(R.id.show);

        btnShow.setOnClickListener(btnShowOnClickListener);
    }

    View.OnClickListener btnShowOnClickListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(
                            ManageDiet.this,
                            ShowWebChartActivity.class);

                    intent.putExtra("PRO1", getPro(pro1));
                    intent.putExtra("NUM1", getNum(num1));
                    intent.putExtra("PRO2", getPro(pro2));
                    intent.putExtra("NUM2", getNum(num2));
                    intent.putExtra("PRO3", getPro(pro3));
                    intent.putExtra("NUM3", getNum(num3));
                    intent.putExtra("PRO4", getPro(pro4));
                    intent.putExtra("NUM4", getNum(num4));
                    intent.putExtra("PRO5", getPro(pro5));
                    intent.putExtra("NUM5", getNum(num5));

                    startActivity(intent);
                }
            };

    private int checkRepeat(String name, ArrayList<String> restaurantName) {

        for (int i = 0; i < restaurantName.size(); i++) {
            if (name.equals(restaurantName.get(i)))
                return i;
        }
        return -1;
    }

    private void sortProducts(ArrayList<String> restaurantName, int[] orderCount)
    {
        int tempMax;
        int temp;
        int[] index;
        int[] ognOrder;
        ognOrder= new int[restaurantName.size()];
        index= new int[restaurantName.size()];
        for(int i=0; i<restaurantName.size(); i++)
        {
            ognOrder[i]=orderCount[i];
        }
        for(int i=0; i<restaurantName.size(); i++ ) {
            tempMax = i;
            for(int j=i;j<restaurantName.size(); j++){
                if(orderCount[j]>orderCount[tempMax])
                    tempMax=j;
            }
            temp=orderCount[tempMax];
            orderCount[tempMax]=orderCount[i];
            orderCount[i]=temp;
            Collections.swap(restaurantName, tempMax, i);
        }

    }

    private int getNum(TextView textView){

        int num = 0;

        String stringNum = textView.getText().toString();
        if(!stringNum.equals("")){
            num = Integer.valueOf(stringNum);
        }

        return (num);
    }
    private String getPro(TextView textView){

        String pro;
        pro = textView.getText().toString();
        return (pro);
    }





}
