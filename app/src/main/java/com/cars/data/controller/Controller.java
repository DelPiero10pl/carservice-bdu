package com.cars.data.controller;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import java.util.List;

public abstract class Controller<T extends RealmModel> {
    public Realm realm;
    private Class model;
    public Controller(Class model) {
        this.model = model;
        this.realm = Realm.getDefaultInstance();
    }

    public List<T> findAll() {
        return query().findAll();
    }

    public boolean remove(int id) {
        final RealmResults result = query().equalTo("id", id).findAll();
        final boolean[] status = {false};
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                status[0] = result.deleteFirstFromRealm();
            }
        });
        return status[0];
    }

    public T findByID(int id) {
        return query().equalTo("id", id).findFirst();
    }

    protected Integer nexID() {
        return realm.where(model).max("id").intValue() + 1;
    }

    protected RealmQuery<T> query() {
        return realm.where(model);
    }

    protected T create() {
        return (T) realm.createObject(model);
    }
}
