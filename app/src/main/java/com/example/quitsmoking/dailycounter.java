package com.example.quitsmoking;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class dailycounter extends AppCompatActivity
{
    int cigcount = 0;
    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailycounter);
        LottieAnimationView ecg = findViewById(R.id.ecg);
        final LottieAnimationView heartback = findViewById(R.id.heartback);
        final LottieAnimationView heart = findViewById(R.id.heartani);
        final LottieAnimationView heartback2 = findViewById(R.id.heartback2);
        final TextView daycounter = findViewById(R.id.daycount);
        final TextView appname = findViewById(R.id.appname);
        final LottieAnimationView counterback = findViewById(R.id.counterback);
        final LottieAnimationView counterback2 = findViewById(R.id.counterback2);
        final TextView countcig = findViewById(R.id.countofcig);
        final LottieAnimationView buttonback = findViewById(R.id.buttonback);
        final TextView taptoadd = findViewById(R.id.taptoadd);
        final LottieAnimationView removecig = findViewById(R.id.remani);
        final TextView quote = findViewById(R.id.quote);
        final MediaPlayer heartsound = MediaPlayer.create(this,R.raw.heatlubdub);


        DateFormat df = new SimpleDateFormat("MMM dd");
        String date = df.format(Calendar.getInstance().getTime());


        ecg.setVisibility(View.VISIBLE);
        ecg.playAnimation();
        ecg.setSpeed(3);
        ecg.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                daycounter.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation)
                            {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation)
                            {
                                appname.setVisibility(View.VISIBLE);
                                YoYo.with(Techniques.FadeIn)
                                        .duration(2000)
                                        .playOn(appname);
                                heartback.setVisibility(View.VISIBLE);
                                heartback2.setVisibility(View.VISIBLE);
                                heartback.playAnimation();
                                heartback2.playAnimation();
                                heart.setVisibility(View.VISIBLE);
                                YoYo.with(Techniques.FadeIn)
                                        .duration(6000)
                                        .playOn(heart);
                                heartsound.start();
                                heartsound.setLooping(true);
                                counterback.setVisibility(View.VISIBLE);
                                counterback.playAnimation();
                                counterback2.setVisibility(View.VISIBLE);
                                counterback2.playAnimation();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .duration(2000)
                        .playOn(daycounter);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        counterback2.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                countcig.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(3000)
                        .playOn(countcig);
                buttonback.setVisibility(View.VISIBLE);
                buttonback.playAnimation();
                buttonback.setSpeed(2);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        buttonback.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                taptoadd.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(1000)
                        .playOn(taptoadd);
                removecig.setVisibility(View.VISIBLE);
                removecig.playAnimation();
                YoYo.with(Techniques.FadeIn)
                        .duration(3000)
                        .playOn(removecig);
                quote.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(2000)
                        .playOn(quote);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }






    public void increasecount(View view)
    {
        TextView countcig = findViewById(R.id.countofcig);
        final LottieAnimationView buttonback = findViewById(R.id.buttonback);
        final TextView taptoadd = findViewById(R.id.taptoadd);
        cigcount++;
        String cigcountstr = Integer.toString(cigcount);
        countcig.setText(cigcountstr);
        YoYo.with(Techniques.Landing)
                .duration(1500)
                .playOn(countcig);
        MediaPlayer cough = MediaPlayer.create(this,R.raw.cough);
        cough.start();

        YoYo.with(Techniques.Shake)
                .duration(600)
                .playOn(buttonback);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);

    }
    public void decreasecount(View view)
    {
        TextView countcig = findViewById(R.id.countofcig);
        LottieAnimationView remani = findViewById(R.id.remani);
        cigcount--;
        if(cigcount<0)
        {
            Toasty.error(this,"THE COUNT IS ZERO",Toasty.LENGTH_SHORT).show();
            cigcount = 0 ;
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(200);
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(remani);
        }
        else
        {
            remani.playAnimation();
            String cigcountstr = Integer.toString(cigcount);
            countcig.setText(cigcountstr);
            YoYo.with(Techniques.Landing)
                    .duration(1500)
                    .playOn(countcig);
            MediaPlayer remsound = MediaPlayer.create(this, R.raw.remcigsound);
            remsound.start();
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }
    }
}
