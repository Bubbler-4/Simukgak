package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ManageDiet extends AppCompatActivity {

    private TextView num1, num2, num3, num4, num5, pro1, pro2, pro3, pro4, pro5;
    private Button btnShow;
    private FileManager fileManager;
    private ArrayList<String> fileValues;
    private GregorianCalendar today = new GregorianCalendar();
    WebView webView;
    private int Num1, Num2, Num3, Num4, Num5;
    private String Pro1, Pro2, Pro3, Pro4, Pro5;
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
        Spinner spinner = (Spinner) findViewById(R.id.dateSelect);

        ArrayList<String> selectlist = new ArrayList<>();
        selectlist.add("이번 달");
        selectlist.add("저번 달");
        selectlist.add("최근 1개월");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, selectlist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int manageDate;
                if(position == 0)
                {
                    manageDate=0;
                }
                else if(position == 1)
                {
                    manageDate=1;
                }
                else
                {
                    manageDate=2;
                }
                ArrayList<String> restaurantName;
                int[] orderCount;
                fileManager = new FileManager(getApplicationContext(), "orderlist_info.txt");
                fileValues=fileManager.readFile();
                restaurantName = new ArrayList<>();
                String[] values;
                String[] dates;
                orderCount= new int[fileValues.size()];
                for(int i=0; i<fileValues.size(); i++)
                {
                    orderCount[i]=0;
                }
                int index;
                int year, month;
                year=today.get(today.YEAR);
                month=today.get(today.MONTH)+1;
                for(int i=0; i<fileValues.size(); i++) {
                    values = fileValues.get(i).split(",");
                    index=checkRepeat(values[0], restaurantName);
                    dates=values[1].split("\\.");
                    if(index==-1) {if(dateCheck(dates, year, month, manageDate)) {
                        restaurantName.add(values[0]);
                        orderCount[restaurantName.size() - 1]++;
                    }
                    }
                    else
                    {
                        if(dateCheck(dates, year, month, manageDate)) {
                            orderCount[index]++;
                        }
                    }
                }

                sortProducts(restaurantName, orderCount);
                pro1.setText("");
                num1.setText("");
                pro2.setText("");
                num2.setText("");
                pro3.setText("");
                num3.setText("");
                pro4.setText("");
                num4.setText("");
                pro5.setText("");
                num5.setText("");
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


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }

        );


        //여기서 제품 받아오면 될것 같군요
        btnShow = (Button)findViewById(R.id.show);

        btnShow.setOnClickListener(btnShowOnClickListener);
    }

    View.OnClickListener btnShowOnClickListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    Pro1=getPro(pro1);
                    Num1=getNum(num1);
                    Pro2=getPro(pro2);
                    Num2=getNum(num2);
                    Pro3=getPro(pro3);
                    Num3=getNum(num3);
                    Pro4=getPro(pro4);
                    Num4=getNum(num4);
                    Pro5=getPro(pro5);
                    Num5=getNum(num5);

                    webView = (WebView)findViewById(R.id.web);
                    webView.addJavascriptInterface(new ManageDiet.WebAppInterface(), "Android");

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl("file:///android_asset/chart.html");

                }
            };

    public class WebAppInterface {

        @JavascriptInterface
        public String getPro1() {
            return Pro1;
        }

        @JavascriptInterface
        public int getNum1() {
            return Num1;
        }

        @JavascriptInterface
        public String getPro2() {
            return Pro2;
        }

        @JavascriptInterface
        public int getNum2() {
            return Num2;
        }

        @JavascriptInterface
        public String getPro3() {
            return Pro3;
        }

        @JavascriptInterface
        public int getNum3() {
            return Num3;
        }

        @JavascriptInterface
        public String getPro4() {
            return Pro4;
        }

        @JavascriptInterface
        public int getNum4() {
            return Num4;
        }

        @JavascriptInterface
        public String getPro5() {
            return Pro5;
        }

        @JavascriptInterface
        public int getNum5() {
            return Num5;
        }
    }



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

    private boolean dateCheck(String[] dates, int year, int month, int manageDate)
    {
        if(manageDate==0)
        {
            if((year==Integer.parseInt(dates[0]))&&(month==Integer.parseInt(dates[1])))
                return true;
            else
                return false;
        }
        if(manageDate==1)
        {
            if((year==Integer.parseInt(dates[0]))&&(month==Integer.parseInt(dates[1])+1))
                return true;
            else if((year==Integer.parseInt(dates[0])+1)&&(month==1)&&(Integer.parseInt(dates[1])==12))
                return true;
            else
                return false;
        }
        if(manageDate==2) {
            if (Integer.parseInt(dates[0]) == year) {
                if (((month - Integer.parseInt(dates[1])) <= 1))
                    return true;
                else
                    return false;
            } else if ((year - Integer.parseInt(dates[0])) == 1) {
                if ((month == 1) && (Integer.parseInt(dates[1]) == 12))
                    return true;
                else
                    return false;
            } else
                return false;
        }
        else
            return false;
    }




}
