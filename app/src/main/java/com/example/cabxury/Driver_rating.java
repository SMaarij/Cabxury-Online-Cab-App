package com.example.cabxury;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Driver_rating extends AppCompatActivity {
    private static final String apiurl = "https://cabxury.000webhostapp.com/driverdata.php";


    RatingBar urating;
    RatingBar arating;
    Button Submit_Review;
    public String demail;
    public String user_id;
    public String userId;
    public String driver_id;
    private TextView userIdTextView;
    public String URating;
    public String ARating;
    public String comment;
    EditText review_comment;

    String url = "https://cabxury.000webhostapp.com/driver_rating.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_rating);

        review_comment = (EditText) findViewById(R.id.comment1);
        urating= findViewById(R.id.rb2);
        arating = findViewById(R.id.rb3);
        Submit_Review = findViewById(R.id.subrev2);
        user_id= getIntent().getStringExtra("user_id");
        demail= getIntent().getStringExtra("demail");
        Submit_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new FetchDataTask().execute(apiurl);
                //               ##### App rating value is stored in Rating variable  #######

                URating = String.valueOf(urating.getRating());

//                #### Driver Rating is stored here ####

                ARating= String.valueOf(arating.getRating());

                //                Comment is Stord here !!!! #####
                comment = String.valueOf(review_comment.getText());

                StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Driver_rating.this,"Review Submitted", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(Driver_rating.this, MapsActivity2.class);

                        startActivity(myIntent); //start the new activity
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Driver_rating.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        //return super.getParams();
                        Map para = new HashMap();

                        para.put("user_id", user_id);
                        para.put("demail", demail);
                        para.put("URating", URating);
                        para.put("ARating", ARating);
                        para.put("comment", comment);

                        return para;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Driver_rating.this);
                requestQueue.add(rq);

                System.out.println(user_id);
                System.out.println(demail);
                System.out.println(URating);
                System.out.println(ARating);
                System.out.println(comment);


            }
        });
    }

}