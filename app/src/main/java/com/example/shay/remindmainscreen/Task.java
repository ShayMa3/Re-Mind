package com.example.shay.remindmainscreen;

/**
 * Created by per6 on 10/26/17.
 */

public class Task {

    private String name;
    private String desc;
    private int doDate;
    private boolean completed;

    public Task(String name, String desc, int doDate, boolean completed)
    {
        this.name = name;
        this.desc = desc;
        this.doDate = doDate;
        this.completed = completed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDoDate() {
        return doDate;
    }

    public void setDoDate(int doDate) {
        this.doDate = doDate;
    }




}
