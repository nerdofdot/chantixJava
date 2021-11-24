package com.example.quitsmoking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class bridge extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String personEmail = "";
    LottieAnimationView progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge);
        progress = findViewById(R.id.progress2);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (account != null)
        {
            personEmail = account.getEmail();
            //Toast.makeText(this, personEmail, Toast.LENGTH_SHORT).show();
        }
        db.collection("accounts_created").document(personEmail).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        if(documentSnapshot.exists())
                        {
                            //Toast.makeText(bridge.this, "posi", Toast.LENGTH_SHORT).show();
                            int newval = Integer.parseInt(documentSnapshot.getString("new"));
                            //Toast.makeText(bridge.this, Integer.toString(newval), Toast.LENGTH_SHORT).show();
                            //old user          call intent directly
                            YoYo.with(Techniques.FadeOut)
                                    .withListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            Intent tomainact = new Intent(bridge.this,mainscreen.class);
                                            startActivity(tomainact);
                                            finish();
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    })
                                    .duration(300)
                                    .playOn(progress);


                        }
                        else
                        {
                            //Toast.makeText(bridge.this, "posinew", Toast.LENGTH_SHORT).show();
                            //newuser
                            newusertochantix();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(bridge.this, "Network error!", Toast.LENGTH_SHORT).show();
                //internet issue
            }
        });

    }
    public void newusertochantix()
    {

        DateFormat day_today = new SimpleDateFormat("dd");
        String day_now =day_today.format(Calendar.getInstance().getTime());
        DateFormat month_today = new SimpleDateFormat("MM");
        String month_now =month_today.format(Calendar.getInstance().getTime());
        DateFormat year_toady = new SimpleDateFormat("yyyy");
        String year_now = year_toady.format(Calendar.getInstance().getTime());


        // create collection
        //on success call enterdetails
        Map<String,Object> pd = new HashMap<>();
        pd.put("age","0");
        pd.put("city"," ");
        pd.put("dob"," ");
        pd.put("gender","3");
        pd.put("name"," ");
        pd.put("phone"," ");

        db.collection(personEmail).document("pd").set(pd)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        //display toast of database created
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // no internet page
            }
        });

        Map<String,Object> vd = new HashMap<>();
        vd.put("count","0");
        vd.put("day",day_now);
        vd.put("month",month_now);
        vd.put("sms","0");
        vd.put("year",year_now);

        db.collection(personEmail).document("vd").set(vd)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        Date d = new Date();
        Map<String,Object> impd = new HashMap<>();
        impd.put("date_started",d);
        impd.put("expenselimit","0");
        impd.put("intakelimit","0");
        impd.put("new_user","0");
        impd.put("priceofone","0");
        impd.put("total_money_spent","0");
        impd.put("total_smoked","0");

        db.collection(personEmail).document("impd").set(impd)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        Map<String ,Object> xmd = new HashMap<>();
        xmd.put("money_spent","0");
        xmd.put("nicotine","0");
        xmd.put("tar","0");
        xmd.put("total_smoked_in_month","0");

        for(int i = 1;i<=12;i++)
        {
            db.collection(personEmail).document("xmd "+i).set(xmd);
        }

        Map<String,Object> zmonthcount = new HashMap<>();
        for(int i =1 ;i<=31;i++)
        {
            zmonthcount.put(Integer.toString(i),"0");
        }

        db.collection(personEmail).document("zmonthcount").set(zmonthcount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        YoYo.with(Techniques.FadeOut)
                                .withListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        Intent openenter = new Intent(bridge.this,enterdetails.class);
                                        startActivity(openenter);
                                        finish();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                })
                                .duration(300)
                                .playOn(progress);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Intent nointernet = new Intent(bridge.this,enterdetails.class);
                        startActivity(nointernet);
                        finish();
                    }
                });
    }
}