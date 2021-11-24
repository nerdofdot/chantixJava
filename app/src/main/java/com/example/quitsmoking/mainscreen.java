package com.example.quitsmoking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.RenderMode;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import es.dmoral.toasty.Toasty;

public class mainscreen extends AppCompatActivity implements OnMapReadyCallback {

    Vibrator vibrator;
    String[] months = new String[13];
    Handler mainHandler = new Handler();


    int loadpage1=0;
    int first=1;
    int intialize=0;

    int daily_intake_limit = -1;
    int daily_count = -1;
    int day_in_db = -1;
    int day_now = -1;
    int month_in_db = -1;
    int month_now = -1;
    int year_in_db = -1;
    int year_now = -1;


    double total_smoked_till_date = -1;
    double life_reduced_till_now = -1;

    int total_smoked_in_month = -1;
    double nicotine_in_month = -1;
    String nicotine_in_month_display = null;
    int nicotine_in_ng = -1;
    int cigarette_count_day_by_day[]=new int[10];
    int cigarette_count_day_by_day_dynamic[]=new int[31];
    int intensity_count = 0;
    int intensity_count2 = 0;
    int tar_addition = 0;
    int tar_in_month = 0;

    double cohb_value = 0;

    String nicotine_facts[]=new String[5];

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MediaPlayer heartsound;

//views for page 1
    BubbleNavigationLinearView bottomnavi;
    View counterpageview[] = new View[14];
    LottieAnimationView ecg;
    LottieAnimationView heartback;
    LottieAnimationView heart;
    LottieAnimationView heartback2;
    TextView daycounter ;
    TextView appname;
    LottieAnimationView counterback;
    LottieAnimationView counterback2;
    TextView countcig;
    LottieAnimationView addbut;
    LottieAnimationView removecig;
    TextView quote;

    TextView lifereducedtxt;
    TextView lifereduced;
    TextView taptoadd;

    //views for page 3
    View toxicpadeview[]= new View[9];
    TextView datedis;
    BubbleNavigationLinearView chemicalselect;
    TextView description_txt;
    TextView quantity_display;
    CircularProgressIndicator cp;
    TextView chemical_name;
    TextView chemical_description;
    LottieAnimationView backforchemical;
    LottieAnimationView bubbleani;

    //views for page 2
    View graphpageviews[]=new View[7];
    BarChart barChart;
    BarChart barChart2;
    BarChart barChart3;
    ArrayList<graphdata> arrayList = new ArrayList<>();
    ArrayList<BarEntry> barEntries;
    ArrayList<String> strings;
    ArrayList<graphdata> arrayList2 = new ArrayList<>();
    ArrayList<BarEntry> barEntries2;
    ArrayList<String> strings2;
    ArrayList<graphdata> arrayList3 = new ArrayList<>();
    ArrayList<BarEntry> barEntries3;
    ArrayList<String> strings3;
    String months_name[]=new String[12];

    TextView year_display;
    TextView description_graph;
    TextView description_graph2;
    TextView description_graph3;
    TextView maximum_in_month_for_graph;
    BubbleNavigationLinearView graphselection;

    Typeface typeface;

   int life_reduced_in_month_array[]=new int[12];
   int total_smoked_in_month_array[]=new int[12];
   double total_money_spent_monthly_array[] = new double[12];

   int max_position_for_life_reduced_in_month = -1;

   double price_of_one_cigarette = -1;
   double total_money_spent = -1;
   double monthly_expense_limit =-1;
   double total_money_spent_monthly=-1;

   int accc = 0;

   //views for page 4
   private static final String TAG = "mainscreen";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;


    private Boolean mpermissionlocationgranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFuselocationproviderclient;
    private static final float DEFAULT_ZOOM = 15f;

    String name_of_oncologist = "";
    String address_of_oncologist = "";
    String phonenumber_of_oncologist = "";
    String website_of_oncologist = "";

    String name_of_cardiologist = "";
    String address_of_cardiologist = "";
    String phonenumber_of_cardiologist = "";
    String website_of_cardiologist = "";

    String name_of_ent = "";
    String address_of_ent = "";
    String phonenumber_of_ent = "";
    String website_of_ent = "";

    String name_of_rehab = "";
    String address_of_rehab = "";
    String phonenumber_of_rehab = "";
    String website_of_rehab = "";

    String api_key_for_maps = "AIzaSyBJQcJHahFxYintO8pPPI7KkKnOaS7DwJM";
    String predefined_url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=";
    boolean all_doctor_data_available= false;

    TextView description_of_doctor;
    TextView name_of_doctor;
    TextView phoneno_of_doctor;
    TextView website_of_doctor;
    TextView address_of_doctor;

    BubbleNavigationLinearView doctorselect;
    LottieAnimationView doctor_animation;
    LottieAnimationView rehab_animation;

    ImageView info_button;

    View doctorpageviews[]=new View[9];

    String name_of_person = "";
    String phone_number_of_person="";
    String city_of_person="";

    int sms_counter = -1;
    int avvv = 0;

    double pack_year_of_person = 0;

    String email_of_person = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //this is to make status bar transparent


        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        typeface = ResourcesCompat.getFont(this, R.font.comfarta);

        //Intent hy = new Intent(mainscreen.this,Loginscreen.class);
       // startActivity(hy);
        //finish();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (account != null)
        {
            email_of_person = account.getEmail();
            //Toast.makeText(this, personEmail, Toast.LENGTH_SHORT).show();
        }


        //views for page one
        if(intialize==0)
        {
            bottomnavi = findViewById(R.id.bottom_navigation_view_linear);

            ecg = findViewById(R.id.ecg);
            heartback = findViewById(R.id.heartback);
            heart = findViewById(R.id.heartani);
            heartback2 = findViewById(R.id.heartback2);
            daycounter = findViewById(R.id.daycount);
            appname = findViewById(R.id.appname);
            counterback = findViewById(R.id.counterback);
            counterback2 = findViewById(R.id.counterback2);
            countcig = findViewById(R.id.countofcig);
            addbut = findViewById(R.id.addbut);
            removecig = findViewById(R.id.remani);
            quote = findViewById(R.id.quote);

            lifereducedtxt = findViewById(R.id.lifereducedtxt);
            lifereduced = findViewById(R.id.lifereduced);
            taptoadd = findViewById(R.id.taptoaddcig);

            counterpageview[0]=ecg;
            counterpageview[1]=heart;
            counterpageview[2]=heartback;
            counterpageview[3]=heartback2;
            counterpageview[4]=counterback;
            counterpageview[5]=counterback2;
            counterpageview[6]=daycounter;
            counterpageview[7]=countcig;
            counterpageview[8]=addbut;
            counterpageview[9]=removecig;
            counterpageview[10]=appname;
            counterpageview[11]=lifereducedtxt;
            counterpageview[12]=lifereduced;
            counterpageview[13]=taptoadd;




            //views for page 3*******************************************

            datedis = findViewById(R.id.datedis);
            chemicalselect = findViewById(R.id.chemicalselect);
            description_txt = findViewById(R.id.desctxt);
            quantity_display = findViewById(R.id.quantity);
            cp = findViewById(R.id.circular_progress);
            chemical_name = findViewById(R.id.chemname);
            chemical_description = findViewById(R.id.chemdesc);
            backforchemical = findViewById(R.id.backforchem);
            bubbleani = findViewById(R.id.bubbleani);

            toxicpadeview[0]= datedis;
            toxicpadeview[1]= chemicalselect;
            toxicpadeview[2]= description_txt;
            toxicpadeview[3]= quantity_display;
            toxicpadeview[4]= cp;
            toxicpadeview[5]= chemical_name;
            toxicpadeview[6]= chemical_description;
            toxicpadeview[7]= bubbleani;
            toxicpadeview[8]= backforchemical;

            DateFormat day_today = new SimpleDateFormat("dd");
            day_now = Integer.parseInt(day_today.format(Calendar.getInstance().getTime()));
            DateFormat month_today = new SimpleDateFormat("MM");
            month_now = Integer.parseInt(month_today.format(Calendar.getInstance().getTime()));
            DateFormat year_toady = new SimpleDateFormat("yyyy");
            year_now = Integer.parseInt(year_toady.format(Calendar.getInstance().getTime()));


            nicotine_facts[0]="Nicotine increases blood pressure and heart rate.";
            nicotine_facts[1]="Nicotine stops formation of new brain cells.";
            nicotine_facts[2]="Nicotine causes increase in blood sugar level.";
            nicotine_facts[3]="Nicotine may result in nausea and vomiting.";
            nicotine_facts[4]="Nicotine may result in infertility and joint pain.";


            //views for page 2 ----------------------------------------------------------------------------

            graphselection = findViewById(R.id.graphselect);

            months_name[0]="Jan";
            months_name[1]="Feb";
            months_name[2]="Mar";
            months_name[3]="Apr";
            months_name[4]="May";
            months_name[5]="Jun";
            months_name[6]="Jul";
            months_name[7]="Aug";
            months_name[8]="Sep";
            months_name[9]="Oct";
            months_name[10]="Nov";
            months_name[11]="Dec";

            barChart= findViewById(R.id.bargraph);
            barChart2=findViewById(R.id.bargraph2);
            barChart3=findViewById(R.id.bargraph3);


            year_display = findViewById(R.id.yeardis);
            description_graph = findViewById(R.id.desc_graph);
            description_graph2 = findViewById(R.id.graphslidinginfo);
            description_graph3=findViewById(R.id.tapongrapghinfo);
            maximum_in_month_for_graph=findViewById(R.id.maximumdisplay);

            graphpageviews[0]=year_display;
            graphpageviews[1]=description_graph;
            graphpageviews[2]=description_graph2;
            graphpageviews[3]=description_graph3;
            graphpageviews[4]=barChart;
            graphpageviews[5]=maximum_in_month_for_graph;
            graphpageviews[6]=graphselection;


            //views for page4---------------------------------------------------------------

            description_of_doctor = findViewById(R.id.doctordis);
            name_of_doctor = findViewById(R.id.desctxtfordoc);
            phoneno_of_doctor = findViewById(R.id.doctorphoneno);
            website_of_doctor = findViewById(R.id.doctorwebsite);
            address_of_doctor=  findViewById(R.id.doctoraddress);
            doctorselect = findViewById(R.id.doctorselect);
            doctor_animation = findViewById(R.id.doctorani);
            rehab_animation = findViewById(R.id.rehabani);
            info_button = findViewById(R.id.infobutton);

            doctorpageviews[0]= description_of_doctor ;
            doctorpageviews[1]= name_of_doctor;
            doctorpageviews[2]= phoneno_of_doctor;
            doctorpageviews[3]= website_of_doctor;
            doctorpageviews[4]= address_of_doctor;
            doctorpageviews[5]= doctor_animation;
            doctorpageviews[6]= doctorselect;
            doctorpageviews[7]= rehab_animation;
            doctorpageviews[8]= info_button;



            intialize=1;
        }




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

        graphselection.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                if(position==0)
                {
                    display_first_graph();
                }
                else if(position==1)
                {
                    display_second_graph();
                }
                else
                {
                    display_third_graph();
                }
            }
        });


        bottomnavi.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position)
            {
                if(position==0)
                {
                    vanishpage4();
                    vanishpage3();
                    vanishpage2();
                    dailycounterpage1();
                }
                else if(position==1)
                {
                    vanishpage4();
                    vanishpage1();
                    vanishpage3();
                    graphpage2();
                }
                else if(position==2)
                {
                    vanishpage4();
                    vanishpage1();
                    vanishpage2();
                    toxicitypage3();
                }
                else
                {
                    vanishpage1();
                    vanishpage2();
                    vanishpage3();
                    doctorpage4();
                }
            }
        });

        doctorselect.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position)
            {
                if(position==0)
                {
                    display_ent();
                }
                else if(position==1)
                {
                    display_oncologist();
                }
                else if(position==2)
                {
                    display_cardiologist();
                }
                else
                {
                    display_rehab();
                }
            }
        });


        if(first==1)
        {
            dailycounterpage1();
            first=0;
        }

        getdata();

        heartsound = MediaPlayer.create(this, R.raw.heatlubdub);
        //bottomnavi.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInUp)
                .duration(5000)
                .playOn(bottomnavi);
        //create_graph_for_life();

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //get the index of selected bar
                int x =barChart.getBarData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);

                String month_on_alertbox= months[x+1];
                String val_on_alertbox = Integer.toString(arrayList.get(x).getValue());

                AlertDialog.Builder builder_for_alertgraph = new AlertDialog.Builder(mainscreen.this);
                builder_for_alertgraph.setCancelable(true);

                View nview = LayoutInflater.from(mainscreen.this).inflate(R.layout.graph_alert,null);

                TextView mon_txt = nview.findViewById(R.id.month_display_alertbox);
                TextView val_txt = nview.findViewById(R.id.val_display_alertbox);
                TextView val_txt2 =nview.findViewById(R.id.val2_display_alertbox);

                double lifelostindays = life_reduced_in_month_array[x]/24;
                String temppp = Double.toString(lifelostindays).substring(0,(Double.toString(lifelostindays).indexOf(".")));

                mon_txt.setText(month_on_alertbox);
                mon_txt.setTextColor(Color.parseColor("#FF611212"));
                val_txt.setText("Life lost :  "+val_on_alertbox+" hours");
                val_txt2.setText("Life lost in "+months[x+1]+" : "+temppp+" days");

                builder_for_alertgraph.setView(nview);

                AlertDialog alertDialog = builder_for_alertgraph.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

            }

            @Override
            public void onNothingSelected() {

            }
        });
        barChart2.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //get the index of selected bar
                int x =barChart2.getBarData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);

                String month_on_alertbox= months[x+1];
                String val_on_alertbox = Integer.toString(arrayList2.get(x).getValue());

                AlertDialog.Builder builder_for_alertgraph = new AlertDialog.Builder(mainscreen.this);
                builder_for_alertgraph.setCancelable(true);

                View nview = LayoutInflater.from(mainscreen.this).inflate(R.layout.graph_alert,null);

                TextView mon_txt = nview.findViewById(R.id.month_display_alertbox);
                TextView val_txt = nview.findViewById(R.id.val_display_alertbox);
                TextView val_txt2 =nview.findViewById(R.id.val2_display_alertbox);

                double moneyspentinmonth = total_money_spent_monthly_array[x];
                String temppp = Double.toString(moneyspentinmonth).substring(0,(Double.toString(moneyspentinmonth).indexOf(".")));
                String tempppt = Double.toString(total_money_spent).substring(0,(Double.toString(total_money_spent).indexOf(".")));


                mon_txt.setText(month_on_alertbox);
                mon_txt.setTextColor(Color.parseColor("#FF611212"));
                val_txt.setText("Spent in month :  ₹ "+temppp);
                val_txt2.setText("Spent till date :  ₹ "+tempppt);

                builder_for_alertgraph.setView(nview);

                AlertDialog alertDialog = builder_for_alertgraph.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

            }

            @Override
            public void onNothingSelected() {

            }
        });
        barChart3.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //get the index of selected bar
                int x =barChart3.getBarData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);

                String month_on_alertbox= months[x+1];
                String val_on_alertbox = Integer.toString(arrayList3.get(x).getValue());

                AlertDialog.Builder builder_for_alertgraph = new AlertDialog.Builder(mainscreen.this);
                builder_for_alertgraph.setCancelable(true);

                View nview = LayoutInflater.from(mainscreen.this).inflate(R.layout.graph_alert,null);

                TextView mon_txt = nview.findViewById(R.id.month_display_alertbox);
                TextView val_txt = nview.findViewById(R.id.val_display_alertbox);
                TextView val_txt2 =nview.findViewById(R.id.val2_display_alertbox);

                String temppp=Double.toString(total_smoked_till_date);
                String temp2 = temppp.substring(0,temppp.indexOf("."));

                mon_txt.setText(month_on_alertbox);
                mon_txt.setTextColor(Color.parseColor("#131313"));
                val_txt.setText("Intake in month : "+ total_smoked_in_month_array[x]);
                val_txt2.setText("Intakes till date : "+temp2);

                builder_for_alertgraph.setView(nview);

                AlertDialog alertDialog = builder_for_alertgraph.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

            }

            @Override
            public void onNothingSelected() {

            }
        });
        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_for_packyears = new AlertDialog.Builder(mainscreen.this);
                builder_for_packyears.setCancelable(true);

                View nview = LayoutInflater.from(mainscreen.this).inflate(R.layout.packyeardisplay,null);
                TextView pack_year_display = nview.findViewById(R.id.packyeardistext);
                TextView pack_year_alert = nview.findViewById(R.id.packyearalert);
                TextView url_worldometer = nview.findViewById(R.id.worldometerurl);

                String temp = Double.toString(pack_year_of_person);
                String temp2 = temp.substring(0,temp.indexOf(".")+2);

                pack_year_display.setText(temp2);

                if(pack_year_of_person>=35)
                {
                    pack_year_alert.setText("Alert! Contact a doctor for lungs screening");
                }

                builder_for_packyears.setView(nview);

                AlertDialog alertDialogpackyears = builder_for_packyears.create();
                alertDialogpackyears.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialogpackyears.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialogpackyears.show();

                url_worldometer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://www.worldometers.info/"));
                        startActivity(i);
                    }
                });
            }
        });

        if (isServicesOK())
        {
            mapactivity();
        }


    }

    public void increasecount(View view)
    {
        daily_count++;
        total_smoked_till_date++;
        total_smoked_in_month++;


        total_money_spent = total_money_spent + price_of_one_cigarette;
        total_money_spent_monthly = total_money_spent_monthly + price_of_one_cigarette;

        db.collection(email_of_person).document("vd").update("count",Integer.toString(daily_count));
        db.collection(email_of_person).document("impd").update("total_smoked",Double.toString(total_smoked_till_date));
        db.collection(email_of_person).document("xmd "+month_now).update("total_smoked_in_month",Integer.toString(total_smoked_in_month));

        db.collection(email_of_person).document("impd").update("total_money_spent",Double.toString(total_money_spent));
        db.collection(email_of_person).document("xmd "+month_now).update("money_spent",Double.toString(total_money_spent_monthly));

        String cigcountstr = Integer.toString(daily_count);
        countcig.setText(cigcountstr);
        //set color of the counter value
        setcigcountcolor();

        YoYo.with(Techniques.Landing)
                .duration(1500)
                .playOn(countcig);
        MediaPlayer cough = MediaPlayer.create(this,R.raw.cough);
        cough.start();
        addbut.playAnimation();

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }

    public void decreasecount(View view)
    {

        if(daily_count==0)
        {
            Toasty.error(this,"THE COUNT IS ZERO",Toasty.LENGTH_SHORT).show();
            daily_count = 0 ;
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(200);
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(removecig);
        }
        else
        {
            daily_count--;
            total_smoked_till_date--;
            total_smoked_in_month--;

            total_money_spent = total_money_spent - price_of_one_cigarette;
            total_money_spent_monthly = total_money_spent_monthly - price_of_one_cigarette;

            removecig.playAnimation();
            String cigcountstr = Integer.toString(daily_count);
            countcig.setText(cigcountstr);
            YoYo.with(Techniques.Landing)
                    .duration(1500)
                    .playOn(countcig);
            //MediaPlayer remsound = MediaPlayer.create(this, R.raw.remcigsound);
            //remsound.start();
            db.collection(email_of_person).document("vd").update("count",Integer.toString(daily_count));
            db.collection(email_of_person).document("impd").update("total_smoked",Double.toString(total_smoked_till_date));
            db.collection(email_of_person).document("xmd "+month_now).update("total_smoked_in_month",Integer.toString(total_smoked_in_month));

            db.collection(email_of_person).document("impd").update("total_money_spent",Double.toString(total_money_spent));
            db.collection(email_of_person).document("xmd "+month_now).update("money_spent",Double.toString(total_money_spent_monthly));

            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }
        setcigcountcolor();
    }
    public void dailycounterpage1()
    {

        if(loadpage1==0)
        {

            ecg.setVisibility(View.VISIBLE);
            ecg.playAnimation();
            ecg.setSpeed(3);
            //getdata();

            ecg.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation)
                {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    daycounter.setVisibility(View.VISIBLE);
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
                    YoYo.with(Techniques.FadeIn)
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
                public void onAnimationEnd(Animator animation) {
                    if(daily_count==-1)
                    {
                        Intent ins = new Intent(mainscreen.this,nointernet.class);
                        startActivity(ins);
                        finish();
                    }

                    calculate_nicotine();
                    calculate_nicotine_ng();
                    calculate_COHb();
                    calculate_tar();
                    calculate_pack_years();


                    countcig.setVisibility(View.VISIBLE);
                    countcig.setText(Integer.toString(daily_count));
                    setcigcountcolor();

                    YoYo.with(Techniques.FadeIn)
                            .duration(1500)
                            .playOn(countcig);
                    removecig.setVisibility(View.VISIBLE);
                    removecig.playAnimation();
                    YoYo.with(Techniques.FadeIn)
                            .duration(1500)
                            .playOn(removecig);
                    addbut.setVisibility(View.VISIBLE);
                    addbut.playAnimation();
                    YoYo.with(Techniques.FadeIn)
                            .duration(1500)
                            .playOn(addbut);
                    //bottom bar.............................

                    quote.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeIn)
                            .duration(1500)
                            .playOn(quote);
                    lifereduced.setVisibility(View.VISIBLE);
                    lifereducedindays();
                    //string manipulation
                    String life_reduced_str = Double.toString(life_reduced_till_now);
                    int h = life_reduced_str.indexOf(".");
                    lifereduced.setText(life_reduced_str.substring(0,h)+" DAYS");
                    //string manipulation
                    lifereducedtxt.setVisibility(View.VISIBLE);
                    taptoadd.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeIn)
                            .duration(1500)
                            .playOn(lifereduced);
                    YoYo.with(Techniques.FadeIn)
                            .duration(1500)
                            .playOn(lifereducedtxt);
                    YoYo.with(Techniques.FadeIn)
                            .duration(1000)
                            .playOn(taptoadd);
                    if(accc==0)
                    {
                        if(total_money_spent_monthly>monthly_expense_limit)
                        {
                            //Toasty.info(mainscreen.this,"You have crossed the expense limit. Spend less.",Toast.LENGTH_LONG).show();
                            Toasty.error(mainscreen.this,"You have crossed the expense limit. Spend less.",Toast.LENGTH_LONG).show();
                        }
                        accc=1;
                    }
                    sendsms();

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            loadpage1=1;

        }
        else if(loadpage1==1)
        {
            heartsound.start();


            for(int i = 0;i<14;i++)
            {
                counterpageview[i].setVisibility(View.VISIBLE);
            }
            for(int i = 0;i<14;i++)
            {
                YoYo.with(Techniques.SlideInUp)
                        .duration(700)
                        .playOn(counterpageview[i]);
            }

        }

    }

    public void vanishpage1()
    {
        heartsound.stop();
        for(int i =0 ;i<14;i++)
        {
            final int loopcount = i;
            YoYo.with(Techniques.SlideOutUp)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            counterpageview[loopcount].setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .duration(700)
                    .playOn(counterpageview[i]);
        }
    }

    public void sendsms()
    {
       if((nicotine_in_month>=45||cohb_value>=45||tar_in_month>=260||pack_year_of_person>=35)&&sms_counter==0&&avvv==1)
       {
           //send sms to doc inform user name phone nictarco pack
           String smsmessage = "Dear doctor, we want to inform you that " + name_of_person.toUpperCase() + " has been smoking a lot of cigarettes.\n";
           String smsmessage2 = "The chemical contents in his/her body are:\nNicotine : " + nicotine_in_month + " mg\n";
           String smsmessage3 = "Tar : " + tar_in_month + " mg\nCarbon monoxide : " + cohb_value + " ppm\nAnd the pack-years are  "+pack_year_of_person;
           String smsmessage4 = "Please contact " + name_of_person.toUpperCase() + " on      " + phone_number_of_person + " and help save their life!\n";
           String smsmessage5 = "Message from CHANTIX (Anti-smoking app)";

           SmsManager smsManager = SmsManager.getDefault();
           //give a phone number of doctor
           smsManager.sendTextMessage("tel:8698882935", null, smsmessage, null, null);
           smsManager.sendTextMessage("tel:8698882935", null, smsmessage2 + smsmessage3, null, null);
           smsManager.sendTextMessage("tel:8698882935", null, smsmessage4 + smsmessage5, null, null);

           smsManager.sendTextMessage("tel:8055686786", null, smsmessage, null, null);
           smsManager.sendTextMessage("tel:8055686786", null, smsmessage2 + smsmessage3, null, null);
           smsManager.sendTextMessage("tel:8055686786", null, smsmessage4 + smsmessage5, null, null);

           //SmsManager smsManager2 = SmsManager.getDefault();
           //give a phone number of doctor cardio
           //smsManager2.sendTextMessage("tel:8698882935", null, smsmessage, null, null);
           //smsManager2.sendTextMessage("tel:8698882935", null, smsmessage2+smsmessage3, null, null);
           //smsManager2.sendTextMessage("tel:8698882935", null, smsmessage4+smsmessage5, null, null);

           //SmsManager smsManager3 = SmsManager.getDefault();
           //give a phone number of doctor oncolog
           //smsManager3.sendTextMessage("tel:8698882935", null, smsmessage, null, null);
           //smsManager3.sendTextMessage("tel:8698882935", null, smsmessage2+smsmessage3, null, null);
           //smsManager3.sendTextMessage("tel:8698882935", null, smsmessage4+smsmessage5, null, null);


           //phone number of doctors
           sms_counter++;
           Toasty.success(mainscreen.this, "We have informed the doctors about your declining health conditions\n" + name_of_oncologist + "  " + phonenumber_of_oncologist, Toast.LENGTH_LONG).show();
           db.collection(email_of_person).document("vd").update("sms",Integer.toString(sms_counter));
       }

    }

    public void getdata()
    {
        db.collection(email_of_person).document("impd").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            daily_intake_limit = Integer.parseInt(documentSnapshot.getString("intakelimit"));
                            total_smoked_till_date = Double.parseDouble(documentSnapshot.getString("total_smoked"));
                            price_of_one_cigarette = Double.parseDouble(documentSnapshot.getString("priceofone"));
                            total_money_spent = Double.parseDouble(documentSnapshot.getString("total_money_spent"));
                            monthly_expense_limit = Double.parseDouble(documentSnapshot.getString("expenselimit"));

                        }
                    }
                });

        db.collection(email_of_person).document("pd").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            name_of_person = documentSnapshot.getString("name");
                            phone_number_of_person = documentSnapshot.getString("phone");
                            city_of_person = documentSnapshot.getString("city");
                            findbestoncologist();
                        }

                    }
                });

        db.collection(email_of_person).document("vd").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            daily_count = Integer.parseInt(documentSnapshot.getString("count"));
                            //Toast.makeText(mainscreen.this,Integer.toString(daily_count),Toast.LENGTH_SHORT).show();
                            day_in_db = Integer.parseInt(documentSnapshot.getString("day"));
                            month_in_db = Integer.parseInt(documentSnapshot.getString("month"));
                            year_in_db = Integer.parseInt(documentSnapshot.getString("year"));
                            sms_counter = Integer.parseInt(documentSnapshot.getString("sms"));
                            if(sms_counter!=-1)
                            {
                                avvv = 1;
                            }
                            if(day_in_db!=-1)
                            {
                                resetdata();
                            }
                        }
                    }
                });


        db.collection(email_of_person).document("xmd " + month_now).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            total_smoked_in_month = Integer.parseInt(documentSnapshot.getString("total_smoked_in_month"));
                            //nicotine_in_month = Double.parseDouble(documentSnapshot.getString("nicotine"));
                            total_money_spent_monthly = Double.parseDouble(documentSnapshot.getString("money_spent"));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Intent in = new Intent(mainscreen.this,nointernet.class);
                startActivity(in);
                finish();
            }
        });

        db.collection(email_of_person).document("zmonthcount").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            int j = 0;
                            if(day_now>10)
                            {
                                for (int i = day_now-10; i <= day_now-1; i++) {
                                    cigarette_count_day_by_day[j] = Integer.parseInt(documentSnapshot.getString(Integer.toString(i)));
                                    j++;
                                }
                            }
                            else
                            {
                                for (int i = 0 ; i<10 ; i++)
                                    cigarette_count_day_by_day[i]=0;
                            }

                           for(int i =0;i<day_now;i++)
                           {
                               cigarette_count_day_by_day_dynamic[i] = Integer.parseInt(documentSnapshot.getString(Integer.toString(i+1)));
                           }
                        }
                    }
                });

        for(int i =1;i<=12;i++)
        {
            final int loopcountervar = i;
            db.collection(email_of_person).document("xmd "+Integer.toString(i)).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists())
                            {
                                total_smoked_in_month_array[loopcountervar-1]=Integer.parseInt(documentSnapshot.getString("total_smoked_in_month"));
                                total_money_spent_monthly_array[loopcountervar-1]=Double.parseDouble(documentSnapshot.getString("money_spent"));
                                calculate_life_lost_for_graph();
                                get_graph_values_for_life();
                                get_graph_values_for_money();
                                get_graph_values_for_intake();
                            }
                        }
                    });
        }


    }
    public void setcigcountcolor()
    {

        if(daily_count>=daily_intake_limit)
        {
            countcig.setTextColor(Color.parseColor("#F8950707"));
            taptoadd.setText("Warning : Limit crossed");
            taptoadd.setTextColor(Color.parseColor("#F8950707"));
        }
        else
        {
            countcig.setTextColor(Color.parseColor("#E2FFFFFF"));
            taptoadd.setText("Tap to add/delete a cigarette.");
            taptoadd.setTextColor(Color.parseColor("#939393"));
        }
    }
    public void resetdata()
    {

        //Toast.makeText(mainscreen.this,Integer.toString(month_in_db),Toast.LENGTH_SHORT).show();
        if(day_in_db!=day_now)
        {

            if(day_now!=1)
            {
                db.collection(email_of_person).document("zmonthcount").update(Integer.toString(day_now-1), Integer.toString(daily_count));
            }
            //Toast.makeText(mainscreen.this,Integer.toString(day_in_db),Toast.LENGTH_LONG).show();
            daily_count = 0;
            db.collection(email_of_person).document("vd").update("count",Integer.toString(daily_count));
            db.collection(email_of_person).document("vd").update("day",Integer.toString(day_now));
            if(avvv==1)
            {
                if (sms_counter == 9)
                {
                    sms_counter = -1;
                    //Toast.makeText(mainscreen.this, Integer.toString(sms_counter), Toast.LENGTH_SHORT).show();
                }
                sms_counter++;
                db.collection(email_of_person).document("vd").update("sms", Integer.toString(sms_counter));
            }

        }
        if(month_in_db!=month_now)
        {
            db.collection(email_of_person).document("vd").update("month",Integer.toString(month_now));
            for(int i =1;i<=31;i++)
            {
                db.collection(email_of_person).document("zmonthcount").update(Integer.toString(i),"0");
            }
        }
        if (year_in_db!=year_now)
        {
            for(int i = 1 ;i<=12;i++)
            {
                db.collection(email_of_person).document("xmd " + i).update("money_spent", "0");
                db.collection(email_of_person).document("xmd " + i).update("nicotine", "0");
                db.collection(email_of_person).document("xmd " + i).update("tar", "0");
                db.collection(email_of_person).document("xmd " + i).update("total_smoked_in_month", "0");
            }

            db.collection(email_of_person).document("vd").update("year",Integer.toString(year_now));
        }
        //Toast.makeText(mainscreen.this,Integer.toString(cigarette_count_day_by_day[6]),Toast.LENGTH_SHORT).show();
    }

    public void lifereducedindays()
    {
        life_reduced_till_now = (total_smoked_till_date*11)/1440;
    }

















    public void toxicitypage3()
    {
        chemicalselect.setCurrentActiveItem(0);

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

        datedis.setText(months[month_now]+ " "+year_now);

        nicodisplay();

    }

    public void nicodisplay()
    {
        //this function displays Nicotine page

        backforchemical.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return new PorterDuffColorFilter(Color.parseColor("#372103"), PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );
        //backforchemical.setRenderMode(RenderMode.AUTOMATIC);

        datedis.setTextColor(Color.parseColor("#372103"));
        int s = give_random_number();
        description_txt.setText(nicotine_facts[s]);
        quantity_display.setText(nicotine_in_month_display + " mg");
        quantity_display.setTextColor(Color.parseColor("#FF5C3705"));
        chemical_name.setText("Nicotine");
        chemical_description.setText("Approx "+nicotine_in_ng+" ng/ml can be present in your urine currently.\n\nMaximum intake limit is 60mg!");


        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(description_txt);
        YoYo.with(Techniques.FadeIn)
                .duration(1300)
                .playOn(quantity_display);
        YoYo.with(Techniques.FadeIn)
                .duration(2300)
                .playOn(chemical_description);
        YoYo.with(Techniques.FadeIn)
                .duration(1800)
                .playOn(chemical_name);
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
                        cp.setMaxProgress(55);
                        cp.setCurrentProgress(nicotine_in_month);
                        if(nicotine_in_month>=35.0)
                        {
                            cp.setProgressColor(Color.parseColor("#F8950707"));
                            description_txt.setText("ALERT : Nicotine level is rising rapidly!");
                            description_txt.setTextColor(Color.parseColor("#F8950707"));
                            YoYo.with(Techniques.Pulse)
                                    .duration(1000)
                                    .repeat(5)
                                    .playOn(description_txt);
                            YoYo.with(Techniques.Pulse)
                                    .duration(1000)
                                    .repeat(5)
                                    .playOn(cp);
                        }
                        else
                        {
                            cp.setProgressColor(Color.parseColor("#814D07"));
                            description_txt.setTextColor(Color.parseColor("#000000"));
                        }
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

        backforchemical.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return new PorterDuffColorFilter(Color.parseColor("#FF163001"), PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );
        //backforchemical.setRenderMode(RenderMode.AUTOMATIC);

        datedis.setTextColor(Color.parseColor("#FF163001"));
        description_txt.setText("Carbon monoxide disrupts oxygen supply to the blood.");

        String temp = Double.toString(cohb_value).substring(0,(Double.toString(cohb_value).indexOf("."))+2);
        quantity_display.setText(temp+" ppm");
        quantity_display.setTextColor(Color.parseColor("#FF214702"));
        chemical_name.setText("Carbon Monoxide");
        chemical_description.setText("Max limit for you is 70ppm.\nCOHb : resets every day.");


        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(description_txt);
        YoYo.with(Techniques.FadeIn)
                .duration(1300)
                .playOn(quantity_display);
        YoYo.with(Techniques.FadeIn)
                .duration(2300)
                .playOn(chemical_description);
        YoYo.with(Techniques.FadeIn)
                .duration(1800)
                .playOn(chemical_name);
        YoYo.with(Techniques.TakingOff)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        cp.setMaxProgress(70);
                        cp.setCurrentProgress(cohb_value);
                        if(cohb_value>=35)
                        {
                            cp.setProgressColor(Color.parseColor("#F8950707"));
                            description_txt.setText("ALERT : COHb level is rising rapidly!");
                            description_txt.setTextColor(Color.parseColor("#F8950707"));
                            YoYo.with(Techniques.Pulse)
                                    .duration(1000)
                                    .repeat(5)
                                    .playOn(description_txt);
                            YoYo.with(Techniques.Pulse)
                                    .duration(1000)
                                    .repeat(5)
                                    .playOn(cp);
                        }
                        else
                        {
                            cp.setProgressColor(Color.parseColor("#FF326A03"));
                            description_txt.setTextColor(Color.parseColor("#000000"));
                        }
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

    public void tardisplay()
    {
        //this function displays Tar page


        backforchemical.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return new PorterDuffColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );
        //backforchemical.setRenderMode(RenderMode.AUTOMATIC);

        datedis.setTextColor(Color.parseColor("#000000"));
        description_txt.setText("Tar results in chronic diseases and damages lungs.");
        quantity_display.setText(tar_in_month+" mg");
        quantity_display.setTextColor(Color.parseColor("#FF373737"));
        chemical_name.setText("Tar");
        chemical_description.setText("Maximum limit for you is 360 mg\nTar detoxes between 1 to 9 months!");


        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(description_txt);
        YoYo.with(Techniques.FadeIn)
                .duration(1300)
                .playOn(quantity_display);
        YoYo.with(Techniques.FadeIn)
                .duration(2300)
                .playOn(chemical_description);
        YoYo.with(Techniques.FadeIn)
                .duration(1800)
                .playOn(chemical_name);
        YoYo.with(Techniques.TakingOff)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        cp.setMaxProgress(350);
                        cp.setCurrentProgress(tar_in_month);
                        if(tar_in_month>=240)
                        {
                            cp.setProgressColor(Color.parseColor("#F8950707"));
                            description_txt.setText("ALERT : Tar level is rising rapidly!");
                            description_txt.setTextColor(Color.parseColor("#F8950707"));
                            YoYo.with(Techniques.Pulse)
                                    .duration(1000)
                                    .repeat(5)
                                    .playOn(description_txt);
                            YoYo.with(Techniques.Pulse)
                                    .duration(1000)
                                    .repeat(5)
                                    .playOn(cp);
                        }
                        else
                        {
                            cp.setProgressColor(Color.parseColor("#FF424242"));
                            description_txt.setTextColor(Color.parseColor("#000000"));
                        }

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
    public void vanishpage3()
    {
        for (int i = 0;i<8;i++)
        {
            final int loopercount = i;
            YoYo.with(Techniques.SlideOutUp)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            toxicpadeview[loopercount].setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .duration(700)
                    .playOn(toxicpadeview[i]);
        }
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toxicpadeview[8].setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(400)
                .playOn(toxicpadeview[8]);
    }
    public void calculate_nicotine()
    {
        nicotine_in_month = (1.8*total_smoked_in_month)/30;
        db.collection(email_of_person).document("xmd "+month_now).update("nicotine",Double.toString(nicotine_in_month));
        String temp = Double.toString(nicotine_in_month);
        nicotine_in_month_display = temp.substring(0,(temp.indexOf(".")+2));
    }
    public void calculate_nicotine_ng()
    {
        for(int i = 0;i<10;i++)
        {
            if(cigarette_count_day_by_day[i]==0)
            {
                intensity_count++;
                //Toast.makeText(mainscreen.this,Integer.toString(intensity_count),Toast.LENGTH_SHORT).show();
            }
        }
        if(intensity_count>=9)
        {
            nicotine_in_ng = 50;
        }
        else
        {
            nicotine_in_ng = 1000;
        }
    }
    public void calculate_COHb()
    {
        for(int i = 0; i<day_now;i++)
        {
           if(cigarette_count_day_by_day_dynamic[i]>=3)
           {
               intensity_count2++;
           }
        }

        if(intensity_count2>=day_now/2)
        {
            cohb_value = 10+(1.12*daily_count);
        }
        else
        {
            cohb_value = 3+(1.12*daily_count);
        }
    }

    public void calculate_tar()
    {

        for(int i = 0;i<day_now;i++)
        {
            if(cigarette_count_day_by_day_dynamic[i]!=0)
            {
                tar_addition += cigarette_count_day_by_day_dynamic[i];
            }
        }
        tar_in_month = (tar_addition*12)/day_now;
        db.collection(email_of_person).document("xmd "+month_now).update("tar",Integer.toString(tar_in_month));
    }

    public void calculate_pack_years()
    {
        pack_year_of_person = total_smoked_till_date/(20*365);
    }

    public int give_random_number()
    {
        double ran = Math.random()*100;
        int passed_number = -1;
        if(ran>=0&&ran<=20)
        {
            passed_number=0;
        }
        else if(ran>=21&&ran<=40)
        {
            passed_number=1;
        }
        else if(ran>=41&&ran<=60)
        {
            passed_number=2;
        }
        else if(ran>=61&&ran<=80)
        {
            passed_number=3;
        }
        else
        {
            passed_number=4;
        }
        return (passed_number);
    }

    //graph section-----------------------------------------------------------------------------------

    public void graphpage2()
    {
        graphselection.setCurrentActiveItem(0);
        //call_the_graphs();

        for(int i = 0 ; i <=6 ; i++)
        {
            graphpageviews[i].setVisibility(View.VISIBLE);
        }
        for (int j = 0;j<7;j++)
        {
            YoYo.with(Techniques.SlideInUp)
                    .duration(700)
                    .playOn(graphpageviews[j]);
        }

        year_display.setText("Year "+year_now);
        maximum_in_month_for_graph.setText("Max life lost : "+months[max_position_for_life_reduced_in_month+1]);
        description_graph.setText("This graph shows life lost monthly (days lost).");


    }


    public void vanishpage2()
    {
        for (int i = 0;i<7;i++)
        {
            final int loopercount = i;
            YoYo.with(Techniques.SlideOutUp)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            graphpageviews[loopercount].setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .duration(700)
                    .playOn(graphpageviews[i]);
            YoYo.with(Techniques.SlideOutUp)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            barChart2.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .duration(700)
                    .playOn(barChart2);
            YoYo.with(Techniques.SlideOutUp)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            barChart3.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .duration(700)
                    .playOn(barChart3);
        }
    }

    private void get_graph_values_for_life() {

        barEntries = new ArrayList<>();
        strings = new ArrayList<>();


        XAxis xAxis = barChart.getXAxis();
        YAxis yAxis = barChart.getAxisLeft();

        //x-axis

        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.parseColor("#FF616161"));
        xAxis.setAxisLineWidth(1f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);


        //y-axis
        yAxis.setEnabled(true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(12f);
        yAxis.setDrawGridLines(true);
        yAxis.setGridLineWidth(1f);
        yAxis.setGridColor(Color.parseColor("#E4E4E4"));
        yAxis.setDrawZeroLine(true);
        yAxis.setZeroLineWidth(1f);
        yAxis.setZeroLineColor(Color.parseColor("#000000"));
        yAxis.setDrawAxisLine(true);
        yAxis.setAxisLineWidth(1f);
        yAxis.setTypeface(typeface);
        barChart.getAxisLeft().setDrawAxisLine(true);
        barChart.getAxisRight().setEnabled(false);

        yAxis.setAxisMinimum(0f);
       // yAxis.setAxisMaximum(1000f);

        //barchart
        barChart.setDrawMarkers(true);
        barChart.setDragXEnabled(true);
        barChart.setDragYEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);

        barChart.setHorizontalScrollBarEnabled(false);
        barChart.setVerticalScrollBarEnabled(false);
        barChart.setVisibleXRangeMaximum(6f);
        barChart.setVisibleXRangeMinimum(6f);
        barChart.moveViewToX(-1);

        Legend l = barChart.getLegend();
        l.setEnabled(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setHighlightPerTapEnabled(true);

        barChart.animateY(2000);

        CustomBarChartRender barChartRender = new CustomBarChartRender(barChart,barChart.getAnimator(), barChart.getViewPortHandler());
        barChartRender.setRadius(50);
        barChart.setRenderer(barChartRender);

       // barChart.invalidate();

        arrayList.clear();
        for (int i =0;i<12;i++)
        {
            arrayList.add(new graphdata(months_name[i],life_reduced_in_month_array[i]));
        }

        for (int i = 0; i < arrayList.size(); i++)
        {
            String months = arrayList.get(i).getMonths();
            int values = arrayList.get(i).getValue();
            barEntries.add(new BarEntry(i, values));
            strings.add(months);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries," ");
        barDataSet.setColors(new int[]{Color.parseColor("#FF202020"),Color.parseColor("#FF611212")});
        barChart.getDescription().setText(" ");
        BarData barData =new BarData(barDataSet);
        barData.setDrawValues(false);
        barData.setValueTextSize(20f);
        barData.setValueTypeface(typeface);
        barChart.setData(barData);

        xAxis.setTypeface(typeface);
        xAxis.setValueFormatter( new IndexAxisValueFormatter(strings));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextColor(Color.parseColor("#FFFFFF"));
        xAxis.setTextSize(14f);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setLabelCount(strings.size());

    }
    private void get_graph_values_for_money() {

        barEntries2 = new ArrayList<>();
        strings2 = new ArrayList<>();


        XAxis xAxis = barChart2.getXAxis();
        YAxis yAxis = barChart2.getAxisLeft();

        //x-axis

        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.parseColor("#FF616161"));
        xAxis.setAxisLineWidth(1f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);


        //y-axis
        yAxis.setEnabled(true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(12f);
        yAxis.setDrawGridLines(true);
        yAxis.setGridLineWidth(1f);
        yAxis.setGridColor(Color.parseColor("#E4E4E4"));
        yAxis.setDrawZeroLine(true);
        yAxis.setZeroLineWidth(1f);
        yAxis.setZeroLineColor(Color.parseColor("#000000"));
        yAxis.setDrawAxisLine(true);
        yAxis.setAxisLineWidth(1f);
        yAxis.setTypeface(typeface);
        barChart2.getAxisLeft().setDrawAxisLine(true);
        barChart2.getAxisRight().setEnabled(false);

        yAxis.setAxisMinimum(0f);
        // yAxis.setAxisMaximum(1000f);

        //barchart
        barChart2.setDrawMarkers(true);
        barChart2.setDragXEnabled(true);
        barChart2.setDragYEnabled(false);
        barChart2.setPinchZoom(false);
        barChart2.setDoubleTapToZoomEnabled(false);

        barChart2.setHorizontalScrollBarEnabled(false);
        barChart2.setVerticalScrollBarEnabled(false);
        barChart2.setVisibleXRangeMaximum(6f);
        barChart2.setVisibleXRangeMinimum(6f);
        barChart2.moveViewToX(-1);

        Legend l = barChart2.getLegend();
        l.setEnabled(false);
        barChart2.setHighlightFullBarEnabled(false);
        barChart2.setHighlightPerTapEnabled(true);

        barChart2.animateY(2000);

        CustomBarChartRender barChartRender = new CustomBarChartRender(barChart2,barChart2.getAnimator(), barChart2.getViewPortHandler());
        barChartRender.setRadius(50);
        barChart2.setRenderer(barChartRender);

        // barChart.invalidate();

        arrayList2.clear();
        for (int i =0;i<12;i++)
        {
            arrayList2.add(new graphdata(months_name[i],(int)total_money_spent_monthly_array[i]));
        }

        for (int i = 0; i < arrayList2.size(); i++)
        {
            String months = arrayList2.get(i).getMonths();
            int values = arrayList2.get(i).getValue();
            barEntries2.add(new BarEntry(i, values));
            strings2.add(months);
        }

        BarDataSet barDataSet2 = new BarDataSet(barEntries2," ");
        barDataSet2.setColors(new int[]{Color.parseColor("#FF202020"),Color.parseColor("#FF234A02")});
        barChart2.getDescription().setText(" ");
        BarData barData2 =new BarData(barDataSet2);
        barData2.setDrawValues(false);
        barData2.setValueTextSize(20f);
        barData2.setValueTypeface(typeface);
        barChart2.setData(barData2);

        xAxis.setTypeface(typeface);
        xAxis.setValueFormatter( new IndexAxisValueFormatter(strings2));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextColor(Color.parseColor("#FFFFFF"));
        xAxis.setTextSize(14f);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setLabelCount(strings2.size());

    }
    private void get_graph_values_for_intake()
    {
        barEntries3 = new ArrayList<>();
        strings3 = new ArrayList<>();


        XAxis xAxis = barChart3.getXAxis();
        YAxis yAxis = barChart3.getAxisLeft();

        //x-axis

        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.parseColor("#FF616161"));
        xAxis.setAxisLineWidth(1f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);


        //y-axis
        yAxis.setEnabled(true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(12f);
        yAxis.setDrawGridLines(true);
        yAxis.setGridLineWidth(1f);
        yAxis.setGridColor(Color.parseColor("#E4E4E4"));
        yAxis.setDrawZeroLine(true);
        yAxis.setZeroLineWidth(1f);
        yAxis.setZeroLineColor(Color.parseColor("#000000"));
        yAxis.setDrawAxisLine(true);
        yAxis.setAxisLineWidth(1f);
        yAxis.setTypeface(typeface);
        barChart3.getAxisLeft().setDrawAxisLine(true);
        barChart3.getAxisRight().setEnabled(false);

        yAxis.setAxisMinimum(0f);
        // yAxis.setAxisMaximum(1000f);

        //barchart
        barChart3.setDrawMarkers(true);
        barChart3.setDragXEnabled(true);
        barChart3.setDragYEnabled(false);
        barChart3.setPinchZoom(false);
        barChart3.setDoubleTapToZoomEnabled(false);

        barChart3.setHorizontalScrollBarEnabled(false);
        barChart3.setVerticalScrollBarEnabled(false);
        barChart3.setVisibleXRangeMaximum(6f);
        barChart3.setVisibleXRangeMinimum(6f);
        barChart3.moveViewToX(-1);

        Legend l = barChart3.getLegend();
        l.setEnabled(false);
        barChart3.setHighlightFullBarEnabled(false);
        barChart3.setHighlightPerTapEnabled(true);

        barChart3.animateY(2000);

        CustomBarChartRender barChartRender = new CustomBarChartRender(barChart3,barChart3.getAnimator(), barChart3.getViewPortHandler());
        barChartRender.setRadius(50);
        barChart3.setRenderer(barChartRender);

        // barChart.invalidate();

        arrayList3.clear();
        for (int i =0;i<12;i++)
        {
            arrayList3.add(new graphdata(months_name[i],total_smoked_in_month_array[i]));
        }

        for (int i = 0; i < arrayList3.size(); i++)
        {
            String months = arrayList3.get(i).getMonths();
            int values = arrayList3.get(i).getValue();
            barEntries3.add(new BarEntry(i, values));
            strings3.add(months);
        }

        BarDataSet barDataSet3 = new BarDataSet(barEntries3," ");
        barDataSet3.setColors(new int[]{Color.parseColor("#FF202020"),Color.parseColor("#FF616161")});
        barChart3.getDescription().setText(" ");
        BarData barData3 =new BarData(barDataSet3);
        barData3.setDrawValues(false);
        barData3.setValueTextSize(20f);
        barData3.setValueTypeface(typeface);
        barChart3.setData(barData3);

        xAxis.setTypeface(typeface);
        xAxis.setValueFormatter( new IndexAxisValueFormatter(strings3));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextColor(Color.parseColor("#FFFFFF"));
        xAxis.setTextSize(14f);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setLabelCount(strings3.size());
    }
    public void display_first_graph()
    {
        description_graph.setText("This graph shows life lost monthly (days lost).");
        maximum_in_month_for_graph.setTextColor(Color.parseColor("#FF611212"));
        maximum_in_month_for_graph.setText("Max life lost : "+months[max_position_for_life_reduced_in_month+1]);
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(description_graph);
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(maximum_in_month_for_graph);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart.setVisibility(View.VISIBLE);
                        barChart2.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.INVISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart2);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart.setVisibility(View.VISIBLE);
                        barChart2.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.INVISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart3);



    }
    public void display_second_graph()
    {

        maximum_in_month_for_graph.setTextColor(Color.parseColor("#FF234A02"));
        String t = Double.toString(total_money_spent_monthly_array[month_now-1]);
        String tt = t.substring(0,t.indexOf("."));
        int moneyinint = Integer.parseInt(tt);
        if(total_money_spent_monthly_array[month_now-1]<monthly_expense_limit)
        {
            maximum_in_month_for_graph.setText(months[month_now] + " : ₹ "+tt);
        }
        else
        {
            maximum_in_month_for_graph.setTextColor(Color.parseColor("#FF611212"));
            maximum_in_month_for_graph.setText("You've overspent this month by  ₹ "+(int)(moneyinint-monthly_expense_limit) +" !");
        }
        description_graph.setText("This graph shows the amount you spent on smoking.");
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(description_graph);
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(maximum_in_month_for_graph);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart2.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.INVISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart2);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart2.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.INVISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart2);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart3);
    }
    public void display_third_graph()
    {
        maximum_in_month_for_graph.setTextColor(Color.parseColor("#131313"));
        description_graph.setText("This graph shows the monthly intake on cigarettes.");
        maximum_in_month_for_graph.setText(months[month_now]+" :  "+total_smoked_in_month_array[month_now-1]+" cigarettes");
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(description_graph);
        YoYo.with(Techniques.FadeIn)
                .duration(4000)
                .playOn(maximum_in_month_for_graph);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart2.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart3);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart);
        YoYo.with(Techniques.FadeOut)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        barChart2.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        barChart3.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .playOn(barChart3);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(1000)
                .playOn(barChart2);
    }

    public void calculate_life_lost_for_graph()
    {
        for(int i = 0;i<12;i++)
        {
            String tempstr = Double.toString((total_smoked_in_month_array[i]*11)/60).substring(0,Double.toString((total_smoked_in_month_array[i]*11)/60).indexOf("."));
            life_reduced_in_month_array[i]=Integer.parseInt(tempstr);
        }
        int max = life_reduced_in_month_array[0];
        for(int i = 0;i<12;i++)
        {
            if(max<=life_reduced_in_month_array[i])
            {
                max = life_reduced_in_month_array[i];
                max_position_for_life_reduced_in_month = i;
            }
        }
    }

    //google places api and page 4-----------------------------------------------------------------------
    public void mapactivity()
    {
        //geocoding here
        getLocationPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {

    }

    public boolean isServicesOK()
    {
        //this function checks play services are running properly

        Log.d(TAG, "isServicesOK: checking google services ver");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mainscreen.this);
        if (available == ConnectionResult.SUCCESS)
        {
            //everything is fine
            Log.d(TAG, "isServicesOK: google play services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            //error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(mainscreen.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else
        {
            Toasty.error(mainscreen.this, "You cannot make Map requests", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void getLocationPermission()
    {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mpermissionlocationgranted = true;
            }
            else
            {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }



    private void findbestoncologist()
    {
        String url = predefined_url+"best+cancer+specialist+in+"+city_of_person+"&key="+api_key_for_maps;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response)
                    {
                        String id = null;
                        Log.e(TAG, "onResponse: rest response"+response.toString() );
                        // tv.setText(response.toString());
                        try {
                            JSONArray jsonArray = (JSONArray) response.get("results");
                            for(int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject rating = jsonArray.getJSONObject(i);
                                double rat = rating.getDouble("rating");

                                if(rat>4.2)
                                {
                                    JSONObject getplaceid = jsonArray.getJSONObject(i);
                                    id = getplaceid.getString("place_id");
                                    JSONObject getplaceaddress = jsonArray.getJSONObject(i);
                                    address_of_oncologist =getplaceaddress.getString("formatted_address");
                                    break;
                                }
                            }

                            Places.initialize(getApplicationContext(), api_key_for_maps);
                            PlacesClient placesClient = Places.createClient(mainscreen.this);

                            // Define a Place ID.
                            final String placeId = id;

                            final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.PHONE_NUMBER,Place.Field.WEBSITE_URI);

                            final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

                            placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                                @Override
                                public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                                    Place place = fetchPlaceResponse.getPlace();
                                    try{
                                        name_of_oncologist = place.getName().toString();
                                    }catch (NullPointerException e)
                                    {
                                        name_of_oncologist = "Not Available";
                                    }
                                    try{
                                        phonenumber_of_oncologist = place.getPhoneNumber().toString();

                                    }catch (NullPointerException e)
                                    {
                                        phonenumber_of_oncologist="Not Available";

                                    }
                                    try{
                                        website_of_oncologist = place.getWebsiteUri().toString();

                                    }catch (NullPointerException e)
                                    {
                                        website_of_oncologist="Not Available";

                                    }

                                    findbestcardiologist();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                            //tv.setText(jsonArray.toString());
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //check internet connection
                        Log.e(TAG, "onResponse: rest response+++++++++"+error.toString() );
                    }
                }
        );
        requestQueue.add(objectRequest);

    }

    private void findbestcardiologist()
    {
        String url = predefined_url+"best+cardiologist+in+"+city_of_person+"&key="+api_key_for_maps;
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest2 = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response)
                    {
                        String id2 = null;
                        Log.e(TAG, "onResponse: rest response"+response.toString() );
                        // tv.setText(response.toString());
                        try {
                            JSONArray jsonArray = (JSONArray) response.get("results");
                            for(int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject rating = jsonArray.getJSONObject(i);
                                double rat = rating.getDouble("rating");

                                if(rat>4.2)
                                {
                                    JSONObject getplaceid2 = jsonArray.getJSONObject(i);
                                    id2 = getplaceid2.getString("place_id");
                                    JSONObject getplaceaddress2 = jsonArray.getJSONObject(i);
                                    address_of_cardiologist =getplaceaddress2.getString("formatted_address");
                                    break;
                                }
                            }

                            Places.initialize(getApplicationContext(), api_key_for_maps);
                            PlacesClient placesClient2 = Places.createClient(mainscreen.this);

                            // Define a Place ID.
                            final String placeId2 = id2;

                            final List<Place.Field> placeFields2 = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.PHONE_NUMBER,Place.Field.WEBSITE_URI);

                            final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId2, placeFields2);

                            placesClient2.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                                @Override
                                public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                                    Place place2 = fetchPlaceResponse.getPlace();
                                    try{
                                        name_of_cardiologist = place2.getName().toString();
                                    }
                                    catch (NullPointerException e){
                                        name_of_cardiologist="Not Available";
                                    }
                                    try{
                                        phonenumber_of_cardiologist = place2.getPhoneNumber().toString();
                                    }
                                    catch (NullPointerException e){
                                        phonenumber_of_cardiologist="Not Available";
                                    }
                                    try{
                                        website_of_cardiologist = place2.getWebsiteUri().toString();

                                    }
                                    catch (NullPointerException e){
                                        website_of_cardiologist = "Not Available";

                                    }

                                    findbestentspecialist();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                            //tv.setText(jsonArray.toString());
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //check internet connection
                        Log.e(TAG, "onResponse: rest response+++++++++"+error.toString() );
                    }
                }
        );
        requestQueue2.add(objectRequest2);
    }

    private void findbestentspecialist()
    {

        String url = predefined_url+"best+ent+doctor+in+"+city_of_person+"&key="+api_key_for_maps;
        RequestQueue requestQueue3 = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest3 = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response)
                    {
                        String id3 = null;
                        Log.e(TAG, "onResponse: rest response"+response.toString() );
                        // tv.setText(response.toString());
                        try {
                            JSONArray jsonArray = (JSONArray) response.get("results");
                            for(int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject rating = jsonArray.getJSONObject(i);
                                double rat = rating.getDouble("rating");

                                if(rat>4.2)
                                {
                                    JSONObject getplaceid3= jsonArray.getJSONObject(i);
                                    id3 = getplaceid3.getString("place_id");
                                    JSONObject getplaceaddress3 = jsonArray.getJSONObject(i);
                                    address_of_ent =getplaceaddress3.getString("formatted_address");
                                    break;
                                }
                            }

                            Places.initialize(getApplicationContext(), api_key_for_maps);
                            PlacesClient placesClient3 = Places.createClient(mainscreen.this);

                            // Define a Place ID.
                            final String placeId3 = id3;

                            final List<Place.Field> placeFields3 = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.PHONE_NUMBER,Place.Field.WEBSITE_URI);

                            final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId3, placeFields3);

                            placesClient3.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                                @Override
                                public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                                    Place place3 = fetchPlaceResponse.getPlace();
                                    try
                                    {
                                        name_of_ent = place3.getName().toString();
                                    }
                                    catch (NullPointerException e){
                                        name_of_ent = "Not Available";
                                    }
                                    try
                                    {
                                        phonenumber_of_ent = place3.getPhoneNumber().toString();
                                    }
                                    catch (NullPointerException e){
                                        phonenumber_of_ent="Not Available";

                                    }
                                    try
                                    {
                                        website_of_ent = place3.getWebsiteUri().toString();
                                    }
                                    catch (NullPointerException e){
                                        website_of_ent = "Not Available";

                                    }

                                    findbestrehab();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                            //tv.setText(jsonArray.toString());
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //check internet connection
                        Toasty.error(mainscreen.this,"Error Fetching information. Check your network connection.",Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onResponse: rest response+++++++++"+error.toString() );
                    }
                }
        );
        requestQueue3.add(objectRequest3);
    }

    private void findbestrehab()
    {

        String url = predefined_url+"rehab+center+in+"+city_of_person+"&key="+api_key_for_maps;
        RequestQueue requestQueue4 = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest4 = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response)
                    {
                        String id4 = null;
                        Log.e(TAG, "onResponse: rest response"+response.toString() );
                        // tv.setText(response.toString());
                        try {
                            JSONArray jsonArray = (JSONArray) response.get("results");
                            for(int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject rating = jsonArray.getJSONObject(i);
                                double rat = rating.getDouble("rating");

                                if(rat>4.2)
                                {
                                    JSONObject getplaceid4= jsonArray.getJSONObject(i);
                                    id4 = getplaceid4.getString("place_id");
                                    JSONObject getplaceaddress4 = jsonArray.getJSONObject(i);
                                    address_of_rehab =getplaceaddress4.getString("formatted_address");
                                    break;
                                }
                            }

                            Places.initialize(getApplicationContext(), api_key_for_maps);
                            PlacesClient placesClient4 = Places.createClient(mainscreen.this);

                            // Define a Place ID.
                            final String placeId4 = id4;

                            final List<Place.Field> placeFields4 = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.PHONE_NUMBER,Place.Field.WEBSITE_URI);

                            final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId4, placeFields4);

                            placesClient4.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                                @Override
                                public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                                    Place place4 = fetchPlaceResponse.getPlace();
                                    try
                                    {
                                        name_of_rehab= place4.getName().toString();
                                    }
                                    catch (NullPointerException e)
                                    {
                                        name_of_rehab = "Not available";
                                    }
                                    try
                                    {
                                        phonenumber_of_rehab= place4.getPhoneNumber().toString();
                                    }
                                    catch (NullPointerException e)
                                    {
                                        phonenumber_of_rehab = "Not available";
                                    }
                                    try
                                    {
                                        website_of_rehab= place4.getWebsiteUri().toString();
                                    }
                                    catch (NullPointerException e)
                                    {
                                        website_of_rehab = "Not available";
                                    }
                                    //website_of_rehab = place4.getWebsiteUri().toString();
                                    //Toasty.success(mainscreen.this, "Location found", Toast.LENGTH_SHORT).show();
                                    all_doctor_data_available = true;
                                    description_of_doctor.setText("ENT Specialist");
                                    name_of_doctor.setText(name_of_ent);
                                    phoneno_of_doctor.setText(phonenumber_of_ent);
                                    website_of_doctor.setText(website_of_ent);
                                    address_of_doctor.setText(address_of_ent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                            //tv.setText(jsonArray.toString());
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //check internet connection
                        Log.e(TAG, "onResponse: rest response+++++++++"+error.toString() );
                    }
                }
        );
        requestQueue4.add(objectRequest4);
    }


    public void doctorpage4()
    {

        //doctorselect.setCurrentActiveItem(0);

        for(int i = 0;i<9;i++)
        {
            doctorpageviews[i].setVisibility(View.VISIBLE);
        }
        for(int i = 0;i<9;i++)
        {
            YoYo.with(Techniques.SlideInUp)
                    .duration(700)
                    .playOn(doctorpageviews[i]);
        }

        rehab_animation.setVisibility(View.INVISIBLE);
        info_button.setVisibility(View.VISIBLE);
        doctor_animation.setAnimation(R.raw.entanimation);
        doctor_animation.playAnimation();
        phoneno_of_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phonenumber_of_ent));
                startActivity(callIntent);
            }
        });
        website_of_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(website_of_ent));
                startActivity(i);
            }
        });
    }

    public void vanishpage4()
    {
        for (int i = 0;i<9;i++)
        {
            final int loopercount = i;
            YoYo.with(Techniques.SlideOutUp)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            doctorpageviews[loopercount].setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .duration(700)
                    .playOn(doctorpageviews[i]);
        }
    }









    public void display_ent()
    {
        info_button.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(doctor_animation);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(phoneno_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(website_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(address_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        doctor_animation.setAnimation(R.raw.entanimation);
                        doctor_animation.playAnimation();
                        doctor_animation.setVisibility(View.VISIBLE);
                        rehab_animation.setVisibility(View.INVISIBLE);
                        phoneno_of_doctor.setText(phonenumber_of_ent);
                        website_of_doctor.setText(website_of_ent);
                        address_of_doctor.setText(address_of_ent);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(doctor_animation);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(phoneno_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(website_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(address_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(rehab_animation);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(700)
                .playOn(rehab_animation);

        description_of_doctor.setText("ENT Specialist");
        name_of_doctor.setText(name_of_ent);

        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(name_of_doctor);

        phoneno_of_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phonenumber_of_ent));
                startActivity(callIntent);
            }
        });
        website_of_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(website_of_ent));
                startActivity(i);
            }
        });
    }
    public void display_oncologist()
    {
        info_button.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(doctor_animation);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(phoneno_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(website_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(address_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        doctor_animation.setAnimation(R.raw.oncologistanimations);
                        doctor_animation.playAnimation();
                        doctor_animation.setVisibility(View.VISIBLE);
                        rehab_animation.setVisibility(View.INVISIBLE);
                        phoneno_of_doctor.setText(phonenumber_of_oncologist);
                        website_of_doctor.setText(website_of_oncologist);
                        address_of_doctor.setText(address_of_oncologist);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(doctor_animation);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(phoneno_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(website_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(address_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(rehab_animation);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(700)
                .playOn(rehab_animation);

        description_of_doctor.setText("Oncologist");
        name_of_doctor.setText(name_of_oncologist);

        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(name_of_doctor);

        phoneno_of_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phonenumber_of_oncologist));
                startActivity(callIntent);
            }
        });
        website_of_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(website_of_oncologist));
                startActivity(i);
            }
        });
    }
    public void display_cardiologist()
    {
        info_button.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(doctor_animation);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(phoneno_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(website_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(address_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        doctor_animation.setAnimation(R.raw.cardiologistanimation);
                        doctor_animation.playAnimation();
                        doctor_animation.setVisibility(View.VISIBLE);
                        rehab_animation.setVisibility(View.INVISIBLE);
                        phoneno_of_doctor.setText(phonenumber_of_cardiologist);
                        website_of_doctor.setText(website_of_cardiologist);
                        address_of_doctor.setText(address_of_cardiologist);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(doctor_animation);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(phoneno_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(website_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(address_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(rehab_animation);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(700)
                .playOn(rehab_animation);

        description_of_doctor.setText("Cardiologist");
        name_of_doctor.setText(name_of_cardiologist);

        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(name_of_doctor);

        phoneno_of_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phonenumber_of_cardiologist));
                startActivity(callIntent);
            }
        });
        website_of_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(website_of_cardiologist));
                startActivity(i);
            }
        });
    }
    public void display_rehab()
    {
        info_button.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(doctor_animation);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(phoneno_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(website_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(700)
                .playOn(address_of_doctor);
        YoYo.with(Techniques.SlideOutLeft)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        doctor_animation.setVisibility(View.INVISIBLE);
                        rehab_animation.setVisibility(View.VISIBLE);
                        rehab_animation.playAnimation();
                        phoneno_of_doctor.setText(phonenumber_of_rehab);
                        website_of_doctor.setText(website_of_rehab);
                        address_of_doctor.setText(address_of_rehab);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(doctor_animation);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(phoneno_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(website_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(address_of_doctor);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(rehab_animation);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .duration(700)
                .playOn(rehab_animation);

        description_of_doctor.setText("Rehab");
        name_of_doctor.setText(name_of_rehab);

        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(name_of_doctor);

        phoneno_of_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phonenumber_of_rehab));
                startActivity(callIntent);
            }
        });
        website_of_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(website_of_rehab));
                startActivity(i);
            }
        });
    }
}
//the project is fucking completed

