package com.app.pawapp.DataAccess.DataAccessObject;

import android.content.Context;

import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.Util.Gson.GsonFactory;
import com.app.pawapp.Util.Util;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.app.pawapp.Util.Util.URL;

public class OwnerDao implements Ws<Owner> {

    //private final String URL = "http://192.168.1.36:60602/Service";

    private static final String ENDPOINT = "OwnerService.svc";

    private static final String INSERT_METHOD = "New";
    private static final String UPDATE_METHOD = "Update";
    private static final String DELETE_METHOD = "Delete";
    private static final String FIND_METHOD = "Find";
    private static final String FIND_ALL_METHOD = "FindAll";
    private static final String LOGIN_METHOD = "Login";

    private static final String ID = "Id";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String NAME = "Name";
    private static final String LAST_NAME = "LastName";
    private static final String BIRTHDATE = "BirthDate";
    private static final String DNI = "DNI";
    private static final String EMAIL = "EMail";
    private static final String ADDRESS = "Address";
    private static final String PHONE_NUMBER = "PhoneNumber";
    private static final String PROFILE_PICTURE = "ProfilePicture";
    private static final String DISTRICT_ID = "DistrictId";

    private static final String IMAGE_BASE_64 = "ImageBase64";
    private static final String IMAGE_EXTENSION = "ImageExtension";

    private static final String RESPONSE = "Response";
    private static final String RESPONSE_CODE = "ResponseCode";

    private Context context;

    private Gson gson;

    OwnerDao(Context context) {
        this.context = context;
        gson = GsonFactory.getWCFGson();
    }

    @Override
    public void insert(Owner toInsert, final WsCallback<Object> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s", URL, ENDPOINT, INSERT_METHOD),
                Util.HttpHelper.POST,
                gson.toJson(toInsert),
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Integer res = 0;
                        if(response != null){
                            WCFResponse<Object> result = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Object.class));
                            res = (int)result.getResponse();
                        }
                        onResult.execute(res);
                    }
                });
    }

    @Override
    public void update(Owner toUpdate, final WsCallback<Boolean> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s", URL, ENDPOINT, UPDATE_METHOD),
                Util.HttpHelper.PUT,
                gson.toJson(toUpdate),
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Boolean res = false;
                        if(response != null){
                            WCFResponse<Boolean> result = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Boolean.class));
                            res = result.getResponse();
                        }
                        onResult.execute(res);
                    }
                });
    }

    @Override
    public void delete(Object id, final WsCallback<Boolean> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s/%s", URL, ENDPOINT, DELETE_METHOD, id),
                Util.HttpHelper.DELETE,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Boolean res = false;
                        if(response != null){
                            WCFResponse<Boolean> result = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Boolean.class));
                            res = result.getResponse();
                        }
                        onResult.execute(res);
                    }
                });
    }

    @Override
    public void find(Object id, final WsCallback<Owner> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s/%s", URL, ENDPOINT, FIND_METHOD, id),
                Util.HttpHelper.GET,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        Owner res = null;
                        if(response != null){
                            WCFResponse<Owner> result = gson.fromJson(response.toString(), Util.getType(WCFResponse.class, Owner.class));
                            res = result.getResponse();
                        }
                        onResult.execute(res);
                    }
                });
    }

    @Override
    public void findAll(final WsCallback<List<Owner>> onResult) {
        Util.HttpHelper.makeRequest(String.format("%s/%s/%s", URL, ENDPOINT, FIND_ALL_METHOD),
                Util.HttpHelper.GET,
                null,
                new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        List<Owner> res = null;
                        if(response != null){
                            WCFResponse<List<Owner>> result = gson.fromJson(response.toString(),
                                    Util.getType(WCFResponse.class, Util.getType(List.class, Owner.class)));
                            res = result.getResponse();
                        }
                        onResult.execute(res);
                    }
                });
    }

    public void login(String username, String password, final WsCallback<Owner> onResult) {

        final JSONObject obj = new JSONObject();

        try {
            obj.put(USERNAME, username);
            obj.put(PASSWORD, password);

        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }

        Util.HttpHelper.makeRequest(String.format("%s/%s/%s", URL, ENDPOINT, LOGIN_METHOD),
                Util.HttpHelper.POST, obj.toString(), new Util.HttpHelper.OnResult() {
                    @Override
                    public void execute(Object response) {
                        try {
                            Owner o = null;
                            JSONObject r = new JSONObject(response.toString());
                            if(!r.isNull(RESPONSE)) {
                                JSONObject obj = r.getJSONObject(RESPONSE);
                                o = new Owner();
                                o.setId(obj.getInt(ID));
                                o.setUsername(obj.getString(USERNAME));
                                o.setPassword(obj.getString(PASSWORD));
                                o.setName(obj.getString(NAME));
                                o.setLastName(obj.getString(LAST_NAME));
                                String dateS = obj.getString(BIRTHDATE);
                                long l = Long.parseLong(dateS.substring(dateS.indexOf("(") + 1, dateS.indexOf(")") - 5));
                                o.setBirthDate(new Date(l));
                                o.setDNI(obj.getString(DNI));
                                o.seteMail(obj.getString(EMAIL));
                                o.setAddress(obj.getString(ADDRESS));
                                o.setPhoneNumber(obj.getString(PHONE_NUMBER));
                                o.setProfilePicture(obj.getString(PROFILE_PICTURE));
                            }

                            onResult.execute(o);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }
}