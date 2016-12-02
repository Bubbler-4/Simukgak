package hanjo.simukgak;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Kwon Ohhyun on 2016-11-27.
 */



public class CreateDutchListViewAdapter extends BaseAdapter implements View.OnClickListener{

    public interface ListBtnClickListener {
        void onListBtnClick(int position, View v);
    }

    private int resourceId;
    private ListBtnClickListener listBtnClickListener;
    private ArrayList<EditText> nameList = new ArrayList<>();
    private ArrayList<EditText> priceList = new ArrayList<>();
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

        final EditText nameEditText = (EditText) convertView.findViewById(R.id.NameEdit);
        final EditText priceEditText = (EditText) convertView.findViewById(R.id.PriceEdit);
        Spinner productSpinner = (Spinner) convertView.findViewById(R.id.productSpin);

        //값을 가져오기 위해 리스트에 저장
        if(nameList.size() == position) {
            nameList.add(nameEditText);
            Log.d("adapter", "!");
        }
        if(priceList.size() == position)
            priceList.add(priceEditText);
        if(productList.size() == position)
            productList.add(productSpinner);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final CreateDutchItem createDutchItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        nameEditText.setText(createDutchItem.getName());
        priceEditText.setText(String.format(Locale.KOREA, "%d", createDutchItem.getPrice()));
        final String[] productArr = new String[createDutchItem.getProductList().size()];
        for(int i = 0; i<createDutchItem.getProductList().size(); i++)
        {
            productArr[i] = createDutchItem.getProductList().get(i);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, productArr);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(arrayAdapter);
        productSpinner.setSelection(createDutchItem.getProductIndex());
        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0; i<getCount(); i++)
                {
                    if(i < nameList.size()) {
                        getItem(i).setName(nameList.get(i).getText().toString());
                        getItem(i).setProduct(productList.get(i).getSelectedItem().toString());
                    }
                }
                nameEditText.setText(createDutchItem.getName());
                priceEditText.setText(String.format(Locale.KOREA, "%d", createDutchItem.getPrice()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }

        );

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                renewItem();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
    public void addItem(String name, int[] price, ArrayList<String> product) {
        CreateDutchItem item = new CreateDutchItem();
        item.setName(name);
        item.setPriceList(price);
        item.setProductList(product);
        listViewItemList.add(item);
    }

    //아이템 제거
    public void deleteItem(int position) {
        nameList.remove(position);
        priceList.remove(position);
        productList.remove(position);
        listViewItemList.remove(position);

    }

    //아이템 갱신
    public void renewItem() {
        for(int i=0; i<getCount(); i++)
        {
            if(i < nameList.size()) {
                getItem(i).setName(nameList.get(i).getText().toString());

                if (getIndexByProduct(productList.get(i).getSelectedItem().toString()) == getItem(i).getProductIndex())
                    getItem(i).setProduct(productList.get(i).getSelectedItem().toString());

                if (priceList.get(i).getText().toString().equals(""))
                    getItem(i).setPrice(0);
                else
                    getItem(i).setPrice(Integer.parseInt(priceList.get(i).getText().toString()));
            }
        }

    }

    private int getIndexByProduct(String str) {
        ArrayList<String> list = getItem(0).getProductList();
        for(int i = 0; i<getItem(0).getProductList().size(); i++)
        {
            if(list.get(i).equals(str))
                return i;
        }
        return -1;
    }

    /**
     *
     * @param product 제품 배열
     * @param price 가격 배열
     * @param amount 수량 배열
     * @return 모든 데이터가 입력되었으면 null 반환, 아니면 에러 메시지를 반환
     */
    public String AllDataSelected(ArrayList<String> product, int[] price, int[] amount) {
        int count = 0;
        int total = 0;
        boolean name;
        for(int i = 0; i<product.size(); i++)
        {
            count = 0;
            total = 0;
            for(int j = 0; j<getCount(); j++)
            {
                if(product.get(i).equals(getItem(j).getProduct())) {
                    count++;
                    total = total + getItem(j).getPrice();
                }
            }
            Log.d("CDAdapter", Integer.toString(total) + " " + Integer.toString(price[i]*amount[i]));

            if(count < amount[i])
                return "0";
            else if(total < price[i]*amount[i])
                return "1" + "," + getItem(i).getProduct() + "," + Integer.toString(price[i]*amount[i] - total);
            else if(total > price[i]*amount[i])
                return "2" + "," + getItem(i).getProduct() + "," + Integer.toString(total - price[i]*amount[i]);
        }
        for(int i = 0; i<getCount(); i++)
        {
            if(getItem(i).getName().equals(""))
                return "3";
        }
        return null; //its ok

    }

}
