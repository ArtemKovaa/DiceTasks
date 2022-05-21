package com.example.dicetasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputEditText etResetEmail;
    Button resetPasswordButton;
    TextView tvLoginBack;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etResetEmail = findViewById(R.id.et_reset_email);
        resetPasswordButton = findViewById(R.id.reset_password_button);
        tvLoginBack = findViewById(R.id.tv_login_back);

        mAuth = FirebaseAuth.getInstance();

        tvLoginBack.setOnClickListener(view -> {
            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
        });

        resetPasswordButton.setOnClickListener(view -> {
            resetPassword();
        });
    }

    private void resetPassword() {
        String email = etResetEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etResetEmail.setError("Это поле должно быть заполнено!");
            etResetEmail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etResetEmail.setError("Пожалуйста, укажите действительную почту!");
            etResetEmail.requestFocus();
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Проверьте " +
                            "почту, чтобы восстановить пароль", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ForgotPasswordActivity.this, "Ошибка " +
                            "восстановления", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}