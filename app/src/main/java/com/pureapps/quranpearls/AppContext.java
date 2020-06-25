package com.pureapps.quranpearls;

import android.content.Context;

import java.util.List;
import java.util.Map;

public class AppContext {
    private String translationLanguage = "English";
    private boolean isStandardTextSize = true;
    private static volatile AppContext _instance;
    private DataContext dataContext;
    private List<Map<String, Map<String, String>>> searchResults;
    private AppContext(){}
    public static AppContext getInstance(Context context){
        if(_instance == null){
            _instance = new AppContext();
            _instance.dataContext = DataContext.getInstance(context);
        }
        return _instance;
    }

    public String getTranslationLanguage() {
        return translationLanguage;
    }

    public void setTranslationLanguage(String translationLanguage) {
        this.translationLanguage = translationLanguage;
    }

    public boolean isStandardTextSize() {
        return isStandardTextSize;
    }

    public void setStandardTextSize(boolean standardTextSize) {
        isStandardTextSize = standardTextSize;
    }

    public List<Map<String, Map<String, String>>> queryData(String query){
        searchResults = dataContext.findMatchingSuraWithAya(query, translationLanguage);
        return searchResults;
    }

    public List<Map<String, Map<String, String>>> getSearchResults(){
        return searchResults;
    }
}
