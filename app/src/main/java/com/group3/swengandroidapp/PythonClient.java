package com.group3.swengandroidapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;
import com.group3.swengandroidapp.XMLRenderer.XmlParser;
import com.group3.swengandroidapp.XMLRenderer.XmlRecipe;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

/**
 * Created by Jack on 08/02/2018.
 */

public class PythonClient extends IntentService{

    public static final String ACTION = "com.group3.swengandroidapp.ACTION";
    public static final String ID = "com.group3.swengandroidapp.ID";

    public static final String LOAD_ALL = "com.group3.swengandroidapp.LOAD_ALL";
    public static final String FETCH_RECIPE = "com.group3.swengandroidapp.FETCH_RECIPE";
    public static final String FETCH_PRESENTATION = "com.group3.swengandroidapp.FETCH_PRESENTATION";

    //IP ADDRESS OF THE SERVER. EDIT THIS FOR YOUR SYSTEM.
    //For USB debugging
    //public static final String IP_ADDR = "192.168.0.20";
    //For device emulator
    public static final String IP_ADDR = "10.0.2.2";


    private DataOutputStream dout;
    private Socket socket;
    private URL url;
    private HttpURLConnection urlConnection;
    private RemoteFileManager remoteFileManager = RemoteFileManager.getInstance();


    public PythonClient() throws IOException {

        super("PythonClientService");
    }



    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }


    public String fetchRecipeFromHttpServer(String id) throws IOException{

        url = new URL (String.format("http://%s:5000/download/recipe/%s", IP_ADDR, id));
        urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);

        } finally {
            urlConnection.disconnect();
        }

    }

    public String fetchPresentationFromHttpServer(String id) throws IOException{

        url = new URL (String.format("http://%s:5000/download/presentation/%s", IP_ADDR, id));
        urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);

        } finally {
            urlConnection.disconnect();
        }

    }

    public String[] fetchRecipeListFromHttpServer() throws IOException{

        url = new URL (String.format("http://%s:5000/recipelist", IP_ADDR));
        urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in).split("\n");

        } finally {
            urlConnection.disconnect();
        }

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String id;

            switch (intent.getStringExtra(ACTION)) {
                case LOAD_ALL:
                    String[] ids = fetchRecipeListFromHttpServer();
                    for (String rid : ids) {
                        if (remoteFileManager.getRecipe(rid) == null) {
                            remoteFileManager.setRecipe(rid, new XmlRecipe(fetchRecipeFromHttpServer(rid)));
                        }
                    }
                    Log.d("sender", "LOAD_ALL");
                    sendMessage(LOAD_ALL, "");
                    break;
                case FETCH_RECIPE:
                    id = intent.getStringExtra(ID);

                    if (remoteFileManager.getRecipe(id) == null) {
                        remoteFileManager.setRecipe(id, new XmlRecipe(fetchRecipeFromHttpServer(id)));
                    }
                    Log.d("sender", "FETCH_RECIPE");
                    sendMessage(FETCH_RECIPE, id);
                    break;
                case FETCH_PRESENTATION:
                    id = intent.getStringExtra(ID);

                    if (remoteFileManager.getPresentation(id) == null){
                        remoteFileManager.setPresentation(id, new XmlParser(fetchPresentationFromHttpServer(id)).parse());
                    }
                    Log.d("sender", "FETCH_PRESENTATION");
                    sendMessage(FETCH_PRESENTATION, id);
                    break;
            }

            Log.d("sender", "B");
        } catch (Exception e) {
            // Restore interrupt status.
            Log.d("sender", "Error");
            e.printStackTrace();
        }
    }

    private void sendMessage(String action, String id) {
        Log.d("sender", "Broadcasting message" + action);
        Intent intent = new Intent("XML-event-name");
        // You can also include some extra data.
        intent.putExtra("message", "whatever message");
        intent.putExtra(ACTION,action);
        intent.putExtra(ID, id);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


}

