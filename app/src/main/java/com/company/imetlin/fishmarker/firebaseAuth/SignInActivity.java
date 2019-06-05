package com.company.imetlin.fishmarker.firebaseAuth;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.imetlin.fishmarker.MainActivity;
import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.pojo.Users;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener/*, GoogleApiClient.OnConnectionFailedListener*/ {

    private static final String TAG = "GOOGLE";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText edEmail;
    private EditText edPassword;
    private ImageView gooogle_button;
    private ImageView facebook_button;
    private RegistrationActivity registrationActivity;
    private Menu menu;



    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private String idToken;
    private final Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);

        registrationActivity = new RegistrationActivity();
        edEmail = (EditText) findViewById(R.id.et_email);
        edPassword = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_registration).setOnClickListener(this);
        findViewById(R.id.btn_forgot_password).setOnClickListener(this);
      //  findViewById(R.id.google_button).setOnClickListener(this);
        /*findViewById(R.id.facebook_button).setOnClickListener(this);*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


/*
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                }
            }
        };*/


        //если уже авторизован
        if (currentUser != null) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }



    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }*/


// Configure sign-in to request the user's basic profile like name and email


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sign_in) {
            if (!edEmail.getText().toString().equals("") ||
                    !edPassword.getText().toString().equals("")) {
                signing(edEmail.getText().toString(), edPassword.getText().toString());
            } else {
                Toast.makeText(SignInActivity.this, R.string.empty_email_or_password, Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.btn_registration) {
            startActivity(new Intent(SignInActivity.this, RegistrationActivity.class));
            finish();

        } else if (view.getId() == R.id.btn_forgot_password) {
            startActivity(new Intent(SignInActivity.this, ForgotPassword.class));
            finish();

      /*  } else if (view.getId() == R.id.google_button) {

            signingWithGoogle();

      *//*  } else if (view.getId() == R.id.facebook_button) {

            signingWithFacebook();*/

        }

         else {
            Toast.makeText(SignInActivity.this, R.string.requirementsy, Toast.LENGTH_SHORT).show();
        }

    }


    /////////////////////////////////email and password auth
    public void signing(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    checkIfEmailVerified();

                    //Toast.makeText(SignInActivity.this, "Autorithation complete", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(SignInActivity.this, R.string.autorithation, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
/////////////////////////////////check email

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()) {

            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

            //user is verified, so you can finish this activity or send user to activity which you want.
            //finish();
            Toast.makeText(SignInActivity.this, R.string.successfully, Toast.LENGTH_SHORT).show();
        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            //            // NOTE: don't forget to log out the user.

            Toast.makeText(SignInActivity.this, R.string.verify, Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            //restart this activity

        }
    }


    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

 /*   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();


            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                String name = account.getDisplayName();
                String email = account.getEmail();
                String location = "null";

                Users user = new Users(
                        name,
                        email,
                        location
                );


                FirebaseDatabase.getInstance().getReference("Users")
                        .child(currentUser.getUid())
                        .setValue(user);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }


        }
    }
*/
 /*   private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else

                            Toast.makeText(SignInActivity.this, R.string.autorithation, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signingWithGoogle() {


        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }
   *//* private void signingWithFacebook(){



    }*//*

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


*/

}

