package com.app.pawapp.DataAccess.DataAccessObject;

import android.support.annotation.NonNull;

import java.util.List;

public interface Ws<T> {
    void insert(T toInsert, WsCallback<Object> onResult);
    void update(T toUpdate, WsCallback<Boolean> onResult);
    void delete(Object id, WsCallback<Boolean> onResult);
    void find(Object id, WsCallback<T> onResult);
    void findAll(WsCallback<List<T>> onResult);

    interface WsCallback<T>{
        void execute(T response);
    }

}

