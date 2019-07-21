package ir.bolive.app.jamisapp.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class Tools {
    public static Bitmap image_resize(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int maxWidth=800;
        int maxHeight=800;

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
}
