package com.thephynix.www.phynix;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.SupportMapFragment;

import static android.support.constraint.Constraints.TAG;

public class CustomGoogleMapsClass {

    private String TAG = "DashActivity";

    private int ERROR_DIALOG_REQUEST = 9001;





    public boolean isServicesOk(Context context, Activity activity){
        Log.d(TAG, "isServicesOk: checking google serives version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);

        if(available == ConnectionResult.SUCCESS){
            //everything is ok user can make map request
            Log.d(TAG, "isServicesOk: Google Play Services is working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOk: an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(activity, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(context, "You can't make map request sorry!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }



}