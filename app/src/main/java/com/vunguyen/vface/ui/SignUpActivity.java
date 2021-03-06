/*
 * SignUpActivity.java
 */
package com.vunguyen.vface.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.vunguyen.vface.R;
import com.vunguyen.vface.helper.LocaleHelper;

/**
 * This class is to implements the functions for Sign Up account activity
 */
public class SignUpActivity extends AppCompatActivity
{
    ImageView ivBackArrow;
    TextView tvLogin;
    EditText emailID, password;
    Button btnSignUp;
    FirebaseAuth firebaseAuth;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();
        initData();
        initAction();
    }

    private void initAction()
    {
        btnSignUp.setOnClickListener(v ->
        {
            String email = emailID.getText().toString();
            String pwd = password.getText().toString();

            if (email.isEmpty())
            {
                emailID.setError("Please enter your email");
                emailID.requestFocus();
            }
            else if(pwd.isEmpty())
            {
                password.setError("Please enter your password");
                password.requestFocus();
            }
            else if (email.isEmpty() && pwd.isEmpty())
            {
                Toast.makeText(SignUpActivity.this, "Fields are empty!",Toast.LENGTH_SHORT).show();
            }
            else if (!email.isEmpty() && !pwd.isEmpty())
            {
                firebaseAuth.createUserWithEmailAndPassword(email,pwd)
                        .addOnCompleteListener(SignUpActivity.this, task ->
                        {
                            if (!task.isSuccessful())
                            {
                                Toast.makeText(SignUpActivity.this, "Register failed. Please try again!"
                                        ,Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(SignUpActivity.this, "Registered Successful. Please log in."
                                        , Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            }
                        });
            }
            else
            {
                Toast.makeText(SignUpActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        ivBackArrow.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, WelcomeScreenActivity.class );
            openActivity(intent);
        });

        tvLogin.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, LoginActivity.class);
            openActivity(intent);
        });
    }

    private void initData()
    {
        // register the account with firebase server
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initView()
    {
        emailID = findViewById(R.id.emailID);
        password = findViewById(R.id.password);
        tvLogin = findViewById(R.id.txtLogin);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    public void openActivity(Intent intent)
    {
        startActivity(intent);
        finish();
    }
}
