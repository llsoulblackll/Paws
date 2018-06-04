package com.app.pawapp.DataAccess.DataAccessObject;

import android.content.Context;

import com.app.pawapp.DataAccess.Entity.District;
import com.app.pawapp.Util.Gson.GsonFactory;
import com.app.pawapp.Util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.app.pawapp.Util.Util.URL;

public class DistrictDao implements Ws<District> {

    private static final String ENDPOINT = "DistrictService.svc";

    private static final String INSERT_METHOD = "New";
    private static final String UPDATE_METHOD = "Update";
    private static final String DELETE_METHOD = "Delete";
    private static final String FIND_METHOD = "Find";
    private static final String FIND_ALL_METHOD = "FindAll";
    private static final String LOGIN_METHOD = "Login";

    private Context context;
    private Gson gson;

    DistrictDao(Context context) {
        this.context = context;
        this.gson = GsonFactory.getWCFGson();
    }

    @Override
    public void insert(District toInsert, WsCallback<Object> onResult) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void update(District toUpdate, WsCallback<Boolean> onResult) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void delete(Object id, WsCallback<Boolean> onResult) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void find(Object id, WsCallback<District> onResult) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void findAll(final WsCallback<List<District>> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s", URL, ENDPOINT, FIND_ALL_METHOD),
                Util.HttpHelper.GET,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {

                        List<District> lDistrict = new ArrayList<>();

                        if (response != null) {
                            //Type t = new TypeToken<WCFResponse<List<District>>>(){}.getType();
                            WCFResponse<List<District>> result = gson.fromJson(response.toString(),
                                    Util.getType(WCFResponse.class, Util.getType(List.class, District.class)));
                            lDistrict = result.getResponse();
                        }

                        onResult.execute(lDistrict);
                    }
                });
    }
}
