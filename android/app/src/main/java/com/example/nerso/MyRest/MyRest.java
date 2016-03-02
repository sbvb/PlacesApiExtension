package com.example.nerso.MyRest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class MyRest {

    private static final String TAG  = "MyRest";

    public MyRest() {
    }

    public static MyRestResponse performRequest(String urlString, String params) {

        int responseCode = 520; //UNKNOWN ERROR
        String responseMessage = "";
        String result = null;
        HttpURLConnection conn = null;
        BufferedReader reader=null;
        MyRestResponse ret = null;

        Log.d(TAG, "[performRequest] - url: " + urlString);

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(urlString);

            // Send POST data request
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(60000); //set timeout to 1 minute

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            responseCode = conn.getResponseCode();
            responseMessage = conn.getResponseMessage();
//            Log.d("ACORest", "response code: " + responseCode);

            if (responseCode == 200) {

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");

                }

                result = sb.toString();
            }
        }
        catch(SocketTimeoutException ex) {
            Log.e(TAG, ex.getLocalizedMessage());
            responseMessage = ex.getLocalizedMessage();
        } catch(Exception ex)
        {
            Log.e(TAG, ex.getLocalizedMessage());
            responseMessage = ex.getLocalizedMessage();
        }
        finally
        {
            try
            {
                reader.close();
            }

            catch(Exception ex) {

                Log.e(TAG, ex.getLocalizedMessage());
            }
        }

        ret = new MyRestResponse(responseCode, responseMessage, result);

        return ret;

    }

    public static MyRestResponse performPostRequest(String urlString, String params) {

        int responseCode = 520; //UNKNOWN ERROR
        String responseMessage = "";
        String result = null;
        HttpURLConnection conn = null;
        BufferedReader reader=null;
        MyRestResponse ret = null;

        Log.d(TAG, "[performRequest] - url: " + urlString);

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(urlString);

            // Send POST data request
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60000); //set timeout to 1 minute
            conn.setDoOutput(true); // force POST method
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(params);
            wr.flush();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            responseCode = conn.getResponseCode();
            responseMessage = conn.getResponseMessage();
//            Log.d("ACORest", "response code: " + responseCode);

            if (responseCode == 200) {

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");

                }

                result = sb.toString();
            }
        }
        catch(SocketTimeoutException ex) {
            Log.e(TAG, ex.getLocalizedMessage());
            responseMessage = ex.getLocalizedMessage();
        } catch(Exception ex)
        {
            Log.e(TAG, ex.getLocalizedMessage());
            responseMessage = ex.getLocalizedMessage();
        }
        finally
        {
            try
            {
                reader.close();
            }

            catch(Exception ex) {

                Log.e(TAG, ex.getLocalizedMessage());
            }
        }

        ret = new MyRestResponse(responseCode, responseMessage, result);

        return ret;

    }
}
