package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.app.MediaHelper;
import ir.bolive.app.jamisapp.util.Tools;

public class CameraActiviy extends AppCompatActivity implements SurfaceHolder.Callback {

    @BindView(R.id.camera_surface)
    SurfaceView surfaceView;
    @BindView(R.id.camera_button)
    Button btnCapture;
    @BindView(R.id.camera_done)
    Button btnDone;
    @BindView(R.id.camera_reCapture)
    Button btnRecapture;
    @BindView(R.id.camera_face)
    Button btnFace;
    @BindView(R.id.camera_ear)
    Button btnEar;
    @BindView(R.id.camera_arrow)
    Button btnArrow;

    SurfaceHolder surfaceHolder;
    Camera camera;
    Bitmap bmpOverlay;

    boolean isClicked=false;
    @BindView(R.id.camera_overlay)
    ImageView imgOverlay;
    byte[] imgData;
    boolean isPreview;
    private String TAG = "CameraPreview";
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        enableCapture(false);
        hideCapture(false);
        openCamera();
    }
    public void openCamera(){
        camera=Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        surfaceHolder= surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setImgOverlay(1);

    }
    @OnClick(R.id.camera_button)
    public void onCameraClick(){
        if(camera!=null){
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if(success){
                        camera.takePicture(myShutterCallback,myPictureCallback_RAW,myPictureCallback);
                    }
                }
            });
        }
    }
    @OnClick(R.id.camera_reCapture)
    public void onRecaptureClick(){
        hideCapture(false);
        StopPreview();
        openCamera();
        StartPreview();
    }
    @OnClick(R.id.camera_done)
    public void onDoneClick() {
        Intent intent=new Intent();
        intent.putExtra("img",path);
        setResult(RESULT_OK,intent);
        killMe();
    }

    @OnClick(R.id.camera_face)
    public void onFaceClick() {
        setImgOverlay(1);
    }
    @OnClick(R.id.camera_ear)
    public void onEarClick() {
        setImgOverlay(2);
    }
    @OnClick(R.id.camera_arrow)
    public void onArrowClick() {
        if (isClicked){
            showOverlays(false);
            isClicked=false;
        }
        else{
            showOverlays(true);
            isClicked=true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        killMe();
    }
    public void killMe(){
        finish();
    }
    //region Methods
    private void hideCapture(boolean shouldHide){
        if (shouldHide){
            btnCapture.setVisibility(View.GONE);
            btnDone.setVisibility(View.VISIBLE);
            btnRecapture.setVisibility(View.VISIBLE);
            imgOverlay.setVisibility(View.GONE);
            btnArrow.setVisibility(View.GONE);
        }
        else{
            btnCapture.setVisibility(View.VISIBLE);
            btnDone.setVisibility(View.GONE);
            btnRecapture.setVisibility(View.GONE);
            imgOverlay.setVisibility(View.VISIBLE);
            btnArrow.setVisibility(View.VISIBLE);
        }
    }
    //endregion
    Camera.PictureCallback myPictureCallback_RAW = new Camera.PictureCallback(){

        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub
        }};
    Camera.ShutterCallback myShutterCallback = () -> {
        // TODO Auto-generated method stub
    };

    Camera.PictureCallback myPictureCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            Bitmap bitmapPicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            bitmapPicture=Bitmap.createScaledBitmap(bitmapPicture,800,600,false);
            Bitmap correctBmp = Tools.combineImages(Tools.rotateImage(bitmapPicture,0f),bmpOverlay);
            path = savePictureToFileSystem(Tools.bitmapToByte(correctBmp));
            isPreview=true;
            imgOverlay.setImageBitmap(correctBmp);
            imgData=Tools.bitmapToByte(correctBmp);
            hideCapture(true);
        }
    };
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        StartPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int i, int i1, int i2) {
        if(!isPreview && holder.getSurface()!=null){
            try{
                camera.stopPreview();
            }catch (Exception e){
                Log.e(TAG,e.getMessage());
            }
        }
        else{
            return;
        }
        StartPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        StopPreview();
    }
    public void StopPreview(){
        try{
            camera.stopPreview();
            camera.release();
            camera=null;
        }catch (Exception e) {
            //ignore
        }
    }
    public void StartPreview() {
        try{
            imgOverlay.setVisibility(View.VISIBLE);
            camera.setPreviewDisplay(surfaceHolder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    camera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera camera) {
                            if(success){
                                enableCapture(true);

                            }
                        }
                    });
                }
            },1000);

        }catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }
    public void enableCapture(boolean shouldEnable){
        if(shouldEnable){
            btnCapture.setBackground(getResources().getDrawable(R.drawable.circle_button));
            btnCapture.setEnabled(true);
        }
        else{
            btnCapture.setBackground(getResources().getDrawable(R.drawable.yellow_circle_button));
            btnCapture.setEnabled(false);
        }
    }
    private void setImgOverlay(int img){
        switch (img){
            case 1:
                imgOverlay.setImageDrawable(getResources().getDrawable(R.drawable.gridface));
                break;
            case 2:
                imgOverlay.setImageDrawable(getResources().getDrawable(R.drawable.gridface2));
                break;
        }
        bmpOverlay = Bitmap.createScaledBitmap(Tools.convertDrawableToBitmap(imgOverlay),800,600,false);
    }
    private void showOverlays(boolean shouldshow){
        if (shouldshow){
            btnFace.setVisibility(View.VISIBLE);
            btnEar.setVisibility(View.VISIBLE);
            btnArrow.setBackground(getResources().getDrawable(R.drawable.ic_arrowback));
        }
        else{
            btnFace.setVisibility(View.GONE);
            btnEar.setVisibility(View.GONE);
            btnArrow.setBackground(getResources().getDrawable(R.drawable.ic_arrow));
        }
    }
    private static String savePictureToFileSystem(byte[] data) {
        File file = MediaHelper.getOutputMediaFile();
        MediaHelper.saveToFile(data, file);
        if (file != null) {
            return file.getAbsolutePath();
        }
        else{
            return null;
        }
    }


}
