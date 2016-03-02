package com.example.nerso.MyRest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class MyRestTask extends AsyncTask<String,Void,MyRestResponse> {

    private static String TAG = "MyRestTask";

    private Context context = null;
    private OnRestTaskListener listener = null;
    private boolean progressBarEnabled;

    public MyRestTask(Context context, OnRestTaskListener listener) {
        this.context = context;
        this.listener = listener;

        progressBarEnabled = false;
    }

    public MyRestTask(Context context) {

        this(context, null);

    }

    public void setOnRestTaskListener(OnRestTaskListener listener) {
        this.listener = listener;
    }

    public void setProgressBarEnabled(boolean progressBarEnabled) {
        this.progressBarEnabled = progressBarEnabled;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Log.d(TAG, "[onPreExecute] - INIT");

        if (progressBarEnabled == true) {
            CustomProgressbar.showProgressBar(context, false);
        }

        if (this.listener != null) {
            this.listener.onPreExecute();
        }

    }

    @Override
    protected MyRestResponse doInBackground(String... params) {

        Log.d(TAG, "[doInBackground] - INIT");
        Log.d(TAG, "params: " + params[1]);

        MyRestResponse response = MyRest.performRequest(params[0], params[1]);

        return response;
    }



    @Override
    protected void onPostExecute(MyRestResponse s) {
        super.onPostExecute(s);

        Log.d(TAG, "[onPostExecute] - INIT");

        //response from API is null
        if (s == null) {

            Log.d(TAG, "response from API is null");

            notifyError(520, "RESPONSE FROM API IS NULL");

            return;
        }

        int responseCode = s.getCode();
        String result = s.getResult();

        if (responseCode == 200) { // RESPONSE CODE OK

            //Result from api is null or is empty
            if ((result == null) || (result.isEmpty())) {

                notifyError(responseCode, "RESPONSE FROM API IS NULL");
                return;

            }


            notifySuccess(result);

        } else { // response code is not OK
            notifyError(responseCode, s.getErrorMessage());
        }

    }

    private void notifySuccess(String result) {

        if (progressBarEnabled == true) {
            CustomProgressbar.hideProgressBar();
        }

        if (this.listener == null) {
            Log.e(TAG, "[notifySuccess] - listener is null");
            return;
        }

        this.listener.onSuccess(result);
    }

    private void notifyError(int responseCode, String message) {

        if (progressBarEnabled == true) {
            CustomProgressbar.hideProgressBar();
        }

        if (this.listener == null) {
            Log.e(TAG, "[notifyError] - listener is null");
            return;
        }

        this.listener.onError(responseCode, message);
    }

}
