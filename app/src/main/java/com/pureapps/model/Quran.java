package com.pureapps.model;

import java.io.Serializable;
import java.util.List;

public class Quran implements Serializable
{
    private List<Sura> sura;

    public List<Sura> getSura ()
    {
        return sura;
    }

    public void setSura (List<Sura> sura)
    {
        this.sura = sura;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sura = "+sura+"]";
    }
}
