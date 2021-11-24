package com.example.quitsmoking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import es.dmoral.toasty.Toasty;

public class toxicityac extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toxicityac);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //this is to make status bar transparent
        CircularProgressIndicator circularProgress = findViewById(R.id.circular_progress);
        TextView monthdisplay = findViewById(R.id.datedis);
        BubbleNavigationLinearView chemicalselect = findViewById(R.id.chemicalselect);

        String[] months = new String[13];
        months[0] = null;
        months[1] = "January";
        months[2] = "February";
        months[3] = "March";
        months[4] = "April";
        months[5] = "May";
        months[6] = "June";
        months[7] = "July";
        months[8] = "August";
        months[9] = "September";
        months[10] = "October";
        months[11] = "November";
        months[12] = "December";

        chemicalselect.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                if(position==0)
                {
                    nicodisplay();
                    //toxicitypage3();
                }
                else if(position==1)
                {
                    tardisplay();
                }
                else if(position==2)
                {
                    codisplay();
                }
            }
        });



        DateFormat df = new SimpleDateFormat("MM");
        String date = df.format(Calendar.getInstance().getTime());

        DateFormat dff = new SimpleDateFormat("yyyy");
        String year = dff.format(Calendar.getInstance().getTime());
        String monthdisstr = months[Integer.parseInt(date)];
        monthdisplay.setText(monthdisstr +" "+ year);

        Map<String,Object>pd = new HashMap<>();
        pd.put("age","20");
        pd.put("name","amaan");
        pd.put("gender","1");
        pd.put("dob","11/11/2000");
        pd.put("phone","7028850531");
        pd.put("city","nasik");

        Map<String,Object>impd = new HashMap<>();
        impd.put("intakelimit","25");
        impd.put("priceofone","18");
        impd.put("expenselimit","3000");
        impd.put("date_started","11/11/2010");

        Map<String,Object>vd = new HashMap<>();
        vd.put("count","9");
        vd.put("day","31");
        vd.put("month","3");
        vd.put("year","2020");

        Map<String,Object>md = new HashMap<>();
        md.put("total_smoked_in_month","0");
        md.put("nicotine","0");
        md.put("tar","0");
        md.put("cohb","0");
        md.put("money_spent","0");

        /**for(int i =1 ;i<=12;i++)
        {
            db.collection("email").document("xmd "+Integer.toString(i)).update("life_reduced_in_month","0");
        }*/

        db.collection("email").document("pd").set(pd)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       //Toast.makeText(toxicityac.this,"f",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        toxicitypage3();


    }
    public void tardisplay()
    {
        //this function displays Tar page
        TextView datedisplay = findViewById(R.id.datedis);
        TextView desctxt = findViewById(R.id.desctxt);
        TextView quantity = findViewById(R.id.quantity);
        TextView chemname = findViewById(R.id.chemname);
        TextView chemdesc = findViewById(R.id.chemdesc);
        LottieAnimationView backforchem = findViewById(R.id.backforchem);
        final CircularProgressIndicator cp = findViewById(R.id.circular_progress);



        backforchem.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return new PorterDuffColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );

        datedisplay.setTextColor(Color.parseColor("#000000"));
        desctxt.setText("Tar results in chronic diseases and damages lungs.");
        quantity.setText("32.4 mg");
        quantity.setTextColor(Color.parseColor("#FF373737"));
        chemname.setText("Tar");
        chemdesc.setText("Maximum limit for you is 65mg");


        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(desctxt);
        YoYo.with(Techniques.FadeIn)
                .duration(1300)
                .playOn(quantity);
        YoYo.with(Techniques.FadeIn)
                .duration(2300)
                .playOn(chemdesc);
        YoYo.with(Techniques.FadeIn)
                .duration(1800)
                .playOn(chemname);
        YoYo.with(Techniques.TakingOff)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        cp.setMaxProgress(1000);
                        cp.setCurrentProgress(400);
                        cp.setProgressColor(Color.parseColor("#FF424242"));
                        YoYo.with(Techniques.Landing)
                                .duration(1000)
                                .playOn(cp);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(cp);

    }
    public void nicodisplay()
    {
        //this function displays Nicotine page
        TextView datedisplay = findViewById(R.id.datedis);
        TextView desctxt = findViewById(R.id.desctxt);
        TextView quantity = findViewById(R.id.quantity);
        TextView chemname = findViewById(R.id.chemname);
        TextView chemdesc = findViewById(R.id.chemdesc);
        LottieAnimationView backforchem = findViewById(R.id.backforchem);
        final CircularProgressIndicator cp = findViewById(R.id.circular_progress);

        backforchem.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return new PorterDuffColorFilter(Color.parseColor("#372103"), PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );
        datedisplay.setTextColor(Color.parseColor("#372103"));
        desctxt.setText("Nicotine increases blood pressure and heart rate.");
        quantity.setText("52.4 mg");
        quantity.setTextColor(Color.parseColor("#FF5C3705"));
        chemname.setText("Nicotine");
        chemdesc.setText("Maximum limit for you is 60mg");


        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(desctxt);
        YoYo.with(Techniques.FadeIn)
                .duration(1300)
                .playOn(quantity);
        YoYo.with(Techniques.FadeIn)
                .duration(2300)
                .playOn(chemdesc);
        YoYo.with(Techniques.FadeIn)
                .duration(1800)
                .playOn(chemname);
        YoYo.with(Techniques.TakingOff)
                .withListener(new Animator.AnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        cp.setMaxProgress(1000);
                        cp.setCurrentProgress(700);
                        cp.setProgressColor(Color.parseColor("#814D07"));
                        YoYo.with(Techniques.Landing)
                                .duration(1000)
                                .playOn(cp);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation)
                    {

                    }
                })
                .duration(1000)
                .playOn(cp);
    }
    public void codisplay()
    {
        //this function displays COHb page
        TextView datedisplay = findViewById(R.id.datedis);
        TextView desctxt = findViewById(R.id.desctxt);
        TextView quantity = findViewById(R.id.quantity);
        TextView chemname = findViewById(R.id.chemname);
        TextView chemdesc = findViewById(R.id.chemdesc);
        LottieAnimationView backforchem = findViewById(R.id.backforchem);
        final CircularProgressIndicator cp = findViewById(R.id.circular_progress);

        backforchem.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return new PorterDuffColorFilter(Color.parseColor("#FF163001"), PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );
        datedisplay.setTextColor(Color.parseColor("#FF163001"));
        desctxt.setText("Carbon monoxide disrupts oxygen supply to the blood.");
        quantity.setText("12.4 ppm");
        quantity.setTextColor(Color.parseColor("#FF214702"));
        chemname.setText("Carbon Monoxide");
        chemdesc.setText("Maximum limit for you is 70ppm");


        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(desctxt);
        YoYo.with(Techniques.FadeIn)
                .duration(1300)
                .playOn(quantity);
        YoYo.with(Techniques.FadeIn)
                .duration(2300)
                .playOn(chemdesc);
        YoYo.with(Techniques.FadeIn)
                .duration(1800)
                .playOn(chemname);
        YoYo.with(Techniques.TakingOff)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        cp.setMaxProgress(1000);
                        cp.setCurrentProgress(600);
                        cp.setProgressColor(Color.parseColor("#FF326A03"));
                        YoYo.with(Techniques.Landing)
                                .duration(1000)
                                .playOn(cp);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(cp);

    }
    public void toxicitypage3()
    {
        TextView datedis = findViewById(R.id.datedis);
        BubbleNavigationLinearView chemicalselect = findViewById(R.id.chemicalselect);
        TextView description_txt = findViewById(R.id.desctxt);
        TextView quantity_display = findViewById(R.id.quantity);
        CircularProgressIndicator cp = findViewById(R.id.circular_progress);
        TextView chemical_name = findViewById(R.id.chemname);
        TextView chemical_description = findViewById(R.id.chemdesc);
        LottieAnimationView backforchemical = findViewById(R.id.backforchem);
        LottieAnimationView bubbleani = findViewById(R.id.bubbleani);

        View toxicpadeview[]= new View[9];
        toxicpadeview[0]= datedis;
        toxicpadeview[1]= chemicalselect;
        toxicpadeview[2]= description_txt;
        toxicpadeview[3]= quantity_display;
        toxicpadeview[4]= cp;
        toxicpadeview[5]= chemical_name;
        toxicpadeview[6]= chemical_description;
        toxicpadeview[7]= backforchemical;
        toxicpadeview[8]= bubbleani;

        for(int i = 0 ; i <=8 ; i++)
        {
            toxicpadeview[i].setVisibility(View.VISIBLE);
        }
        for (int j = 0;j<9;j++)
        {
            YoYo.with(Techniques.SlideInUp)
                    .duration(700)
                    .playOn(toxicpadeview[j]);
        }

        /**
        datedis.setVisibility(View.VISIBLE);
        chemicalselect.setVisibility(View.VISIBLE);
        description_txt.setVisibility(View.VISIBLE);
        quantity_display.setVisibility(View.VISIBLE);
        cp.setVisibility(View.VISIBLE);
        chemical_name.setVisibility(View.VISIBLE);
        chemical_description.setVisibility(View.VISIBLE);
        backforchemical.setVisibility(View.VISIBLE);
        bubbleani.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .playOn(datedis);
        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .playOn(chemicalselect);
        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .playOn(description_txt);
        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .playOn(quantity_display);
        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .playOn(cp);
        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .playOn(chemical_name);
        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .playOn(chemical_description);
        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .playOn(backforchemical);
        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .playOn(bubbleani);*/

        nicodisplay();

    }
}