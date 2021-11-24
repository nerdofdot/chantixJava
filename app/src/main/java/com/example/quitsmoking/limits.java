package com.example.quitsmoking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class limits extends AppCompatActivity {
    Vibrator vibrator;
    TextView ciglimit;
    EditText priceofone;
    EditText monthlyexpense;
    Date datestarted;
    double total_smoked_before;

    String personEmail = "";

    int com1 = 0;
    int com2 = 0;
    int com3 = 0;

    int cigarlimit = 0;
    double b=0;
    double bb = 0;
    double total_money_spent_till_date = 0;
    int newuser = -1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limits);
        ciglimit= findViewById(R.id.ciglimit);
        TextView dailylimtext= findViewById(R.id.dailylimittext);
        TextView backforciglim= findViewById(R.id.backforlimit);
        TextView moneytext= findViewById(R.id.moneytext);
        TextView info= findViewById(R.id.info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        priceofone= findViewById(R.id.priceofone);
        monthlyexpense = findViewById(R.id.moneylimit);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (account != null)
        {
            personEmail = account.getEmail();
            //Toast.makeText(this, personEmail, Toast.LENGTH_SHORT).show();
        }



        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        Croller croller = findViewById(R.id.croller);
        croller.setIndicatorWidth(15);
        croller.setMax(51);
        croller.setStartOffset(45);

        db.collection(personEmail).document("impd").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            datestarted = documentSnapshot.getDate("date_started");
                            //Toast.makeText(limits.this, datestarted.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.error(limits.this,"Check the internet connection.",Toast.LENGTH_LONG).show();
                        Intent hg = new Intent(limits.this,nointernet.class);
                        startActivity(hg);
                        finish();
                    }
                });

        db.collection("accounts_created").document(personEmail).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        if(documentSnapshot.exists())
                        {
                            //old user
                            newuser=1;
                        }
                        else
                        {
                            //new user
                            newuser=0;
                        }
                    }
                });

        dailylimtext.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(dailylimtext);
        croller.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(2500)
                .playOn(croller);
        backforciglim.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(backforciglim);
        moneytext.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(5500)
                .playOn(moneytext);
        priceofone.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(7000)
                .playOn(priceofone);
        monthlyexpense.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(7000)
                .playOn(monthlyexpense);
        info.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(8000)
                .playOn(info);



        croller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                // use the progress
                //vibrator.vibrate(10);
                String ciglimitstr= Integer.toString(progress-1);
                ciglimit.setText(ciglimitstr);

            }

            @Override
            public void onStartTrackingTouch(Croller croller) {
                // tracking started
            }

            @Override
            public void onStopTrackingTouch(Croller croller) {
                // tracking stopped
                vibrator.vibrate(100);
                YoYo.with(Techniques.Tada)
                        .duration(1000)
                        .playOn(ciglimit);
            }
        });


    }
    public void senddata(View view)
    {
        String cigarettelimit = ciglimit.getText().toString();
        String priceofonecigarette = priceofone.getText().toString();
        String monthlyexpenseoncigarette = monthlyexpense.getText().toString();
        b = Double.parseDouble(priceofonecigarette);
        bb = Double.parseDouble(monthlyexpenseoncigarette);
        cigarlimit = Integer.parseInt(ciglimit.getText().toString());

        if(cigarettelimit.equals("0"))
        {
            Toasty.error(limits.this,"The limit can't be set to Zero",Toast.LENGTH_SHORT).show();
            vibrator.vibrate(100);
        }
        else
        {
            com1 = 1;
        }

        if(b==0)
        {
            Toasty.error(limits.this,"Set a valid price.",Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Tada)
                    .duration(1000)
                    .playOn(priceofone);
            priceofone.setText("");
            vibrator.vibrate(100);
        }
        else
        {
            com2 = 1;
        }

        if(bb==0)
        {
            Toasty.error(limits.this,"Set a valid expense limit.",Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Tada)
                    .duration(1000)
                    .playOn(monthlyexpense);
            monthlyexpense.setText("");
            vibrator.vibrate(100);
        }
        else
        {
            com3 = 1;
        }

        if(com1==1&&com2==1&&com3==1&&newuser==0)
        {
            calculateprevioussmoked();
        }
        else if(com1==1&&com2==1&&com3==1&&newuser==1)
        {
            setthedata();
        }


    }

    public void calculateprevioussmoked()
    {
        long millis = datestarted.getTime();
        Date now = Calendar.getInstance().getTime();
        long millis2 = now.getTime();
        int days = (int) TimeUnit.MILLISECONDS.toDays(millis);
        int days2 = (int) TimeUnit.MILLISECONDS.toDays(millis2);
        //Toast.makeText(limits.this, Integer.toString(days2-days), Toast.LENGTH_SHORT).show();
        total_smoked_before = cigarlimit * (days2-days);
        total_money_spent_till_date = total_smoked_before*b;
        db.collection(personEmail).document("impd").update("total_smoked",Double.toString(total_smoked_before));
        db.collection(personEmail).document("impd").update("total_money_spent",Double.toString(total_money_spent_till_date));
        setthedata();
    }

    public void setthedata()
    {
        Map<String,Object> g = new HashMap<>();
        g.put("new","1");

        db.collection("accounts_created").document(personEmail).set(g);
        db.collection(personEmail).document("impd").update("priceofone",Double.toString(b));
        db.collection(personEmail).document("impd").update("intakelimit",Integer.toString(cigarlimit));
        db.collection(personEmail).document("impd").update("expenselimit",Double.toString(bb))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //intent

                        Intent main = new Intent(limits.this,mainscreen.class);
                        startActivity(main);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(limits.this,"Check your internet and try again.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
