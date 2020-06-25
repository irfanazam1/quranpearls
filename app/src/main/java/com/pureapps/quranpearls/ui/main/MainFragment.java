package com.pureapps.quranpearls.ui.main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import com.github.clans.fab.FloatingActionMenu;
import com.pureapps.quranpearls.AboutPage;
import com.pureapps.quranpearls.AppContext;
import com.pureapps.quranpearls.R;
import com.pureapps.quranpearls.SettingsActivity;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    public static MainFragment newInstance() {
        return new MainFragment();
    }
    private TextView txtCategory1;
    private TextView txtCategory2;
    private TextView txtCategory3;
    private TextView txtCategory4;
    private TextView txtCategory5;
    private TextView txtCategory6;
    private GridLayout categoryGrid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        prepareCategories();
        FloatingActionMenu floatingMenu = this.getActivity().findViewById(R.id.settingsAction);
        floatingMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                openSettings();
            }
        });

        FloatingActionMenu aboutPage = this.getActivity().findViewById(R.id.aboutPage);
        aboutPage.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                openAboutPage();
            }
        });
    }

    private void prepareCategories(){
        categoryGrid = (GridLayout) this.getActivity().findViewById(R.id.categoryGrid);
        txtCategory1 = (TextView) this.getActivity().findViewById(R.id.txtCategory1);
        txtCategory2 = (TextView) this.getActivity().findViewById(R.id.txtCategory2);
        txtCategory3 = (TextView) this.getActivity().findViewById(R.id.txtCategory3);
        txtCategory4 = (TextView) this.getActivity().findViewById(R.id.txtCategory4);
        txtCategory5 = (TextView) this.getActivity().findViewById(R.id.txtCategory5);
        txtCategory6 = (TextView) this.getActivity().findViewById(R.id.txtCategory6);
        ArrayAdapter<String> adapter;
        String[] translations = null;
        if(AppContext.getInstance(getContext()).getTranslationLanguage().equals("English")) {
            translations = getResources().getStringArray(R.array.English);
            txtCategory1.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.arefruqaa_regular));
            txtCategory2.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.arefruqaa_regular));
            txtCategory3.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.arefruqaa_regular));
            txtCategory4.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.arefruqaa_regular));
            txtCategory5.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.arefruqaa_regular));
            txtCategory6.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.arefruqaa_regular));

        }
        else{
            translations = getResources().getStringArray(R.array.Urdu);
            txtCategory1.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.amiri_regular));
            txtCategory2.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.amiri_regular));
            txtCategory3.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.amiri_regular));
            txtCategory4.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.amiri_regular));
            txtCategory5.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.amiri_regular));
            txtCategory6.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.amiri_regular));
        }
        txtCategory1.setText(translations[0]);
        txtCategory2.setText(translations[1]);
        txtCategory3.setText(translations[2]);
        txtCategory4.setText(translations[3]);
        txtCategory5.setText(translations[4]);
        txtCategory6.setText(translations[5]);

    }

    public void openSettings() {
        Intent intent = new Intent(this.getContext(), SettingsActivity.class);
        startActivity(intent);
    }

    public void openAboutPage(){
        Intent intent = new Intent(this.getContext(), AboutPage.class);
        startActivity(intent);
    }

}
