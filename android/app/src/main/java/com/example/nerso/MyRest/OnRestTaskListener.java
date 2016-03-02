package com.example.nerso.MyRest;

public interface OnRestTaskListener {

    public void onPreExecute();
    public void onSuccess(String result);
    public void onError(int responseCode, String message);

}
