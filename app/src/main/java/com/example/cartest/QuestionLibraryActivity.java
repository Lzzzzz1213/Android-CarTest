package com.example.cartest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author CodeLee
 * @data 2020年6月11日
 * @description 展示题库、选项判断
 * */

public class QuestionLibraryActivity extends AppCompatActivity {
    private Handler handler = new MyHandler();
    private TextView textView;
    private Button nextButton;
    private Button lastButton;
    private List<Data.ResultBean> resultBeans;
    private ProgressBar wait;
    private RadioButton radioButton_A;
    private RadioButton radioButton_B;
    private RadioButton radioButton_C;
    private RadioButton radioButton_D;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private GetItem getItem = new GetItem();
    private String trueItem;
    private LinearLayout multipleLayout;
    private LinearLayout radioLayout;
    private CheckBox checkButton_A;
    private CheckBox checkButton_B;
    private CheckBox checkButton_C;
    private CheckBox checkButton_D;
    private LinearLayout explainLayout;
    private int i = 1;
    private Bitmap bitmap;
    private String baseUrl = "";
    private LinearLayout lastAndNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionlibrary);
        textView = ((TextView) findViewById(R.id.textView));
        wait = ((ProgressBar) findViewById(R.id.wait));
        radioGroup = ((RadioGroup) findViewById(R.id.radioGroup));
        explainLayout = ((LinearLayout) findViewById(R.id.explainLayout));
        imageView = ((ImageView) findViewById(R.id.imageView));
        multipleLayout = ((LinearLayout) findViewById(R.id.multipleLayout));
        radioLayout = ((LinearLayout) findViewById(R.id.radioLayout));
        radioButton_A = ((RadioButton) findViewById(R.id.radioButton_A));
        radioButton_B = ((RadioButton) findViewById(R.id.radioButton_B));
        radioButton_C = ((RadioButton) findViewById(R.id.radioButton_C));
        radioButton_D = ((RadioButton) findViewById(R.id.radioButton_D));
        checkButton_A = ((CheckBox) findViewById(R.id.checkButton_A));
        checkButton_B = ((CheckBox) findViewById(R.id.checkButton_B));
        checkButton_C = ((CheckBox) findViewById(R.id.checkButton_C));
        checkButton_D = ((CheckBox) findViewById(R.id.checkButton_D));
        lastAndNextButton = ((LinearLayout) findViewById(R.id.lastAndNextButton));
        Intent intent = getIntent();
        baseUrl = intent.getStringExtra("url");
        if (baseUrl.contains("testType=order")){
            Toast.makeText(this, "顺序题库加载略慢，请稍等……", Toast.LENGTH_SHORT).show();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(baseUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    String result = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    Gson gson = new Gson();
                    Data data = gson.fromJson(result, Data.class);
                    resultBeans = data.getResult();
                    String thisQuestion = (resultBeans.get(i).getQuestion()).toString();
                    String optionsForThisQuestionA = (resultBeans.get(i).getItem1()).toString();
                    String optionsForThisQuestionB = (resultBeans.get(i).getItem2()).toString();
                    String optionsForThisQuestionC = (resultBeans.get(i).getItem3()).toString();
                    String optionsForThisQuestionD = (resultBeans.get(i).getItem4()).toString();
                    String theAnswerToThisQuestion = (resultBeans.get(i).getAnswer()).toString();
                    trueItem = ((String) getItem.returnAnswer(theAnswerToThisQuestion));
                    String thePhotoUrlToThisQuestion = (resultBeans.get(i).getUrl()).toString();
                    String theExplanationToThisQuestion = (resultBeans.get(i).getExplains()).toString();
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bitmap = getUrlImage(thePhotoUrlToThisQuestion);
                    bundle.putString("item1", optionsForThisQuestionA);
                    bundle.putString("item2", optionsForThisQuestionB);
                    bundle.putString("item3", optionsForThisQuestionC);
                    bundle.putString("item4", optionsForThisQuestionD);
                    bundle.putString("answer", theAnswerToThisQuestion);
                    bundle.putParcelable("image", bitmap);
                    bundle.putString("explanation", theExplanationToThisQuestion);
                    message.what = 100;
                    message.setData(bundle);
                    message.obj = thisQuestion;
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        nextButton = ((Button) findViewById(R.id.nextButton));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        i = i + 1;
                        String thisQuestion = (resultBeans.get(i).getQuestion()).toString();
                        String optionsForThisQuestionA = (resultBeans.get(i).getItem1()).toString();
                        String optionsForThisQuestionB = (resultBeans.get(i).getItem2()).toString();
                        String optionsForThisQuestionC = (resultBeans.get(i).getItem3()).toString();
                        String optionsForThisQuestionD = (resultBeans.get(i).getItem4()).toString();
                        String theAnswerToThisQuestion = (resultBeans.get(i).getAnswer()).toString();
                        trueItem = ((String) getItem.returnAnswer(theAnswerToThisQuestion));
                        String thePhotoUrlToThisQuestion = (resultBeans.get(i).getUrl()).toString();
                        String theExplanationToThisQuestion = (resultBeans.get(i).getExplains()).toString();
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bitmap = getUrlImage(thePhotoUrlToThisQuestion);
                        bundle.putString("item1", optionsForThisQuestionA);
                        bundle.putString("item2", optionsForThisQuestionB);
                        bundle.putString("item3", optionsForThisQuestionC);
                        bundle.putString("item4", optionsForThisQuestionD);
                        bundle.putString("answer", theAnswerToThisQuestion);
                        bundle.putString("url", (resultBeans.get(i).getUrl()).toString());
                        bundle.putParcelable("image", bitmap);
                        bundle.putString("explanation", theExplanationToThisQuestion);
                        message.what = 100;
                        message.setData(bundle);
                        message.obj = thisQuestion;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        lastButton = ((Button) findViewById(R.id.lastButton));
        lastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (i - 1 == 0) {
                            Message message = new Message();
                            message.what = 400;
                            message.obj = "no";
                            handler.sendMessage(message);
                        } else {
                            i = i - 1;
                            String thisQuestion = (resultBeans.get(i).getQuestion()).toString();
                            String optionsForThisQuestionA = (resultBeans.get(i).getItem1()).toString();
                            String optionsForThisQuestionB = (resultBeans.get(i).getItem2()).toString();
                            String optionsForThisQuestionC = (resultBeans.get(i).getItem3()).toString();
                            String optionsForThisQuestionD = (resultBeans.get(i).getItem4()).toString();
                            String theAnswerToThisQuestion = (resultBeans.get(i).getAnswer()).toString();
                            trueItem = ((String) getItem.returnAnswer(theAnswerToThisQuestion));
                            String thePhotoUrlToThisQuestion = (resultBeans.get(i).getUrl()).toString();
                            String theExplanationToThisQuestion = (resultBeans.get(i).getExplains()).toString();
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bitmap = getUrlImage(thePhotoUrlToThisQuestion);
                            bundle.putString("item1", optionsForThisQuestionA);
                            bundle.putString("item2", optionsForThisQuestionB);
                            bundle.putString("item3", optionsForThisQuestionC);
                            bundle.putString("item4", optionsForThisQuestionD);
                            bundle.putString("answer", theAnswerToThisQuestion);
                            bundle.putString("url", (resultBeans.get(i).getUrl()).toString());
                            bundle.putParcelable("image", bitmap);
                            bundle.putString("explanation", theExplanationToThisQuestion);
                            message.what = 100;
                            message.setData(bundle);
                            message.obj = thisQuestion;
                            handler.sendMessage(message);
                        }
                    }
                }).start();
            }
        });
    }

    public void Check(View view) {
        switch (view.getId()) {
            case R.id.radioButton_A:
                radioButton_A.setChecked(true);
                if (trueItem.equals("A")) {
                    Toast.makeText(this, "回答正确", Toast.LENGTH_SHORT).show();
                    radioButton_A.setTextColor(Color.rgb(0, 255, 127));
                } else {
                    Toast.makeText(this, "回答错误，真确的答案是:" + trueItem, Toast.LENGTH_SHORT).show();
                    radioButton_A.setTextColor(Color.rgb(255, 48, 48));
                    explainLayout.setVisibility(explainLayout.VISIBLE);
                }
                break;
            case R.id.radioButton_B:
                radioButton_B.setChecked(true);
                if (trueItem.equals("B")) {
                    Toast.makeText(this, "回答正确", Toast.LENGTH_SHORT).show();
                    radioButton_B.setTextColor(Color.rgb(0, 255, 127));
                } else {
                    Toast.makeText(this, "回答错误，真确的答案是:" + trueItem, Toast.LENGTH_SHORT).show();
                    radioButton_B.setTextColor(Color.rgb(255, 48, 48));
                    explainLayout.setVisibility(explainLayout.VISIBLE);
                }
                break;
            case R.id.radioButton_C:
                radioButton_C.setChecked(true);
                if (trueItem.equals("C")) {
                    Toast.makeText(this, "回答正确", Toast.LENGTH_SHORT).show();
                    radioButton_C.setTextColor(Color.rgb(0, 255, 127));
                } else {
                    Toast.makeText(this, "回答错误，真确的答案是:" + trueItem, Toast.LENGTH_SHORT).show();
                    radioButton_C.setTextColor(Color.rgb(255, 48, 48));
                    explainLayout.setVisibility(explainLayout.VISIBLE);
                }
                break;
            case R.id.radioButton_D:
                radioButton_D.setChecked(true);
                if (trueItem.equals("D")) {
                    Toast.makeText(this, "回答正确", Toast.LENGTH_SHORT).show();
                    radioButton_D.setTextColor(Color.rgb(0, 255, 127));
                } else {
                    Toast.makeText(this, "回答错误，真确的答案是:" + trueItem, Toast.LENGTH_SHORT).show();
                    radioButton_D.setTextColor(Color.rgb(255, 48, 48));
                    explainLayout.setVisibility(explainLayout.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    public void MultipleChoice(View view) {
        CheckBox checkBox = (CheckBox) view;
        boolean checked = checkBox.isChecked();
        switch (view.getId()) {
            case R.id.checkButton_A:
                checkButton_A.setChecked(true);
                if (checked) {
                    if (trueItem.contains("A")) {
                        Toast.makeText(this, "回答正确", Toast.LENGTH_SHORT).show();
                        checkButton_A.setTextColor(Color.rgb(0, 255, 127));
                    } else {
                        Toast.makeText(this, "回答错误，真确的答案是:" + trueItem, Toast.LENGTH_SHORT).show();
                        checkButton_A.setTextColor(Color.rgb(255, 48, 48));
                        explainLayout.setVisibility(explainLayout.VISIBLE);
                    }
                } else {
                    checkButton_A.setChecked(false);
                    checkButton_A.setTextColor(Color.rgb(0, 0, 0));
                }
                break;
            case R.id.checkButton_B:
                checkButton_B.setChecked(true);
                if (checked) {
                    if (trueItem.contains("B")) {
                        Toast.makeText(this, "回答正确", Toast.LENGTH_SHORT).show();
                        checkButton_B.setTextColor(Color.rgb(0, 255, 127));
                    } else {
                        Toast.makeText(this, "回答错误，真确的答案是:" + trueItem, Toast.LENGTH_SHORT).show();
                        checkButton_B.setTextColor(Color.rgb(255, 48, 48));
                        explainLayout.setVisibility(explainLayout.VISIBLE);
                    }
                } else {
                    checkButton_B.setChecked(false);
                    checkButton_B.setTextColor(Color.rgb(0, 0, 0));
                }
                break;
            case R.id.checkButton_C:
                checkButton_C.setChecked(true);
                if (checked) {
                    if (trueItem.contains("C")) {
                        Toast.makeText(this, "回答正确", Toast.LENGTH_SHORT).show();
                        checkButton_C.setTextColor(Color.rgb(0, 255, 127));
                    } else {
                        Toast.makeText(this, "回答错误，真确的答案是:" + trueItem, Toast.LENGTH_SHORT).show();
                        checkButton_C.setTextColor(Color.rgb(255, 48, 48));
                        explainLayout.setVisibility(explainLayout.VISIBLE);
                    }
                } else {
                    checkButton_C.setChecked(false);
                    checkButton_C.setTextColor(Color.rgb(0, 0, 0));
                }
                break;
            case R.id.checkButton_D:
                checkButton_D.setChecked(true);
                if (checked) {
                    if (trueItem.contains("D")) {
                        Toast.makeText(this, "回答正确", Toast.LENGTH_SHORT).show();
                        checkButton_D.setTextColor(Color.rgb(0, 255, 127));
                    } else {
                        Toast.makeText(this, "回答错误，真确的答案是:" + trueItem, Toast.LENGTH_SHORT).show();
                        checkButton_D.setTextColor(Color.rgb(255, 48, 48));
                        explainLayout.setVisibility(explainLayout.VISIBLE);
                    }
                } else {
                    checkButton_D.setChecked(false);
                    checkButton_D.setTextColor(Color.rgb(0, 0, 0));
                }
                break;
            default:
                break;
        }

    }
    /*
    * 进入下一题或上一题对页面初始化
    */
    public void initializationActivity() {
        explainLayout.setVisibility(explainLayout.GONE);
        radioGroup.clearCheck();
        radioButton_A.setTextColor(Color.rgb(0, 0, 0));
        radioButton_B.setTextColor(Color.rgb(0, 0, 0));
        radioButton_C.setTextColor(Color.rgb(0, 0, 0));
        radioButton_D.setTextColor(Color.rgb(0, 0, 0));
        radioButton_A.setEnabled(true);
        radioButton_B.setEnabled(true);
        radioButton_C.setEnabled(true);
        radioButton_D.setEnabled(true);
        checkButton_A.setTextColor(Color.rgb(0, 0, 0));
        checkButton_B.setTextColor(Color.rgb(0, 0, 0));
        checkButton_C.setTextColor(Color.rgb(0, 0, 0));
        checkButton_D.setTextColor(Color.rgb(0, 0, 0));
        checkButton_A.setChecked(false);
        checkButton_B.setChecked(false);
        checkButton_C.setChecked(false);
        checkButton_D.setChecked(false);
    }

    //获取网页图片
    private Bitmap getUrlImage(String url) {
        Bitmap bitmap = null;
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
//            httpURLConnection.setConnectTimeout(6000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    //使用Handler更新界面文字图片等
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                lastButton.setEnabled(true);
                initializationActivity();
                imageView.setVisibility(imageView.VISIBLE);
                lastAndNextButton.setVisibility(lastAndNextButton.VISIBLE);
                String answer = msg.getData().getString("answer");
                int type = Integer.parseInt(answer);
                String explanation = msg.getData().getString("explanation");
                String A = msg.getData().getString("item1");
                String B = msg.getData().getString("item2");
                String C = msg.getData().getString("item3");
                String D = msg.getData().getString("item4");
                String Url = msg.getData().getString("url");
                Bitmap bitmap = msg.getData().getParcelable("image");
                wait.setVisibility(wait.GONE);
                textView.setText((CharSequence) msg.obj);
                TextView explanationTextView = (TextView) findViewById(R.id.explanation);
                explanationTextView.setText(explanation);
                if (bitmap == null) {
                    imageView.setVisibility(imageView.GONE);
                } else {
                    imageView.setImageBitmap(bitmap);
                }
                if (type > 4) {
                    //多选
                    radioLayout.setVisibility(radioLayout.GONE);
                    multipleLayout.setVisibility(multipleLayout.VISIBLE);
                    checkButton_A.setText("A." + A);
                    checkButton_B.setText("B." + B);
                    checkButton_C.setText("C." + C);
                    checkButton_D.setText("D." + D);

                } else {
                    //单选
                    radioLayout.setVisibility(radioLayout.VISIBLE);
                    multipleLayout.setVisibility(multipleLayout.GONE);
                    radioButton_A.setText("A." + A);
                    radioButton_B.setText("B." + B);
                    radioButton_C.setText("C." + C);
                    radioButton_D.setText("D." + D);
                    if (B.equals("错误") || C.equals("") && D.equals("")) {
                        radioButton_C.setVisibility(radioButton_C.GONE);
                        radioButton_D.setVisibility(radioButton_D.GONE);
                    } else {
                        radioButton_C.setVisibility(radioButton_C.VISIBLE);
                        radioButton_D.setVisibility(radioButton_D.VISIBLE);
                    }
                }

            } else if (msg.what == 400) {
                Toast.makeText(QuestionLibraryActivity.this, "这已经是第一题了", Toast.LENGTH_SHORT).show();
                lastButton.setEnabled(false);
            }
        }
    }
}