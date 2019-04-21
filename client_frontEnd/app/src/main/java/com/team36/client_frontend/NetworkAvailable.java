package com.team36.client_frontend;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

class NetworkAvailable {
    private Activity activity;

    NetworkAvailable(Activity activity){
        this.activity = activity;
        netAvailable();
    }

    boolean netAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean netAvailable = ((networkInfo != null) && (networkInfo.isConnected()));
        if (!netAvailable) {
            Toast toast = Toast.makeText(activity, R.string.login_snackbar_networkError, Toast.LENGTH_LONG);
            toast.show();
        }

        return netAvailable;
    }
}
