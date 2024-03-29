package ir.bolive.app.jamisapp.app;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

import ir.bolive.app.jamisapp.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Controller extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setFontAttrId(R.attr.font)
        .setDefaultFontPath("opensans.ttf")
        .build());
        AndroidNetworking.initialize(getApplicationContext());
    }
}
