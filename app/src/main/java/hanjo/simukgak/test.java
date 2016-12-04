package hanjo.simukgak;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class test extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final int REQ_YES = 1;
    String job;
    String UserID;
    Bitmap profile;
    ListView listview ;
    ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String value = intent.getExtras().getString("key");
        job = intent.getStringExtra("Job");
        UserID = intent.getStringExtra("email_id");
        profile = (Bitmap)intent.getExtras().get("bm");

        // Adapter 생성
        adapter = new ReviewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

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
                                    adapter.deleteItem(position);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
        listview.setOnTouchListener(touchListener);
        listview.setOnScrollListener(touchListener.makeScrollListener());


        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.pgh),
                "prsdnt", "Mom's Touch", "Psy Bugger","0" , "2016-11-30", "F", "it is really suck") ;

        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.css),
                "Silsae", "Mom's Touch", "Psy Bugger","0" , "2016-11-30", "A", "괜찮아!!") ;

        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.jyr),
                "numone", "Mom's Touch", "Psy Bugger","0" , "2016-11-30", "B", "별로인듯") ;


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
                Snackbar.make(view, "리뷰를 작성하시겠습니까?", 3000).setActionTextColor(Color.parseColor("#FF0000"))
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
                adapter.addItem(new BitmapDrawable(profile),
                        UserID, store_name, food_name,"0" , strCurDate, grade_text, type_comment);
            }
        }
    }
}

