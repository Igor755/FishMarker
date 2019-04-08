package com.company.imetlin.fishmarker.firebaseAuth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.company.imetlin.fishmarker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    ProgressBar progressBar;
    EditText et_email;
    Button btn_send_pass;

    FirebaseAuth firebaseAuth;

    public Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);

        progressBar = findViewById(R.id.progressbar);
        et_email = findViewById(R.id.et_email);
        btn_send_pass =findViewById(R.id.btn_send_pass);
        progressBar.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_send_pass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                final String email = et_email.getText().toString().trim();



                if (email.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    et_email.setError(getText(R.string.empty_email));
                    et_email.requestFocus();
                    return;
                }
                if (!RegistrationActivity.isEmailValid(email)) {
                    progressBar.setVisibility(View.GONE);
                    et_email.setError(getText(R.string.not_valid_email));
                    et_email.requestFocus();
                    return;
                }



                firebaseAuth.sendPasswordResetEmail(et_email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgotPassword.this, "Password sent to your email", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });


    }
    @Override
    public void onBackPressed() {
        backToBack();
    }
    public void backToBack(){
        startActivity(new Intent(ForgotPassword.this, SignInActivity.class));
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.back:
                backToBack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_water, menu);
        return true;
    }
}
