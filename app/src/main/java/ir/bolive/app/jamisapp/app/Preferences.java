package ir.bolive.app.jamisapp.app;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences  {
    private static final String PREF_NAME="Preferences";
    private static final String KEY_IMEI="imeiCode";
    private static final String KEY_PASSWORD ="pass";
    private static final String KEY_FULLNAME="fullname";
    private static final String KEY_USERNAME="username";
    private static final int PRIVATE_MODE=0;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public Preferences(Context context){
        preferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=preferences.edit();
    }
    public String getKeyImei() {
        return preferences.getString(KEY_IMEI,null);
    }
    public void setKeyImei(String imei){
        editor.putString(KEY_IMEI,imei);
        editor.commit();
    }
    public String getKeyFullname() {
        return preferences.getString(KEY_FULLNAME,null);
    }
    public void setKeyFullname(String imei){
        editor.putString(KEY_FULLNAME,imei);
        editor.commit();
    }
    public String getKeyUsername() {
        return preferences.getString(KEY_USERNAME,null);
    }
    public void setKeyUsername(String imei){
        editor.putString(KEY_USERNAME,imei);
        editor.commit();
    }
    public String getKeyPass() {
        return preferences.getString(KEY_PASSWORD,null);
    }
    public void setKeyPass(String pass){
        editor.putString(KEY_PASSWORD,pass);
        editor.commit();
    }

}
