package com.example.cabxury;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Customerconfirmation extends AppCompatActivity {

    TextView textView;
    TextView textView1;
    TextView textView2;
    Button Submit_Review;
    public String demail;

    public String user_id;


    private static final String apiurl = "https://cabxury.000webhostapp.com/driverdata.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerconfirmation);
        textView = findViewById(R.id.text14);
        textView1 = findViewById(R.id.text16);
        textView2 = findViewById(R.id.text18);
        demail = getIntent().getStringExtra("demail");
        Submit_Review = findViewById(R.id.subrev);


        new FetchDataTask().execute(apiurl);

        Submit_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Customerconfirmation.this,Driver_rating.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("demail",demail);
                startActivity(intent);
            }
        });

        Button P_btn = findViewById(R.id.driver_profile_button);
        P_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profile = new Intent(Customerconfirmation.this, Driver_profile.class);
                profile.putExtra("demail",demail);
                startActivity(profile);
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
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    String phoneNumber = jsonObject.getString("phone_number");
                    user_id = jsonObject.getString("user_id");



                    textView.setText(name);
                    textView1.setText(email);
                    textView2.setText(phoneNumber);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(Customerconfirmation.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
            }

        }


    }
}
