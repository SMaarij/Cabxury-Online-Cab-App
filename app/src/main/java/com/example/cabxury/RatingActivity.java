package com.example.cabxury;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RatingActivity extends AppCompatActivity {

    RatingBar ratingBar;
    Button Submit_Review;
    public String driver_id;

    String transmode;
    public String uemail;
    private static final String apiurl = "https://cabxury.000webhostapp.com/userdata.php";
    TextView textView1;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Submit_Review = findViewById(R.id.subrev);
        TextView textView = findViewById(R.id.dn);
        uemail = getIntent().getStringExtra("uemail");
        textView1 = findViewById(R.id.textView38);
        textView2 = findViewById(R.id.textView40);



        Intent modeintent = getIntent();
        transmode = getIntent().getStringExtra("mode");

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView transmode1 = findViewById(R.id.tr);
        transmode1.setText(transmode);

        new FetchDataAsyncTask().execute(apiurl);


        Submit_Review.setOnClickListener(v -> {
            Intent intent = new Intent(RatingActivity.this,review.class);
            intent.putExtra("uemail", uemail);
            intent.putExtra("driver_id", driver_id);
            startActivity(intent);
        });
    }
    private class FetchDataAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                response = sb.toString();
                in.close();
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return response;
        }

        TextView textView = findViewById(R.id.dn);
        @Override
        protected void onPostExecute(String result) {
            try {
                // Parse the JSON response from the API URL
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                // Get the value of the "name" key from the JSON object
                String name = jsonObject.getString("name");
                String phone = jsonObject.getString("phone_number");
                String model = jsonObject.getString("car_model");
                driver_id = jsonObject.getString("driver_id");




                // Set the text of the text view with id "dn" to the name value
                textView.setText(name);
                textView1.setText(phone);
                textView2.setText(model);

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        }
    }
}


