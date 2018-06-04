package com.app.pawapp.DataAccess.DataAccessObject;

import android.content.Context;

import com.app.pawapp.DataAccess.Entity.Race;
import com.app.pawapp.Util.Gson.GsonFactory;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RaceDao implements Ws<Race>{

    private static final String ENDPOINT = "RaceService.svc";

    private static final String INSERT_METHOD = "New";
    private static final String UPDATE_METHOD = "Update";
    private static final String DELETE_METHOD = "Delete";
    private static final String FIND_METHOD = "Find";
    private static final String FIND_ALL_METHOD = "FindAll";
    private static final String LOGIN_METHOD = "Login";

    private Gson gson;
    private Context context;

    RaceDao(Context context){
        this.context = context;
        gson = GsonFactory.getWCFGson();
    }

    @Override
    public void insert(Race toInsert, WsCallback<Object> onResult) {

    }

    @Override
    public void update(Race toUpdate, WsCallback<Boolean> onResult) {

    }

    @Override
    public void delete(Object id, WsCallback<Boolean> onResult) {

    }

    @Override
    public void find(Object id, WsCallback<Race> onResult) {

    }

    @Override
    public void findAll(WsCallback<List<Race>> onResult) {

    }

    public void findAll(int specieId, final WsCallback<List<Race>> onResult) {
        Util.HttpHelper.makeRequest(String.format(Locale.getDefault(), "%s/%s/%s/%d", Util.URL, ENDPOINT, FIND_ALL_METHOD, specieId),
                Util.HttpHelper.GET,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<Race> lRace = new ArrayList<>();
                        if(response != null){
                            WCFResponse<List<Race>> result = gson.fromJson(response.toString(),
                                    Util.getType(WCFResponse.class, Util.getType(List.class, Race.class)));
                            lRace = result.getResponse();
                        }
                        onResult.execute(lRace);
                    }
                });
    }
}
