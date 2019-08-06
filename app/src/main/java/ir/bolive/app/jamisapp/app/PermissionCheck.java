package ir.bolive.app.jamisapp.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.PublicKey;

public class PermissionCheck {
    Context context;
    public PermissionCheck(Context context){
        this.context=context;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isGranted(Context context, String permission) {
        if (!needRequestPermission()) return true;
        return (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isCameraGranted(Context context){
        return isGranted(context,Manifest.permission.CAMERA);
    }
    public static boolean needRequestPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    public static boolean isCameraAvailable(Context context){
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
}
