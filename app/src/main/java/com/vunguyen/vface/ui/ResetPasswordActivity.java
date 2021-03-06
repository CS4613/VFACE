/*
 * ResetPasswordActivity.java
 */
package com.vunguyen.vface.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.vunguyen.vface.R;
import com.vunguyen.vface.helper.LocaleHelper;

/**
 * This class implements functions for reset password activity features
 */
public class ResetPasswordActivity extends AppCompatActivity
{
    EditText emailID;
    Button btnSend;
    FirebaseAuth mFirebaseAuth;
    ImageView ivBackArrow;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();
        initData();
        initAction();
    }

    private void initAction()
    {
        btnSend.setOnClickListener(v ->
        {
            String email = emailID.getText().toString();
            if (email.isEmpty())
            {
                Toast.makeText(ResetPasswordActivity.this, "Please enter your valid email address", Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Send password reset link to the registered email
                mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task ->
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(ResetPasswordActivity.this, "Reset link is sent to your email."
                                , Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(ResetPasswordActivity.this, "Error Occurred!" + message
                                , Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
        ivBackArrow.setOnClickListener(v -> onBackPressed());
    }

    private void initData()
    {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void initView()
    {
        emailID = findViewById(R.id.emailID);
        btnSend = findViewById(R.id.btnSend);
        // Back to previous activity
        ivBackArrow = findViewById(R.id.ivBackArrow);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
        finish();
    }
}
