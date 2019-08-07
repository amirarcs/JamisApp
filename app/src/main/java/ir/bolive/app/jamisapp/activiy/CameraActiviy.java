package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;


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

    }
    @OnClick(R.id.camera_button)
    public void onCameraClick(){
        if(camera!=null){
            camera.takePicture(myShutterCallback,myPictureCallback_RAW,myPictureCallback);
        }
    }
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
