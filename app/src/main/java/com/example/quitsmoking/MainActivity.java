package com.example.quitsmoking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sdsmdg.harjot.crollerTest.Croller;




public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    int cigcount = 0;
    com.shawnlin.numberpicker.NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LottieAnimationView mansmoke = findViewById(R.id.mansmoke);
        final TextView counter = findViewById(R.id.counter);
        TextView countertext = findViewById(R.id.counttext);
        numberPicker = findViewById(R.id.numberpicc);

        numberPicker.setOnValueChangedListener(new com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(com.shawnlin.numberpicker.NumberPicker picker, int oldVal, int newVal) {

                String abc = Integer.toString(newVal);
                Toast.makeText(MainActivity.this,abc,Toast.LENGTH_SHORT).show();
            }
        });

        //Intent in = new Intent(this, mainscreen.class);
        //startActivity(in);
    }
    public void fkj(View view)
    {
        Intent in = new Intent(this,mainscreen.class);
        startActivity(in);
    }
    public void fkjgg(View view)
    {
        Intent in = new Intent(this,limits.class);
        startActivity(in);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}
