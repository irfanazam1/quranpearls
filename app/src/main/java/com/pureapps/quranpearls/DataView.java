package com.pureapps.quranpearls;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataView extends AppCompatActivity {
    private AppContext appContext;
    private int index;
    private TextView txtAya;
    private TextView txtTranslation;
    private TextView txtPageNumber;
    private TextView txtSuraName;
    float x1 = 0.0f;
    float x2 = 0.0f;
    static final int MIN_DISTANCE = 150;
    private String suraName;
    private List<String> arabicAyas;
    private List<String> translationAyas;

    private void init(){
        appContext = AppContext.getInstance(this);
        List<Map<String, Map<String, String>>> data = appContext.getSearchResults();
        if(data != null && data.size() > 0) {
            arabicAyas = new LinkedList<>();
            translationAyas = new LinkedList<>();
            for (String aya : data.get(0).get(suraName).keySet()) {
                arabicAyas.add(data.get(0).get(suraName).get(aya));
            }
            for (String aya : data.get(1).get(suraName).keySet()) {
                translationAyas.add(data.get(1).get(suraName).get(aya));
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);
        txtAya = (TextView) findViewById(R.id.txtHadith);
        txtTranslation = (TextView) findViewById(R.id.txtTranslation);
        txtPageNumber = (TextView) findViewById(R.id.pageNumber);
        txtSuraName = (TextView) findViewById(R.id.suraName);
        suraName =  getIntent().getStringExtra("sura");
        init();
        setContent(0);
    }

    private void setContent(int index){
        txtSuraName.setText(suraName);
        txtAya.setText(arabicAyas.get(index));
        if (appContext.isStandardTextSize()) {
            txtAya.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            txtTranslation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        } else {
            txtAya.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
            txtTranslation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        }
        txtTranslation.setText(translationAyas.get(index));
        String strPageNumber = String.format("%s/%s", index + 1, arabicAyas.size());
        txtPageNumber.setText(strPageNumber);
        if(appContext.getTranslationLanguage().equals("English")){
            txtTranslation.setTypeface(ResourcesCompat.getFont(this, R.font.arefruqaa_regular));
        }
        else{
            txtTranslation.setTypeface(ResourcesCompat.getFont(this, R.font.amiri_regular));
        }
    }

    private void navigateContent(boolean forward){
        if(forward){
            index++;
        }
        else{
            index--;
        }
        if(index < 0){
            index = 0;
        }

        else if(index >= arabicAyas.size()){
            index = arabicAyas.size() - 1;
        }
        setContent(index);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        navigateContent(false);
                    }

                    // Right to left swipe action
                    else
                    {
                        navigateContent(true);
                    }

                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void moveNext(View view) {
        navigateContent(true);
    }

    public void movePrev(View view) {
        navigateContent(false);
    }
}
