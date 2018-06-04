package com.app.pawapp.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.Set;

public final class Util {

    //public static final String URL = "http://pawswcf-dev.us-west-1.elasticbeanstalk.com/Service";
    public static final String URL = "http://192.168.1.36:60602/Service";
    public static final String RESPONSE = "Response";
    public static final String RESPONSE_CODE = "ResponseCode";
    public static final String RESPONSE_MESSAGE = "ResponseMessage";

    private Util() {
    }

    public static Type getType(final Type rawType, final Type ... typesParams){
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return typesParams;
            }

            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    public static void showAlert(String msg, Context context){
        new AlertDialog.Builder(context)
                .setTitle("Aviso")
                .setMessage(msg)
                .setNeutralButton("OK", null)
                .create()
                .show();
    }

    public static String toBase64(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static byte[] fromBase64(String data) {
        return Base64.decode(data, Base64.DEFAULT);
    }

    public static byte[] bitmapToBytes(Bitmap bitmap, String extension) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        switch (extension) {
            case "png":
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
                break;
            case "jpeg":
            case "jpg":
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                break;
            case "webp":
                bitmap.compress(Bitmap.CompressFormat.WEBP, 70, baos);
                break;
        }

        return baos.toByteArray();
    }

    public static class PermissionHelper {

        public static boolean checkPermission(String permission, Context context) {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }

        public static int requestPermission(Activity activity, String... permission) {
            int requestCode = new Random().nextInt(Integer.MAX_VALUE);
            ActivityCompat.requestPermissions(activity, permission, requestCode);
            return requestCode;
        }
    }

    public static class HttpHelper {

        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";

        private static final int CONNECTION_TIMEOUT = 420000;

        private static HttpURLConnection urlConnection = null;

        public interface OnResult {
            void execute(Object response);
        }

        private interface OnExecute {
            Object execute(String url);
        }


        //URL OF THE API, HTTP METHOD TO USE, BODY IF ANY, AND A CALLBACK TO BE EXECUTED WHEN IT RETURNS
        public static void makeRequest(String url, final String method, final Object body, OnResult onResult) {

            OnExecute onExecute = new OnExecute() {

                @Override
                public Object execute(String innerUrl) {
                    String res = null;

                    try {
                        OutputStream requestData = null;
                        BufferedReader responseDataReader = null;
                        try {
                            urlConnection = (HttpURLConnection) new URL(innerUrl).openConnection();
                            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                            urlConnection.setReadTimeout(CONNECTION_TIMEOUT);
                            urlConnection.setRequestMethod(method);

                            if (body != null && (method.equals(POST) || method.equals(PUT))) {
                                urlConnection.addRequestProperty("Content-Type", "application/json");
                                urlConnection.setDoOutput(true);
                                requestData = urlConnection.getOutputStream();
                                requestData.write(body.toString().getBytes(Charset.forName("UTF-8")));
                            }

                            responseDataReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                            StringBuilder responseBuilder = new StringBuilder();

                            String lineHolder;
                            while ((lineHolder = responseDataReader.readLine()) != null) {
                                responseBuilder.append(lineHolder);
                            }

                            res = responseBuilder.toString();

                        } finally {
                            if (requestData != null)
                                requestData.close();
                            if (responseDataReader != null)
                                responseDataReader.close();
                            if(urlConnection != null)
                                urlConnection.disconnect();
                        }
                    } catch (IOException ex) {
                        //throw new RuntimeException(ex);
                        ex.printStackTrace();
                        return null;
                    }
                    return res;
                }
            };

            new AsyncTaskImpl(onExecute, onResult).execute(url);
        }

        //ON EXECUTE IS THE METHOD TO BE EXECUTED ASYNCHRONOUSLY AND ON RESULT IS EXECUTED WITH THE RESULT OF THAT METHOD AFTER IT IS COMPLETED
        private static class AsyncTaskImpl extends AsyncTask<Object, Object, Object> {

            private OnExecute onExecute;
            private OnResult onResult;


            AsyncTaskImpl(OnExecute onExecute, OnResult onResult) {
                this.onExecute = onExecute;
                this.onResult = onResult;
            }

            @Override
            protected Object doInBackground(Object... params) {
                return onExecute.execute(params[0].toString());
            }

            @Override
            protected void onPostExecute(Object result) {
                onResult.execute(result);
            }
        }
    }

    public static final class SharedPreferencesHelper {
        //GENERICS FOR STATIC METHODS NEED THEIR OWN GENERIC SIGNATURE
        public static Object getValue(String key, Context context) {
            return PreferenceManager.getDefaultSharedPreferences(context).getAll().get(key);
        }

        @SuppressWarnings("unchecked")
        public static boolean setValue(String key, Object value, Context context) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            if (value instanceof String)
                editor.putString(key, value.toString());
            else if (value instanceof Integer)
                editor.putInt(key, (int) value);
            else if (value instanceof Float)
                editor.putFloat(key, (float) value);
            else if (value instanceof Boolean)
                editor.putBoolean(key, (boolean) value);
            else if (value instanceof Long)
                editor.putLong(key, (long) value);
            else if (value instanceof Set<?>)
                editor.putStringSet(key, (Set<String>) value);
            else
                throw new RuntimeException("Only primitives or Set<String> are/is allowed");

            editor.apply();

            return true;
        }
    }

    public static final class FragmentHelper {

        public static void navigate(Class<? extends Fragment> fragmentClass, int containerResource, String tag, FragmentManager fragmentManager) {
            Fragment frag = fragmentManager.findFragmentByTag(tag);
            if (frag != null) {
                if (frag.isVisible())
                    return;
                else {
                    for (Fragment f : fragmentManager.getFragments()) {
                        if (f.isVisible()) {
                            fragmentManager.beginTransaction()
                                    .detach(f)
                                    .attach(frag)
                                    .commit();
                            break;
                        }
                    }
                }
            } else {
                for (Fragment f : fragmentManager.getFragments()) {
                    if (f.isVisible()) {
                        fragmentManager.beginTransaction()
                                .detach(f)
                                .commit();
                        break;
                    }
                }
                try {
                    fragmentManager.beginTransaction()
                            .add(containerResource, fragmentClass.newInstance(), tag)
                            .commit();
                } catch(InstantiationException ex){
                    throw new RuntimeException(ex);
                } catch(IllegalAccessException ex){
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
