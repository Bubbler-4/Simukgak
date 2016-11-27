package hanjo.simukgak;

/**
 * Created by lg on 2016-11-27.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class ShowWebChartActivity extends ActionBarActivity {

    WebView webView;
    int num1, num2, num3, num4, num5;
    String pro1, pro2, pro3, pro4, pro5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webchart);

        Intent intent = getIntent();
        pro1 = intent.getStringExtra("PRO1");
        num1 = intent.getIntExtra("NUM1", 0);
        pro2 = intent.getStringExtra("PRO2");
        num2 = intent.getIntExtra("NUM2", 0);
        pro3 = intent.getStringExtra("PRO3");
        num3 = intent.getIntExtra("NUM3", 0);
        pro4 = intent.getStringExtra("PRO4");
        num4 = intent.getIntExtra("NUM4", 0);
        pro5 = intent.getStringExtra("PRO5");
        num5 = intent.getIntExtra("NUM5", 0);

        webView = (WebView)findViewById(R.id.web);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/chart.html");
    }

    public class WebAppInterface {

        @JavascriptInterface
        public String getPro1() {
            return pro1;
        }

        @JavascriptInterface
        public int getNum1() {
            return num1;
        }

        @JavascriptInterface
        public String getPro2() {
            return pro2;
        }

        @JavascriptInterface
        public int getNum2() {
            return num2;
        }

        @JavascriptInterface
        public String getPro3() {
            return pro3;
        }

        @JavascriptInterface
        public int getNum3() {
            return num3;
        }

        @JavascriptInterface
        public String getPro4() {
            return pro4;
        }

        @JavascriptInterface
        public int getNum4() {
            return num4;
        }

        @JavascriptInterface
        public String getPro5() {
            return pro5;
        }

        @JavascriptInterface
        public int getNum5() {
            return num5;
        }
    }

}
