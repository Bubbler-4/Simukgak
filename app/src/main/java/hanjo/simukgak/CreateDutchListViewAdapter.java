package hanjo.simukgak;

/**
 * Created by Kwon Ohhyun on 2016-11-29.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Created by Kwon Ohhyun on 2016-11-27.
 */



public class CreateDutchListViewAdapter extends BaseAdapter implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public interface ListBtnClickListener {
        void onListBtnClick(int position, View v);
    }

    private int resourceId;
    private ListBtnClickListener listBtnClickListener;
    private ArrayList<EditText> nameList = new ArrayList<>();
    private ArrayList<Spinner> productList = new ArrayList<>();


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
        TextView priceEditText = (TextView) convertView.findViewById(R.id.PriceEdit);
        Spinner productSpinner = (Spinner) convertView.findViewById(R.id.productSpin);

        //값을 가져오기 위해 리스트에 저장
        if(nameList.size() == position)
            nameList.add(nameEditText);
        if(productList.size() == position)
            productList.add(productSpinner);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CreateDutchItem createDutchItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        nameEditText.setText(createDutchItem.getName());
        priceEditText.setText(Integer.toString(createDutchItem.getPrice()) + "원 ");
        final String[] productArr = new String[createDutchItem.getProductList().size()];
        for(int i = 0; i<createDutchItem.getProductList().size(); i++)
        {
            productArr[i] = createDutchItem.getProductList().get(i);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, productArr);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(arrayAdapter);
        productSpinner.setSelection(createDutchItem.getProductIndex());
        productSpinner.setOnItemSelectedListener(this);

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

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        renewItem();
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
    public void addItem(String name, int[] price, ArrayList<String> product) {
        CreateDutchItem item = new CreateDutchItem();
        item.setName(name);
        item.setPriceList(price);
        item.setProductList(product);
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
        productList.remove(position);
        listViewItemList.remove(position);

    }

    public void renewItem()
    {
        for(int i=0; i<getCount(); i++)
        {
            getItem(i).setName(nameList.get(i).getText().toString());
            getItem(i).setProduct(productList.get(i).getSelectedItem().toString());
            getItem(i).setProductIndex(getIndexByProduct(getItem(i).getProduct()));
            getItem(i).setPrice(listViewItemList.get(i).getPrice());
        }

    }

    public int getIndexByProduct(String str)
    {
        ArrayList<String> list = getItem(0).getProductList();
        for(int i = 0; i<getItem(0).getProductList().size(); i++)
        {
            if(list.get(i).equals(str))
                return i;
        }
        return -1;
    }

    public int getSelectedAmount(String str)
    {
        int count = 0;
        for(int i = 0; i<getCount(); i++)
        {
            if(getItem(i).getProduct().equals(str))
                count++;
        }
        return count;
    }

}
