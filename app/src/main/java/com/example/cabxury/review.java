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

public class review extends AppCompatActivity {
    private static final String apiurl = "https://cabxury.000webhostapp.com/user_rating.php";


    RatingBar drating;
    RatingBar arating;
    Button Submit_Review;
    EditText review_comment;
    private TextView userIdTextView;
    public String uemail;
    public String driver_id;
    public String DRating;
    public String ARating;
    public String comment;

    Button button1;

    String url = "https://cabxury.000webhostapp.com/user_rating.php";
    //String url2 = "https://cabxury.000webhostapp.com/user_ride.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);



        review_comment = (EditText) findViewById(R.id.comment);
        drating = findViewById(R.id.rb);
        arating = findViewById(R.id.rb1);
        uemail = getIntent().getStringExtra("uemail");
        driver_id = getIntent().getStringExtra("driver_id");
        Submit_Review = findViewById(R.id.subrev);
        button1 = findViewById(R.id.button2);

        Submit_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //new FetchDataTask().execute(apiurl);
                //               ##### App rating value is stored in Rating variable  #######

                DRating = String.valueOf(drating.getRating());

//                #### Driver Rating is stored here ####

                ARating= String.valueOf(arating.getRating());

                //                Comment is Stord here !!!! #####
                comment = String.valueOf(review_comment.getText());


                StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(review.this,"Review Submitted", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(review.this, MapsMarkerActivity.class);
                        myIntent.putExtra("uemail", uemail);

                        startActivity(myIntent); //start the new activity
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(review.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        //return super.getParams();
                        Map para = new HashMap();

                        para.put("driver_id", driver_id);
                        para.put("uemail", uemail);
                        para.put("DRating", DRating);
                        para.put("ARating", ARating);
                        para.put("comment", comment);

                        return para;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(review.this);
                requestQueue.add(rq);

            }
        });
    }




    private class FetchDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String userId = jsonObject.getString("user_id");

                    userIdTextView.setText(userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(review.this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(review.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}