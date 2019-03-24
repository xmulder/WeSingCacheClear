package com.jc.wesingcacheclear;

public class Wsc {

    private String wscTextView;
    private int wscImageId;

    public Wsc(String wscTextView,int wscImageId){
        this.wscImageId=wscImageId;
        this.wscTextView=wscTextView;
    }

    public String getWscTextView() {
        return wscTextView;
    }

    public int getWscImageId() {
        return wscImageId;
    }

}
