package com.app.pawapp.DataAccess.DataAccessObject;

import com.google.gson.annotations.SerializedName;

public class WCFResponse<T> {

    @SerializedName("ResponseCode") private int responseCode;
    @SerializedName("ResponseMessage") private String responseMessage;
    @SerializedName("Response") private T response;

    public WCFResponse() {
    }

    public WCFResponse(int responseCode, String responseMessage, T response) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.response = response;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
