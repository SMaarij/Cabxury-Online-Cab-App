package com.example.cabxury;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity
{

    private Button WelcomeDriverBtn,WelcomeUserBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        WelcomeDriverBtn = findViewById(R.id.welcome_driver_btn);
        WelcomeUserBtn= findViewById(R.id.welcome_user_btn);

        WelcomeUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UserLogin = new Intent(WelcomeActivity.this, user_login.class);
                startActivity(UserLogin);
            }
        });




        WelcomeDriverBtn.setOnClickListener(view -> {
            Intent LoginRegisterDriverIntent = new Intent(WelcomeActivity.this, DriverLoginRegisterActivity.class);
            startActivity(LoginRegisterDriverIntent);

        });
    }
}