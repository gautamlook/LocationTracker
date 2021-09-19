package tracklocation.devdeeds.com.tracklocationproject.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.JobIntentService;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Random;

import tracklocation.devdeeds.com.tracklocationproject.Database.DatabaseClient;
import tracklocation.devdeeds.com.tracklocationproject.Database.Task;
import tracklocation.devdeeds.com.tracklocationproject.Database.TaskDao;
import tracklocation.devdeeds.com.tracklocationproject.settings.Constants;


/**
 * Created by devdeeds.com on 27-09-2017.
 */

public class LocationMonitoringService extends JobIntentService implements
        android.location.LocationListener {


    private static final String TAG = LocationMonitoringService.class.getSimpleName();

    Handler handler = new Handler();
    private Location location;

    public static final String ACTION_LOCATION_BROADCAST = LocationMonitoringService.class.getName() + "LocationBroadcast";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    public static final String EXTRA_ACCURACY = "extra_accuracy";
    public static final String EXTRA_TIME = "extra_time";
    protected LocationManager locationManager;
    TaskDao dataBaseManager;

    public class LocalBinder extends Binder {
        public LocationMonitoringService getService() {
            return LocationMonitoringService.this;
        }
    }

    public void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, LocationMonitoringService.class, 2, intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        onHandleWork(intent);
        //Make it stick to the notification panel so it is less prone to get cancelled by the Operating System.
        return START_STICKY;
    }

    private final IBinder iBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        dataBaseManager = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .taskDao();
        getLocation();
        handler.postDelayed(new Runnable() {
            public void run() {
                updateGPSCoordinates();
                handler.postDelayed(this, 5000);
            }
        }, 0);
    }

//    /*
//     * LOCATION CALLBACKS
//     */
//    @Override
//    public void onConnected(Bundle dataBundle) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//
//            Log.d(TAG, "== Error On onConnected() Permission not granted");
//            //Permission not granted by user so cancel the further execution.
//
//            return;
//        }
//      //  LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);
//
//        Log.d(TAG, "Connected to Google API");
//    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */

    //to get the location change
    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    boolean tripStatus;
    int randomValue;

    public boolean isTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(boolean tripStatus) {
        this.tripStatus = tripStatus;
    }

    public void setEnableTrip(boolean flag) {
        this.tripStatus = flag;
        randomValue = new Random().nextInt(900) + 100;
    }

    private void sendMessageToUI(String lat, String lng, String accuracy, String timeMillisecond) {
        if (tripStatus) {
            Task df = new Task();
            df.setTripid(randomValue + "");
            df.setTime(timeMillisecond);
            df.setAccuracy(accuracy);
            df.setLat(lat);
            df.setLongi(lng);
            dataBaseManager.insert(df);
        }

        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lng);
        intent.putExtra(EXTRA_ACCURACY, accuracy);
        intent.putExtra(EXTRA_TIME, timeMillisecond);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }


    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            locationManager = (LocationManager) LocationMonitoringService.this
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                //this.canGetLocation = true;

                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            0,
                            0, this);
                    Log.d("Network", "Network");

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                0,
                                0, this);

                        Log.d("GPS Enabled", "GPS Enabled");

                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            Log.e("Error : Location",
                    "Impossible to connect to LocationManager", e);
        }

        return location;
    }

    public void updateGPSCoordinates() {
        if (location != null) {
            sendMessageToUI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), String.valueOf(location.getAccuracy()), String.valueOf(location.getTime()));
        }
    }
}