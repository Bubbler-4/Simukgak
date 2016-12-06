package hanjo.simukgak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kwon Ohhyun on 2016-12-04.
 */

public class ManageMenuAdapter extends BaseAdapter implements View.OnClickListener {
    public interface ListBtnClickListener {
        void onListBtnClick(int position, View v);
    }

    private int resourceId;
    private int listSortStatus = 0;
    private ManageMenuAdapter.ListBtnClickListener listBtnClickListener;
    private ArrayList<ManageMenuItem> listViewItemList = new ArrayList<>(); // Adapter에 추가된 데이터를 저장하기 위한 ArrayList

    // ListViewAdapter의 생성자
    public ManageMenuAdapter(Context context, int resource, ManageMenuAdapter.ListBtnClickListener clickListener) {

        this.listBtnClickListener = clickListener;
        this.resourceId = resource;
    }

    public void onClick(View v) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int) v.getTag(), v);
        }
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.orderview*/, parent, false);
        }

        TextView rankTextView = (TextView) convertView.findViewById(R.id.rankText);
        TextView productTextView = (TextView) convertView.findViewById(R.id.productText);
        TextView priceTextView = (TextView) convertView.findViewById(R.id.priceText);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ManageMenuItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영

        rankTextView.setText(String.format(Locale.KOREA, "%d", position + 1));
        productTextView.setText(listViewItem.getProduct());
        priceTextView.setText(String.format(Locale.KOREA, "%d", listViewItem.getPrice()));

        //delete button
        Button deleteButton = (Button) convertView.findViewById(R.id.deleteButton);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(this);

        Button editButton = (Button) convertView.findViewById(R.id.editButton);
        editButton.setTag(position);
        editButton.setOnClickListener(this);

        return convertView;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public ManageMenuItem getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String product, int price) {
        ManageMenuItem item = new ManageMenuItem();

        item.setProduct(product);
        item.setPrice(price);

        listViewItemList.add(item);
    }

    public void deleteItem(int position) {
        listViewItemList.remove(position);
    }

    public void editItem(int position, String product, int price) {
        getItem(position).setProduct(product);
        getItem(position).setPrice(price);
    }
}
