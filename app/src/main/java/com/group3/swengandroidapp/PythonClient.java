package com.group3.swengandroidapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.group3.swengandroidapp.XMLRenderer.InstructionalVideo;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;
import com.group3.swengandroidapp.XMLRenderer.XmlParser;
import com.group3.swengandroidapp.XMLRenderer.XmlRecipe;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Handles the client to server communication
 */

public class PythonClient extends IntentService{

    //Intent definitions
    public static final String ACTION = "com.group3.swengandroidapp.ACTION";
    public static final String ID = "com.group3.swengandroidapp.UPDATED_RECIPE_ID";

    public static final String LOAD_ALL = "com.group3.swengandroidapp.LOAD_ALL";
    public static final String FETCH_RECIPE = "com.group3.swengandroidapp.FETCH_RECIPE";
    public static final String FETCH_MY_RECIPE = "com.group3.swengandroidapp.FETCH_MY_RECIPE";
    public static final String FETCH_PRESENTATION = "com.group3.swengandroidapp.FETCH_PRESENTATION";
    public static final String FETCH_MY_PRESENTATION = "com.group3.swengandroidapp.FETCH_MY_PRESENTATION";
    public static final String FETCH_INSTRU_VID = "com.group3.swengandroidapp.FETCH_INSTRU_VID";
    protected static final String SERVER_TIMEOUT = "PythonClient.serveTimeout";

    //IP ADDRESS OF THE SERVER. EDIT THIS FOR YOUR SYSTEM.
    //For USB debugging
    public static final String IP_ADDR = "192.168.0.20";
    //For device emulator
    //public static final String IP_ADDR = "10.0.2.2";

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

        url = new URL (String.format("http://%s:5000/download/recipe/%s/%s"+".xml", IP_ADDR, id, id));
        urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);

        } finally {
            urlConnection.disconnect();
        }

    }

    public String fetchMyRecipeFromHttpServer(String id) throws IOException{

        url = new URL (String.format("http://%s:5000/download/myRecipe/%s/%s"+".xml", IP_ADDR, id, id));
        urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);

        } finally {
            urlConnection.disconnect();
        }

    }

    public String fetchPresentationFromHttpServer(String id) throws IOException{

        url = new URL (String.format("http://%s:5000/download/recipe/%s/%s"+".pws", IP_ADDR, id, id));
        urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);

        } finally {
            urlConnection.disconnect();
        }

    }

    public String fetchMyPresentationFromHttpServer(String id) throws IOException{

        url = new URL (String.format("http://%s:5000/download/myRecipe/%s/%s"+".pws", IP_ADDR, id, id));
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
        urlConnection.setConnectTimeout(2000);

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in).split(".xml");
        }
        finally{
            urlConnection.disconnect();
        }

    }

    public String[] fetchMyRecipeListFromHttpServer() throws IOException{

        url = new URL (String.format("http://%s:5000/myRecipeList", IP_ADDR));
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(2000);

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in).split(".xml");
        }
        finally{
            urlConnection.disconnect();
        }

    }

    public String[] fetchInstruvidListFromHttpServer() throws IOException{

        url = new URL (String.format("http://%s:5000/instruvidList", IP_ADDR));
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(2000);

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in).split(".");

        } finally {
            urlConnection.disconnect();
        }

    }

    public String fetchInstruvidFromHttpServer(String id) throws IOException{

        url = new URL (String.format("http://%s:5000/download/instruvid/%s"+".mp4", IP_ADDR, id));
        urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);

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
                    Log.d("TEST", "LOADALL");
                    String[] ids = fetchRecipeListFromHttpServer();
                    for (String rid : ids) {
                        if (remoteFileManager.getRecipe(rid) == null) {
                            remoteFileManager.setRecipe(rid, new XmlRecipe(fetchRecipeFromHttpServer(rid)));
                        }
                    }


                    String[] myIds = fetchMyRecipeListFromHttpServer();
                    for (String rid : myIds) {
                        if (remoteFileManager.getMyRecipe(rid) == null) {
                            remoteFileManager.setMyRecipe(rid, new XmlRecipe(fetchMyRecipeFromHttpServer(rid)));
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
                case FETCH_MY_RECIPE:
                    id = intent.getStringExtra(ID);

                    if (remoteFileManager.getMyRecipe(id) == null) {
                        remoteFileManager.setMyRecipe(id, new XmlRecipe(fetchMyRecipeFromHttpServer(id)));
                    }
                    Log.d("sender", "FETCH_MY_RECIPE");
                    sendMessage(FETCH_MY_RECIPE, id);
                    break;
                case FETCH_PRESENTATION:
                    id = intent.getStringExtra(ID);

                    if (remoteFileManager.getPresentation(id) == null){
                        remoteFileManager.setPresentation(id, new XmlParser(fetchPresentationFromHttpServer(id)).parse());
                    }
                    Log.d("sender", "FETCH_PRESENTATION");
                    sendMessage(FETCH_PRESENTATION, id);
                    break;
                case FETCH_MY_PRESENTATION:
                    id = intent.getStringExtra(ID);

                    if (remoteFileManager.getMyPresentation(id) == null) {
                        remoteFileManager.setMyPresentation(id, new XmlParser(fetchMyPresentationFromHttpServer(id)).parse());
                    }
                    Log.d("sender", "FETCH_MY_PRESENTATION");
                    sendMessage(FETCH_MY_PRESENTATION, id);
                    break;
            }

            Log.d("sender", "B");
        }catch(IOException e1) {    // Timeout exception thrown / unable to connect to server
            // Send broadcast notifying server timeout
            Intent timeoutintent = new Intent();
            timeoutintent.setAction(PythonClient.SERVER_TIMEOUT);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(timeoutintent);
            Log.d("PythonClient", "Server Connection Timeout");
        }
        catch (Exception e) {
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

