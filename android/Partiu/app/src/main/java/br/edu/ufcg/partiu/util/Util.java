package br.edu.ufcg.partiu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import java.util.Calendar;
import java.util.Date;

import br.edu.ufcg.partiu.model.LocationUser;
import br.edu.ufcg.partiu.model.User;

/**
 * Created by caiovidal on 12/07/17.
 */

public class Util {

    private static SharedPreferences preferences;

    public static SharedPreferences getPreferences(Context context) {
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences;
    }

    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static void saveLastLocation(Context context, Location location) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putFloat(Constants.LAT_USER, (float) location.getLatitude());
        editor.putFloat(Constants.LONG_USER, (float) location.getLongitude());
        editor.apply();
    }

    public static LocationUser getLastLocation(Context context) {
        float latitude = getPreferences(context).getFloat(Constants.LAT_USER, -1);
        float longitude = getPreferences(context).getFloat(Constants.LONG_USER, -1);
        return new LocationUser(latitude, longitude);
    }

    public static String getSessionToken(Context context) {
        return getPreferences(context).getString(Constants.TOKEN, "");
    }

    public static RoundedBitmapDrawable toRoundBitmap(Context context, Bitmap source) {
        RoundedBitmapDrawable output = RoundedBitmapDrawableFactory.create(context.getResources(), source);
        output.setCircular(true);
        output.setCornerRadius(Math.max(source.getWidth(), source.getHeight()) / 2.0f);
        return output;
    }
}
