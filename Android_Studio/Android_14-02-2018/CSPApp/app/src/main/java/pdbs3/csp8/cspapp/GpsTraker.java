package pdbs3.csp8.cspapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

/**
 * Created by DELL on 26-01-2018.
 */

public class GpsTraker extends Service implements LocationListener {

    private final Context context;
    boolean isGpsEnable = false;
    boolean isNetworkEnable = false;
    boolean cangetlocation = false;
   public Location location;
    protected LocationManager locationManager;


    public GpsTraker(Context context) {
        this.context = context;
    }
// create get location method

    public Location getLocation() {

        try {

            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGpsEnable = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
            isNetworkEnable = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);


            if ((ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {

                if (isGpsEnable) {

                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
                        if (locationManager != null) {

                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        }
                    }

                }

                if (location == null) {


                    if (isNetworkEnable) {


                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, this);
                        if (locationManager!=null) {

                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        }
                    }

                }


            }



        } catch (Exception ex) {

        }
        return location;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}