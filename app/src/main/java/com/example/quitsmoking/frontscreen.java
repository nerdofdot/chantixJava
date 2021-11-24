package com.example.quitsmoking;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class frontscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontscreen);
        TextView welcome = findViewById(R.id.welcome);
        final TextView appname = findViewById(R.id.appname);
        welcome.setVisibility(View.VISIBLE);
        final LottieAnimationView ecg = findViewById(R.id.ecg);
        final LottieAnimationView heartback = findViewById(R.id.heartback);
        final LottieAnimationView heartback2 = findViewById(R.id.heartback2);
        final LottieAnimationView heart = findViewById(R.id.heartani);
        final LottieAnimationView buttonback = findViewById(R.id.buttonback);
        final TextView signin = findViewById(R.id.sigin);
        final TextView getstart = findViewById(R.id.getstart);
        final MediaPlayer heartsound = MediaPlayer.create(this,R.raw.heatlubdub);
        final MediaPlayer appnamesound = MediaPlayer.create(this,R.raw.chantix);
        YoYo.with(Techniques.SlideInDown)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                       ecg.setVisibility(View.VISIBLE);
                       ecg.playAnimation();
                        appname.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .withListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation)
                                    {
                                        heartback.setVisibility(View.VISIBLE);
                                        heartback.playAnimation();
                                        heartback2.setVisibility(View.VISIBLE);
                                        heartback2.playAnimation();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                })
                                .duration(2000)
                                .playOn(appname);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(4000)
                .playOn(welcome);

        heartback.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                heart.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation)
                            {
                                heartsound.start();
                                heartsound.setLooping(true);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation)
                            {
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
                        })
                        .duration(4000)
                        .playOn(heart);
                appnamesound.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
        buttonback.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                getstart.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(1000)
                        .playOn(getstart);
                signin.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(1000)
                        .playOn(signin);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
