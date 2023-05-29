package com.rheredias.colormatch.util;


import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class ApiCallTask extends AsyncTask<Void, Void, String> {

    public String url;
    public String result;
    public int r,g,b;
    NetworkTask networkTask = new NetworkTask();

    public ApiCallTask(String apiName) {
        this.url = apiName;
    }

    public void setUrl(String url){
       this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder result = null;
        try {
            //String urlEncoder = URLEncoder.encode("255,99,71", "UTF-8");
            URL url = new URL(this.url + this.r + "," + this.g + "," + this.b);
            result = new StringBuilder();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //buferes para leer
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                JSONObject jsonNameColor = jsonResponse.getJSONObject("name");
                String nameColor = jsonNameColor.getString("value");

                this.result = nameColor;
                networkTask.texto = nameColor;
                //networkTask.execute();
                //String colorNameEs = networkTask.result;
                //String nameColorES = translate(nameColor);
                // Utiliza los valores obtenidos como desees
                //System.out.println("nameColorES: " + nameColorES);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Maneja el caso de una respuesta nula
        }

        //JSONArray translationsArray = jsonArray.getJSONArray(0);
        //JSONObject translationObject = translationsArray.getJSONObject(0);
        //String translatedText = translationObject.getString("trans");
        //jsonNameColor = jsonResponse.getJSONObject("name");
        //nameColor = jsonNameColor.getString("value");

        //String nameColorES = translate(nameColor);
        // Utiliza los valores obtenidos como desees
        //System.out.println("nameColorES: " + nameColorES);
        //translate(nameColor);

    }
    /*
    private String translate(String texto) throws MalformedURLException, JSONException {
        NetworkTask networkTask = new NetworkTask();
        String result;
        networkTask.texto = texto;
        networkTask.execute();

        // Parse the JSON response
        //JSONArray jsonArray = new JSONArray(networkTask.result.toString());
        //JSONArray translationsArray = jsonArray.getJSONArray(0);
        //JSONObject translationObject = translationsArray.getJSONObject(0);
        //String translatedText = translationObject.getString("trans");

        System.out.println("Texto traducido: " + networkTask.result);

        return networkTask.result;
    }
    */

}