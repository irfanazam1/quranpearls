package com.pureapps.quranpearls;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CategoryView extends AppCompatActivity {
    private GridLayout categoryGrid;
    private String query;
    private AppContext appContext;
    private List<Map<String, Map<String, String>>> dataList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        categoryGrid = (GridLayout) findViewById(R.id.category_view_grid);
        query = getIntent().getStringExtra("query");
        appContext = AppContext.getInstance(this);
        dataList = appContext.queryData(query);
        buildContent();
    }

    public void buildContent(){
        GridLayout gridLayout = (GridLayout) findViewById(R.id.category_view_grid);
        gridLayout.removeAllViewsInLayout();
        if(dataList != null && dataList.size() > 0){
            int numCards = dataList.get(0).size();
            int numCols = numCards / 4;
            int numRows = (numCards % 2 == 0) ? (numCards / 2) : (numCards / 2) + 1;
            gridLayout.setColumnCount(numCols);
            gridLayout.setRowCount(numRows);
            float layoutWeight = numCards / 8;
            for(String sura : dataList.get(0).keySet()){
                CardView cardView = new CardView(this);
                cardView.setTag(sura);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.setMargins(16,16,16,16);
                cardView.setCardElevation(15.0f);
                cardView.setRadius(30.0f);
                cardView.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sura_card, null));
                layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, layoutWeight);
                layoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, layoutWeight);
                cardView.setLayoutParams(layoutParams);
                gridLayout.addView(cardView);;
                FrameLayout.LayoutParams textViewLayout = new FrameLayout.LayoutParams(0, 0);
                textViewLayout.width = FrameLayout.LayoutParams.MATCH_PARENT;
                textViewLayout.height = FrameLayout.LayoutParams.MATCH_PARENT;
                textViewLayout.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
                TextView textSuraName = new TextView(this);
                textSuraName.setLayoutParams(textViewLayout);
                textSuraName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0f);
                textSuraName.setTypeface(ResourcesCompat.getFont(this, R.font.amiri_bold));
                textSuraName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                String label = String.format("%s", sura);
                textSuraName.setText(label);
                textSuraName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.text_color, null));
                textSuraName.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                cardView.addView(textSuraName);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), DataView.class);
                        intent.putExtra("sura", v.getTag().toString());
                        startActivity(intent);
                    }
                });
             }
        }
    }

}
