package com.app.pawapp.DataAccess.DataAccessObject;

import android.content.Context;

import com.app.pawapp.DataAccess.Entity.Survey;
import com.app.pawapp.Util.Gson.GsonFactory;
import com.app.pawapp.Util.Util;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import static com.app.pawapp.Util.Util.URL;

public class SurveyDao implements Ws<Survey>{

    //private final String URL = "http://pawswcf-dev.us-west-1.elasticbeanstalk.com/Service";
    //private final String URL = "http://192.168.1.36:60602/Service";
    private static final String ENDPOINT = "SurveyService.svc";

    private static final String INSERT_METHOD = "New";
    private static final String UPDATE_METHOD = "Update";
    private static final String DELETE_METHOD = "Delete";
    private static final String FIND_METHOD = "Find";
    private static final String FIND_ALL_METHOD = "FindAll";
    private static final String LOGIN_METHOD = "Login";

    private static final String ID = "Id";
    private static final String NAME = "Name";
    private static final String AGE = "Age";
    private static final String DESCRIPTION = "Description";
    private static final String LOST_LAT = "LostLat";
    private static final String LOST_LON = "LostLon";
    private static final String RACE = "Race";
    private static final String SIZE = "Size";
    private static final String IMG_PATH = "ImagePath";
    private static final String IMG_EXTENSION = "ImageExtension";
    private static final String IMG_BASE_64 = "ImageBase64";

    private static final String RESPONSE = "Response";
    private static final String RESPONSE_CODE = "ResponseCode";

    private Context context;
    private Gson gson;

    SurveyDao(Context context){
        this.context = context;
        this.gson = GsonFactory.getWCFGson();
    }

    @Override
    public void insert(Survey toInsert, final WsCallback<Object> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s", URL, ENDPOINT, INSERT_METHOD),
                Util.HttpHelper.POST,
                gson.toJson(toInsert),
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Integer returnValue = 0;
                        if(response != null){
                            //NEED TO CREATE A CUSTOM ANONYMOUS SUBCLASS, REMOVING TYPE ERASURE
                            Type t = new TypeToken<WCFResponse<Integer>>(){}.getType();
                            WCFResponse<Integer> result = gson.fromJson(response.toString(), t);
                            returnValue = result.getResponse();
                        }

                        onResult.execute(returnValue);
                    }
                });
    }

    @Override
    public void update(Survey toUpdate, final WsCallback<Boolean> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s", URL, ENDPOINT, UPDATE_METHOD),
                Util.HttpHelper.PUT,
                gson.toJson(toUpdate),
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Boolean res = false;
                        if(response != null){
                            Type t = new TypeToken<WCFResponse<Boolean>>(){}.getType();
                            WCFResponse<Boolean> result = gson.fromJson(response.toString(), t);
                            res = result.getResponse();
                        }

                        onResult.execute(res);
                    }
                });
    }

    @Override
    public void delete(Object id, WsCallback<Boolean> onResult) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void find(Object id, final WsCallback<Survey> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s/%s", URL, ENDPOINT, FIND_METHOD, id),
                Util.HttpHelper.GET,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Survey res = null;
                        if(response != null){
                            WCFResponse<Survey> result = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Survey.class));
                            res = result.getResponse();
                        }

                        onResult.execute(res);
                    }
                });
    }

    @Override
    public void findAll(WsCallback<List<Survey>> onResult) {
        throw new RuntimeException("Not implemented");
    }
}
