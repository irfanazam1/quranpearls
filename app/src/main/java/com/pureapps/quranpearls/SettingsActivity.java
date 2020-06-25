package com.pureapps.quranpearls;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            final Preference languagePref = (Preference) findPreference(getResources().getString(R.string.language_key));
            final Preference optionPreference = (Preference) findPreference(getResources().getString(R.string.text_size_key));
            setTextSizePrefTitle(optionPreference, AppContext.getInstance(getContext()).isStandardTextSize());
            if (languagePref != null) {
                languagePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        AppContext.getInstance(getContext()).setTranslationLanguage((String) newValue);
                        return true;
                    }
                });
            }
            optionPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Boolean value = (Boolean) newValue;
                    AppContext.getInstance(getContext()).setStandardTextSize(!value);
                    setTextSizePrefTitle(optionPreference, !value);
                    return true;
                }
            });
        }
        private void setTextSizePrefTitle(Preference pref, boolean value){
            if(pref != null) {
                if (!value) {
                    pref.setTitle(getResources().getString(R.string.text_size_large));
                } else {
                    pref.setTitle(getResources().getString(R.string.text_size_standard));
                }
            }
        }
    }
}