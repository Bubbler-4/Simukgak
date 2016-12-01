package hanjo.simukgak;

/**
 * Created by lg on 2016-11-27.
 */
       // import android.support.v7.app.ActionBarActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.text.InputFilter;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;

public class Manage extends AppCompatActivity {

    EditText num1, num2, num3, num4, num5, pro1, pro2, pro3, pro4, pro5;
    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pro1 = (EditText)findViewById(R.id.pro1);
        num1 = (EditText)findViewById(R.id.num1);
        pro2 = (EditText)findViewById(R.id.pro2);
        num2 = (EditText)findViewById(R.id.num2);
        pro3 = (EditText)findViewById(R.id.pro3);
        num3 = (EditText)findViewById(R.id.num3);
        pro4 = (EditText)findViewById(R.id.pro4);
        num4 = (EditText)findViewById(R.id.num4);
        pro5 = (EditText)findViewById(R.id.pro5);
        num5 = (EditText)findViewById(R.id.num5);
        //여기서 제품 받아오면 될것 같군요
        btnShow = (Button)findViewById(R.id.show);

        btnShow.setOnClickListener(btnShowOnClickListener);
    }

    OnClickListener btnShowOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(
                            Manage.this,
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

    private int getNum(EditText editText){

        int num = 0;

        String stringNum = editText.getText().toString();
        if(!stringNum.equals("")){
            num = Integer.valueOf(stringNum);
        }

        return (num);
    }
    private String getPro(EditText editText){

        String pro;
        pro = editText.getText().toString();
        return (pro);
    }
}