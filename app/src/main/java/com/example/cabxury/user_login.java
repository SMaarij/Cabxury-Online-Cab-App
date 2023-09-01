package com.example.cabxury;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class user_login extends AppCompatActivity {

    EditText email, pass;
    Button user_logbtn,user_signbtn;
    String semail, spassword;

    LottieAnimationView anime;
    String url = "https://cabxury.000webhostapp.com/userlogin.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

            email = (EditText) findViewById(R.id.email);
            pass = (EditText) findViewById(R.id.password);
            user_logbtn = (Button) findViewById(R.id.ulogbtn);
            user_signbtn= findViewById(R.id.usignbtn);

            user_signbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent y= new Intent(user_login.this,user_signup.class);
                    startActivity(y);
                }
            });

            user_logbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    semail = email.getText().toString().trim();
                    spassword = pass.getText().toString().trim();

                    anime = findViewById(R.id.lottieAnimationView5);
                    anime.playAnimation();
                    anime.loop(true);



                    StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Login successfully"))
                            {
                                Toast.makeText(user_login.this, response, Toast.LENGTH_SHORT).show();
                                Intent maps = new Intent(user_login.this, MapsMarkerActivity.class);
                                maps.putExtra("uemail",semail);
                                startActivity(maps);
                            }
                            else {
                                Toast.makeText(user_login.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(user_login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map getParams() throws AuthFailureError {
                            //return super.getParams();
                            Map para = new HashMap();

                            para.put("email", semail);
                            para.put("pass", spassword);
                            return para;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(user_login.this);
                    requestQueue.add(rq);

                }
            });
    }
}