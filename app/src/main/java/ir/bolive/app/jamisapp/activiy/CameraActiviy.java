package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.view.SurfaceView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;

public class CameraActiviy extends AppCompatActivity {
    private Camera mCamera;
    private CameraSurface mSurface;
    private Camera.PictureCallback mPicture;
    private Button capture, switchCamera;
    private Context myContext;
    private SurfaceView cameraPreview;
    private boolean cameraFront = false;
    public static Bitmap bitmap;

}
