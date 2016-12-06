package hanjo.simukgak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
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
    private String[] restaurantList;
    private String restaurant;
    private ListView listview;
    private View mProgressView;

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

            listview =(ListView)findViewById(R.id.koreaList);
            listview.setAdapter(adapter);

            mProgressView = findViewById(R.id.login_progress2);

            SocketWrapper.object().deleteObservers();
            SocketWrapper.object().addObserver(this);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id){
                    restaurant = restaurantList[position];
                    SocketWrapper.object().requestMenuList(restaurant);
                    showProgress(true);
                }
            });
        }

    @Override
    public void update(Observable o, Object arg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgress(false);
            }
        });

        SocketWrapper sw = (SocketWrapper) o;
        String menuList = sw.getMenuList();

        Intent intent = new Intent(order2.this,order3.class);
        intent.putExtra("StoreName",restaurant);
        intent.putExtra("menuList", menuList);
        startActivity(intent);
        finish();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            listview.setVisibility(show ? View.GONE : View.VISIBLE);
            listview.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    listview.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            listview.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
