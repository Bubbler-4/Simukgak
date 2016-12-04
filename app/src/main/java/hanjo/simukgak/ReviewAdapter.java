package hanjo.simukgak;

/**
 * Created by lg on 2016-12-04.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Review> reviewList = new ArrayList<Review>() ;

    // ListViewAdapter의 생성자
    public ReviewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return reviewList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView7) ;
        TextView idTextView = (TextView) convertView.findViewById(R.id.textView14) ;
        TextView storeTextView = (TextView) convertView.findViewById(R.id.textView16) ;
        TextView foodTextView = (TextView) convertView.findViewById(R.id.textView17) ;
        final TextView n_likeTextView = (TextView) convertView.findViewById(R.id.textView18);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.textView13) ;
        TextView gradeTextView = (TextView) convertView.findViewById(R.id.textView15) ;
        TextView commentTextView = (TextView) convertView.findViewById(R.id.textView11) ;

        // Data Set(reviewList)에서 position에 위치한 데이터 참조 획득
        final Review review = reviewList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(review.getProfile());
        idTextView.setText(review.getId());
        storeTextView.setText(review.getStore_name());
        foodTextView.setText(review.getFood_name());
        n_likeTextView.setText(review.getName_like());
        timeTextView.setText(review.getTime());
        gradeTextView.setText(review.getGrade());
        commentTextView.setText(review.getComment());

        Button likeButton = (Button) convertView.findViewById(R.id.button);
        likeButton.setTag(position);
        likeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                int to = Integer.parseInt(review.getName_like());
                to = to + 1;
                String _to = Integer.toString(to);
                review.setName_like(_to);
                n_likeTextView.setText(review.getName_like());
                // TODO : click event
            }
        });
        // 좋아요 눌러주기

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return reviewList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String id, String s_n, String f_n, String n_l, String time, String grade, String comment) {
        Review item = new Review();
        item.setProfile(icon);
        item.setId(id);
        item.setStore_name(s_n);
        item.setFood_name(f_n); item.setName_like(n_l);
        item.setTime(time);
        item.setGrade(grade);
        item.setComment(comment);

        reviewList.add(item);
    }
    public void deleteItem(int position)
    {
        reviewList.remove(position);
    }
}
