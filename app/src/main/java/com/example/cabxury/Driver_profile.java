package com.example.cabxury;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Driver_profile extends AppCompatActivity {
    public String demail;
    EditText name, pass, phone_number;
    Button driver_updbtn,driver_history,driver_logout, driver_delete;
    String nname, spassword, sphone;
    String url = "https://cabxury.000webhostapp.com/driverupdate.php";
    String url2 = "https://cabxury.000webhostapp.com/driver_delete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_driver);

        demail = getIntent().getStringExtra("demail");

        name = (EditText) findViewById(R.id.change_name);
        pass = (EditText) findViewById(R.id.new_password);
        phone_number = (EditText) findViewById(R.id.update_phone_number);

        driver_logout = findViewById(R.id.driver_logout);
        driver_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Driver_profile.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                Intent logout = new Intent(Driver_profile.this, DriverLoginRegisterActivity.class); //create new intent
                startActivity(logout); //start the new activity
            }
        });

        driver_updbtn= findViewById(R.id.prof_confirmbtn);
        driver_updbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nname = name.getText().toString().trim();
                spassword = pass.getText().toString().trim();
                sphone = phone_number.getText().toString().trim();

                StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Update successful"))
                        {
                            Toast.makeText(Driver_profile.this, response, Toast.LENGTH_SHORT).show();
                            Intent upd = new Intent(Driver_profile.this, Customerconfirmation.class);
                            startActivity(upd);
                        }
                        else {
                            Toast.makeText(Driver_profile.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Driver_profile.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        //return super.getParams();
                        Map para = new HashMap();

                        para.put("nname", nname);
                        para.put("npass", spassword);
                        para.put("upd_phone_number", sphone);
                        para.put("email", demail);
                        return para;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Driver_profile.this);
                requestQueue.add(rq);

            }
        });

        driver_delete= findViewById(R.id.driver_delete);
        driver_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest rq = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Account Deleted"))
                        {
                            Toast.makeText(Driver_profile.this, response, Toast.LENGTH_SHORT).show();
                            Intent upd = new Intent(Driver_profile.this, DriverLoginRegisterActivity.class);
                            startActivity(upd);
                        }
                        else {
                            Toast.makeText(Driver_profile.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Driver_profile.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        //return super.getParams();
                        Map para = new HashMap();
                        para.put("email", demail);
                        return para;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Driver_profile.this);
                requestQueue.add(rq);

            }
        });
    }
}
