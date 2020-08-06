package com.example.cartest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/***
 * @author CodeLee
 * @data 2020年6月12日
 * @description 主页面的Activity逻辑
 */
public class HomePageActivity extends AppCompatActivity {
    /**
     * 服务器数据接口
     * 本程序所使用《阿凡达数据》提供的《驾考题库》接口
     * */
    private final String baseUrl = "";//自己注册申请（免费）
    RequestParameters requestParameters = new RequestParameters();
    private RadioButton subject_1;
    private RadioButton subject_4;
    private RadioButton A1;
    private RadioButton A2;
    private RadioButton B1;
    private RadioButton B2;
    private RadioButton C1;
    private RadioButton C2;
    private Button randButton;
    private Button orderButton;
    private RadioGroup radioGroupSubject;
    private RadioGroup radioGroupModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        subject_1 = ((RadioButton) findViewById(R.id.subject_1));
        subject_4 = ((RadioButton) findViewById(R.id.subject_1));
        A1 = ((RadioButton) findViewById(R.id.A1));
        A2 = ((RadioButton) findViewById(R.id.A2));
        B1 = ((RadioButton) findViewById(R.id.B1));
        B2 = ((RadioButton) findViewById(R.id.B2));
        C1 = ((RadioButton) findViewById(R.id.C1));
        C2 = ((RadioButton) findViewById(R.id.C2));
        randButton = ((Button) findViewById(R.id.randButton));
        orderButton = ((Button) findViewById(R.id.orderButton));
        radioGroupSubject = ((RadioGroup) findViewById(R.id.subject));
        radioGroupModel = ((RadioGroup) findViewById(R.id.model));
        randButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "";
                String model = "";
                String testType = "&testType=rand";
                String parameter = "";
                int subjectCount = radioGroupSubject.getChildCount();
                int modelCount = radioGroupModel.getChildCount();
                for (int i = 0; i < subjectCount; i++) {
                    RadioButton radioButton = ((RadioButton) radioGroupSubject.getChildAt(i));
                    if (radioButton.isChecked()) {
                        subject = "&subject=" + requestParameters.getSubject(i);
                        break;
                    }
                }
                for (int i = 0; i < modelCount; i++) {
                    RadioButton radioButton = ((RadioButton) radioGroupModel.getChildAt(i));
                    if (radioButton.isChecked()) {
                        model = "&model=" + requestParameters.getModel(i);
                        break;
                    }
                }
                parameter = subject + model + testType;
                String newUrl = baseUrl + parameter;
                Intent intent = new Intent(HomePageActivity.this, QuestionLibraryActivity.class);
                intent.putExtra("url", newUrl);
                startActivity(intent);
            }
        });
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "";
                String model = "";
                String testType = "&testType=order";
                String parameter = "";
                int subjectCount = radioGroupSubject.getChildCount();
                int modelCount = radioGroupModel.getChildCount();
                for (int i = 0; i < subjectCount; i++) {
                    RadioButton radioButton = ((RadioButton) radioGroupSubject.getChildAt(i));
                    if (radioButton.isChecked()) {
                        subject = "&subject=" + requestParameters.getSubject(i);
                    }
                }
                for (int i = 0; i < modelCount; i++) {
                    RadioButton radioButton = ((RadioButton) radioGroupModel.getChildAt(i));
                    if (radioButton.isChecked()) {
                        model = "&model=" + requestParameters.getModel(i);
                    }
                }
                parameter = subject + model + testType;
                String newUrl = baseUrl + parameter;
                Intent intent = new Intent(HomePageActivity.this, QuestionLibraryActivity.class);
                intent.putExtra("url", newUrl);
                startActivity(intent);
            }
        });
    }

}