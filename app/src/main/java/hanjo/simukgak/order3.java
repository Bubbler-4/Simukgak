package hanjo.simukgak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class order3 extends AppCompatActivity {
public CustomExpandableListViewAdapter adapter;
    public ExpandableListView expandableListView;
    public HashMap<String, ArrayList<ListViewItem>> category_itemList;
    public ArrayList<String> category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order3);
    }
}
