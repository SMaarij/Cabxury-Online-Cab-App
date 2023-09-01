package com.example.cabxury;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class userhistory extends AppCompatActivity {

    private String ridername;
    public String drivername;
    public String pickuplocation;
    public String destination, uemail;
    public String fare;

    String url = "https://cabxury.000webhostapp.com/userhistory.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_userhistory);

        TextView textView = findViewById(R.id.textView19);
        TextView textView1 = findViewById(R.id.textView20);
        TextView textView2 = findViewById(R.id.textView21);
        TextView textView3 = findViewById(R.id.textView22);
        TextView textView4 = findViewById(R.id.textView23);

        new FetchDataTask().execute(url);
        uemail = getIntent().getStringExtra("uemail");


        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userhistory.this,profile_activity.class);
                intent.putExtra("uemail",uemail);
                startActivity(intent);
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
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);

                // Check if the response is an empty array
                if (jsonArray.length() == 0) {
                    Toast.makeText(userhistory.this, "No data found", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject jsonObject = jsonArray.getJSONObject(0);

                // Verify that the JSON keys match the actual keys in the JSON data
                if (!jsonObject.has("ridername") || !jsonObject.has("drivername")
                        || !jsonObject.has("pickup_location") || !jsonObject.has("destination")
                        || !jsonObject.has("ride_fare")) {
                    Toast.makeText(userhistory.this, "Invalid JSON data format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Retrieve the data from JSON
                String name = jsonObject.getString("ridername");
                String drivername = jsonObject.getString("drivername");
                String pickuplocation = jsonObject.getString("pickup_location");
                String destination = jsonObject.getString("destination");
                String fare = jsonObject.getString("ride_fare");

                // Find the TextView elements to update
                TextView textView = findViewById(R.id.textView19);
                TextView textView1 = findViewById(R.id.textView20);
                TextView textView2 = findViewById(R.id.textView21);
                TextView textView3 = findViewById(R.id.textView22);
                TextView textView4 = findViewById(R.id.textView23);

                // Update the TextView elements with the retrieved data
                textView.setText(name);
                textView1.setText(drivername);
                textView2.setText(pickuplocation);
                textView3.setText(destination);
                textView4.setText(fare);

            } catch (JSONException e) {
                Log.e("ERROR", e.getMessage(), e);
                Toast.makeText(userhistory.this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
