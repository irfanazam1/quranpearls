package com.pureapps.quranpearls;

import android.content.Context;

import com.pureapps.model.Aya;
import com.pureapps.model.Quran;
import com.pureapps.model.Sura;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataContext {
    private static volatile DataContext _instance;
    private DataContext(){}
    private Context context;
    private Map<String, Integer> resources = new HashMap<>();
    private Map<String, Quran> quranCache = new HashMap<>();

    public static DataContext getInstance(Context context){
        if(_instance == null){
            _instance = new DataContext();
            _instance.initContent();
            _instance.context = context;
        }
        return _instance;
    }

    private void initContent(){
        resources.put("English", R.raw.english);
        resources.put("Urdu", R.raw.urdu);
        resources.put("Arabic", R.raw.arabic);
    }

    public static Quran loadQuran(InputStream inputStream) throws Exception {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(inputStream);
            Quran quran = (Quran) objectInputStream.readObject();
            return quran;
        }
        finally {
            if(objectInputStream != null){
                try{
                    objectInputStream.close();
                }
                catch(Exception e){}
            }
        }

    }

    public List<Map<String, Map<String, String>>> findMatchingSuraWithAya(String query, String language){
        List<Map<String, Map<String, String>>> result = new LinkedList<>();
        try {
            Quran englishQuran = null;
            Quran arabicQuran = null;
            if(quranCache.containsKey("English")){
                englishQuran = quranCache.get("English");
            }
            else{
                englishQuran = loadQuran(context.getResources().openRawResource(resources.get("English")));
                quranCache.put("English", englishQuran);
            }
            if(quranCache.containsKey("Arabic")){
                arabicQuran = quranCache.get("Arabic");
            }
            else{
                arabicQuran = loadQuran(context.getResources().openRawResource(resources.get("Arabic")));
                quranCache.put("Arabic", arabicQuran);
            }
            if (englishQuran != null && englishQuran.getSura() != null) {
                Map<String, Map<String, String>> englishQuranMap = queryQuran(englishQuran, query);
                Map<String, Map<String, String>> arabicQuranMap = convertQuranToSuraMap(arabicQuran, true);
                result.add(getMatchingListOfAyasFromQuran(arabicQuranMap, englishQuranMap));
                if(language.equals("English")){
                    result.add(englishQuranMap);
                }
                else {
                    Quran translationQuran = null;
                    if(quranCache.containsKey(language)){
                        translationQuran = quranCache.get(language);
                    }
                    else {
                        translationQuran = loadQuran(context.getResources().openRawResource(resources.get(language)));
                        quranCache.put(language, translationQuran);
                    }
                    Map<String, Map<String, String>> translationQuranMap = convertQuranToSuraMap(translationQuran, false);
                    result.add(getMatchingListOfAyasFromQuran(translationQuranMap, englishQuranMap));
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public static Map<String, Map<String, String>> convertQuranToSuraMap(Quran quran, boolean addAyaNumber){
        Map<String, Map<String, String>> suraMap = new HashMap<>();
        for(Sura sura : quran.getSura()){
            Map<String, String> ayaMap = new HashMap<>();
            for(Aya aya : sura.getAya()){
                String ayaText = aya.getText();
                if(addAyaNumber) {
                    ayaText = String.format("%s - (%s)", aya.getText(), aya.getIndex());
                }
                ayaMap.put(aya.getIndex(), ayaText);
            }
            suraMap.put(sura.getName(), ayaMap);
        }
        return suraMap;
    }

    public static Map<String, Map<String, String>> queryQuran(Quran quran , String text){
        Map<String, Map<String, String>> result = new HashMap<>();
        for(Sura sura : quran.getSura()){
            Map<String, String> ayaMap  = new HashMap<>();
            for(Aya aya : sura.getAya()){
                if(aya.getText().contains(text)){
                    ayaMap.put(aya.getIndex(), aya.getText());
                }
            }
            if(ayaMap != null && ayaMap.size() > 0) {
                result.put(sura.getName(), ayaMap);
            }
        }
        return result;
    }

    public static Map<String, Map<String, String>> getMatchingListOfAyasFromQuran(Map<String, Map<String, String>> fullQuran, Map<String, Map<String, String>> referenceQuran ){
        Map<String, Map<String, String>> result = new HashMap<>();
        for(String sura : referenceQuran.keySet()){
            Map<String, String> ayaMap = referenceQuran.get(sura);
            Map<String, String> matchingAyaMap = new HashMap<>();
            for(String aya : ayaMap.keySet()){
                matchingAyaMap.put(aya, fullQuran.get(sura).get(aya));
            }
            result.put(sura, matchingAyaMap);
        }
        return result;
    }
}
