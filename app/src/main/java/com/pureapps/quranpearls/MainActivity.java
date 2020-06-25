package com.pureapps.quranpearls;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.github.clans.fab.FloatingActionMenu;
import com.pureapps.quranpearls.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        loadSettings();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    public void openCategoryView(View view) {
        Intent intent = new Intent(this, CategoryView.class);
        intent.putExtra("query", view.getTag().toString());
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void loadSettings(){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        AppContext.getInstance(this).setTranslationLanguage(sharedPref.getString(getResources().getString(R.string.language_key),
                getResources().getString(R.string.default_language)));
        Boolean textSizePref = sharedPref.getBoolean(getResources().getString(R.string.text_size_key), false);
        AppContext.getInstance(this).setStandardTextSize(!textSizePref);
    }

}
