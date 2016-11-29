package hanjo.simukgak;

/**
 * Created by Kwon Ohhyun on 2016-11-29.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Created by Kwon Ohhyun on 2016-11-27.
 */



public class CreateDutchListViewAdapter extends BaseAdapter implements View.OnClickListener {

    public interface ListBtnClickListener {
        void onListBtnClick(int position, View v);
    }

    private int resourceId;
    private ListBtnClickListener listBtnClickListener;
    private ArrayList<EditText> nameList = new ArrayList<EditText>();
    private ArrayList<EditText> priceList = new ArrayList<EditText>();


    // ListViewAdapter의 생성자
    public CreateDutchListViewAdapter(Context context, int resource, ListBtnClickListener clickListener) {
        //super(context, resource);

        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
    }

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<CreateDutchItem> listViewItemList = new ArrayList<CreateDutchItem>();

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.listview_item*/, parent, false);
        }

        EditText nameEditText = (EditText) convertView.findViewById(R.id.NameEdit);
        EditText priceEditText = (EditText) convertView.findViewById(R.id.PriceEdit);
        if(nameList.size() == position)
            nameList.add(nameEditText);
        if(priceList.size() == position)
            priceList.add(priceEditText);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CreateDutchItem createDutchItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영

        nameEditText.setText(createDutchItem.getName());
        priceEditText.setText(Integer.toString(createDutchItem.getPrice()));

        Button deleteButton = (Button) convertView.findViewById(R.id.deleteButton);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(this);

        return convertView;
    }

    public void onClick(View v) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int) v.getTag(), v);
        }
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public CreateDutchItem getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String name, int price) {
        CreateDutchItem item = new CreateDutchItem();

        item.setName(name);
        item.setPrice(price);

        listViewItemList.add(item);
    }

    public void editItemName(int position, String name) {
        getItem(position).setName(name);
    }

    public void editItemPrice(int position, int price) {
        getItem(position).setPrice(price);
    }

    //아이템 제거
    public void deleteItem(int position) {
        nameList.remove(position);
        priceList.remove(position);
        listViewItemList.remove(position);
    }

    public void renewItem()
    {
        for(int i=0; i<getCount(); i++)
        {
            editItemName(i, nameList.get(i).getText().toString());
            editItemPrice(i, Integer.parseInt(priceList.get(i).getText().toString()));
        }

    }

}
