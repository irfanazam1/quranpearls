package com.pureapps.quranpearls;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.TextViewCompat;

import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Calendar;

import mehdi.sakout.aboutpage.Element;

public class AboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        setupPageContent();
    }

    private void setupPageContent(){
        mehdi.sakout.aboutpage.Element element = new Element();
        element.setTitle("Quran Pearls");
        View aboutPage = new mehdi.sakout.aboutpage.AboutPage(this)
                .isRTL(false)
                .setDescription(getResources().getString(R.string.description))
                .addEmail(getResources().getString(R.string.email))
                .addPlayStore(getResources().getString(R.string.playstore))
                .addGitHub(getResources().getString(R.string.github))
                .addItem(element)
                .addItem(getCopyRight())
                .create();
        setContentView(aboutPage);
    }

    private Element getCopyRight(){
        Element element = new Element();
        String copyRight = String.format("Copyright - PureApps (%s)", Calendar.getInstance().get(Calendar.YEAR));
        element.setTitle(copyRight);
        return element;
    }
}
