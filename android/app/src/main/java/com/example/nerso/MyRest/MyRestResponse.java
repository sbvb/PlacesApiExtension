package com.example.nerso.MyRest;

public class MyRestResponse {

    private int code;
    private String errorMessage;
    private String result;

    public MyRestResponse(int code, String errorMessage, String result) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
