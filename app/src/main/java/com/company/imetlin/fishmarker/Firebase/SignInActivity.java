package com.company.imetlin.fishmarker.Firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.company.imetlin.fishmarker.MainActivity;
import com.company.imetlin.fishmarker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText edEmail;
    private EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);

        mAuth = FirebaseAuth.getInstance();


        edEmail = (EditText) findViewById(R.id.et_email);
        edPassword = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_registration).setOnClickListener(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //если уже авторизован

        if (currentUser != null) {
            Intent intent = new Intent();
            setResult(MainActivity.RESULT_OK, intent);
            finish();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sign_in) {
            if (!edEmail.getText().toString().equals("") ||
                    !edPassword.getText().toString().equals("")) {
                signing(edEmail.getText().toString(), edPassword.getText().toString());
            } else {
                Toast.makeText(SignInActivity.this, "username or password is empty", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.btn_registration) {
            startActivity(new Intent(SignInActivity.this, RegistrationActivity.class));
            finish();

        } else {
            Toast.makeText(SignInActivity.this, "Does not meet the requirements", Toast.LENGTH_SHORT).show();
        }

    }


    public void signing(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    checkIfEmailVerified();

                    //Toast.makeText(SignInActivity.this, "Autorithation complete", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(SignInActivity.this, "Autorithation ERROR", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {

            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

            //user is verified, so you can finish this activity or send user to activity which you want.
            //finish();
            Toast.makeText(SignInActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            //            // NOTE: don't forget to log out the user.

            Toast.makeText(SignInActivity.this, "Please verify your account................", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            //restart this activity

        }
    }




    @Override
    public void onBackPressed() {
        this.finishAffinity();    }
}
