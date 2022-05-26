package com.example.dicetasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    TextView tvForgotPassword;
    Button loginButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.et_login_email);
        etLoginPassword = findViewById(R.id.et_login_password);
        tvRegisterHere = findViewById(R.id.tv_register_here);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        loginButton = findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();
        /*DatabaseReference database = FirebaseDatabase.getInstance().getReference("RandomTasks");
        com.example.dicetasks.data.Task task1 = new com.example.dicetasks.data.Task("Первый раз", "Создайте своё первое задание", 1, 3, 0);
        com.example.dicetasks.data.Task task2 = new com.example.dicetasks.data.Task("Велосипедная прогулка", "Проведите время с семьей или друзьями, катаясь на велоспипедах", 2, 3, 0);
        com.example.dicetasks.data.Task task3 = new com.example.dicetasks.data.Task("\uD83C\uDD71edolaga", "Сделайте домашнее задание", 3, 3, 0);
        com.example.dicetasks.data.Task task4 = new com.example.dicetasks.data.Task("Дед инсайд", "Поиграйте в доту", 3, 3, 0);
        com.example.dicetasks.data.Task task5 = new com.example.dicetasks.data.Task("Дед инсайд", "Поиграйте в доту", 3, 3, 0);
        database.push().setValue(task1);
        database.push().setValue(task2);
        database.push().setValue(task3);
        database.push().setValue(task4);
        database.push().setValue(task5);*/
        loginButton.setOnClickListener(view -> {
            loginUser();
        });

        tvRegisterHere.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        tvForgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });
    }

    private void loginUser() {
        String email = etLoginEmail.getText().toString().trim();
        String password = etLoginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etLoginEmail.setError("Это поле должно быть заполнено!");
            etLoginEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)) {
            etLoginPassword.setError("Это поле должно быть заполнено!");
            etLoginPassword.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etLoginEmail.setError("Пожалуйста, укажите действительную почту!");
            etLoginEmail.requestFocus();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Авторизация " +
                                "прошла успешно", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Ошибка " +
                                "авторизации", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}