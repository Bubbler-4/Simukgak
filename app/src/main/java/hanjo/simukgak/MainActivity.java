package hanjo.simukgak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SocketWrapper.object().initSocket(this);
        initFirebase();
        init();
    }

    private void init() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FBConfig.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", "ExampleUser");
        editor.commit();
    }

    private void initFirebase() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(FBConfig.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(FBConfig.TOPIC_GLOBAL);

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(FBConfig.SHARED_PREF, 0);
                    String regId = pref.getString("regId", null);

                    Log.e(TAG, "Firebase reg id: " + regId);

                } else if (intent.getAction().equals(FBConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };

        SharedPreferences pref = getApplicationContext().getSharedPreferences(FBConfig.SHARED_PREF, 0);
        String token = pref.getString("regId", null);
        SocketWrapper.object().sendFBToken(token);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FBConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FBConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        // FBPushUtil.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public void onSelectCustomer(View view) {
        Intent onSelectCustomerIntent = new Intent(this, CustomerActivity.class);

        onSelectCustomerIntent.putExtra("key", "value");

        final int result = 1;
        startActivity(onSelectCustomerIntent);
        //startActivityForResult(onSelectCustomerIntent, result);
    }

    public void onSelectRestaurant(View view) {
        Intent onSelectCustomerIntent = new Intent(this, RestaurantMyPage.class);

        onSelectCustomerIntent.putExtra("key", "value");

        final int result = 1;
        startActivity(onSelectCustomerIntent);
        //startActivityForResult(onSelectCustomerIntent, result);
    }
}
