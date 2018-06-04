package com.app.pawapp.DataAccess.DataAccessObject;

import android.content.Context;

import com.app.pawapp.DataAccess.Entity.Specie;
import com.app.pawapp.Util.Gson.GsonFactory;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SpecieDao implements Ws<Specie>{

    private static final String ENDPOINT = "SpecieService.svc";

    private static final String INSERT_METHOD = "New";
    private static final String UPDATE_METHOD = "Update";
    private static final String DELETE_METHOD = "Delete";
    private static final String FIND_METHOD = "Find";
    private static final String FIND_ALL_METHOD = "FindAll";
    private static final String LOGIN_METHOD = "Login";

    private Gson gson;
    private Context context;

    SpecieDao(Context context){
        this.context = context;
        gson = GsonFactory.getWCFGson();
    }

    @Override
    public void insert(Specie toInsert, WsCallback<Object> onResult) {

    }

    @Override
    public void update(Specie toUpdate, WsCallback<Boolean> onResult) {

    }

    @Override
    public void delete(Object id, WsCallback<Boolean> onResult) {

    }

    @Override
    public void find(Object id, WsCallback<Specie> onResult) {

    }

    @Override
    public void findAll(final WsCallback<List<Specie>> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s", Util.URL, ENDPOINT, FIND_ALL_METHOD),
                Util.HttpHelper.GET,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<Specie> lSpecie = new ArrayList<>();
                        if(response != null){
                            WCFResponse<List<Specie>> result = gson.fromJson(response.toString(),
                                    Util.getType(WCFResponse.class, Util.getType(List.class, Specie.class)));
                            lSpecie = result.getResponse();
                        }
                        onResult.execute(lSpecie);
                    }
                });
    }
}
