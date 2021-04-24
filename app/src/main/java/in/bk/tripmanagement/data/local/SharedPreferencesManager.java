package in.bk.tripmanagement.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import in.bk.tripmanagement.model.TripDetails;
import in.bk.tripmanagement.utils.Constants;

import static android.content.ContentValues.TAG;


public class SharedPreferencesManager {
    private SharedPreferences sharedPreferences;
    Context context;

    public SharedPreferencesManager(Context context) {
        this.context=context;
        this.sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }


    public SharedPreferencesManager() {
    }


    public void clearLocalData() {
        sharedPreferences.edit().clear().apply();
    }

//    public void saveAllBidFarmers(TripDetails tripDetails) {
//        sharedPreferences.edit().putString(Constants.TRIP_DETAILS,
//                new Gson().toJson(tripDetails, new TripDetails() {
//                }.getType())).apply();
//    }
//
//    public TripDetails getAllBidFarmers() {
//
//        TripDetails tripDetails = new TripDetails();
//
//        try {
//             tripDetails = new TripDetails(sharedPreferences
//                    .getString(Constants.TRIP_DETAILS, ""));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return allItemsList;
//    }

}













