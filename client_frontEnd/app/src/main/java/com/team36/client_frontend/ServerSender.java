package com.team36.client_frontend;
// Cameron MacKay

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class ServerSender extends AsyncTask<String,String,String> {

    private ServerResponded mCallback;

    private Context mContext;

    public ServerSender(Context context) {
        mContext = context;
        mCallback = (ServerResponded) context;
    }
    @Override
    protected void onPostExecute(String s) {
        mCallback.onTaskComplete(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[1]);
            byte[] postData       = strings[0].getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;

            //set headers
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty( "charset", "utf-8");
            con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

            //send data to server
            BufferedOutputStream test = new BufferedOutputStream(con.getOutputStream());
            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(test,"utf-8"));
            os.write(strings[0]);
            os.flush();
            os.close();
            // For POST only - END

            //get response code
            int responseCode = con.getResponseCode();
            //System.out.println("POST Response Code :: " + responseCode);

            //Handle data passed back from server
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return(response.toString());
            } else {
                return("error");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }
}

