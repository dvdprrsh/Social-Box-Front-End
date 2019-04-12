package com.team36.client_frontend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SendToServer {
    public String Send(String toSend, String link) throws IOException {
        URL url = new URL(link);

        //What to post
        byte[] postData       = toSend.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;

        //set headers
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty( "charset", "utf-8");
        con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

        //send data to server
        DataOutputStream os = new DataOutputStream(con.getOutputStream());
        os.write(postData);
        os.flush();
        os.close();
        // For POST only - END

        //get responce code
        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

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
    }
}

