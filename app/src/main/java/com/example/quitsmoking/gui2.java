package com.example.quitsmoking;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import es.dmoral.toasty.Toasty;

public class gui2 extends AppCompatActivity {

    int counter = 0;
    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui2);
        ImageView mans = findViewById(R.id.mans);
        TextView tapadd = findViewById(R.id.tapadd);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .repeat(0)
                .playOn(mans);

    }
    public void increase(View view)
    {
        counter++;
        String countstr = Integer.toString(counter);
        TextView count = findViewById(R.id.countofcig);
        count.setText(countstr);
        YoYo.with(Techniques.Landing)
                .duration(1000)
                .playOn(count);
        final LottieAnimationView addani = findViewById(R.id.addani);
        addani.playAnimation();
        addani.setSpeed(2);
        LottieAnimationView smoke = findViewById(R.id.smoking);
        smoke.playAnimation();
        MediaPlayer cough = MediaPlayer.create(this,R.raw.cough);
        cough.start();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }
    public void decrease(View view)
    {
        counter--;
        if(counter<0)
        {
            Toasty.error(this,"THE COUNT IS ZERO",Toasty.LENGTH_SHORT).show();
            counter = 0 ;
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(200);
        }
        else
         {
            String countstr = Integer.toString(counter);
            TextView count = findViewById(R.id.countofcig);
            count.setText(countstr);
            YoYo.with(Techniques.Landing)
                    .duration(1000)
                    .playOn(count);
            final LottieAnimationView remani = findViewById(R.id.remani);
            remani.playAnimation();
            LottieAnimationView smoke = findViewById(R.id.smoking);
            smoke.playAnimation();
            ImageView mansmo = findViewById(R.id.mans);
            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .playOn(mansmo);
            MediaPlayer remsound = MediaPlayer.create(this, R.raw.remcigsound);
            remsound.start();
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }
    }
}
