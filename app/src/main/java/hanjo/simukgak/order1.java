package hanjo.simukgak;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

public class order1 extends AppCompatActivity implements Observer {

    static final String[] LIST_MENU ={"한식","중식","일식"};
    private ListView listview;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order1);

        LinearLayout layout =(LinearLayout)findViewById(R.id.back);
        layout.setBackgroundResource(R.drawable.bg2);

        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,LIST_MENU);

        listview =(ListView) findViewById(R.id.store);
        listview.setAdapter(adapter);

        mProgressView = findViewById(R.id.login_progress1);

        SocketWrapper.object().deleteObservers();
        SocketWrapper.object().addObserver(this);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                SocketWrapper.object().requestRestaurantList(LIST_MENU[position]);
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
        String[] restaurantList = sw.getRestaurantList();

        Intent intent = new Intent(order1.this,order2.class);
        intent.putExtra("restaurantList", restaurantList);
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