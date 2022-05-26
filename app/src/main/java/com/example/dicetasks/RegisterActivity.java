package com.example.dicetasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dicetasks.data.TasksDB;
import com.example.dicetasks.data.TasksDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etRegisterEmail;
    TextInputEditText etRegisterPassword;
    TextView tvLoginHere;
    Button registerButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegisterEmail = findViewById(R.id.et_register_email);
        etRegisterPassword = findViewById(R.id.et_register_password);
        tvLoginHere = findViewById(R.id.tv_login_here);
        registerButton = findViewById(R.id.register_button);

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(view -> {
            createUser();
        });

        tvLoginHere.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private void createUser() {
        String email = etRegisterEmail.getText().toString().trim();
        String password = etRegisterPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etRegisterEmail.setError("Это поле должно быть заполнено!");
            etRegisterEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)) {
            etRegisterPassword.setError("Это поле должно быть заполнено!");
            etRegisterPassword.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etRegisterEmail.setError("Пожалуйста, укажите действительную почту!");
            etRegisterEmail.requestFocus();
        }
        else if (password.length() < 6) {
            etRegisterPassword.setError("Пароль слишком короткий!");
            etRegisterPassword.requestFocus();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Регистрация " +
                                "прошла успешно", Toast.LENGTH_SHORT).show();

                        //Code in charge of making up new randoms
                        //TasksDB tasksDB = TasksDB.getInstance(getBaseContext());
                        //TasksDao tasksDao = tasksDB.tasksDao();

                        DatabaseReference randTasksBase = FirebaseDatabase.getInstance().getReference("RandomTasks");
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Tasks");
                        DatabaseReference stats = FirebaseDatabase.getInstance().getReference("Statistics");
                        ValueEventListener valueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int counter = 0;
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    com.example.dicetasks.data.Task task = ds.getValue(com.example.dicetasks.data.Task.class);
                                    com.example.dicetasks.data.Statistics statistics = ds.getValue(com.example.dicetasks.data.Statistics.class);
                                    if (task != null) {
                                        task.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        statistics.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        if (counter == 0) {
                                            stats.push().setValue(statistics);
                                        }
                                        String key = database.push().getKey();
                                        task.setKey(key);
                                        database.child(key).setValue(task);
                                        counter++;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
                        randTasksBase.addValueEventListener(valueEventListener);



                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Ошибка " +
                                "регистрации", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}