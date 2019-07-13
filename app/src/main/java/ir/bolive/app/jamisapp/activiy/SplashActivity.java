package ir.bolive.app.jamisapp.activiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.app.Preferences;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_imageView)
    ImageView imgLogo;
    @BindView(R.id.splash_power)
    TextView lblPower;
    @BindView(R.id.splash_title)
    TextView lblTitle;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        preferences=new Preferences(this);
        loasAnimation();
    }
    private void loasAnimation(){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        animation.setStartOffset(200);
        imgLogo.startAnimation(animation);
        lblTitle.startAnimation(animation);
        lblPower.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferences.getKeyIsloggedin()){
                    Navigate(false);
                }
                else{
                    Navigate(true);
                }
            }
        },1000);
    }
    private void Navigate(boolean shouldGoToLogin)
    {
        Intent intent;
        if (shouldGoToLogin){
            intent=new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            intent=new Intent(SplashActivity.this,MainActivity.class);
            intent.putExtra("fullname",preferences.getKeyFullname());
            startActivity(intent);
            finish();
        }
    }
}
