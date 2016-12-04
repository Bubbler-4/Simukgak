package hanjo.simukgak;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Review_write extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_write);  // layout xml 과 자바파일을 연결

        final TextView GradeText = (TextView) findViewById(R.id.Grade);

        SeekBar GradeBar = (SeekBar) findViewById(R.id.seekBar);
        GradeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekbar, int progress,
                                          boolean fromUser) {
                if (progress == 0) {
                    GradeText.setText("F");
                }
                if (progress > 0 && progress <= 1) {
                    GradeText.setText("D");
                }
                if (progress > 1 && progress <= 2) {
                    GradeText.setText("C");
                }
                if (progress > 2 && progress <= 3) {
                    GradeText.setText("B");
                }
                if (progress > 3 && progress <= 4) {
                    GradeText.setText("A");
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekbar) {
            }
        });

        Button OkButton = (Button) findViewById(R.id.ok);
        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                final EditText StoreName = (EditText) findViewById(R.id.Store_name);
                final EditText FoodName = (EditText) findViewById(R.id.Food_name);
                final EditText TypeComment = (EditText) findViewById(R.id.Type_Comment);

                if (StoreName.getText().toString().length() == 0) {
                    //공백일 때 처리할 내용
                } else {
                    if (FoodName.getText().toString().length() == 0) {
                        //공백일 때 처리할 내용
                    } else {
                        if (TypeComment.getText().toString().length() == 0) {
                            //공백일 때 처리할 내용
                        } else {
                            intent.putExtra("Store_name", StoreName.getText().toString());
                            intent.putExtra("Food_name", FoodName.getText().toString());
                            intent.putExtra("Grade_text", GradeText.getText().toString());
                            intent.putExtra("Type_comment", TypeComment.getText().toString());

                            setResult(RESULT_OK, intent);
                            Toast.makeText(Review_write.this, "입력 성공", Toast.LENGTH_SHORT).show();
                            finish();
                            //공백이 아닐 때 처리할 내용
                        }
                    }
                    //공백이 아닐 때 처리할 내용
                }
                //공백이 아닐 때 처리할 내용

            }
        });
        Button CancleButton = (Button) findViewById(R.id.cancle);
        CancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Review_write.this, "입력 취소", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

