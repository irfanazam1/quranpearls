package com.pureapps.model;

import java.io.Serializable;
import java.util.List;

public class Sura implements Serializable
{
    private String name;
    private List<Aya> aya;
    private String index;

    public String getName (){
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public List<Aya> getAya () {
        return aya;
    }

    public void setAya (List<Aya> aya) {
        this.aya = aya;
    }

    public String getIndex () {
        return index;
    }

    public void setIndex (String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "ClassPojo [name = "+name+", aya = "+aya+", index = "+index+"]";
    }
}
