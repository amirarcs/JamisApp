package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;

public class CameraActiviy extends AppCompatActivity implements SurfaceHolder.Callback {

    @BindView(R.id.camera_surface)
    SurfaceView surfaceView;
    @BindView(R.id.camera_button)
    Button btnCapture;
    @BindView(R.id.camera_done)
    Button btnDone;
    @BindView(R.id.camera_reCapture)
    Button btnRecapture;
    SurfaceHolder surfaceHolder;
    Camera camera;
    @BindView(R.id.camera_overlay)
    ImageView imgOverlay;
    byte[] imgData;
    boolean isPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceHolder= surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.setBackground(getResources().getDrawable(R.drawable.grid));
        hideCapture(false);

    }
    @OnClick(R.id.camera_button)
    public void onCameraClick(){
        if(camera!=null){
            camera.takePicture(myShutterCallback,myPictureCallback_RAW,myPictureCallback);
        }
    }
    @OnClick(R.id.camera_reCapture)
    public void onRecaptureClick(){

    }
    @OnClick(R.id.camera_done)
    public void onDoneClick() {
        Intent intent=new Intent();
        intent.putExtra("img",imgData);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    //region Methods
    private void hideCapture(boolean shouldHide){
        if (shouldHide){
            btnCapture.setVisibility(View.GONE);
            btnDone.setVisibility(View.VISIBLE);
            btnRecapture.setVisibility(View.VISIBLE);
            imgOverlay.setVisibility(View.VISIBLE);
        }
        else{
            btnCapture.setVisibility(View.VISIBLE);
            btnDone.setVisibility(View.GONE);
            btnRecapture.setVisibility(View.GONE);
            imgOverlay.setVisibility(View.GONE);
        }
    }
    //endregion
    Camera.PictureCallback myPictureCallback_RAW = new Camera.PictureCallback(){

        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub
        }};
    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback(){

        public void onShutter() {
            // TODO Auto-generated method stub
        }};

    Camera.PictureCallback myPictureCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            Bitmap bitmapPicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Bitmap correctBmp = Bitmap.createBitmap(bitmapPicture, 0, 0, bitmapPicture.getWidth(), bitmapPicture.getHeight(), null, true);
            isPreview=true;
            imgOverlay.setImageBitmap(correctBmp);
            imgData=bytes;
            hideCapture(true);
        }
    };
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        camera=Camera.open();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int i, int i1, int i2) {
        if(isPreview){
            camera.stopPreview();
            isPreview=false;
        }
        if(camera==null){
            try{
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                isPreview=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera=null;
    }
}
