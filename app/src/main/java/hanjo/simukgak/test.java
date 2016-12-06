package hanjo.simukgak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class test extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer {

    private static final int REQ_YES = 1;
    private String UserID;
    private Bitmap profile;
    private ListView listview ;
    private ReviewAdapter adapter;
    private String job;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        UserID = intent.getStringExtra("email_id");
        profile = (Bitmap)intent.getParcelableExtra("bm");
        job = intent.getStringExtra("Job");
        // Adapter 생성
        adapter = new ReviewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        mProgressView = findViewById(R.id.board_progress);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(listview,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Review review = (Review) adapter.getItem(position);
                                    if(review.getId().equals(UserID)) {
                                        adapter.deleteItem(position);
                                        SocketWrapper.object().deleteReview(position);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
        listview.setOnTouchListener(touchListener);
        listview.setOnScrollListener(touchListener.makeScrollListener());


/*        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                Review item = (Review) parent.getItemAtPosition(position) ;

            //    String titleStr = item.getTitle() ;
            //    String descStr = item.getDesc() ;
                //   Drawable iconDrawable = item.getIcon() ;

                // TODO : use item data.
            }
        }) ;*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "리뷰를 작성하시겠습니까?", 3000).setActionTextColor(Color.parseColor("#F5C30B"))
                        .setAction("YES", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(
                                        test.this, // 현재 화면의 제어권자
                                        Review_write.class); // 다음 넘어갈 클래스 지정
                                startActivityForResult(intent, REQ_YES); // 다음 화면으로 넘어간다
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        ImageView pfImage = (ImageView) header.findViewById(R.id.profileImage);
        pfImage.setImageBitmap(profile);
        TextView ui = (TextView) header.findViewById(R.id.UserId);
        ui.setText(UserID);

        navigationView.setNavigationItemSelectedListener(this);

        SocketWrapper.object().deleteObservers();
        SocketWrapper.object().addObserver(this);

        SocketWrapper.object().requestReviews();
        showProgress(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_seller) {
            // Handle the camera action
        } else if (id == R.id.nav_customer) {

        } else if (id == R.id.nav_myWrite) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent intent){
        if (requestCode == REQ_YES){
            if (resultCode == RESULT_OK) {
                String store_name = intent.getStringExtra("Store_name");
                String food_name = intent.getStringExtra("Food_name");
                String grade_text = intent.getStringExtra("Grade_text");
                String type_comment = intent.getStringExtra("Type_comment");
                long now = System.currentTimeMillis();
                SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(now);
                String strCurDate = CurDateFormat.format(date);
                //     adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_menu_camera),
                adapter.addItem(job, new BitmapDrawable(profile),
                        UserID, store_name, food_name,"0" , strCurDate, grade_text, type_comment);
                SocketWrapper.object().sendReview(job, store_name, food_name, strCurDate, grade_text, type_comment);
            }
        }
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
        String reviewList = sw.getReviewList();

        try {
            JSONArray array = new JSONArray(reviewList);
            int len = array.length();
            for(int i = 0; i < len; i += 1) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String profile = obj.getString("profile");
                String job = obj.getString("job");
                String store = obj.getString("store");
                String food = obj.getString("food");
                String date = obj.getString("date");
                String grade = obj.getString("grade");
                String comment = obj.getString("comment");
                int like = obj.getInt("like");

                byte[] byteArray = Base64.decode(profile, Base64.NO_WRAP | Base64.URL_SAFE);
                Bitmap profileBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                adapter.addItem(job, new BitmapDrawable(profileBitmap), id, store, food, String.valueOf(like), date, grade, comment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
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

