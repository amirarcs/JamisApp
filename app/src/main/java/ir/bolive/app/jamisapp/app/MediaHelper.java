package ir.bolive.app.jamisapp.app;

import android.os.Environment;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.io.File;
import java.util.Date;
import java.util.Locale;

public class MediaHelper {
    public static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Spike");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(MediaHelper.class.getSimpleName(),"failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
    }

    public static boolean saveToFile(byte[] bytes, File file) {
        boolean saved = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
            saved = true;
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return saved;
    }
}
