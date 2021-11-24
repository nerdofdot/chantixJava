package com.example.quitsmoking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.shawnlin.numberpicker.NumberPicker;

import org.joda.time.Days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class enterdetails extends AppCompatActivity implements NumberPicker.OnValueChangeListener {


    int ageofperson = 0;
    RadioGroup genderselect;
    int gender=-1;
    String nameofperson ="";
    String pincode = "";
    String city = "";
    String phonenumber = "";

    String personEmail = "";

    int com1 = 0;
    int com2 = 0;
    int com3 = 0;
    int com4 = 0;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    com.shawnlin.numberpicker.NumberPicker numberPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterdetails);



        final TextView name = findViewById(R.id.detailtxt);
        final TextView namejj = findViewById(R.id.taptoadddetails);
        final LottieAnimationView userani = findViewById(R.id.userani);
        userani.setMinAndMaxFrame(0,64);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (account != null)
        {
            personEmail = account.getEmail();
            //Toast.makeText(this, personEmail, Toast.LENGTH_SHORT).show();
        }

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        YoYo.with(Techniques.FadeIn)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        userani.setVisibility(View.VISIBLE);
                        userani.playAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1500)
                .playOn(name);

        userani.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                TextView personaldetailtxt = findViewById(R.id.personaldetailtxt);
                YoYo.with(Techniques.FadeIn)
                        .duration(1000)
                        .repeat(0)
                        .playOn(personaldetailtxt);
                TextView personaldetailtxt2 = findViewById(R.id.personaldetailtxt2);
                YoYo.with(Techniques.FadeIn)
                        .duration(1500)
                        .repeat(0)
                        .playOn(personaldetailtxt2);
                TextView personaldetailtxt3 = findViewById(R.id.personaldetailtxt3);
                YoYo.with(Techniques.FadeIn)
                        .duration(2000)
                        .repeat(0)
                        .playOn(personaldetailtxt3);
                TextView personaldetailtxt4 = findViewById(R.id.personaldetailtxt4);
                YoYo.with(Techniques.FadeIn)
                        .duration(2500)
                        .repeat(0)
                        .playOn(personaldetailtxt4);
                YoYo.with(Techniques.FadeIn)
                        .duration(3000)
                        .repeat(0)
                        .playOn(namejj);
                namejj.setVisibility(View.VISIBLE);
                personaldetailtxt.setVisibility(View.VISIBLE);
                personaldetailtxt2.setVisibility(View.VISIBLE);
                personaldetailtxt3.setVisibility(View.VISIBLE);
                personaldetailtxt4.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });



        //int pickedValue = numberPicker.getValue();
        //String agg = Integer.toString(pickedValue);
        //Toast.makeText(this,agg,Toast.LENGTH_SHORT).show();

    }
    public void showdialog(View view)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(enterdetails.this);
        final View mview = getLayoutInflater().inflate(R.layout.dialogfordetails,null);
        final RadioButton[] radiobutton = new RadioButton[1];

        final TextView name = findViewById(R.id.detailtxt);

        genderselect = mview.findViewById(R.id.radioGroupgender);
        final LottieAnimationView tick = findViewById(R.id.tick);

        final TextView personaldetailtxt= findViewById(R.id.personaldetailtxt);


        final EditText nameenetring = mview.findViewById(R.id.phonenoenter);
        TextView ok = mview.findViewById(R.id.ok);
        TextView cancel = mview.findViewById(R.id.cancel);
        numberPicker = mview.findViewById(R.id.numberpicc);




        alert.setView(mview);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        mview.setClipToOutline(true);
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.backfordilog);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameofperson = nameenetring.getText().toString();
                gender = genderselect.getCheckedRadioButtonId();
                radiobutton[0] = mview.findViewById(gender);
                String genstr = (String) radiobutton[0].getText();
                if(genstr.equalsIgnoreCase("male"))
                {
                    gender = 1;
                }
                else if (genstr.equalsIgnoreCase("female"))
                {
                    gender = 0;
                }
                else if(genstr.equalsIgnoreCase("others"))
                {
                    gender = 2;
                }
                ageofperson=numberPicker.getValue();

                alertDialog.dismiss();
                if((!(nameofperson.equalsIgnoreCase("")))&&(ageofperson>=10&&ageofperson<=100)&&((gender==1)||(gender==0)||(gender==2)))
                {
                    com1=5;
                    db.collection(personEmail).document("pd").update("gender",Integer.toString(gender));
                    db.collection(personEmail).document("pd").update("age",Integer.toString(ageofperson));
                    db.collection(personEmail).document("pd").update("name",nameofperson)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    tick.setVisibility(View.VISIBLE);
                                    tick.playAnimation();
                                    personaldetailtxt.setBackgroundResource(R.drawable.completeback);
                                    YoYo.with(Techniques.FadeIn)
                                            .duration(1000)
                                            .playOn(personaldetailtxt);
                                    com1=1;
                                    displaynext();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toasty.error(enterdetails.this,"Re-enter details. Connection error!", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
        YoYo.with(Techniques.FadeInDown)
                .duration(1000)
                .playOn(mview);

        alertDialog.show();

        //String agg = Integer.toString(ageofperson);
        //Toast.makeText(this,agg,Toast.LENGTH_SHORT).show();
    }
    public void selectdob(View view)
    {
        final AlertDialog.Builder alert2 = new AlertDialog.Builder(enterdetails.this);
        final View mview2 = getLayoutInflater().inflate(R.layout.dialogfordob,null);
        TextView setdob = mview2.findViewById(R.id.setbuttt);
        final TextView personaldetailtxt2 = findViewById(R.id.personaldetailtxt2);
        alert2.setView(mview2);
        final SingleDateAndTimePicker dobpic = mview2.findViewById(R.id.singleDateAndTimePicker);
        final LottieAnimationView tick2 = findViewById(R.id.tick2);

        final AlertDialog alertDialog2 = alert2.create();
        alertDialog2.setCanceledOnTouchOutside(false);
        mview2.setClipToOutline(true);
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog2.getWindow().setBackgroundDrawableResource(R.drawable.backfordilog);
        YoYo.with(Techniques.FadeInDown)
                .duration(1000)
                .playOn(mview2);

        alertDialog2.show();
        setdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dobpic.getDate().toString();

                //Toast.makeText(enterdetails.this,date,Toast.LENGTH_SHORT).show();
                alertDialog2.dismiss();
                com2=5;
                db.collection(personEmail).document("pd").update("dob",date)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                tick2.setVisibility(View.VISIBLE);
                                tick2.playAnimation();
                                personaldetailtxt2.setBackgroundResource(R.drawable.completeback);
                                YoYo.with(Techniques.FadeIn)
                                        .duration(1000)
                                        .playOn(personaldetailtxt2);
                                com2=1;
                                displaynext();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toasty.error(enterdetails.this,"Re-enter details. Connection error!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    public void extradetails(View view)
    {
        final AlertDialog.Builder alert3 = new AlertDialog.Builder(enterdetails.this);
        final View mview3 = getLayoutInflater().inflate(R.layout.dialogforextradetail,null);
        alert3.setView(mview3);

        final AlertDialog alertDialog3 = alert3.create();
        alertDialog3.setCanceledOnTouchOutside(false);
        mview3.setClipToOutline(true);
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog3.getWindow().setBackgroundDrawableResource(R.drawable.backfordilog);
        YoYo.with(Techniques.FadeInDown)
                .duration(1000)
                .playOn(mview3);

        alertDialog3.show();
        TextView setextra = mview3.findViewById(R.id.setextdetail);
        TextView cancelextra = mview3.findViewById(R.id.cancelextdetails);

        final EditText phonenuenter = mview3.findViewById(R.id.phonenoenter);
        final EditText cityenter = mview3.findViewById(R.id.cityenter);
        final EditText pincodeenter = mview3.findViewById(R.id.pincodeenter);
        final LottieAnimationView tick3 = findViewById(R.id.tick3);

        final TextView personaldetailtxt3 = findViewById(R.id.personaldetailtxt3);

        setextra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = cityenter.getText().toString();
                pincode = pincodeenter.getText().toString();
                phonenumber = phonenuenter.getText().toString();
                alertDialog3.dismiss();
                if((!city.equalsIgnoreCase(""))&&(!pincode.equalsIgnoreCase(""))&&(!phonenumber.equalsIgnoreCase(""))) {
                    com3=5;
                    db.collection(personEmail).document("pd").update("phone",phonenumber);
                    db.collection(personEmail).document("pd").update("city",city.toLowerCase())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    tick3.setVisibility(View.VISIBLE);
                                    tick3.playAnimation();
                                    personaldetailtxt3.setBackgroundResource(R.drawable.completeback);
                                    YoYo.with(Techniques.FadeIn)
                                            .duration(1000)
                                            .playOn(personaldetailtxt3);
                                    com3=1;
                                    displaynext();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toasty.error(enterdetails.this,"Re-enter details. Connection error!", Toast.LENGTH_LONG).show();
                                }
                            });

                }
                else
                    Toasty.error(enterdetails.this,"Enter the Extra details",Toasty.LENGTH_SHORT).show();
            }
        });
        cancelextra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog3.dismiss();
            }
        });



    }
    public void datestartedsmoking(View view)
    {
        final AlertDialog.Builder alert4 = new AlertDialog.Builder(enterdetails.this);
        final View mview4 = getLayoutInflater().inflate(R.layout.dialogforstartingdate,null);
        alert4.setView(mview4);

        final AlertDialog alertDialog4 = alert4.create();
        alertDialog4.setCanceledOnTouchOutside(false);
        mview4.setClipToOutline(true);
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog4.getWindow().setBackgroundDrawableResource(R.drawable.backfordilog);
        YoYo.with(Techniques.FadeInDown)
                .duration(1000)
                .playOn(mview4);

        alertDialog4.show();
        TextView setsmokingdate = mview4.findViewById(R.id.setbuttt2);
        final SingleDateAndTimePicker smokepic = mview4.findViewById(R.id.singleDateAndTimePickersmoking);
        final LottieAnimationView tick4 = findViewById(R.id.tick4);
        final TextView personaldetailtxt4 = findViewById(R.id.personaldetailtxt4);

        setsmokingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Date datestarted = smokepic.getDate();

                //Toast.makeText(enterdetails.this, dd, Toast.LENGTH_SHORT).show();
                alertDialog4.dismiss();
                com4=5;
                db.collection(personEmail).document("impd").update("date_started",datestarted)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                tick4.setVisibility(View.VISIBLE);
                                tick4.playAnimation();
                                personaldetailtxt4.setBackgroundResource(R.drawable.completeback);
                                YoYo.with(Techniques.FadeIn)
                                        .duration(1000)
                                        .playOn(personaldetailtxt4);
                                com4=1;
                                displaynext();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(enterdetails.this,"Re-enter details. Connection error!", Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }

    public void displaynext()
    {
        if(com1==1&&com2==1&&com3==1&&com4==1)
        {
            //put in an intent
            Intent yu = new Intent(enterdetails.this,limits.class);
            startActivity(yu);
            Animatoo.animateFade(enterdetails.this);
            finish();
        }
        if(com1==5&&com2==5&&com3==5&&com4==5)
        {
            //values not updated
            Toasty.error(enterdetails.this,"Re-enter details. Connection error! Can't progress.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

}
