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
        com.example.dicetasks.data.Task task1 = new com.example.dicetasks.data.Task("Первый раз", "Создать своё первое задание", 1, 3, 1);
        com.example.dicetasks.data.Task task2 = new com.example.dicetasks.data.Task("Велосипедная прогулка", "Провести время с семьей или друзьями, катаясь на велоспипедах", 2, 3, 0);
        com.example.dicetasks.data.Task task3 = new com.example.dicetasks.data.Task("\uD83C\uDD71edolaga", "Сделать домашнее задание", 3, 3, 0);
        com.example.dicetasks.data.Task task4 = new com.example.dicetasks.data.Task("Геймер", "Поиграть в онлайн-игру", 4, 3, 0);
        com.example.dicetasks.data.Task task5 = new com.example.dicetasks.data.Task("Чистюля", "Прибраться в своей комнате", 5, 3, 0);
        com.example.dicetasks.data.Task task6 = new com.example.dicetasks.data.Task("Чистюля", "Прибраться в квартире", 6, 3, 0);
        com.example.dicetasks.data.Task task7 = new com.example.dicetasks.data.Task("Чистюля", "Помыть полы в доме", 1, 3, 0);
        com.example.dicetasks.data.Task task8 = new com.example.dicetasks.data.Task("Italiano", "Приготовить пиццу", 2, 3, 0);
        com.example.dicetasks.data.Task task9 = new com.example.dicetasks.data.Task("Пекарь", "Испечь что-нибудь", 3, 3, 0);
        com.example.dicetasks.data.Task task10 = new com.example.dicetasks.data.Task("Социализация", "Позвонить своему лучшему другу или подруге", 4, 3, 0);
        com.example.dicetasks.data.Task task11 = new com.example.dicetasks.data.Task("Избавление от лишнего", "Сфотографировать ненужные вещи и выложить их на продажу в каком-то интернет-сервисе", 5, 3, 0);
        com.example.dicetasks.data.Task task12 = new com.example.dicetasks.data.Task("Взгяд в будущее", "Составить список целей на ближайший месяц, полгода, год.", 6, 3, 0);
        com.example.dicetasks.data.Task task13 = new com.example.dicetasks.data.Task("Старые знакомства", "Вспомнить старого друга, с которым давно не общались, и отправить ему сообщение в мессенджере", 1, 3, 0);
        com.example.dicetasks.data.Task task14 = new com.example.dicetasks.data.Task("На будущее", "Составить и записать список подарков, которые обрадуют ваших близких", 2, 3, 0);
        com.example.dicetasks.data.Task task15 = new com.example.dicetasks.data.Task("С понедельника будет поздно...", "Наконец-то заняться спортом. Вы же давно мечтаете прокачать свое тело?", 3, 3, 0);
        com.example.dicetasks.data.Task task16 = new com.example.dicetasks.data.Task("Певец", "Вспомнить свою любимую песню и спеть ее", 4, 3, 0);
        com.example.dicetasks.data.Task task17 = new com.example.dicetasks.data.Task("Связь с родственниками", "Позвонить родственнику, с которым вы давно не общались", 5, 3, 0);
        com.example.dicetasks.data.Task task18 = new com.example.dicetasks.data.Task("Лучший \"Я\"", "Завести календарь хороших привычек", 6, 3, 0);
        com.example.dicetasks.data.Task task19 = new com.example.dicetasks.data.Task("Читатель", "Перечитать свою любимую книгу", 1, 3, 0);
        com.example.dicetasks.data.Task task20 = new com.example.dicetasks.data.Task("Здоровое тело", "Научиться делать массаж лица", 2, 3, 0);
        com.example.dicetasks.data.Task task21 = new com.example.dicetasks.data.Task("Живая музыка", "Купить билет на концерт любимого исполнителя", 3, 3, 0);
        com.example.dicetasks.data.Task task22 = new com.example.dicetasks.data.Task("Каллиграфия", "Освойте искусство писать четким, красивым почерком", 4, 3, 0);
        com.example.dicetasks.data.Task task23 = new com.example.dicetasks.data.Task("Чистый воздух", "Отложить телефон в сторону и выйти погулять по близайшему парку", 5, 3, 0);
        com.example.dicetasks.data.Task task24 = new com.example.dicetasks.data.Task("Полиглот", "Начать учить новый язык", 6, 3, 0);
        com.example.dicetasks.data.Task task25 = new com.example.dicetasks.data.Task("Стирка", "Постирать грязные вещи", 1, 3, 0);
        com.example.dicetasks.data.Task task26 = new com.example.dicetasks.data.Task("Время перемен", "Обновить расположение мебели в своем доме", 2, 3, 0);
        com.example.dicetasks.data.Task task27 = new com.example.dicetasks.data.Task("Художник", "Нарисовать портрет или пейзаж", 3, 3, 0);
        com.example.dicetasks.data.Task task28 = new com.example.dicetasks.data.Task("Планирование будущего", "Написать список своих желаний", 4, 3, 0);
        com.example.dicetasks.data.Task task29 = new com.example.dicetasks.data.Task("Обстановка в мире", "Прочитать самые главные новости к этому часу", 5, 3, 0);
        com.example.dicetasks.data.Task task30 = new com.example.dicetasks.data.Task("Блоггер", "Завести канал на YouTube и снять свое первое видео", 6, 3, 0);
        com.example.dicetasks.data.Task task31 = new com.example.dicetasks.data.Task("До конца", "Довести до конца отложенное дело", 1, 3, 0);
        com.example.dicetasks.data.Task task32 = new com.example.dicetasks.data.Task("Выпить воды", "Побороть скуку это не поможет, но лишним не станет", 2, 3, 0);
        com.example.dicetasks.data.Task task33 = new com.example.dicetasks.data.Task("Анализ", "Проанализировать свои траты в этом месяце", 3, 3, 0);
        com.example.dicetasks.data.Task task34 = new com.example.dicetasks.data.Task("Большой экран", "Посмотреть фильм в кинотеатре (не забудьте позвать своих друзей)", 4, 3, 0);
        com.example.dicetasks.data.Task task35 = new com.example.dicetasks.data.Task("Здоровье", "Сделать гимнастику под музыку", 5, 3, 0);


        database.push().setValue(task1);
        database.push().setValue(task2);
        database.push().setValue(task3);
        database.push().setValue(task4);
        database.push().setValue(task5);
        database.push().setValue(task6);
        database.push().setValue(task7);
        database.push().setValue(task8);
        database.push().setValue(task9);
        database.push().setValue(task11);
        database.push().setValue(task12);
        database.push().setValue(task13);
        database.push().setValue(task14);
        database.push().setValue(task15);
        database.push().setValue(task16);
        database.push().setValue(task17);
        database.push().setValue(task18);
        database.push().setValue(task19);
        database.push().setValue(task20);
        database.push().setValue(task21);
        database.push().setValue(task22);
        database.push().setValue(task23);
        database.push().setValue(task24);
        database.push().setValue(task25);
        database.push().setValue(task26);
        database.push().setValue(task27);
        database.push().setValue(task28);
        database.push().setValue(task29);
        database.push().setValue(task30);
        database.push().setValue(task31);
        database.push().setValue(task32);
        database.push().setValue(task33);
        database.push().setValue(task34);
        database.push().setValue(task35);*/
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