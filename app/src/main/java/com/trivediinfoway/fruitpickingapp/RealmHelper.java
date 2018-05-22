package com.trivediinfoway.fruitpickingapp;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by TI A1 on 18-04-2018.
 */

public class RealmHelper {
    Realm realm;
    AtomicLong primaryKeyValue;

    RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void save(final DataClass data1) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataClass data = realm.copyToRealm(data1);
            }
        });
    }
    public long getNextId() {
        return primaryKeyValue.getAndIncrement();
    }

    public ArrayList<DataClass> retrieve(String name) {
        ArrayList<DataClass> list = new ArrayList<DataClass>();
        RealmResults<DataClass> result = realm.where(DataClass.class).contains("worker_name",name).findAll();

        for (DataClass d : result) {
            DataClass data = new DataClass();
            data.setWorker_name(d.getWorker_name());
            data.setNumber(d.getNumber());
            data.setTime_of_record(d.getTime_of_record());
            data.setFruit(d.getFruit());
            data.setWeight(d.getWeight());
            list.add(data);
        }
        return list;
    }
    public ArrayList<DataClass> retrieveAllData() {
        ArrayList<DataClass> list = new ArrayList<DataClass>();
        RealmResults<DataClass> result = realm.where(DataClass.class).findAll();

        for (DataClass d : result) {
            DataClass data = new DataClass();
            data.setWorker_name(d.getWorker_name());
            data.setNumber(d.getNumber());
            data.setTime_of_record(d.getTime_of_record());
            data.setFruit(d.getFruit());
            data.setWeight(d.getWeight());
            list.add(data);
        }
        return list;
    }
    public ArrayList<String> retrieveNumber(String name)
    {
        ArrayList<String> list = new ArrayList<String>();
        RealmResults<DataClass> result = realm.where(DataClass.class).contains("worker_name",name).findAll();

        for (DataClass d : result) {
            list.add(d.getNumber()+"");
        }
        return list;
    }
    public ArrayList<String> retrieveByDate(String strdate)
    {
        ArrayList<String> list = new ArrayList<String>();
        RealmResults<DataClass> result = realm.where(DataClass.class).contains("time_of_record",strdate).findAll();

        for (DataClass d : result) {
            list.add(d.getWeight()+"");
        }
        return list;
    }
    public ArrayList<String> retrieveWeight(String name)
    {
        ArrayList<String> list = new ArrayList<String>();
        RealmResults<DataClass> result = realm.where(DataClass.class).contains("worker_name",name).findAll();

        for (DataClass d : result) {
            list.add(d.getWeight()+"");
        }
        return list;
    }
    public ArrayList<String> retrieveFruit(String name)
    {
        ArrayList<String> list = new ArrayList<String>();
        RealmResults<DataClass> result = realm.where(DataClass.class).contains("worker_name",name).findAll();

        for (DataClass d : result) {
            list.add(d.getFruit()+"");
        }
        return list;
    }
    public ArrayList<String> retrieveTiomeOfRecord(String name)
    {
        ArrayList<String> list = new ArrayList<String>();
        RealmResults<DataClass> result = realm.where(DataClass.class).contains("worker_name",name).findAll();

        for (DataClass d : result) {
            list.add(d.getTime_of_record()+"");
        }
        return list;
    }
    public void deleteDatabase(int id) {
        RealmResults<DataClass> result = realm.where(DataClass.class).findAll();
        DataClass d = result.get(id);
        realm.beginTransaction();
        result.deleteFromRealm(id);
        realm.commitTransaction();
    }
    /*public void UpdateDatabase(int id,String name) {
        RealmResults<DataClass> result = realm.where(DataClass.class).findAll();
        realm.beginTransaction();
        result.get(id).setEmploy_name(name);
        realm.commitTransaction();
    }*/
    /*public void UpdateDatabaseFlag(int id,boolean flag) {
        RealmResults<DataClass> result = realm.where(DataClass.class).findAll();
        realm.beginTransaction();
        result.get(id).setFlag(flag);
        realm.commitTransaction();
    }*/
}

