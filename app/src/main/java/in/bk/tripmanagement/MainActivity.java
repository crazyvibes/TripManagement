package in.bk.tripmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.bk.tripmanagement.data.local.SharedPreferencesManager;
import in.bk.tripmanagement.databinding.ActivityMainBinding;
import in.bk.tripmanagement.model.Locations;
import in.bk.tripmanagement.model.TripDetails;
import in.bk.tripmanagement.utils.GPSTracker;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    private Handler handler;
    private GPSTracker gpsTracker;
    private LocationManager locationManager;
    private String startTime;
    private String endTime;
    public TripDetails tripDetails=new TripDetails();
    public List<Locations> locationList=new ArrayList<>();
    public SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        initializeListener();
    }

    private void init() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        gpsTracker = new GPSTracker(MainActivity.this);
        sharedPreferencesManager =new SharedPreferencesManager(this);
    }

    private void initializeListener() {

        binding.btStart.setOnClickListener(this);
        binding.btStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btStart:
                startTracking();
                break;

            case R.id.btStop:
                stopTracking();
                break;
        }
    }


    private void startTracking() {
        startTime=getTimeinHHMMSS();
        Log.e("TAG", "startTime: " + startTime);
        handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //call function
                getLocationDetails();
                handler.postDelayed(this, 5*1000);
            }
        }, 5*1000);
    }

    private void stopTracking() {
        endTime=getTimeinHHMMSS();
        startTime=getTimeinHHMMSS();
        Log.e("TAG", "endTime: " + endTime);
        handler.removeCallbacksAndMessages(null);


        tripDetails.setStart_time(startTime);
        tripDetails.setEnd_time(endTime);
        tripDetails.setTrip_id("1");
        tripDetails.setLocations(locationList);


      logObject(tripDetails);
    }

    private void getLocationDetails() {
        LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        GPSTracker gpsTracker = new GPSTracker(this);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            String getLat = String.valueOf(gpsTracker.getLatitude());
            String getLong = String.valueOf(gpsTracker.getLongitude());
            String getAccuracy = String.valueOf(gpsTracker.getAccuracy());


            Locations locations=new Locations();
            locations.setLongitude(getLat);
            locations.setLatitude(getLong);
            locations.setAccuracy(getAccuracy);
            locations.setTimestamp(getTimeinHHMMSS());

            locationList.add(locations);

            Log.e("TAG", "getLocation: " + getLat + " " + getLong);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeinHHMMSS() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static void logObject( Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        Log.d(TAG, json);
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "onRequestPermissionsResult: "+"permission granted" );
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.e(TAG, "onRequestPermissionsResult: "+"permission denied" );
                }
                return;
            }

        }
    }
}