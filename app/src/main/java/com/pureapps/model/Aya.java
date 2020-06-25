package com.pureapps.model;

import java.io.Serializable;

public class Aya implements Serializable
{
    private String index;
    private String text;
    private String content;

    public String getIndex () {
        return index;
    }

    public void setIndex (String index) {
        this.index = index;
    }

    public String getText () {
        return text;
    }

    public void setText (String text) {
        this.text = text;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [index = "+index+", text = "+text+", content = "+content+"]";
    }
}