package com.company.imetlin.fishmarker.Firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText edEmail;
    private EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mAuth = FirebaseAuth.getInstance();
        //если пользователь зарегестрирован или прошел авторизацию
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {

                    Intent intent = new Intent();

                    setResult(MainActivity.RESULT_OK, intent);
                    finish();


                } else {

                }

            }
        };

        edEmail = (EditText) findViewById(R.id.et_email);
        edPassword = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_registration).setOnClickListener(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //если уже авторизован
        if (currentUser != null){
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
            }
            else{
                Toast.makeText(RegisterActivity.this, "username or password is empty", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.btn_registration) {
            if ((isEmailValid(edEmail.getText().toString())) ||
                    !edEmail.getText().toString().equals("") ||
                    (isPasswordValid(edPassword.getText().toString())) ||
                    !edPassword.getText().toString().equals("")) {
                regisration(edEmail.getText().toString(), edPassword.getText().toString());
            }
            else{
                Toast.makeText(RegisterActivity.this, "Does not meet the requirements", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void signing(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent();
                    setResult(MainActivity.RESULT_OK, intent);
                    finish();
                    Toast.makeText(RegisterActivity.this, "Autorization complete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Autorization ERROR", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void regisration(String email, String passrord) {
        mAuth.createUserWithEmailAndPassword(email, passrord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration complete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration ERROR", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
    public static boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }
    public static boolean isPasswordValid(String password) {
        final Pattern PASSWORD_REGEX = Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", Pattern.CASE_INSENSITIVE);
        return PASSWORD_REGEX.matcher(password).matches();
    }
    public void RulesClick(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Требования к паролю")
                .setMessage("1)At least 8 chars\n" +
                        "2)Contains at least one digit\n" +
                        "3)Contains at least one lower alpha char and one upper alpha char\n" +
                        "4)Contains at least one char within a set of special chars (@#%$^ etc.)\n" +
                        "5)Does not contain space, tab, etc.")
                .setIcon(R.drawable.i)
                .setCancelable(false)
                .setNegativeButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
