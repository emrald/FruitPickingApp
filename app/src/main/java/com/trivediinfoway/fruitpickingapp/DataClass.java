package com.trivediinfoway.fruitpickingapp;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by TI A1 on 01-05-2018.
 */

public class DataClass extends RealmObject implements Parcelable {

    String fruit;
    String weight;
    String number;
    String time_of_record;
    String worker_name;

    public DataClass() {
       super();
    }

    protected DataClass(Parcel in) {
        worker_name = in.readString();
        number = in.readString();
        time_of_record = in.readString();
        fruit = in.readString();
        weight = in.readString();
    }

    public static final Creator<DataClass> CREATOR = new Creator<DataClass>() {
        @Override
        public DataClass createFromParcel(Parcel in) {
            return new DataClass(in);
        }

        @Override
        public DataClass[] newArray(int size) {
            return new DataClass[size];
        }
    };

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime_of_record() {
        return time_of_record;
    }

    public void setTime_of_record(String time_of_record) {
        this.time_of_record = time_of_record;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(worker_name);
        parcel.writeString(number);
        parcel.writeString(time_of_record);
        parcel.writeString(fruit);
        parcel.writeString(weight);
    }
}
