package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView ivTop,ivHeart,ivBeat,ivBottom;
    TextView textView;
    CharSequence charSequence;
    int index;
    long delay=200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ivTop = findViewById(R.id.iv_top);
        ivHeart = findViewById(R.id.iv_heart);
        //ivBeat=findViewById(R.id.iv_beat);
        ivBottom = findViewById(R.id.iv_bot);
        textView= findViewById(R.id.text_view);

        //set full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize top animation
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.top_wave );

        //Start Top animation
        ivTop.setAnimation(animation1);

        //Initialize Object animator
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                ivHeart,
                PropertyValuesHolder.ofFloat("scaleX",1.2f),
                PropertyValuesHolder.ofFloat("scaleY",1.2f)
        );

        //set duration
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();

        /*Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/demoapp-ae96a.appspot.com/o/heart_beat.gif?alt=media&token=b21dddd8-782c-457c-babd-f2e922ba172b")
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivBeat);*/
        //Initialize button animation
        Animation animation2 = AnimationUtils.loadAnimation( this , R.anim.bottom_wave);

        //start button animation
        ivBottom.setAnimation(animation2);

        //initialize handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                ;
                finish();

            }

        },4000);
    }
}