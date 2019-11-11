package ir.bolive.app.jamisapp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

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
    public static Bitmap rotateImage(Bitmap image,float angle){
        Matrix matrix=new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(image,0,0,image.getWidth(),image.getHeight(),matrix,true);
    }
    public static Bitmap combineImages(Bitmap img1,Bitmap img2){
        Bitmap imgOverlay=Bitmap.createBitmap(img1.getWidth(),img1.getWidth(),img1.getConfig());
        Canvas canvas=new Canvas(imgOverlay);
        canvas.drawBitmap(img1,new Matrix(),null);
        canvas.drawBitmap(img2,0,0,null);
        return imgOverlay;
    }


//    public static Bitmap combineImages (ArrayList<Bitmap> bitmap){
//
//        int w = 0, h = 0;
//        for (int i = 0; i < bitmap.size(); i++) {
//            if (i < bitmap.size() - 1) {
//                w = bitmap.get(i).getWidth() > bitmap.get(i + 1).getWidth() ? bitmap.get(i).getWidth() : bitmap.get(i + 1).getWidth();
//            }
//            h += bitmap.get(i).getHeight();
//        }
//
//        Bitmap temp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(temp);
//        int top = 0;
//        for (int i = 0; i < bitmap.size(); i++) {
//            Log.d("HTML", "Combine: "+i+"/"+bitmap.size()+1);
//
//            top = (i == 0 ? 0 : top+bitmap.get(i).getHeight());
//            canvas.drawBitmap(bitmap.get(i), 0f, top, null);
//        }
//        return temp;
//    }
    public static Bitmap convertDrawableToBitmap(ImageView imageView){
         return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }
    public static Bitmap decodeImage(byte[] imgData){
         Bitmap bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
        return bitmap;
    }
    public static ByteArrayOutputStream bitmapToByteArray(Bitmap imgBitmap){
        Bitmap photo=image_resize(imgBitmap);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream;
    }
    public static byte[] bitmapToByte(Bitmap imgBitmap){
        Bitmap photo=image_resize(imgBitmap);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data=stream.toByteArray();
        return data;
    }
    public static void hideKeyboard(Activity activity){
        InputMethodManager inputMethodManager=(InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
