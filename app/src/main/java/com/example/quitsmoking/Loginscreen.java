package com.example.quitsmoking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import es.dmoral.toasty.Toasty;


public class Loginscreen extends AppCompatActivity
{

    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    LottieAnimationView progress;

    TextView welcome_txt;
    TextView chantix_txt;
    TextView already_signin;
    private ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null)
        {
            Intent intent = new Intent(getApplicationContext(), bridge.class);
            startActivity(intent);
            finish();
            Animatoo.animateFade(Loginscreen.this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        welcome_txt = findViewById(R.id.welcome_txt);
        chantix_txt = findViewById(R.id.chantix_name_txt);
        progress = findViewById(R.id.progress);
        final LottieAnimationView logo = findViewById(R.id.siginanimation);
        final SignInButton signInButton = findViewById(R.id.googlesignin);
        already_signin = findViewById(R.id.alreadysignin);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(welcome_txt);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(chantix_txt);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(signInButton);
        YoYo.with(Techniques.FadeIn)
                .duration(3500)
                .playOn(already_signin);
        logo.setVisibility(View.VISIBLE);
        logo.playAnimation();


        //progressDialog =new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        createRequest();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                progress.playAnimation();
                YoYo.with(Techniques.FadeIn)
                        .duration(400)
                        .playOn(progress);
                YoYo.with(Techniques.FadeOut)
                        .duration(300)
                        .playOn(logo);
                YoYo.with(Techniques.FadeOut)
                        .duration(300)
                        .playOn(welcome_txt);
                YoYo.with(Techniques.FadeOut)
                        .duration(400)
                        .playOn(chantix_txt);
                YoYo.with(Techniques.FadeOut)
                        .duration(200)
                        .playOn(signInButton);
                YoYo.with(Techniques.FadeOut)
                        .duration(300)
                        .playOn(already_signin);


                // progressDialog.setMessage("Please Wait for a Moment.....");
                //progressDialog.show();
                signIn();

            }
        });
    }
    private   void   createRequest(){

        //configure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }
            catch (ApiException e)
            {
                // Google Sign In failed
                // ...
                Toasty.error(this, "Failed!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "55555555", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            YoYo.with(Techniques.FadeOut)
                                    .withListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation)
                                        {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent intent = new Intent(getApplicationContext(), bridge.class);
                                            startActivity(intent);
                                            finish();
                                            Animatoo.animateFade(Loginscreen.this);
                                            Toasty.success(Loginscreen.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    })
                                    .duration(500)
                                    .playOn(progress);

                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Toasty.error(Loginscreen.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}


 /*buttonout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
                Toast.makeText(MainActivity11.this, "Sign out successful", Toast.LENGTH_SHORT).show();
            }
        });*/

 // data fetch og signed in user
/* GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (account != null) {
                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personId = account.getId();
                Uri personPhoto = account.getPhotoUrl();
                }*/