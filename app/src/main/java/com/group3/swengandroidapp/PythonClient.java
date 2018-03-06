package com.group3.swengandroidapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.group3.swengandroidapp.XMLRenderer.PresentationManager;
import com.group3.swengandroidapp.XMLRenderer.XmlParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.List;

/**
 * Created by Jack on 08/02/2018.
 */

public class PythonClient extends IntentService{

    private DataOutputStream dout;
    private Socket socket;
    private URL url;
    private HttpURLConnection urlConnection;
    private PresentationManager manager = PresentationManager.getInstance();


    public PythonClient() throws IOException {

        super("PythonClientService");
    }

//        try{
//
//            //dout = new DataOutputStream( socket.getOutputStream() );
//
//        }
//
//        catch(Exception e){
//            e.printStackTrace() ;
//            //System.out.print("Connection established");
//        }




    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }


    public String connectToHttpServer(String id) throws IOException{

        url = new URL ("http://10.0.2.2:5000/download/" + id);
        urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);

        } finally {
            urlConnection.disconnect();
        }

    }


//    private List<Entry> readStream(InputStream is) throws IOException, XmlPullParserException {
////        StringBuilder sb = new StringBuilder();
////        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
//          List<Entry> XMLentries = null;
////
////        for (String line = r.readLine(); line != null; line =r.readLine()){
////            sb.append(line);
////        }
////
////        String xml = sb.toString();
//
//        XMLentries = stackOverflowXmlParser.parse(is);
//
//        is.close();
//        return XMLentries;
//    }
//
//
//    public List<Entry> connectToHttpServer(String id) throws IOException, XmlPullParserException{
//
//        url = new URL ("http://10.0.2.2:5000/download/" + id);
//        urlConnection = (HttpURLConnection) url.openConnection();
//
//        try {
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            return readStream(in);
//
//        } finally {
//            urlConnection.disconnect();
//        }
//
//    }

    public void pingServer() throws IOException{

        try{
            socket = new Socket("10.0.2.2", 2400);
            dout = new DataOutputStream( socket.getOutputStream() );
            dout.writeChars("Hello World");
            dout.flush();
        }

        catch(Exception e){
            e.printStackTrace() ;
        }

    }

    public void closeConnection() throws IOException{

        try {
            dout.close();
            socket.close();
        }

        catch(Exception e){
            e.printStackTrace() ;
        }

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.d("sender", "A");
            manager.setXML(connectToHttpServer("example.pws"));
            manager.setPresentation(new XmlParser(manager.getXML()).parse());
            Log.d("sender", "B");
            sendMessage();
            Log.d("sender", "C");
        } catch (Exception e) {
            // Restore interrupt status.
            Log.d("sender", "Error");
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("XML-event-name");
        // You can also include some extra data.
        intent.putExtra("message", "XML received!");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }




//    public class PythonClientResultReceiver<T> extends ResultReceiver {
//
//        @Override
//        protected void onReceiveResult(int resultCode, Bundle resultData) {
//
//            if (mReceiver != null) {
//                if(resultCode == RESULT_CODE_OK){
//                    mReceiver.onSuccess(resultData.getSerializable(PARAM_RESULT));
//                } else {
//                    mReceiver.onError((Exception) resultData.getSerializable(PARAM_EXCEPTION));
//                }
//            }
//        }
//
//    }


}

