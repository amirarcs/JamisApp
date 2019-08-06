package ir.bolive.app.jamisapp.activiy;

import android.content.Context;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraSurface extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;

    public CameraSurface(Context context, Camera camera){
        super(context);
        mCamera=camera;
        mSurfaceHolder=getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            if(mCamera==null){
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
            }
        }
        catch (Exception e){
            Log.d(VIEW_LOG_TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        refreshCamera(mCamera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
    public void refreshCamera(Camera camera){
        if(mSurfaceHolder.getSurface()==null) {
            return;
        }
        try{
            mCamera.stopPreview();
        }
        catch (Exception e){

        }
        try{
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        }
        catch (Exception e){
            Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
    public void setCamera(Camera camera){
        mCamera=camera;
    }
}
