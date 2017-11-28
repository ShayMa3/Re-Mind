package com.example.shay.remindmainscreen;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by per6 on 11/3/17.
 */

public class Task implements Parcelable {
    private String name;
    private String description;
    private String date;
    private String availability;
    private boolean star;
    private boolean habit;

    @Override
    public String toString() {
        return name;
    }

    public Task(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.star = false;
        this.habit = false;
    }

    public Task(){
        this.name = "Default Task";
        this.description = "Default Description";
        this.date = "00/00/0000";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public boolean isHabit() {
        return habit;
    }

    public void setHabit(boolean habit) {
        this.habit = habit;
    }

    protected Task(Parcel in) {
        name = in.readString();
        description = in.readString();
        date = in.readString();
        availability = in.readString();
        star = in.readByte() != 0x00;
        habit = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(availability);
        dest.writeByte((byte) (star ? 0x01 : 0x00));
        dest.writeByte((byte) (habit ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
