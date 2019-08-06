package ir.bolive.app.jamisapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.io.ByteArrayOutputStream;

public class Tools {
    public static Bitmap image_resize(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int maxWidth=1000;
        int maxHeight=1000;

        if(width>maxWidth && height>maxHeight){
            double ratio;
            if (width > height) {
                // landscape
                ratio = (width+.0) / maxWidth;
                width = maxWidth;
                height = (int) (height / ratio);
            } else if (height > width) {
                // portrait
                ratio = (height+.0) / maxHeight;
                height = maxHeight;
                width = (int) (width / ratio);
            } else {
                // square
                height = maxHeight;
                width = maxWidth;
            }
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public static void loadBackgroundAnimation(CoordinatorLayout coordinatorLayout){
        AnimationDrawable animDrawable=(AnimationDrawable)coordinatorLayout.getBackground();
        animDrawable.setEnterFadeDuration(500);
        animDrawable.setExitFadeDuration(5000);
        animDrawable.start();
    }
    public static Bitmap decodeImage(byte[] imgData){
         Bitmap bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
        return bitmap;
    }
}
